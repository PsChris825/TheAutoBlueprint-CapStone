import React, { useEffect, useState } from 'react';
import { fetchPosts } from '../../api/postApi'; 
const PostList = () => {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    const loadPosts = async () => {
      try {
        const data = await fetchPosts();
        setPosts(data);
      } catch (error) {
        console.error("Error loading posts:", error);
      }
    };

    loadPosts();
  }, []);

  return (
    <div>
      <h1>Post List</h1>
      <ul>
        {posts.map((post) => (
          <li key={post.postId}>
            <h2>{post.title}</h2>
            <p>{post.postDescription}</p>
            <img src={post.imageUrl} alt={post.title} />
            <a href={`/post/${post.postId}/comments`}>View Comments</a>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default PostList;
