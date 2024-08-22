import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { savePost } from '../../api/postApi';
import { AuthContext } from '../../providers/AuthProvider';

const PostForm = ({ onClose }) => {
  const { principal } = useContext(AuthContext); 
  const [post, setPost] = useState({
    title: '',
    postDescription: '',
    imageUrl: '',
    userId: ''
  });

  const navigate = useNavigate();

  useEffect(() => {
    if (principal) {
      // Set the userId from the context when the component mounts
      setPost((prevPost) => ({
        ...prevPost,
        userId: principal.app_user_id,
      }));
    }
  }, [principal]);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setPost((prevPost) => ({ ...prevPost, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      await savePost(post);
      navigate('/post-list'); // Navigate to the posts list or another page on success
      onClose(); // Close the modal
    } catch (error) {
      if (error.message === 'Failed to save post') {
        alert('You do not have permission to perform this action.');
      } else {
        console.error("Error saving post:", error);
      }
    }
  };

  return (
    <form onSubmit={handleSubmit} className="max-w-lg mx-auto p-6">
      <h2 className="text-xl font-semibold mb-4">Create a New Post</h2>
      <div className="mb-4">
        <label htmlFor="title" className="block text-sm font-medium text-gray-700">Title</label>
        <input
          type="text"
          name="title"
          value={post.title}
          onChange={handleChange}
          required
          className="mt-1 p-2 w-full border rounded"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="postDescription" className="block text-sm font-medium text-gray-700">Description</label>
        <textarea
          name="postDescription"
          value={post.postDescription}
          onChange={handleChange}
          required
          className="mt-1 p-2 w-full border rounded"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="imageUrl" className="block text-sm font-medium text-gray-700">Image URL</label>
        <input
          type="text"
          name="imageUrl"
          value={post.imageUrl}
          onChange={handleChange}
          className="mt-1 p-2 w-full border rounded"
        />
      </div>
      <button type="submit" className="bg-blue-500 text-white py-2 px-4 rounded">
        Save Post
      </button>
      <button type="button" onClick={onClose} className="ml-4 bg-gray-500 text-white py-2 px-4 rounded">
        Cancel
      </button>
    </form>
  );
};

export default PostForm;
