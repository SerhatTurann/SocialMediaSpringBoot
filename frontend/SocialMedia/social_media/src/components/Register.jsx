import React, { useState } from "react";
import "../Css/Register.css";
import { Link, useNavigate } from "react-router-dom";

const Register = () => {
  const [isSuccess, setIsSuccess] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const [form, setForm] = useState({
    username: "",
    password: "",
  });

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
    const res = await fetch("http://localhost:8080/auth/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(newLogin),
    });

    if (!res.ok) {
      throw new Error("Giriş başarısız. Lütfen bilgilerinizi kontrol edin.");
    }

    setIsSuccess(true);
    navigate("/login");
  } catch (err) {
    setError(err.message);
  }
};

  return (
    <div className="register-container">
      <form className="register-form" onSubmit={handleSubmit}>
        <h2>Kayıt Ol</h2>
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
        <button type="submit">Kayıt Ol</button>
        <p className="register-link">
          Zaten hesabın var mı? <Link to="/login">Giriş yap</Link>
        </p>
      </form>
    </div>
  );
};

export default Register;
