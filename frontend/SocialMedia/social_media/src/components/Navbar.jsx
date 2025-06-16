import { useState, useEffect } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { confirmAlert } from 'react-confirm-alert';
import { BASE_URL } from "../config";
import { fetchWithAuth } from "../utils/fetchWithAuth";
import "../Css/Navbar.css";

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [searchQuery, setSearchQuery] = useState("");
  const navigate = useNavigate();
  const [isLoaded, setIsLoaded] = useState(false);
  const [error, setError] = useState(null);
  const [user, setUser] = useState(null);

  const token = localStorage.getItem("token");
  const userId = localStorage.getItem("userId")

  const toggleMenu = () => setIsOpen(!isOpen);

  useEffect(() => {
      async function fetchUser() {
        const url = `${BASE_URL}/users/${userId}`;
        const response = await fetchWithAuth(url, {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "Authorization": token,
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
    document.body.style.overflow = isOpen ? "hidden" : "auto";
    return () => {
      document.body.style.overflow = "auto";
    };
  }, [isOpen]);


  const closeMenu = () => setIsOpen(false);

  const handleSearch = (e) => {
    e.preventDefault();
    console.log("Aranan:", searchQuery);
    // Arama yönlendirmesi burada yapılabilir
  };

  

const handleLogout = () => {
  confirmAlert({
    title: 'Çıkış Yap',
    message: 'Çıkış yapmak istediğinizden emin misiniz?',
    buttons: [
      {
        label: 'Evet',
        onClick: () => {
          localStorage.removeItem("token");
          localStorage.removeItem("username");
          localStorage.removeItem("userId");
          navigate("/login");
        }
      },
      {
        label: 'Hayır',
        onClick: () => {} // hiçbir şey yapma
      }
    ]
  });
};



  const handleLoginRedirect = () => {
    navigate("/login");
  };

  return (
    <>
      <nav className="navbar">
        <div className="navbar-container">
          {/* Sol: Logo */}
          <div className="navbar-left">
            <div className="navbar-logo">LOGO</div>
          </div>

          {/* Orta: Menü */}
          <div className={`navbar-center ${isOpen ? "open" : ""}`}>
            <NavLink to="/" onClick={closeMenu}>Anasayfa</NavLink>
            <NavLink to="/explore" onClick={closeMenu}>Keşfet</NavLink>
            <NavLink to="/notifications" onClick={closeMenu}>Bildirimler</NavLink>
            <NavLink to="/messages" onClick={closeMenu}>Mesajlar</NavLink>
          </div>

          {/* Sağ: Arama + Profil/Giriş + Hamburger */}
          <div className="navbar-right">
            <form className="search-form" onSubmit={handleSearch}>
              <input
                type="text"
                placeholder="Ara..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
              />
            </form>

            {token ? (
                <>
                  {user && (
                    <NavLink to={`/profile/${userId}`} onClick={closeMenu} className="navbar-profile">
                      <img
                        src={user.profilePhotoUrl}
                        alt="Profil"
                        className="profile-img"
                      />
                      <span className="profile-username">{user.userName}</span>
                    </NavLink>
                  )}


                  <button className="auth-button logout" onClick={handleLogout}>
                    Çıkış Yap
                  </button>
                </>
              ) : (
                <button className="auth-button login" onClick={handleLoginRedirect}>
                  Giriş Yap
                </button>
              )}


            <div className="hamburger" onClick={toggleMenu}>
              {isOpen ? "✖" : "☰"}
            </div>
          </div>
        </div>
      </nav>
      <div className="navbar-space"></div>
    </>
  );
};

export default Navbar;
