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
    userId: '',
    username: '' 
  });

  const navigate = useNavigate();

  useEffect(() => {
    if (principal) {
      setPost((prevPost) => ({
        ...prevPost,
        userId: principal.app_user_id,
        username: principal.sub,
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
      navigate('/post-list');
      onClose();
    } catch (error) {
      if (error.message === 'Failed to save post') {
        alert('You do not have permission to perform this action.');
      } else {
        console.error("Error saving post:", error);
      }
    }
  };

  return (
    <form onSubmit={handleSubmit} className="w-full max-w-5xl mx-auto p-6">
      <h2 className="text-2xl font-semibold mb-6">Create a New Post</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-4">
        <div>
          <label htmlFor="title" className="block text-sm font-medium text-gray-700">Title</label>
          <input
            type="text"
            name="title"
            value={post.title}
            onChange={handleChange}
            required
            className="mt-1 p-3 w-full border rounded"
          />
        </div>
        <div>
          <label htmlFor="imageUrl" className="block text-sm font-medium text-gray-700">Image URL</label>
          <input
            type="text"
            name="imageUrl"
            value={post.imageUrl}
            onChange={handleChange}
            className="mt-1 p-3 w-full border rounded"
          />
        </div>
      </div>
      <div className="mb-6">
        <label htmlFor="postDescription" className="block text-sm font-medium text-gray-700">Description</label>
        <textarea
          name="postDescription"
          value={post.postDescription}
          onChange={handleChange}
          required
          className="mt-1 p-3 w-full border rounded h-48 resize-none"
        />
      </div>
      <div className="flex justify-end space-x-4">
        <button type="submit" className="bg-blue-600 text-white py-3 px-6 rounded-md hover:bg-blue-700 transition duration-200">
          Save Post
        </button>
        <button type="button" onClick={onClose} className="bg-gray-600 text-white py-3 px-6 rounded-md hover:bg-gray-700 transition duration-200">
          Cancel
        </button>
      </div>
    </form>
  );
};

export default PostForm;
