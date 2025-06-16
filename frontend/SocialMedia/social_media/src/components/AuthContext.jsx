// src/context/AuthContext.jsx
import React, { createContext, useState, useContext } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState({
    token: null,
    username: null,
  });

  const login = (token, username) => {
    setAuth({ token, username });
  };

  const logout = () => {
    setAuth({ token: null, username: null });
  };

  return (
    <AuthContext.Provider value={{ auth, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

// Custom hook
export const useAuth = () => useContext(AuthContext);
