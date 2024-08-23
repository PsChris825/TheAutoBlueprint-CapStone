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
    <div className="max-w-4xl mx-auto p-4 bg-gray-100 rounded-lg shadow-md">
      <h1 className="text-2xl font-bold text-[#5894CD] mb-6">
        {id && id !== 'new' ? 'Edit Part' : 'Add Part'}
      </h1>
      <form onSubmit={handleSubmit} className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label className="block text-gray-700 font-semibold">Part Name:</label>
          <input
            type="text"
            name="partName"
            value={part.partName}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          />
        </div>
        <div>
          <label className="block text-gray-700 font-semibold">Part Number:</label>
          <input
            type="text"
            name="partNumber"
            value={part.partNumber}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          />
        </div>
        <div>
          <label className="block text-gray-700 font-semibold">Manufacturer:</label>
          <input
            type="text"
            name="manufacturer"
            value={part.manufacturer}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          />
        </div>
        <div>
          <label className="block text-gray-700 font-semibold">OEM Number:</label>
          <input
            type="text"
            name="OEMNumber"
            value={part.OEMNumber}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          />
        </div>
        <div>
          <label className="block text-gray-700 font-semibold">Weight:</label>
          <input
            type="number"
            step="0.01"
            name="weight"
            value={part.weight}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          />
        </div>
        <div>
          <label className="block text-gray-700 font-semibold">Category:</label>
          <select
            name="categoryId"
            value={part.categoryId}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          >
            <option value="">Select a category</option>
            {categories.map(category => (
              <option key={category.categoryId} value={category.categoryId}>
                {category.categoryName}
              </option>
            ))}
          </select>
        </div>
        <div className="md:col-span-2">
          <label className="block text-gray-700 font-semibold">Details:</label>
          <textarea
            name="details"
            value={part.details}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          />
        </div>
        <input
          type="hidden"
          name="carId"
          value={part.carId}
        />
        <div className="md:col-span-2 flex justify-end space-x-4 mt-6">
          <button
            type="submit"
            className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition duration-200"
          >
            Save
          </button>
          <button
            type="button"
            onClick={onClose}
            className="px-4 py-2 bg-gray-500 text-white rounded hover:bg-gray-600 transition duration-200"
          >
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
};

export default PartForm;
