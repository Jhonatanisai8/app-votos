import "./App.css";
import Header from "./pages/header/Header";
import Login from "./pages/auth/login/Login";
import Signup from "./pages/auth/signup/Signup";
import Dashboard from "./user/dashboard/Dashboard";
import { Routes, Route } from "react-router-dom";
import CrearEncuesta from "./user/crear-encuesta/CrearEncuesta";
import MisEncuestas from "./user/vista-mis-encuestas/MisEncuestas";
import EncuestaDetalles from "./user/vista-encuestas-detalles/EncuestaDetalles";

function App() {
  return (
    <>
      <Header />
      <Routes>
        <Route path="/registro" element={<Signup />} />
        <Route path="/login" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/encuesta/crear" element={<CrearEncuesta />} />
        <Route path="/mis-encuestas" element={<MisEncuestas />} />
        <Route path="/encuesta/detalles/:id" element={<EncuestaDetalles />} />
      </Routes>
    </>
  );
}

export default App;
