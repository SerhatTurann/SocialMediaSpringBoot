import React, { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import "../Css/Login.css";

const Login = () => {
  const [form, setForm] = useState({ username: "", password: "" });
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      navigate("/");
    }
  }, [navigate]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    const newLogin = {
      userName: form.username,
      password: form.password,
    };

    try {
      const res = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(newLogin),
      });

      if (!res.ok) {
        throw new Error("Giriş başarısız. Lütfen bilgilerinizi kontrol edin.");
      }

      const contentType = res.headers.get("content-type");

      let token = "";
      if (contentType && contentType.includes("application/json")) {
        const data = await res.json();
        token = data.token;
        localStorage.setItem("username", data.username);
        localStorage.setItem("userId", data.userId);
      } else {
        const rawToken = await res.text();
        token = rawToken.startsWith("Bearer ") ? rawToken.split(" ")[1] : rawToken;
      }

      localStorage.setItem("token", token);

      navigate("/");
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <h2>Giriş Yap</h2>
        <input
          type="text"
          name="username"
          placeholder="Kullanıcı Adı"
          value={form.username}
          onChange={handleChange}
          required
        />
        <input
          type="password"
          name="password"
          placeholder="Şifre"
          value={form.password}
          onChange={handleChange}
          required
        />
        <button type="submit">Giriş Yap</button>

        {error && <div className="error-message">{error}</div>}

        <p className="register-link">
          Hesabın yok mu? <Link to="/register">Kayıt Ol</Link>
        </p>
      </form>
    </div>
  );
};

export default Login;
