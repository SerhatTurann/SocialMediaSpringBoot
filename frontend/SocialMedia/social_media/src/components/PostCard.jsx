import React, { useState, useEffect } from "react";
import "../Css/PostCard.css";
import { Link, useNavigate } from "react-router-dom";
import { FaHeart, FaComment, FaEllipsisV } from "react-icons/fa";
import Comment from "./Comment";
import { fetchWithAuth } from "../utils/fetchWithAuth";

const PostCard = ({
  postId,
  title,
  text,
  username,
  userId,
  imageUrl,
  likes,
  comments,
  onDelete, // Üst bileşenden gelen silme callback fonksiyonu
}) => {
  const [liked, setLiked] = useState(false);
  const [likeCount, setLikeCount] = useState(likes.length);
  const [showComments, setShowComments] = useState(false);
  const [showOptions, setShowOptions] = useState(false); // Güncelle/Sil menüsünü açmak için
  const navigate = useNavigate();
  const currentUserId = localStorage.getItem("userId");
  const token = localStorage.getItem("token");
  const [showConfirm, setShowConfirm] = useState(false);


  
  useEffect(() => {
    if (currentUserId && likes.some((like) => like.userId === parseInt(currentUserId))) {
      setLiked(true);
    }
  }, [likes, currentUserId]);

  const handleLikeClick = async () => {
    try {
      if (liked) {
        const response = await fetchWithAuth(
        `http://localhost:8080/likes/delete?userId=${currentUserId}&postId=${postId}`,
        { method: "DELETE",
          headers: { "Content-Type": "application/json",
            "Authorization": token
           }
         },
        navigate
       );

        if (response.ok) {
          setLikeCount((prev) => prev - 1);
          setLiked(false);
        } else {
          console.error("Unlike işlemi başarısız oldu.");
        }
      } else {
        const response = await fetchWithAuth(
        "http://localhost:8080/likes/add",
        {
          method: "POST",
          headers: { "Content-Type": "application/json",
            "Authorization": token
           },
          body: JSON.stringify({ userId: currentUserId, postId }),
        },
        navigate
      );

        if (response.ok) {
          setLikeCount((prev) => prev + 1);
          setLiked(true);
        } 
        else {
          console.error("Like işlemi başarısız oldu.");
        }
      }
    } catch (error) {
      console.error("Like/unlike hatası:", error);
    }
  };

  const handleCommentToggle = () => {
    setShowComments((prev) => !prev);
  };

  const handleUpdate = () => {
    console.log("Güncelleme ekranına yönlendir");
    // navigate veya modal açılabilir
  };

  const handleDelete = async () => {
  try {
    const response = await fetchWithAuth(
      `http://localhost:8080/posts/delete/${postId}`,
      {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          "Authorization": token
        }
      },
      navigate
    );

    if (response.ok) {
      console.log("Post başarıyla silindi.");
      if (onDelete) {
        onDelete(postId);
      }
    } else {
      console.error("Silme işlemi başarısız.");
    }
  } catch (error) {
    console.error("Silme hatası:", error);
  } finally {
    setShowConfirm(false); // modal'ı kapat
  }
};



  return (
    <div className="post-card">
      {/* Sağ üstteki üç nokta menüsü */}
      {parseInt(currentUserId) === userId && (
        <div className="post-options">
          <FaEllipsisV onClick={() => setShowOptions(!showOptions)} className="options-icon" />
          {showOptions && (
            <div className="options-menu">
              <button onClick={handleUpdate}>Güncelle</button>
              <button onClick={() => setShowConfirm(true)}>Sil</button>
            </div>
          )}
        </div>
      )}
      {showConfirm && (
        <div className="confirm-box">
          <p>Bu gönderiyi silmek istiyor musunuz?</p>
          <div className="confirm-buttons">
            <button onClick={handleDelete} className="yes-btn">Evet</button>
            <button onClick={() => setShowConfirm(false)} className="no-btn">Vazgeç</button>
          </div>
        </div>
      )}

      {imageUrl && (
        <div className="post-image-container">
          <img src={imageUrl} alt="Post" className="post-image" />
        </div>
      )}


      <h2 className="post-title">{title}</h2>
      <p className="post-text">{text}</p>
      <Link to={`/users/${userId}`}>
        <div className="post-user">👤 {username}</div>
      </Link>

      <div className="post-actions">
        <button
          className={`action-button-like ${liked ? "liked" : ""}`}
          onClick={handleLikeClick}
        >
          <FaHeart className="icon" /> <span>{likeCount}</span>
        </button>

        <button className="action-button-comment" onClick={handleCommentToggle}>
          <FaComment className="icon" /> <span>{comments.length}</span>
        </button>
      </div>

      {showComments && (
        <Comment postId={postId} userId={currentUserId} comments={comments} />
      )}
    </div>
  );
};

export default PostCard;
