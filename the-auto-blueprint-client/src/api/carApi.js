const API_URL = "http://localhost:8080/api/car";
const EXTERNAL_API_URL = "http://localhost:8080/api/external-car";

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

export async function fetchCarsByFilter(make, model, year) {
  try {
    const response = await fetch(`${API_URL}/make/${make}/model/${model}/year/${year}`, {
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

export async function fetchOrStoreCarData(make, model, year) {
  try {
    // Step 1: Fetch car data from your backend API
    const backendResponse = await fetch(`${API_URL}/car?make=${make}&model=${model}&year=${year}`, {
      headers: getAuthHeaders()
    });
    
    if (backendResponse.ok) {
      // Data is available in your database
      return await backendResponse.json();
    }

    // Step 2: Data is not available in your database, fetch from third-party API
    const externalResponse = await fetch(`${EXTERNAL_API_URL}/${make}/${model}/${year}`, {
      headers: getAuthHeaders()
    });

    if (!externalResponse.ok) {
      const errorDetails = await externalResponse.text();
      console.error("Response Error Details:", errorDetails);
      throw new Error("Failed to fetch data from third-party API");
    }

    const externalData = await externalResponse.json();

    // Step 3: Store the fetched data in your database
    const storeResponse = await fetch(`${API_URL}/car`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        ...getAuthHeaders()
      },
      body: JSON.stringify(externalData),
    });

    if (!storeResponse.ok) {
      const errorDetails = await storeResponse.text();
      console.error("Response Error Details:", errorDetails);
      throw new Error("Failed to store data in the database");
    }

    // Return the newly stored data
    return await storeResponse.json();
  } catch (error) {
    console.error("Error fetching or storing car data:", error);
    throw error;
  }
}