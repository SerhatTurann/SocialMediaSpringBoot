import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../Css/PostForm.css";
import { fetchWithAuth } from "../utils/fetchWithAuth";


const PostForm = ({onPostAdded }) => {
  const [title, setTitle] = useState("");
  const [text, setText] = useState("");
  const [imageUrl, setImageUrl] = useState("");
  const [isSuccess, setIsSuccess] = useState(false); // Başarı durumu
  const navigate = useNavigate();

  const token = localStorage.getItem("token");
  const currentUserId = localStorage.getItem("userId");
  const savePost = async () => {
    const response = await fetchWithAuth(
      "http://localhost:8080/posts/add",
      {
        method: "POST",
        headers: { "Content-Type": "application/json",
                   "Authorization": token
         },
        body: JSON.stringify({ title, user_id: currentUserId, text,imageUrl }),
      },
      navigate
    );

    if (!response) return; // 401 handled

    if (response.ok) {
      setIsSuccess(true);
      setTimeout(() => setIsSuccess(false), 3000);
      if (onPostAdded) onPostAdded();
      setTitle("");
      setText("");
      setImageUrl("");
    } else {
      console.error("Post gönderme başarısız");
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const newPost = {
      title: title.trim(),
      text: text.trim(),
      imageUrl:imageUrl.trim()
    };

    console.log("Yeni Gönderi:", newPost);
    savePost();
    if (onPostAdded) onPostAdded();

    // Formu temizle
    setTitle("");
    setText("");
    setImageUrl("");
  };

  const handleCancel = () => {
    navigate("/"); // Anasayfaya yönlendiriyor
  };

  return (
    <div className="post-form-container">
      <button className="cancel-button" onClick={handleCancel}>
        ✖
      </button>
      <h2>Yeni Gönderi Oluştur</h2>
      <form onSubmit={handleSubmit} className="post-form">
        <input
          type="text"
          placeholder="Başlık"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />
        <textarea
          placeholder="İçerik"
          value={text}
          onChange={(e) => setText(e.target.value)}
          required
        ></textarea>
        <input
          type="text"
          placeholder="ImageUrl"
          value={imageUrl}
          onChange={(e) => setImageUrl(e.target.value)}
        />
        <div className="form-buttons">
          <button type="submit">Gönder</button>
        </div>
      </form>

      {/* Başarı mesajı */}
      {isSuccess && <div className="success-message">Post başarıyla gönderildi!</div>}
    </div>
  );
};

export default PostForm;
