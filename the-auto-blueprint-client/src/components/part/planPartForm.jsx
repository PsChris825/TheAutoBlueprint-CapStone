import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { fetchPlanPartById, savePlanPart, updatePlanPart } from '../../api/planPartApi';
import { fetchPartsByCategoryId, fetchPartCategories } from '../../api/partCategoryApi';
import Modal from '../modal/modal';
import PartForm from '../part/partForm';

const PlanPartForm = () => {
  const navigate = useNavigate();
  const { planId, carId, planPartId } = useParams();
  const [planPart, setPlanPart] = useState({
    planPartId: null,
    planId: planId || '',
    partId: '',
    price: '',
    timeToInstall: '',
    tutorialUrl: '',
    supplierUrl: '',
  });
  const [categories, setCategories] = useState([]);
  const [parts, setParts] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('');
  const [showPartFormModal, setShowPartFormModal] = useState(false);
  const [successMessage, setSuccessMessage] = useState('');

  useEffect(() => {
    const loadCategories = async () => {
      try {
        const data = await fetchPartCategories();
        setCategories(data);
      } catch (error) {
        console.error('Failed to fetch part categories:', error);
      }
    };
    loadCategories();
  }, []);

  useEffect(() => {
    const loadParts = async () => {
      if (selectedCategory) {
        try {
          const data = await fetchPartsByCategoryId(selectedCategory);
          setParts(data);
        } catch (error) {
          console.error('Failed to fetch parts:', error);
        }
      }
    };
    loadParts();
  }, [selectedCategory]);

  useEffect(() => {
    if (planPartId) {
      const loadPlanPart = async () => {
        try {
          const data = await fetchPlanPartById(planPartId);
          setPlanPart(data);
        } catch (error) {
          console.error('Failed to fetch plan part:', error);
        }
      };
      loadPlanPart();
    }
  }, [planPartId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPlanPart(prevPart => ({ ...prevPart, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (planPart.planPartId) {
        await updatePlanPart(planPart.planPartId, planPart);
        setSuccessMessage('Plan part successfully updated!');
      } else {
        await savePlanPart(planPart);
        setSuccessMessage('Plan part successfully saved!');
      }
      setTimeout(() => navigate(`/modification-plan/${planId}/details`), 500);
    } catch (error) {
      console.error('Failed to save/update plan part:', error);
    }
  };

  const handleCategoryChange = (e) => {
    setSelectedCategory(e.target.value);
  };

  const handlePartSelect = (partId) => {
    setPlanPart(prevPart => ({ ...prevPart, partId }));
  };

  const handleNewPart = () => {
    setShowPartFormModal(true);
  };

  const handlePartFormSave = (newPart) => {
    setPlanPart(prevPart => ({ ...prevPart, partId: newPart.partId }));
    setSuccessMessage('New part successfully added!');
    setShowPartFormModal(false);
  };

  return (
    <div className="max-w-4xl mx-auto p-6 bg-gray-100 rounded-lg shadow-md">
      <h1 className="text-2xl font-bold text-[#5894CD] mb-6">
        {planPartId ? 'Edit Plan Part' : 'Add New Plan Part'}
      </h1>
      {successMessage && <p className="text-green-500 mb-4">{successMessage}</p>}
      <form onSubmit={handleSubmit} className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label className="block text-gray-700 font-semibold">Category:</label>
          <select
            value={selectedCategory}
            onChange={handleCategoryChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          >
            <option value="">Select a category</option>
            {categories.map(cat => (
              <option key={cat.categoryId} value={cat.categoryId}>
                {cat.categoryName}
              </option>
            ))}
          </select>
        </div>
        <div>
          <label className="block text-gray-700 font-semibold">Part:</label>
          <select
            value={planPart.partId}
            onChange={(e) => handlePartSelect(e.target.value)}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          >
            <option value="">Select a part</option>
            {parts.map(part => (
              <option key={part.partId} value={part.partId}>
                {part.partName}
              </option>
            ))}
          </select>
          <button
            type="button"
            onClick={handleNewPart}
            className="mt-2 text-[#5894CD] hover:text-blue-700 transition duration-200"
          >
            Add New Part
          </button>
        </div>
        <div>
          <label className="block text-gray-700 font-semibold">Price:</label>
          <input
            type="number"
            step="0.01"
            name="price"
            value={planPart.price}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          />
        </div>
        <div>
          <label className="block text-gray-700 font-semibold">Time to Install (hours):</label>
          <input
            type="number"
            step="0.1"
            name="timeToInstall"
            value={planPart.timeToInstall}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          />
        </div>
        <div className="md:col-span-2">
          <label className="block text-gray-700 font-semibold">Tutorial URL:</label>
          <input
            type="text"
            name="tutorialUrl"
            value={planPart.tutorialUrl}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          />
        </div>
        <div className="md:col-span-2">
          <label className="block text-gray-700 font-semibold">Supplier URL:</label>
          <input
            type="text"
            name="supplierUrl"
            value={planPart.supplierUrl}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          />
        </div>
        <div className="md:col-span-2 flex justify-end space-x-4 mt-6">
          <button
            type="submit"
            className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition duration-200"
          >
            Save
          </button>
          <button
            type="button"
            onClick={() => navigate(`/modification-plan/${planId}/details`)}
            className="px-4 py-2 bg-gray-500 text-white rounded hover:bg-gray-600 transition duration-200"
          >
            Cancel
          </button>
        </div>
      </form>

      <Modal isOpen={showPartFormModal} onClose={() => setShowPartFormModal(false)}>
        <PartForm 
          onClose={() => setShowPartFormModal(false)} 
          carId={carId}
          categories={categories}
          onSave={handlePartFormSave}
        />
      </Modal>
    </div>
  );
};

export default PlanPartForm;
