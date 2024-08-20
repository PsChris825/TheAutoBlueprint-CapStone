import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { saveCar, fetchCarById, updateCar } from "../../api/carApi";

const CarForm = () => {
  const [car, setCar] = useState({
    make: "",
    model: "",
    year: "",
    engine: "",
    power: "",
    driveType: "",
    transmissionType: "",
  });

  const navigate = useNavigate();
  const { id } = useParams();

  useEffect(() => {
    if (id) {
      fetchCarById(id)
        .then((carData) => setCar(carData))
        .catch((error) => console.error("Error fetching car:", error));
    }
  }, [id]);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setCar((prevCar) => ({ ...prevCar, [name]: value }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    const saveOrUpdateCar = id ? updateCar(id, car) : saveCar(car);

    saveOrUpdateCar
      .then(() => navigate("/car-list"))
      .catch((error) => console.error("Error saving car:", error));
  };

  return (
    <form onSubmit={handleSubmit} className="max-w-lg mx-auto p-6">
      <h2 className="text-xl font-semibold mb-4">{id ? "Edit Car" : "Add a Car"}</h2>
      <div className="mb-4">
        <label htmlFor="make" className="block text-sm font-medium text-gray-700">Make</label>
        <input
          type="text"
          name="make"
          value={car.make}
          onChange={handleChange}
          required
          className="mt-1 p-2 w-full border rounded"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="model" className="block text-sm font-medium text-gray-700">Model</label>
        <input
          type="text"
          name="model"
          value={car.model}
          onChange={handleChange}
          required
          className="mt-1 p-2 w-full border rounded"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="year" className="block text-sm font-medium text-gray-700">Year</label>
        <input
          type="number"
          name="year"
          value={car.year}
          onChange={handleChange}
          required
          className="mt-1 p-2 w-full border rounded"
        />
      </div>
      {/* Add other car fields (engine, power, etc.) similar to the above */}
      <button type="submit" className="bg-blue-500 text-white py-2 px-4 rounded">
        {id ? "Update Car" : "Add Car"}
      </button>
    </form>
  );
};

export default CarForm;
