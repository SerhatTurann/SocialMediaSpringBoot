import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import 'react-confirm-alert/src/react-confirm-alert.css';
import './App.css'
import Navbar from "./components/Navbar";
import Home from "./components/Home";
import PostForm from "./components/PostForm";
import Login from "./components/Login";
import Register from "./components/Register";
import Profile from "./components/Profile";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function App() {
  let currentUserId=3;

  return (
    <Router>
      <div className="app-container">
        <ToastContainer />
        <Navbar />
        <div className="content">
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/" element={<Home />} />
            <Route path="/profile/:userId" element={<Profile />} />
            <Route path="/create-post" element={<PostForm />} />
          </Routes>
        </div>
      </div>
    </Router>
  )
}

export default App
