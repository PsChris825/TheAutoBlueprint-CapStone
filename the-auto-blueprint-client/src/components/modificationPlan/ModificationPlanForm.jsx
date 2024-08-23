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
  const { id } = useParams(); 

  useEffect(() => {
    if (id) {
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
        response = await updateModificationPlan(id, modificationPlan);
      } else {
        response = await saveModificationPlan(modificationPlan);
      }
  
      console.log('Response from save or update:', response); 
  
      navigate(`/profile`);
    } catch (error) {
      console.error("Error saving or updating modification plan:", error);
    }
  };

  const handleCarSave = (savedCar) => {
    setModificationPlan((prevPlan) => ({
      ...prevPlan,
      carId: savedCar.carId,
    }));
    setSelectedCar(savedCar); 
    setShowCarFormModal(false);
  };

  const handleCarSelect = (carId) => {
    fetchCarById(carId).then(carData => {
      setModificationPlan((prevPlan) => ({
        ...prevPlan,
        carId
      }));
      setSelectedCar(carData); 
    });
    setShowCarList(false);
  };

  return (
    <div style={{ backgroundColor: '#f3f4f6', minHeight: '100vh', padding: '1.5rem' }}>
      <form onSubmit={handleSubmit} className="max-w-lg mx-auto p-6 rounded-lg shadow-lg bg-white">
        <h2 className="text-2xl font-bold text-gray-800 mb-6">
          {id ? "Edit Blueprint" : "Add a Blueprint"}
        </h2>
        <div className="mb-4">
          <label htmlFor="planName" className="block text-sm font-medium text-gray-700">Blueprint Name</label>
          <input
            type="text"
            id="planName"
            name="planName"
            value={modificationPlan.planName}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
            required
          />
        </div>
        <div className="mb-4">
          <label htmlFor="planDescription" className="block text-sm font-medium text-gray-700">Description</label>
          <textarea
            id="planDescription"
            name="planDescription"
            value={modificationPlan.planDescription}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
            required
          />
        </div>
        <div className="mb-4">
          <label htmlFor="carId" className="block text-sm font-medium text-gray-700">Car</label>
          <div className="mt-1">
            {selectedCar ? (
              <p className="text-gray-700">{selectedCar.make} {selectedCar.model} {selectedCar.year}</p>
            ) : (
              <p className="text-gray-700">No car selected</p>
            )}
            <button
              type="button"
              onClick={() => setShowCarList(true)}
              className="bg-blue-600 text-white py-2 px-4 rounded mt-2 mr-2 font-bold hover:bg-blue-700 hover:shadow-lg transition duration-200 ease-in-out"
            >
              Select Car
            </button>
            <button
              type="button"
              onClick={() => setShowCarFormModal(true)}
              className="bg-blue-600 text-white py-2 px-4 rounded mt-2 font-bold hover:bg-blue-700 hover:shadow-lg transition duration-200 ease-in-out"
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
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
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
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
            required
          />
        </div>
        <div className="mt-6 flex justify-center">
          <button
            type="submit"
            className="w-48 py-2 text-white text-lg font-semibold rounded-md transition duration-200"
            style={{
              backgroundColor: '#1E3A8A', // Darker blue
              border: '2px solid #1E3A8A',
            }}
            onMouseOver={(e) => e.currentTarget.style.backgroundColor = '#3B82F6'} // Lighter blue on hover
            onMouseOut={(e) => e.currentTarget.style.backgroundColor = '#1E3A8A'} // Return to original color
          >
            {id ? "Update Plan" : "Save Plan"}
          </button>
        </div>
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
