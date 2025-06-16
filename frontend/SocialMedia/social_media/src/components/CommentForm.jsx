import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { fetchWithAuth } from "../utils/fetchWithAuth";

const CommentForm = ({ postId, onAddComment }) => {
  const [text, setText] = useState("");
  const [isSuccess, setIsSuccess] = useState(false);
  const navigate = useNavigate();
  const currentUserId = localStorage.getItem("userId");
  const token = localStorage.getItem("token");

  const handleSubmit = async (e) => {
    e.preventDefault();

    const newComment = {
      postId,
      userId : currentUserId,
      text: text.trim(),
    };

    try {
      const data = await fetchWithAuth("http://localhost:8080/comments/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": token
        },
        body: JSON.stringify(newComment),
      }, navigate); // 401 olursa navigate ile yönlendir
      const response = await data.json();
      console.log("Backend'den dönen yorum:", data);

      if (onAddComment) {
        console.log("Yorum verisi (data):", response);
        onAddComment(response); // Yeni yorum parent bileşene aktarılır
      }

      setText("");
      setIsSuccess(true);
      setTimeout(() => setIsSuccess(false), 3000);
    } catch (error) {
      console.error("Yorum gönderilemedi:", error);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit} className="comment-form">
        <textarea
          value={text}
          onChange={(e) => setText(e.target.value)}
          placeholder="Yorumunuzu yazın..."
          required
        />
        <button type="submit">Gönder</button>
      </form>
      {isSuccess && <div className="success-message">Yorum başarıyla gönderildi!</div>}
    </div>
  );
};

export default CommentForm;
