const API_URL = "http://localhost:8080/api/plan-part";

const getToken = () => localStorage.getItem('jwt');

const getAuthHeaders = () => ({
  "Authorization": `Bearer ${getToken()}`,
  "Content-Type": "application/json",
});

export async function fetchPlanParts() {
  try {
    const response = await fetch(API_URL, { headers: getAuthHeaders() });
    if (!response.ok) {
      throw new Error("Failed to fetch plan parts");
    }
    return await response.json();
  } catch (error) {
    console.error("Error fetching plan parts:", error);
    throw error;
  }
}

export async function fetchPlanPartById(id) {
  try {
    const response = await fetch(`${API_URL}/${id}`, { headers: getAuthHeaders() });
    if (!response.ok) {
      throw new Error(`Failed to fetch plan part with id ${id}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Error fetching plan part with id ${id}:`, error);
    throw error;
  }
}

export async function savePlanPart(planPart) {
  try {
    const response = await fetch(API_URL, {
      method: "POST",
      headers: getAuthHeaders(),
      body: JSON.stringify(planPart),
    });
    if (!response.ok) {
      throw new Error("Failed to save plan part");
    }
    return await response.json();
  } catch (error) {
    console.error("Error saving plan part:", error);
    throw error;
  }
}

export async function updatePlanPart(id, planPart) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "PUT",
      headers: getAuthHeaders(),
      body: JSON.stringify(planPart),
    });
    if (!response.ok) {
      throw new Error(`Failed to update plan part with id ${id}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Error updating plan part with id ${id}:`, error);
    throw error;
  }
}

export async function deletePlanPart(id) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "DELETE",
      headers: getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error(`Failed to delete plan part with id ${id}`);
    }
  } catch (error) {
    console.error(`Error deleting plan part with id ${id}:`, error);
    throw error;
  }
}
