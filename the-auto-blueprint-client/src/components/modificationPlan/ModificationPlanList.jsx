import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { fetchCars, deleteCar } from "../../api/carApi";

const CarList = () => {
  const [cars, setCars] = useState([]);

  useEffect(() => {
    fetchCars()
      .then((carList) => setCars(carList))
      .catch((error) => console.error("Error fetching cars:", error));
  }, []);

  const handleDelete = (id) => {
    if (window.confirm("Are you sure you want to delete this car?")) {
      deleteCar(id)
        .then(() => setCars(cars.filter((car) => car.carId !== id)))
        .catch((error) => console.error("Error deleting car:", error));
    }
  };

  return (
    <div className="max-w-3xl mx-auto p-6">
      <h2 className="text-xl font-semibold mb-4">Car List</h2>
      <ul>
        {cars.map((car) => (
          <li key={car.carId} className="mb-2 p-4 border rounded flex justify-between items-center">
            <div>
              <strong>{car.make} {car.model} ({car.year})</strong>
              <p>{car.engine} - {car.power} - {car.driveType} - {car.transmissionType}</p>
            </div>
            <div>
              <Link to={`/car-form/${car.carId}`} className="text-blue-500 mr-4">Edit</Link>
              <button onClick={() => handleDelete(car.carId)} className="text-red-500">Delete</button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CarList;
