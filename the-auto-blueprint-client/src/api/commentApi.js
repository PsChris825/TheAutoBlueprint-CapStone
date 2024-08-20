const API_URL = "http://localhost:8080/api/comments";

const getToken = () => localStorage.getItem('jwt');

const getAuthHeaders = () => ({
  "Authorization": `Bearer ${getToken()}`,
  "Content-Type": "application/json",
});

export async function fetchCommentsByPostId(postId) {
  try {
    const response = await fetch(`${API_URL}/post/${postId}`, { headers: getAuthHeaders() });
    if (!response.ok) {
      throw new Error("Failed to fetch comments for the post");
    }
    return await response.json();
  } catch (error) {
    console.error(`Error fetching comments for post with id ${postId}:`, error);
    throw error;
  }
}

export async function saveComment(comment) {
  try {
    const response = await fetch(API_URL, {
      method: "POST",
      headers: getAuthHeaders(),
      body: JSON.stringify(comment),
    });
    if (!response.ok) {
      throw new Error("Failed to save comment");
    }
    return await response.json();
  } catch (error) {
    console.error("Error saving comment:", error);
    throw error;
  }
}

export async function updateComment(commentId, comment) {
  try {
    const response = await fetch(`${API_URL}/${commentId}`, {
      method: "PUT",
      headers: getAuthHeaders(),
      body: JSON.stringify(comment),
    });
    if (!response.ok) {
      throw new Error(`Failed to update comment with id ${commentId}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Error updating comment with id ${commentId}:`, error);
    throw error;
  }
}

export async function deleteComment(commentId) {
  try {
    const response = await fetch(`${API_URL}/${commentId}`, {
      method: "DELETE",
      headers: getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error(`Failed to delete comment with id ${commentId}`);
    }
  } catch (error) {
    console.error(`Error deleting comment with id ${commentId}:`, error);
    throw error;
  }
}
