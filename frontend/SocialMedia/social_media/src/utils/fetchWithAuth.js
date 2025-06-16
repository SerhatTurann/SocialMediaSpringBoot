import { useNavigate } from "react-router-dom";

// Bu fonksiyon hook olmadığı için navigate'i dışarıdan parametre olarak alacağız
export async function fetchWithAuth(url, options = {}, navigate) {
  try {
    const response = await fetch(url, options);

    if (response.status === 401) {
      // Eğer 401 ise login sayfasına yönlendir
      if (navigate) {
        navigate("/login");
      }
      // Burada istersen hata fırlatabilirsin veya null dönebilirsin
      return null;
    }

    return response;
  } catch (error) {
    console.error("Fetch hatası:", error);
    return null;
  }
}
