import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { fetchPartById, savePart, updatePart } from '../../api/partApi';

const PartForm = () => {
  const { id, categoryId } = useParams();
  const navigate = useNavigate();
  const [part, setPart] = useState({
    partName: '',
    partNumber: '',
    manufacturer: '',
    OEMNumber: '',
    weight: '',
    details: '',
    categoryId,
    carId: ''
  });

  useEffect(() => {
    if (id && id !== 'new') {
      const loadPart = async () => {
        try {
          const data = await fetchPartById(id);
          setPart(data);
        } catch (error) {
          console.error(`Failed to fetch part with id ${id}:`, error);
        }
      };

      loadPart();
    }
  }, [id]);

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
      navigate(`/part-category/${categoryId}/parts`);
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
            type="text"
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
          Category ID:
          <input
            type="text"
            name="categoryId"
            value={part.categoryId}
            readOnly // Prevent users from changing the category
          />
        </label>
        <label>
          Car ID:
          <input
            type="text"
            name="carId"
            value={part.carId}
            onChange={handleChange}
          />
        </label>
        <button type="submit">Save</button>
      </form>
    </div>
  );
};

export default PartForm;
