import React, { useState, useEffect } from "react";
import { saveCar, updateCar } from "../../api/carApi";

const CarForm = ({ carId }) => {
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

  useEffect(() => {
    const fetchCar = async () => {
      if (carId) {
        try {
          const response = await fetch(`http://localhost:8080/api/car/${carId}`);
          if (!response.ok) {
            throw new Error("Failed to fetch car");
          }
          const carData = await response.json();
          setCar(carData);
        } catch (error) {
          console.error("Error fetching car:", error);
        }
      }
    };

    fetchCar();
  }, [carId]);

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
      if (carId) {
        // Update existing car
        await updateCar(carId, car);
        setMessage("Car updated successfully!");
      } else {
        // Create new car
        await saveCar(car);
        setMessage("Car saved successfully!");
      }
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
