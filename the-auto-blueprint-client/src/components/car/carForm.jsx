import React, { useState } from "react";
import { saveCar } from "../../api/carApi";

const CarForm = ({ onSave }) => {
  const [car, setCar] = useState({
    make: "",
    model: "",
    year: "",
    engine: "",
    power: "",
    driveType: "",
    transmissionType: ""
  });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setCar((prevCar) => ({ ...prevCar, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault(); // Prevent page refresh

    try {
      const savedCar = await saveCar(car);
      onSave(savedCar); // Pass the saved car ID to the parent component
    } catch (error) {
      console.error("Error saving car:", error);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="max-w-xl mx-auto p-6 bg-gray-100 rounded-lg shadow-md">
      <h3 className="text-2xl font-bold text-[#5894CD] mb-6">Add a New Car</h3>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label htmlFor="make" className="block text-gray-700 font-semibold">Make:</label>
          <input
            type="text"
            id="make"
            name="make"
            value={car.make}
            onChange={handleChange}
            required
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          />
        </div>
        <div>
          <label htmlFor="model" className="block text-gray-700 font-semibold">Model:</label>
          <input
            type="text"
            id="model"
            name="model"
            value={car.model}
            onChange={handleChange}
            required
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          />
        </div>
        <div>
          <label htmlFor="year" className="block text-gray-700 font-semibold">Year:</label>
          <input
            type="text"
            id="year"
            name="year"
            value={car.year}
            onChange={handleChange}
            required
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          />
        </div>
        <div>
          <label htmlFor="engine" className="block text-gray-700 font-semibold">Engine:</label>
          <input
            type="text"
            id="engine"
            name="engine"
            value={car.engine}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          />
        </div>
        <div>
          <label htmlFor="power" className="block text-gray-700 font-semibold">Power:</label>
          <input
            type="text"
            id="power"
            name="power"
            value={car.power}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          />
        </div>
        <div>
          <label htmlFor="driveType" className="block text-gray-700 font-semibold">Drive Type:</label>
          <input
            type="text"
            id="driveType"
            name="driveType"
            value={car.driveType}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          />
        </div>
        <div>
          <label htmlFor="transmissionType" className="block text-gray-700 font-semibold">Transmission Type:</label>
          <input
            type="text"
            id="transmissionType"
            name="transmissionType"
            value={car.transmissionType}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-[#5894CD] focus:border-[#5894CD]"
          />
        </div>
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
          Add Car
        </button>
      </div>
    </form>
  );
};

export default CarForm;
