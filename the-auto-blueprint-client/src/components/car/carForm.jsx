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
    <form onSubmit={handleSubmit} className="car-form">
      <h3>Add a New Car</h3>
      <div>
        <label htmlFor="make">Make:</label>
        <input
          type="text"
          id="make"
          name="make"
          value={car.make}
          onChange={handleChange}
          required
        />
      </div>
      <div>
        <label htmlFor="model">Model:</label>
        <input
          type="text"
          id="model"
          name="model"
          value={car.model}
          onChange={handleChange}
          required
        />
      </div>
      <div>
        <label htmlFor="year">Year:</label>
        <input
          type="text"
          id="year"
          name="year"
          value={car.year}
          onChange={handleChange}
          required
        />
      </div>
      <div>
        <label htmlFor="engine">Engine:</label>
        <input
          type="text"
          id="engine"
          name="engine"
          value={car.engine}
          onChange={handleChange}
        />
      </div>
      <div>
        <label htmlFor="power">Power:</label>
        <input
          type="text"
          id="power"
          name="power"
          value={car.power}
          onChange={handleChange}
        />
      </div>
      <div>
        <label htmlFor="driveType">Drive Type:</label>
        <input
          type="text"
          id="driveType"
          name="driveType"
          value={car.driveType}
          onChange={handleChange}
        />
      </div>
      <div>
        <label htmlFor="transmissionType">Transmission Type:</label>
        <input
          type="text"
          id="transmissionType"
          name="transmissionType"
          value={car.transmissionType}
          onChange={handleChange}
        />
      </div>
      <button type="submit">Add Car</button>
    </form>
  );
};

export default CarForm;
