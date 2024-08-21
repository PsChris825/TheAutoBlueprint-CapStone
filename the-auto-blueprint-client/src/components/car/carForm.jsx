import React, { useState, useEffect } from "react";
import { saveCar, updateCar } from "../../api/carApi";
import { useNavigate } from "react-router-dom";

const CarForm = ({ onSave }) => {
  const [car, setCar] = useState({
    make: "",
    model: "",
    year: "",
    engine: "",
    power: "",
    driveType: "",
    transmissionType: "",
  });
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCar((prevCar) => ({
      ...prevCar,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const savedCar = await saveCar(car);
      setMessage("Car saved successfully!");
      if (onSave) onSave(savedCar); // Call onSave callback if provided
    } catch (error) {
      console.error("Error saving car:", error);
      setMessage("Error saving car. Please try again.");
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ maxWidth: "600px", margin: "0 auto" }}>
      {/* Car details fields */}
      <div>
        <label>Make</label>
        <input
          type="text"
          name="make"
          value={car.make}
          onChange={handleChange}
        />
      </div>
      <div>
        <label>Model</label>
        <input
          type="text"
          name="model"
          value={car.model}
          onChange={handleChange}
        />
      </div>
      <div>
        <label>Year</label>
        <input
          type="text"
          name="year"
          value={car.year}
          onChange={handleChange}
        />
      </div>
      <div>
        <label>Engine</label>
        <input
          type="text"
          name="engine"
          value={car.engine}
          onChange={handleChange}
        />
      </div>
      <div>
        <label>Power</label>
        <input
          type="text"
          name="power"
          value={car.power}
          onChange={handleChange}
        />
      </div>
      <div>
        <label>Drive Type</label>
        <input
          type="text"
          name="driveType"
          value={car.driveType}
          onChange={handleChange}
        />
      </div>
      <div>
        <label>Transmission Type</label>
        <input
          type="text"
          name="transmissionType"
          value={car.transmissionType}
          onChange={handleChange}
        />
      </div>
      <button type="submit">{carId ? "Update" : "Save"}</button>
      {message && <p>{message}</p>}
    </form>
  );
};

export default CarForm;
