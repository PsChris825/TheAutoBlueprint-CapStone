import React, { useEffect, useState, useContext } from 'react';
import { fetchPosts, saveComment, fetchCommentsByPostId } from '../../api/postApi';
import Modal from '../modal/modal';
import PostForm from './postForm';
import { AuthContext } from '../../providers/AuthProvider';

const PostList = () => {
  const [posts, setPosts] = useState([]);
  const [newComment, setNewComment] = useState({});
  const [postComments, setPostComments] = useState({});
  const [showPostForm, setShowPostForm] = useState(false);
  const [refresh, setRefresh] = useState(false);
  const { principal } = useContext(AuthContext);

  useEffect(() => {
    const loadPosts = async () => {
      try {
        const data = await fetchPosts();
        if (data) {
          setPosts(data);
    
          const commentsPromises = data.map(post => fetchCommentsByPostId(post.postId));
          const commentsData = await Promise.all(commentsPromises);
    
          const commentsMap = data.reduce((acc, post, index) => {
            acc[post.postId] = commentsData[index].sort((a, b) => new Date(a.createdAt) - new Date(b.createdAt));
            return acc;
          }, {});
    
          setPostComments(commentsMap);
        }
      } catch (error) {
        console.error("Error loading posts:", error);
      }
    };

    loadPosts();
  }, [refresh]);

  const handleCommentChange = (postId, event) => {
    const { value } = event.target;
    setNewComment(prevState => ({
      ...prevState,
      [postId]: value
    }));
  };

  const handleCommentSubmit = async (postId, event) => {
    event.preventDefault();
    const comment = {
      commentText: newComment[postId],
      postId,
      userId: principal.app_user_id,
      username: principal.sub,
    };

    try {
      const response = await saveComment(comment);
      console.log("Comment saved successfully:", response);
      
      // Add the new comment to the existing comments and sort them
      setPostComments(prevState => ({
        ...prevState,
        [postId]: [...(prevState[postId] || []), response.payload].sort((a, b) => new Date(a.createdAt) - new Date(b.createdAt))
      }));

      // Clear the input field
      setNewComment(prevState => ({ ...prevState, [postId]: '' }));

    } catch (error) {
      console.error("Error saving comment:", error);
      alert("Failed to save comment. Please try again.");
    }
  };

  const handlePostSubmit = async (post) => {
    post.username = principal.sub;

    try {
      await savePost(post);
      setShowPostForm(false);
      setRefresh(prev => !prev); // Refresh the posts list
    } catch (error) {
      console.error("Error saving post:", error);
      alert("Failed to save post. Please try again.");
    }
  };

  const handlePostFormClose = () => {
    setShowPostForm(false);
    setRefresh(prev => !prev); // Toggle `refresh` state to trigger data reload
  };

  return (
    <div className="post-list-container max-w-3xl mx-auto p-6">
      <div className="post-list-content">
        <ul className="post-list space-y-6">
          {posts.length > 0 ? (
            posts.map(post => (
              <li key={post.postId} className="post-item bg-white p-4 rounded-lg shadow-md">
                <h2 className="text-xl font-semibold mb-2">{post.title}</h2>
                <p className="text-gray-500 mb-2">Posted by {post.username}</p>
                <p className="mb-4">{post.postDescription}</p>
                {post.imageUrl && (
                  <img
                    src={post.imageUrl}
                    alt={post.title}
                    className="post-image max-w-1/2 max-h-64 h-auto rounded-md mb-4"
                  />
                )}
                <div className="comment-section mt-4">
                  <h3 className="text-lg font-semibold mb-2">Comments</h3>
                  <ul className="space-y-3">
                    {postComments[post.postId] ? (
                      postComments[post.postId].map(comment => (
                        <li key={comment.commentId} className="comment-item bg-gray-100 p-3 rounded-md shadow-sm">
                          <p className="text-sm font-semibold text-gray-700 mb-1">
                            {comment.username} says:
                          </p>
                          <p className="text-gray-700 mb-1">{comment.commentText}</p>
                        </li>
                      ))
                    ) : (
                      <p className="text-gray-500">No comments yet.</p>
                    )}
                  </ul>
                  <form onSubmit={(e) => handleCommentSubmit(post.postId, e)} className="comment-form mt-4">
                    <textarea
                      value={newComment[post.postId] || ''}
                      onChange={(e) => handleCommentChange(post.postId, e)}
                      placeholder="Add a comment..."
                      required
                      className="comment-input w-full p-3 border rounded-md shadow-sm focus:ring-blue-800 focus:border-blue-800"
                    />
                    <button
                      type="submit"
                      className="submit-comment mt-2 bg-blue-800 text-white py-2 px-4 rounded-md hover:bg-blue-900 transition duration-200"
                    >
                      Submit Comment
                    </button>
                  </form>
                </div>
              </li>
            ))
          ) : (
            <p className="text-gray-500">No posts available.</p>
          )}
        </ul>
      </div>
      <button
        onClick={() => setShowPostForm(true)}
        className="add-post-button mt-6 bg-blue-800 text-white py-3 px-6 rounded-md hover:bg-blue-900 transition duration-200"
      >
        Add Post
      </button>

      <Modal isOpen={showPostForm} onClose={handlePostFormClose}>
        <div className="w-full max-w-full p-8">
          <PostForm onSave={handlePostSubmit} onClose={handlePostFormClose} />
        </div>
      </Modal>
    </div>
  );
};

export default PostList;
