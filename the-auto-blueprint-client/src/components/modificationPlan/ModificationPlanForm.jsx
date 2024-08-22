import React, { useState, useEffect, useContext } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { saveModificationPlan, fetchModificationPlanById, updateModificationPlan } from "../../api/modificationPlanApi";
import { AuthContext } from "../../providers/AuthProvider";
import CarForm from "../car/carForm";
import Modal from "../modal/modal";
import CarList from "../car/carList";
import { fetchCarById } from "../../api/carApi";

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
  const [selectedCar, setSelectedCar] = useState(null);
  const [showCarFormModal, setShowCarFormModal] = useState(false);
  const [showCarList, setShowCarList] = useState(false);

  const navigate = useNavigate();
  const { id } = useParams(); // Get the planId from the URL

  useEffect(() => {
    if (id) {
      // Fetch and populate form if ID exists (Edit mode)
      fetchModificationPlanById(id)
        .then((planData) => {
          setModificationPlan({
            ...planData,
            appUserId: principal ? principal.app_user_id : planData.appUserId,
          });
          if (planData.carId) {
            fetchCarById(planData.carId).then(carData => setSelectedCar(carData));
          }
        })
        .catch((error) => console.error("Error fetching modification plan:", error));
    } else if (principal) {
      // Initialize form for creating a new plan
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

  const handleSubmit = async (event) => {
    event.preventDefault();
  
    try {
      let response;
      if (id) {
        // Update existing plan
        response = await updateModificationPlan(id, modificationPlan);
      } else {
        // Save new plan
        response = await saveModificationPlan(modificationPlan);
      }
  
      console.log('Response from save or update:', response); // Log full response
  
      // Ensure the response contains the `planId`
      const planId = response.planId; // Access `planId` correctly from the response
      if (planId) {
        navigate(`/modification-plan/${planId}/details`); // Redirect to the details page of the new plan
      } else {
        console.error('Plan ID not found in response:', response);
      }
    } catch (error) {
      console.error("Error saving or updating modification plan:", error);
    }
  };

  const handleCarSave = (savedCar) => {
    setModificationPlan((prevPlan) => ({
      ...prevPlan,
      carId: savedCar.carId,
    }));
    setSelectedCar(savedCar); // Update selected car details
    setShowCarFormModal(false);
  };

  const handleCarSelect = (carId) => {
    fetchCarById(carId).then(carData => {
      setModificationPlan((prevPlan) => ({
        ...prevPlan,
        carId
      }));
      setSelectedCar(carData); // Update selected car details
    });
    setShowCarList(false);
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
          <div className="mt-1">
            {selectedCar ? (
              <p>{selectedCar.make} {selectedCar.model} {selectedCar.year}</p>
            ) : (
              <p>No car selected</p>
            )}
            <button
              type="button"
              onClick={() => setShowCarList(true)}
              className="bg-blue-500 text-white py-2 px-4 rounded mt-2"
            >
              Select Car
            </button>
            <button
              type="button"
              onClick={() => setShowCarFormModal(true)}
              className="bg-green-500 text-white py-2 px-4 rounded mt-2"
            >
              Add Car
            </button>
          </div>
        </div>
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

      <Modal isOpen={showCarFormModal} onClose={() => setShowCarFormModal(false)}>
        <CarForm onSave={handleCarSave} />
      </Modal>

      <Modal isOpen={showCarList} onClose={() => setShowCarList(false)}>
        <CarList onCarSelect={handleCarSelect} />
      </Modal>
    </div>
  );
};

export default ModificationPlanForm;
