import React, { useState } from 'react';
import { saveComment } from '../api/commentApi';

const CommentForm = ({ postId, onCommentAdded }) => {
  const [commentText, setCommentText] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    const comment = { postId, commentText };
    try {
      const savedComment = await saveComment(comment);
      onCommentAdded(savedComment);
      setCommentText('');
    } catch (error) {
      console.error("Error adding comment:", error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Comment</label>
        <textarea
          value={commentText}
          onChange={(e) => setCommentText(e.target.value)}
          required
        />
      </div>
      <button type="submit">Add Comment</button>
    </form>
  );
};

export default CommentForm;
