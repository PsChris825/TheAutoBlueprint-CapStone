import React, { useEffect, useState } from 'react';
import { fetchCommentsByPostId } from '../../api/commentApi'; 
const CommentList = () => {
  const { postId } = useParams();
  const [comments, setComments] = useState([]);

  useEffect(() => {
    const loadComments = async () => {
      try {
        const data = await fetchCommentsByPostId(postId);
        setComments(data);
      } catch (error) {
        console.error("Error loading comments:", error);
      }
    };

    loadComments();
  }, [postId]);

  return (
    <div>
      <h1>Comments for Post {postId}</h1>
      <ul>
        {comments.map((comment) => (
          <li key={comment.commentId}>
            <p>{comment.text}</p>
            <p><small>Posted by: {comment.author}</small></p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CommentList;
