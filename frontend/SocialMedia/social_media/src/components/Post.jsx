import React, { useState, useEffect } from "react";
import PostCard from "./PostCard";
import { BASE_URL } from "../config";
import { fetchWithAuth } from "../utils/fetchWithAuth";
import { useNavigate } from "react-router-dom";

function Post({ isPostAdded, filterByUserId = null,onPostCountChange }) {
  const [error, setError] = useState(null);
  const [isLoaded, setIsLoaded] = useState(false);
  const [postList, setPostList] = useState([]);
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const handlePostDelete = (deletedPostId) => {
    setPostList((prevPosts) => prevPosts.filter(post => post.id !== deletedPostId));
  };

  useEffect(() => {
    async function fetchPosts() {
      const url = filterByUserId
        ? `${BASE_URL}/posts?userId=${filterByUserId}`
        : `${BASE_URL}/posts`;

      const response = await fetchWithAuth(url, {
          method: "GET",
          headers: { "Content-Type": "application/json",
            "Authorization": token
           },}, navigate);
      if (!response) return;

      if (response.ok) {
        const result = await response.json();
        setPostList(result.reverse());
        if (onPostCountChange) onPostCountChange(result.length);
        setIsLoaded(true);
      } else {
        setError(new Error("Veri çekilemedi"));
        setIsLoaded(true);
      }
    }

    fetchPosts();
  }, [isPostAdded, filterByUserId]);

  if (error) return <div>Hata oluştu.</div>;
  if (!isLoaded) return <div>Yükleniyor...</div>;

  return (
    <ul>
      {postList.map((post) => (
        <div key={post.id}>
          <PostCard
            postId={post.id}
            title={post.title}
            text={post.text}
            username={post.user?.userName || "Anonim"}
            userId={post.user?.id || 0}
            imageUrl={post.imageUrl}
            likes={post.likes}
            comments={post.comments}
            onDelete={handlePostDelete}
          />
        </div>
      ))}
    </ul>
  );
}


export default Post;
