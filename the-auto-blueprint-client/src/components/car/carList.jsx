import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { fetchCarsByFilter, fetchMakes, fetchModelsByMake, fetchYearsByMakeAndModel } from "../../api/carApi";

const CarList = () => {
  const [cars, setCars] = useState([]);
  const [makes, setMakes] = useState([]);
  const [models, setModels] = useState([]);
  const [years, setYears] = useState([]);
  const [selectedMake, setSelectedMake] = useState("");
  const [selectedModel, setSelectedModel] = useState("");
  const [selectedYear, setSelectedYear] = useState("");

  useEffect(() => {
    const loadMakes = async () => {
      try {
        const data = await fetchMakes();
        setMakes(data);
      } catch (error) {
        console.error("Error fetching makes:", error);
      }
    };

    loadMakes();
  }, []);

  useEffect(() => {
    if (selectedMake) {
      const loadModels = async () => {
        try {
          const data = await fetchModelsByMake(selectedMake);
          setModels(data);
        } catch (error) {
          console.error("Error fetching models:", error);
        }
      };

      loadModels();
    } else {
      setModels([]);
      setSelectedModel("");
    }
  }, [selectedMake]);

  useEffect(() => {
    if (selectedMake && selectedModel) {
      const loadYears = async () => {
        try {
          const data = await fetchYearsByMakeAndModel(selectedMake, selectedModel);
          setYears(data);
        } catch (error) {
          console.error("Error fetching years:", error);
        }
      };

      loadYears();
    } else {
      setYears([]);
      setSelectedYear("");
    }
  }, [selectedMake, selectedModel]);

  useEffect(() => {
    const loadCars = async () => {
      try {
        console.log("Fetching cars with filter:", { selectedMake, selectedModel, selectedYear });
        const data = await fetchCarsByFilter(selectedMake, selectedModel, selectedYear);
        console.log("Fetched cars:", data);
        setCars(data);
      } catch (error) {
        console.error("Error fetching cars:", error);
      }
    };

    // Only load cars if at least make, model, and year are selected
    if (selectedMake && selectedModel && selectedYear) {
      loadCars();
    } else {
      setCars([]);
    }
  }, [selectedMake, selectedModel, selectedYear]);

  return (
    <div>
      <h2>Car List</h2>
      <div>
        <label>
          Make:
          <select value={selectedMake} onChange={(e) => setSelectedMake(e.target.value)}>
            <option value="">Select Make</option>
            {makes.map((make) => (
              <option key={make.make} value={make.make}>{make.make}</option>
            ))}
          </select>
        </label>
        <label>
          Model:
          <select value={selectedModel} onChange={(e) => setSelectedModel(e.target.value)}>
            <option value="">Select Model</option>
            {models.map((model) => (
              <option key={model.model} value={model.model}>{model.model}</option>
            ))}
          </select>
        </label>
        <label>
          Year:
          <select value={selectedYear} onChange={(e) => setSelectedYear(e.target.value)}>
            <option value="">Select Year</option>
            {years.map((year) => (
              <option key={year} value={year}>{year}</option>
            ))}
          </select>
        </label>
      </div>
      <div className="my-4">
        <Link to="/car-form" className="bg-blue-500 text-white py-2 px-4 rounded">
          Add Car
        </Link>
      </div>
      <ul>
        {cars.map((car) => (
          <li key={car.carId}>
            {car.make} {car.model} ({car.year}) - {car.engine}, {car.power} HP, {car.driveType}, {car.transmissionType}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CarList;
