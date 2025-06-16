import { useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import { toast } from "react-toastify";

const useSessionChecker = () => {
  const navigate = useNavigate();
  const hasLoggedOut = useRef(false);

  const checkTokenValidity = () => {
    const token = localStorage.getItem("token");
    if (!token) return false;

    try {
      const decoded = jwtDecode(token);
      const currentTime = Date.now() / 1000;

      return decoded.exp >= currentTime;
    } catch (error) {
      return false;
    }
  };

  useEffect(() => {
    const checkSession = () => {
      if (!checkTokenValidity() && !hasLoggedOut.current) {
        hasLoggedOut.current = true;

        toast.error("Oturum süreniz doldu. Giriş sayfasına yönlendiriliyorsunuz.", {
          position: "top-center",
          autoClose: 4000,
        });

        setTimeout(() => {
          localStorage.removeItem("token");
          localStorage.removeItem("username");
          localStorage.removeItem("userId");
          navigate("/login");
        }, 4000); // toast gösterildikten sonra yönlendirme
      }
    };

    checkSession();
    const interval = setInterval(checkSession, 60 * 1000);

    return () => clearInterval(interval);
  }, [navigate]);

  return checkTokenValidity();
};

export default useSessionChecker;
