import React, { useState, useEffect, useContext } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { saveModificationPlan, fetchModificationPlanById, updateModificationPlan } from "../../api/modificationPlanApi";
import { AuthContext } from "../../providers/AuthProvider";

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

  const navigate = useNavigate();
  const { id } = useParams();

  useEffect(() => {
    if (id) {
      fetchModificationPlanById(id)
        .then((planData) => {
          // Populate the form with fetched data
          setModificationPlan({
            ...planData,
            appUserId: principal ? principal.app_user_id : planData.appUserId // Ensure appUserId is set correctly
          });
        })
        .catch((error) => console.error("Error fetching modification plan:", error));
    } else if (principal) {
      // If creating a new plan, set the appUserId from the context
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

  return (
    <form onSubmit={handleSubmit} className="max-w-lg mx-auto p-6">
      <h2 className="text-xl font-semibold mb-4">
        {id ? "Edit Modification Plan" : "Add a Modification Plan"}
      </h2>
      <div className="mb-4">
        <label htmlFor="planName" className="block text-sm font-medium text-gray-700">Plan Name</label>
        <input
          type="text"
          name="planName"
          value={modificationPlan.planName}
          onChange={handleChange}
          required
          className="mt-1 p-2 w-full border rounded"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="planDescription" className="block text-sm font-medium text-gray-700">Plan Description</label>
        <textarea
          name="planDescription"
          value={modificationPlan.planDescription}
          onChange={handleChange}
          required
          className="mt-1 p-2 w-full border rounded"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="carId" className="block text-sm font-medium text-gray-700">Car ID</label>
        <input
          type="number"
          name="carId"
          value={modificationPlan.carId}
          onChange={handleChange}
          required
          className="mt-1 p-2 w-full border rounded"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="budget" className="block text-sm font-medium text-gray-700">Budget</label>
        <input
          type="number"
          name="budget"
          value={modificationPlan.budget}
          onChange={handleChange}
          required
          className="mt-1 p-2 w-full border rounded"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="planHoursOfCompletion" className="block text-sm font-medium text-gray-700">Estimated Hours to Complete</label>
        <input
          type="number"
          name="planHoursOfCompletion"
          value={modificationPlan.planHoursOfCompletion}
          onChange={handleChange}
          required
          className="mt-1 p-2 w-full border rounded"
        />
      </div>
      <button type="submit" className="bg-blue-500 text-white py-2 px-4 rounded">
        {id ? "Update Plan" : "Add Plan"}
      </button>
    </form>
  );
};

export default ModificationPlanForm;
