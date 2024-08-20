const API_URL = "http://localhost:8080/api/posts";
const API_URL_C = "http://localhost:8080/api/comments";

const getToken = () => localStorage.getItem('jwt');

const getAuthHeaders = () => ({
  "Authorization": `Bearer ${getToken()}`,
});

// Fetch all posts
export async function fetchPosts() {
  try {
    const response = await fetch(API_URL, {
      headers: getAuthHeaders(),
    });
    if (!response.ok) {
      const errorDetails = await response.text();
      console.error("Response Error Details:", errorDetails);
      throw new Error("Failed to fetch posts");
    }
    return await response.json();
  } catch (error) {
    console.error("Error fetching posts:", error);
    throw error;
  }
}

// Fetch post by ID
export async function fetchPostById(postId) {
  try {
    const response = await fetch(`${API_URL}/${postId}`, {
      headers: getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error(`Failed to fetch post with ID ${postId}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Error fetching post with ID ${postId}:`, error);
    throw error;
  }
}

// Save a new post
export async function savePost(post) {
  try {
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: {
        "Content-Type": "application/json",
        ...getAuthHeaders(),
      },
      body: JSON.stringify(post),
    });

    if (!response.ok) {
      const errorDetails = await response.text();
      console.error("Response Error Details:", errorDetails);
      throw new Error('Failed to save post');
    }

    const data = await response.json();
    console.log("Post saved successfully:", data);
    return data;
  } catch (error) {
    console.error("Error saving post:", error);
    throw error;
  }
}

// Update an existing post by ID
export async function updatePost(postId, post) {
  try {
    const response = await fetch(`${API_URL}/${postId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        ...getAuthHeaders(),
      },
      body: JSON.stringify(post),
    });
    if (!response.ok) {
      throw new Error(`Failed to update post with ID ${postId}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Error updating post with ID ${postId}:`, error);
    throw error;
  }
}

// Delete a post by ID
export async function deletePost(postId) {
  try {
    const response = await fetch(`${API_URL}/${postId}`, {
      method: "DELETE",
      headers: getAuthHeaders(),
    });
    if (!response.ok) {
      throw new Error(`Failed to delete post with ID ${postId}`);
    }
  } catch (error) {
    console.error(`Error deleting post with ID ${postId}:`, error);
    throw error;
  }
}

// Fetch comments by post ID
export async function fetchCommentsByPostId(postId) {
  try {
    const response = await fetch(`${API_URL_C}/post/${postId}`, { headers: getAuthHeaders() });
    if (!response.ok) {
      throw new Error("Failed to fetch comments for the post");
    }
    return await response.json();
  } catch (error) {
    console.error(`Error fetching comments for post with id ${postId}:`, error);
    throw error;
  }
}

// Save a new comment
export async function saveComment(comment) {
  try {
    const response = await fetch(API_URL_C, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        ...getAuthHeaders(),
      },
      body: JSON.stringify(comment),
    });
    if (!response.ok) {
      const errorDetails = await response.text();
      console.error("Response Error Details:", errorDetails);
      throw new Error("Failed to save comment");
    }
    return await response.json();
  } catch (error) {
    console.error("Error saving comment:", error);
    throw error;
  }
}

// Update an existing comment by ID
export async function updateComment(commentId, comment) {
  try {
    const response = await fetch(`${API_URL_C}/${commentId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        ...getAuthHeaders(),
      },
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

// Delete a comment by ID
export async function deleteComment(commentId) {
  try {
    const response = await fetch(`${API_URL_C}/${commentId}`, {
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
