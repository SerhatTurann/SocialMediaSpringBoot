// components/Profile.jsx
import React, { useEffect, useState } from "react";
import "../Css/Profile.css";
import Post from "./Post";
import { useParams, useNavigate } from "react-router-dom";
import { BASE_URL } from "../config";
import { fetchWithAuth } from "../utils/fetchWithAuth";
import EditProfileModal from "./EditProfileModal";

const Profile = () => {
  const { userId } = useParams();
  const currentUserId = localStorage.getItem("userId");
  const currentUserName = localStorage.getItem("username");
  const [isOwnProfile, setIsOwnProfile] = useState(false);
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const [followerList, setFollowerList] = useState([]);
  const [followingList, setFollowingList] = useState([]);
  const [isLoaded, setIsLoaded] = useState(false);
  const [error, setError] = useState(null);
  const [user, setUser] = useState(null);
  const [postCount, setPostCount] = useState(0);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);

  const handlePostCountChange = (postCount) => setPostCount(postCount);

  useEffect(() => {
    if (!userId) {
      navigate("/login");
      return;
    }
    setIsOwnProfile(userId === currentUserId);
  }, [userId, currentUserId, navigate]);

  useEffect(() => {
    async function fetchUser() {
      const url = `${BASE_URL}/users/${userId}`;
      const response = await fetchWithAuth(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: token,
        },
      });
      if (!response) return;

      if (response.ok) {
        const result = await response.json();
        setUser(result);
        setIsLoaded(true);
      } else {
        setError(new Error("Veri çekilemedi"));
        setIsLoaded(true);
      }
    }

    fetchUser();
  }, [userId, token]);

  useEffect(() => {
    async function fetchFollowers() {
      setIsLoaded(false);
      setError(null);
      const url = `${BASE_URL}/follows/followers/${userId}`;
      const response = await fetchWithAuth(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: token,
        },
      });
      if (!response) return;

      if (response.ok) {
        const result = await response.json();
        setFollowerList(result.reverse());
        setIsLoaded(true);
      } else {
        setError(new Error("Veri çekilemedi"));
        setIsLoaded(true);
      }
    }

    fetchFollowers();
  }, [userId, token]);

  useEffect(() => {
    async function fetchFollowing() {
      setIsLoaded(false);
      setError(null);
      const url = `${BASE_URL}/follows/following/${userId}`;
      const response = await fetchWithAuth(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: token,
        },
      });
      if (!response) return;

      if (response.ok) {
        const result = await response.json();
        setFollowingList(result.reverse());
        setIsLoaded(true);
      } else {
        setError(new Error("Veri çekilemedi"));
        setIsLoaded(true);
      }
    }

    fetchFollowing();
  }, [userId, token]);

  if (error) return <div>Hata oluştu.</div>;
  if (!isLoaded) return <div>Yükleniyor...</div>;

  return (
    <div className="profile-container">
      <div className="profile-header">
        <img
          className="profile-avatar"
          src={user.profilePhotoUrl}
          alt="User Avatar"
        />
        <div className="profile-info">
          <h2 className="profile-username">{user.userName}</h2>

          {isOwnProfile ? (
            <button
              className="edit-profile-btn"
              onClick={() => setIsEditModalOpen(true)}
            >
              Profili Düzenle
            </button>
          ) : (
            <button className="follow-btn">Takip Et</button>
          )}

          <div className="profile-stats">
            <span>
              <strong>{postCount}</strong> posts
            </span>
            <span>
              <strong>{followerList.length}</strong> followers
            </span>
            <span>
              <strong>{followingList.length}</strong> following
            </span>
          </div>

          <p className="profile-bio">{user.bio}</p>
        </div>
      </div>

      <Post filterByUserId={userId} onPostCountChange={handlePostCountChange} />

      {isEditModalOpen && (
        <EditProfileModal
          user={user}
          onClose={() => setIsEditModalOpen(false)}
          onUpdate={(updatedUser) => setUser(updatedUser)}
        />
      )}
    </div>
  );
};

export default Profile;
