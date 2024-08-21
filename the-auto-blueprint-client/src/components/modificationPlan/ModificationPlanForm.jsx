import React, { useState, useEffect, useContext } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { saveModificationPlan, fetchModificationPlanById, updateModificationPlan } from "../../api/modificationPlanApi";
import { AuthContext } from "../../providers/AuthProvider";
import CarForm from "../car/CarForm"; // Import CarForm

const ModificationPlanForm = () => {
  const { principal } = useContext(AuthContext); 
  const [modificationPlan, setModificationPlan] = useState({
    planName: "",
    planDescription: "",
    carId: "",
    appUserId: "", 
    budget: "",
    planHoursOfCompletion: "",
  });
  const [showCarForm, setShowCarForm] = useState(false); // State to control CarForm visibility

  const navigate = useNavigate();
  const { id } = useParams();

  useEffect(() => {
    if (id) {
      fetchModificationPlanById(id)
        .then((planData) => {
          setModificationPlan({
            ...planData,
            appUserId: principal ? principal.app_user_id : planData.appUserId // Ensure appUserId is set correctly
          });
        })
        .catch((error) => console.error("Error fetching modification plan:", error));
    } else if (principal) {
      setModificationPlan((prevPlan) => ({
        ...prevPlan,
        appUserId: principal.app_user_id,
      }));
    }
  }, [id, principal]);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setModificationPlan((prevPlan) => ({ ...prevPlan, [name]: value }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    const saveOrUpdatePlan = id 
      ? updateModificationPlan(id, modificationPlan) 
      : saveModificationPlan(modificationPlan);

    saveOrUpdatePlan
      .then(() => navigate("/modification-list"))
      .catch((error) => console.error("Error saving modification plan:", error));
  };

  const handleCarSave = (savedCar) => {
    setModificationPlan((prevPlan) => ({
      ...prevPlan,
      carId: savedCar.carId // Update the modification plan with the new car ID
    }));
    setShowCarForm(false); // Hide CarForm after saving
  };

  return (
    <div>
      <form onSubmit={handleSubmit} className="max-w-lg mx-auto p-6">
        <h2 className="text-xl font-semibold mb-4">
          {id ? "Edit Modification Plan" : "Add a Modification Plan"}
        </h2>
        <div className="mb-4">
          <label htmlFor="planName" className="block text-sm font-medium text-gray-700">Plan Name</label>
          <input
            type="text"
            id="planName"
            name="planName"
            value={modificationPlan.planName}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm"
            required
          />
        </div>
        <div className="mb-4">
          <label htmlFor="planDescription" className="block text-sm font-medium text-gray-700">Plan Description</label>
          <textarea
            id="planDescription"
            name="planDescription"
            value={modificationPlan.planDescription}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm"
            required
          />
        </div>
        <div className="mb-4">
          <label htmlFor="carId" className="block text-sm font-medium text-gray-700">Car</label>
          <input
            type="text"
            id="carId"
            name="carId"
            value={modificationPlan.carId}
            readOnly
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm"
          />
          <button
            type="button"
            onClick={() => setShowCarForm(!showCarForm)}
            className="mt-2 bg-blue-500 text-white py-2 px-4 rounded"
          >
            {showCarForm ? "Cancel Add Car" : "Add Car"}
          </button>
        </div>
        {showCarForm && <CarForm onSave={handleCarSave} />} {/* Conditionally render CarForm */}
        <div className="mb-4">
          <label htmlFor="budget" className="block text-sm font-medium text-gray-700">Budget</label>
          <input
            type="number"
            id="budget"
            name="budget"
            value={modificationPlan.budget}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm"
            required
          />
        </div>
        <div className="mb-4">
          <label htmlFor="planHoursOfCompletion" className="block text-sm font-medium text-gray-700">Hours of Completion</label>
          <input
            type="number"
            id="planHoursOfCompletion"
            name="planHoursOfCompletion"
            value={modificationPlan.planHoursOfCompletion}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm"
            required
          />
        </div>
        <button
          type="submit"
          className="bg-blue-500 text-white py-2 px-4 rounded"
        >
          {id ? "Update Plan" : "Save Plan"}
        </button>
      </form>
    </div>
  );
};

export default ModificationPlanForm;
