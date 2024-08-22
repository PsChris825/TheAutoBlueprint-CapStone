import React, { useEffect, useState } from 'react';
import { fetchPosts, saveComment, fetchCommentsByPostId } from '../../api/postApi';
import Modal from '../modal/modal'; // Adjust path as needed
import PostForm from './postForm'; // Adjust path as needed

const PostList = () => {
  const [posts, setPosts] = useState([]);
  const [newComment, setNewComment] = useState({});
  const [postComments, setPostComments] = useState({});
  const [showPostForm, setShowPostForm] = useState(false);
  const [refresh, setRefresh] = useState(false);

  useEffect(() => {
    const loadPosts = async () => {
      try {
        const data = await fetchPosts();
        console.log("Fetched posts:", data);
        setPosts(data);

        const commentsPromises = data.map(post => fetchCommentsByPostId(post.postId));
        const commentsData = await Promise.all(commentsPromises);

        const commentsMap = data.reduce((acc, post, index) => {
          acc[post.postId] = commentsData[index];
          return acc;
        }, {});

        console.log("Fetched comments:", commentsMap);
        setPostComments(commentsMap);
      } catch (error) {
        console.error("Error loading posts:", error);
      }
    };

    loadPosts();
  }, [refresh]); // Reload posts when `refresh` state changes

  const handleCommentChange = (postId, event) => {
    setNewComment(prevState => ({
      ...prevState,
      [postId]: event.target.value
    }));
  };

  const handleCommentSubmit = async (postId, event) => {
    event.preventDefault();
    const comment = {
      commentText: newComment[postId],
      postId,
    };

    try {
      const response = await saveComment(comment);
      console.log("Comment saved successfully:", response);

      setNewComment(prevState => ({ ...prevState, [postId]: '' }));

      const updatedComments = await fetchCommentsByPostId(postId);
      console.log("Updated comments:", updatedComments);
      setPostComments(prevState => ({
        ...prevState,
        [postId]: updatedComments
      }));
    } catch (error) {
      console.error("Error saving comment:", error);
      alert("Failed to save comment. Please try again.");
    }
  };

  const handlePostFormClose = () => {
    setShowPostForm(false);
    setRefresh(prev => !prev); // Toggle `refresh` state to trigger data reload
  };

  return (
    <div className="post-list-container">
      <div className="post-list-content">
        <ul className="post-list">
          {posts.length > 0 ? (
            posts.map(post => (
              <li key={post.postId} className="post-item">
                <h2>{post.title}</h2>
                <p>{post.postDescription}</p>
                {post.imageUrl && <img src={post.imageUrl} alt={post.title} className="post-image" />}
                <div className="comment-section">
                  <h3>Comments</h3>
                  <ul>
                    {postComments[post.postId] ? (
                      postComments[post.postId].map(comment => (
                        <li key={comment.commentId} className="comment-item">
                          <p>{comment.commentText}</p>
                        </li>
                      ))
                    ) : (
                      <p>No comments yet.</p>
                    )}
                  </ul>
                  <form onSubmit={(e) => handleCommentSubmit(post.postId, e)} className="comment-form">
                    <textarea
                      value={newComment[post.postId] || ''}
                      onChange={(e) => handleCommentChange(post.postId, e)}
                      placeholder="Add a comment..."
                      required
                      className="comment-input"
                    />
                    <button type="submit" className="submit-comment">Submit Comment</button>
                  </form>
                </div>
              </li>
            ))
          ) : (
            <p>No posts available.</p>
          )}
        </ul>
      </div>
      <button
        onClick={() => setShowPostForm(true)}
        className="add-post-button"
      >
        Add Post
      </button>

      <Modal isOpen={showPostForm} onClose={handlePostFormClose}>
        <PostForm onClose={handlePostFormClose} />
      </Modal>
    </div>
  );
};

export default PostList;
