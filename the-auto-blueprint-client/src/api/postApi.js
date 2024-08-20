const API_URL = "http://localhost:8080/api/posts";

const getToken = () => localStorage.getItem('jwt');

const getAuthHeaders = () => ({
  "Authorization": `Bearer ${getToken()}`,
  "Content-Type": "application/json",
});

export async function fetchPosts() {
  try {
    const response = await fetch(API_URL, { headers: getAuthHeaders() });
    if (!response.ok) {
      throw new Error("Failed to fetch posts");
    }
    return await response.json();
  } catch (error) {
    console.error("Error fetching posts:", error);
    throw error;
  }
}

export async function fetchPostById(postId) {
  try {
    const response = await fetch(`${API_URL}/${postId}`, { headers: getAuthHeaders() });
    if (!response.ok) {
      throw new Error(`Failed to fetch post with id ${postId}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Error fetching post with id ${postId}:`, error);
    throw error;
  }
}

export async function savePost(post) {
  try {
    const response = await fetch(API_URL, {
      method: "POST",
      headers: getAuthHeaders(),
      body: JSON.stringify(post),
    });
    if (!response.ok) {
      throw new Error("Failed to save post");
    }
    return await response.json();
  } catch (error) {
    console.error("Error saving post:", error);
    throw error;
  }
}

export async function updatePost(postId, post) {
  try {
    const response = await fetch(`${API_URL}/${postId}`, {
      method: "PUT",
      headers: getAuthHeaders(),
      body: JSON.stringify(post),
    });
    if (!response.ok) {
      throw new Error(`Failed to update post with id ${postId}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Error updating post with id ${postId}:`, error);
    throw error;
  }
}

export async function deletePost(postId) {
  try {
    const response = await fetch(`${API_URL}/${postId}`, {
      method: "DELETE",
      headers: getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error(`Failed to delete post with id ${postId}`);
    }
  } catch (error) {
    console.error(`Error deleting post with id ${postId}:`, error);
    throw error;
  }
}
