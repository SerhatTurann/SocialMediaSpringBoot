import React, { useState, useEffect } from "react";
import Post from "./Post";
import PostForm from "./PostForm";
import "../Css/Home.css";
import { useNavigate } from "react-router-dom";
import useSessionChecker from "./useSessionChecker";

function Home() {
  const [isPostAdded, setIsPostAdded] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const currentUserId = localStorage.getItem("userId");
  const navigate = useNavigate();

  const sessionIsValid = useSessionChecker();

  if (!sessionIsValid) {
    return null; // render'ı durdur
  }


  const handlePostAdded = () => {
    setIsPostAdded(!isPostAdded);
    setIsModalOpen(false);
  };

  

  return (
    <div className="home-layout">
      {/* Sol Panel */}
      <div className="left-panel">
        <button className="open-modal-btn" onClick={() => setIsModalOpen(true)}>
          + Gönderi Oluştur
        </button>
        <div className="post-shortcuts">
          <button>📷 Fotoğraf</button>
          <button>🎥 Canlı Yayın</button>
          <button>🏷️ Etiket</button>
        </div>
      </div>

      {/* Orta Panel (Postlar) */}
      <div className="middle-panel">
        {isModalOpen && (
          <div className="modal-overlay" onClick={() => setIsModalOpen(false)}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
              <button className="modal-close-btn" onClick={() => setIsModalOpen(false)}>
                ✖
              </button>
              <PostForm onPostAdded={handlePostAdded} />
            </div>
          </div>
        )}
        <Post isPostAdded={isPostAdded} />
      </div>

      {/* Sağ Panel */}
      <div className="right-panel">
        <h3>🔍 Keşfet</h3>
        <ul>
          <li>#react</li>
          <li>#geliştirici</li>
          <li>#yeniPostlar</li>
          <li>#tasarım</li>
        </ul>
      </div>
    </div>
  );
}

export default Home;
