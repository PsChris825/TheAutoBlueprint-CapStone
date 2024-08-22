import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { fetchPartById, savePart, updatePart } from '../../api/partApi';

const PartForm = ({ onClose, carId, categories }) => {
  const { id } = useParams();
  const [part, setPart] = useState({
    partName: '',
    partNumber: '',
    manufacturer: '',
    OEMNumber: '',
    weight: '',
    details: '',
    categoryId: '',
    carId: carId || ''
  });

  useEffect(() => {
    if (id && id !== 'new') {
      const loadPart = async () => {
        try {
          const data = await fetchPartById(id);
          setPart(prevPart => ({
            ...prevPart,
            ...data,
            carId: carId || data.carId
          }));
        } catch (error) {
          console.error(`Failed to fetch part with id ${id}:`, error);
        }
      };

      loadPart();
    } else {
      setPart(prevPart => ({ ...prevPart, carId }));
    }
  }, [id, carId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPart(prevPart => ({ ...prevPart, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (id && id !== 'new') {
        await updatePart(id, part);
      } else {
        await savePart(part);
      }
      onClose(); // Close the modal after saving
    } catch (error) {
      console.error('Failed to save part:', error);
    }
  };

  return (
    <div>
      <h1>{id && id !== 'new' ? 'Edit Part' : 'Add Part'}</h1>
      <form onSubmit={handleSubmit}>
        <label>
          Part Name:
          <input
            type="text"
            name="partName"
            value={part.partName}
            onChange={handleChange}
          />
        </label>
        <label>
          Part Number:
          <input
            type="text"
            name="partNumber"
            value={part.partNumber}
            onChange={handleChange}
          />
        </label>
        <label>
          Manufacturer:
          <input
            type="text"
            name="manufacturer"
            value={part.manufacturer}
            onChange={handleChange}
          />
        </label>
        <label>
          OEM Number:
          <input
            type="text"
            name="OEMNumber"
            value={part.OEMNumber}
            onChange={handleChange}
          />
        </label>
        <label>
          Weight:
          <input
            type="number"
            step="0.01"
            name="weight"
            value={part.weight}
            onChange={handleChange}
          />
        </label>
        <label>
          Details:
          <textarea
            name="details"
            value={part.details}
            onChange={handleChange}
          />
        </label>
        <label>
          Category:
          <select
            name="categoryId"
            value={part.categoryId}
            onChange={handleChange}
          >
            <option value="">Select a category</option>
            {categories.map(category => (
              <option key={category.categoryId} value={category.categoryId}>
                {category.categoryName}
              </option>
            ))}
          </select>
        </label>
        <input
          type="hidden"
          name="carId"
          value={part.carId}
        />
        <button type="submit">Save</button>
        <button type="button" onClick={onClose}>Cancel</button>
      </form>
    </div>
  );
};

export default PartForm;
