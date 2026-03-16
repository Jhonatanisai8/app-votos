import "./App.css";
import Header from "./pages/header/Header";
import Login from "./pages/auth/login/Login";
import Signup from "./pages/auth/signup/Signup";
import Dashboard from "./user/dashboard/Dashboard";
import { Routes, Route } from "react-router-dom";

function App() {
  return (
    <>
      <Header />
      <Routes>
        <Route path="/registro" element={<Signup />} />
        <Route path="/login" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </>
  );
}

export default App;
