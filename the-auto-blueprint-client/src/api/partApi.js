const API_URL = "http://localhost:8080/api/parts";

const getToken = () => localStorage.getItem('jwt');

const getAuthHeaders = () => ({
  "Authorization": `Bearer ${getToken()}`,
  "Content-Type": "application/json",
});

export async function fetchParts() {
  try {
    const response = await fetch(API_URL, { headers: getAuthHeaders() });
    if (!response.ok) {
      throw new Error("Failed to fetch parts");
    }
    return await response.json();
  } catch (error) {
    console.error("Error fetching parts:", error);
    throw error;
  }
}

export async function fetchPartById(id) {
  try {
    const response = await fetch(`${API_URL}/${id}`, { headers: getAuthHeaders() });
    if (!response.ok) {
      throw new Error(`Failed to fetch part with id ${id}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Error fetching part with id ${id}:`, error);
    throw error;
  }
}

export async function fetchPartsByCategoryId(categoryId) {
  try {
    const response = await fetch(`${API_URL}/category/${categoryId}`, { headers: getAuthHeaders() });
    if (!response.ok) {
      throw new Error(`Failed to fetch parts for category with id ${categoryId}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Error fetching parts for category with id ${categoryId}:`, error);
    throw error;
  }
}

export async function savePart(part) {
  try {
    const response = await fetch(API_URL, {
      method: "POST",
      headers: getAuthHeaders(),
      body: JSON.stringify(part),
    });
    if (!response.ok) {
      throw new Error("Failed to save part");
    }
    return await response.json();
  } catch (error) {
    console.error("Error saving part:", error);
    throw error;
  }
}

export async function updatePart(id, part) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "PUT",
      headers: getAuthHeaders(),
      body: JSON.stringify(part),
    });
    if (!response.ok) {
      throw new Error(`Failed to update part with id ${id}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Error updating part with id ${id}:`, error);
    throw error;
  }
}

export async function deletePart(id) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "DELETE",
      headers: getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error(`Failed to delete part with id ${id}`);
    }
  } catch (error) {
    console.error(`Error deleting part with id ${id}:`, error);
    throw error;
  }
}
