const API_URL = "http://localhost:8080/api/car";

const getToken = () => localStorage.getItem('jwt');

const getAuthHeaders = () => ({
  "Authorization": `Bearer ${getToken()}`
});

export async function fetchCars() {
  try {
    const response = await fetch(API_URL, {
      headers: getAuthHeaders()
    });
    if (!response.ok) {
      const errorDetails = await response.text();
      console.error("Response Error Details:", errorDetails);
      throw new Error("Failed to fetch cars");
    }
    return await response.json();
  } catch (error) {
    console.error("Error fetching cars:", error);
    throw error;
  }
}

export async function fetchCarById(id) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      headers: getAuthHeaders()
    });
    if (!response.ok) {
      throw new Error(`Failed to fetch car with id ${id}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Error fetching car with id ${id}:`, error);
    throw error;
  }
}

// Fetch all makes
export async function fetchMakes() {
  try {
    const response = await fetch(`${API_URL}/makes`, {
      headers: getAuthHeaders()
    });
    if (!response.ok) {
      const errorDetails = await response.text();
      console.error("Response Error Details:", errorDetails);
      throw new Error("Failed to fetch makes");
    }
    return await response.json();
  } catch (error) {
    console.error("Error fetching makes:", error);
    throw error;
  }
}

// Fetch models by make
export async function fetchModelsByMake(make) {
  try {
    const response = await fetch(`${API_URL}/models?make=${make}`, {
      headers: getAuthHeaders()
    });
    if (!response.ok) {
      throw new Error(`Failed to fetch models for make ${make}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Error fetching models for make ${make}:`, error);
    throw error;
  }
}

// Fetch years by make and model
export async function fetchYearsByMakeAndModel(make, model) {
  try {
    const response = await fetch(`${API_URL}/years/${make}/${model}`, {
      headers: getAuthHeaders()
    });
    if (!response.ok) {
      throw new Error(`Failed to fetch years for make ${make} and model ${model}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Error fetching years for make ${make} and model ${model}:`, error);
    throw error;
  }
}

// Fetch cars by year
export async function fetchCarsByYear(year) {
  try {
    const response = await fetch(`${API_URL}/year/${year}`, {
      headers: getAuthHeaders()
    });
    if (!response.ok) {
      throw new Error(`Failed to fetch cars for year ${year}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Error fetching cars for year ${year}:`, error);
    throw error;
  }
}

export async function saveCar(car) {
  try {
    const response = await fetch(API_URL, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        ...getAuthHeaders()
      },
      body: JSON.stringify(car),
    });
    if (!response.ok) {
      throw new Error("Failed to save car");
    }
    return await response.json();
  } catch (error) {
    console.error("Error saving car:", error);
    throw error;
  }
}

export async function updateCar(id, car) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        ...getAuthHeaders()
      },
      body: JSON.stringify(car),
    });
    if (!response.ok) {
      throw new Error(`Failed to update car with id ${id}`);
    }
  } catch (error) {
    console.error(`Error updating car with id ${id}:`, error);
    throw error;
  }
}

export async function deleteCar(id) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "DELETE",
      headers: getAuthHeaders()
    });
    if (!response.ok) {
      throw new Error(`Failed to delete car with id ${id}`);
    }
  } catch (error) {
    console.error(`Error deleting car with id ${id}:`, error);
    throw error;
  }
}
