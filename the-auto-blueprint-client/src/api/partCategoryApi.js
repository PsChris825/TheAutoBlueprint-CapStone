const API_URL = "http://localhost:8080/api/part-category";

const getToken = () => localStorage.getItem('jwt');

const getAuthHeaders = () => ({
  "Authorization": `Bearer ${getToken()}`
});

export async function fetchPartCategories() {
  try {
    const response = await fetch(API_URL, {
      headers: getAuthHeaders()
    });
    if (!response.ok) {
      throw new Error("Failed to fetch categories");
    }
    return await response.json();
  } catch (error) {
    console.error("Error fetching part categories:", error);
    throw error;
  }
}

export async function fetchPartsByCategoryId(categoryId) {
  try {
    const response = await fetch(`http://localhost:8080/api/parts/category/${categoryId}`, {
      headers: getAuthHeaders()
    });
    if (!response.ok) {
      throw new Error(`Failed to fetch parts for category ${categoryId}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Error fetching parts for category ${categoryId}:`, error);
    throw error;
  }
}
