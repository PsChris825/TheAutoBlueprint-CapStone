const API_URL = "http://localhost:8080/api/modification-plan";

const getToken = () => localStorage.getItem('jwt');

const getAuthHeaders = () => ({
  "Authorization": `Bearer ${getToken()}`
});

// Fetch all modification plans
export async function fetchModificationPlans() {
  try {
    const response = await fetch(API_URL, {
      headers: getAuthHeaders()
    });
    if (!response.ok) {
      const errorDetails = await response.text();
      console.error("Response Error Details:", errorDetails);
      throw new Error("Failed to fetch modification plans");
    }
    return await response.json();
  } catch (error) {
    console.error("Error fetching modification plans:", error);
    throw error;
  }
}

// Fetch modification plan by ID
export async function fetchModificationPlanById(id) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      headers: getAuthHeaders()
    });
    if (!response.ok) {
      throw new Error(`Failed to fetch modification plan with ID ${id}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Error fetching modification plan with ID ${id}:`, error);
    throw error;
  }
}

// Save a new modification plan
export async function saveModificationPlan(modificationPlan) {
  try {
    const response = await fetch(API_URL, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        ...getAuthHeaders()
      },
      body: JSON.stringify(modificationPlan),
    });
    if (!response.ok) {
      const errorDetails = await response.text();
      console.error("Response Error Details:", errorDetails);
      throw new Error("Failed to save modification plan");
    }
    return await response.json();
  } catch (error) {
    console.error("Error saving modification plan:", error);
    throw error;
  }
}

// Update an existing modification plan by ID
export async function updateModificationPlan(id, modificationPlan) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        ...getAuthHeaders()
      },
      body: JSON.stringify(modificationPlan),
    });

    if (!response.ok) {
      const errorDetails = await response.text();
      console.error("Response Error Details:", errorDetails);
      throw new Error(`Failed to update modification plan with ID ${id}`);
    }

    // Handle empty response body
    const responseBody = await response.text();
    return responseBody ? JSON.parse(responseBody) : {}; // Parse response if not empty

  } catch (error) {
    console.error(`Error updating modification plan with ID ${id}:`, error);
    throw error;
  }
}

// Delete a modification plan by ID
export async function deleteModificationPlan(id) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "DELETE",
      headers: getAuthHeaders()
    });
    if (!response.ok) {
      throw new Error(`Failed to delete modification plan with ID ${id}`);
    }
  } catch (error) {
    console.error(`Error deleting modification plan with ID ${id}:`, error);
    throw error;
  }
}
