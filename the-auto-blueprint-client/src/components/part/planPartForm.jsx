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
    tutorialUrl: '',
    supplierUrl: '',
    timeToInstall: ''
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
        // Update existing plan part
        await updatePlanPart(planPart.planPartId, planPart);
        setSuccessMessage('Plan part successfully updated!');
      } else {
        // Create new plan part
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
    <div>
      <h1>{planPartId ? 'Edit Plan Part' : 'Add New Plan Part'}</h1>
      {successMessage && <p className="text-green-500">{successMessage}</p>}
      <form onSubmit={handleSubmit}>
        <label>
          Plan ID:
          <input
            type="number"
            name="planId"
            value={planPart.planId}
            readOnly
          />
        </label>
        <label>
          Category:
          <select value={selectedCategory} onChange={handleCategoryChange}>
            <option value="">Select a category</option>
            {categories.map(cat => (
              <option key={cat.categoryId} value={cat.categoryId}>
                {cat.categoryName}
              </option>
            ))}
          </select>
        </label>
        <label>
          Part:
          <select value={planPart.partId} onChange={(e) => handlePartSelect(e.target.value)}>
            <option value="">Select a part</option>
            {parts.map(part => (
              <option key={part.partId} value={part.partId}>
                {part.partName}
              </option>
            ))}
          </select>
          <button type="button" onClick={handleNewPart}>
            Add New Part
          </button>
        </label>
        <label>
          Price:
          <input
            type="number"
            step="0.01"
            name="price"
            value={planPart.price}
            onChange={handleChange}
          />
        </label>
        <label>
          Tutorial URL:
          <input
            type="text"
            name="tutorialUrl"
            value={planPart.tutorialUrl}
            onChange={handleChange}
          />
        </label>
        <label>
          Supplier URL:
          <input
            type="text"
            name="supplierUrl"
            value={planPart.supplierUrl}
            onChange={handleChange}
          />
        </label>
        <label>
          Time to Install (minutes):
          <input
            type="number"
            name="timeToInstall"
            value={planPart.timeToInstall}
            onChange={handleChange}
          />
        </label>
        <button type="submit">Save</button>
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
