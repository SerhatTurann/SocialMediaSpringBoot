// components/EditProfileModal.jsx
import React, { useState } from "react";
import "../Css/EditProfileModal.css";
import { BASE_URL } from "../config";
import { fetchWithAuth } from "../utils/fetchWithAuth";

const EditProfileModal = ({ user, onClose, onUpdate }) => {
  const [bio, setBio] = useState(user.bio || "");
  const [profilePhotoUrl, setProfilePhotoUrl] = useState(user.profilePhotoUrl || "");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const token = localStorage.getItem("token");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    const response = await fetchWithAuth(`${BASE_URL}/users/update/patch/${user.id}`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
        "Authorization": token,
      },
      body: JSON.stringify({ bio, profilePhotoUrl }),
    });

    setLoading(false);

    if (response && response.ok) {
      const updatedUser = await response.json();
      onUpdate(updatedUser); // Profile bileşenini güncelle
      onClose();             // Modalı kapat
    } else {
      setError("Güncelleme başarısız oldu.");
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <h2>Profili Düzenle</h2>
        <form onSubmit={handleSubmit}>
          <label>
            Profil Fotoğrafı URL:
            <input
              type="text"
              value={profilePhotoUrl}
              onChange={(e) => setProfilePhotoUrl(e.target.value)}
            />
          </label>
          <label>
            Biyografi:
            <textarea
              value={bio}
              onChange={(e) => setBio(e.target.value)}
              rows={4}
            />
          </label>

          {error && <p className="error-message">{error}</p>}
          <div className="modal-buttons">
            <button type="submit" disabled={loading}>
              {loading ? "Kaydediliyor..." : "Kaydet"}
            </button>
            <button type="button" onClick={onClose}>İptal</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default EditProfileModal;
