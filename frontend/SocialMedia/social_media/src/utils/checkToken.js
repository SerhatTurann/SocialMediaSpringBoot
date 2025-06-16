import jwtDecode from "jwt-decode";

const checkTokenValidity = () => {
  const token = localStorage.getItem("token");
  if (!token) return false;

  try {
    const decoded = jwtDecode(token);
    const currentTime = Date.now() / 1000; // saniye cinsinden

    if (decoded.exp < currentTime) {
      // Token süresi geçmiş
      localStorage.removeItem("token");
      localStorage.removeItem("userId");
      return false;
    }

    return true;
  } catch (error) {
    // Token bozuk veya decode edilemedi
    return false;
  }
};
