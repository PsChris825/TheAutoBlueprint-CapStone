import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { savePlanPart } from '../../api/planPartApi';

const PlanPartForm = () => {
  const navigate = useNavigate();
  const [planPart, setPlanPart] = useState({
    planPartId: null,
    planId: '',
    partId: '',
    price: '',
    tutorialUrl: '',
    supplierUrl: '',
    timeToInstall: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPlanPart(prevPart => ({ ...prevPart, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await savePlanPart(planPart);
      navigate('/plan-parts');
    } catch (error) {
      console.error('Failed to save plan part:', error);
    }
  };

  return (
    <div>
      <h1>Add New Plan Part</h1>
      <form onSubmit={handleSubmit}>
        <label>
          Plan ID:
          <input
            type="number"
            name="planId"
            value={planPart.planId}
            onChange={handleChange}
          />
        </label>
        <label>
          Part ID:
          <input
            type="number"
            name="partId"
            value={planPart.partId}
            onChange={handleChange}
          />
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
    </div>
  );
};

export default PlanPartForm;