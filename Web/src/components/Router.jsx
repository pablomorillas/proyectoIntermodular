import Home from "../pages/Home";
import { Routes, Route, Navigate } from "react-router-dom";
import Content from "./Content";
import YourRequests from "../pages/YourRequests";
import Requests from "../pages/Requests";
import SolicitudDetail from "../pages/SolicitudDetail";
import Responses from "../pages/Responses";
import Contact from "../pages/Contact";
import ErrorPage from "../pages/ErrorPage";
import Login from "../pages/Login";
import Register from "../pages/Register";
import Profile from "../pages/Profile";

function AppRouter() {
  return (
    <Routes>
      <Route element={<Content />}>
        <Route path="/" element={<Home />} />
        <Route path="/inicio" element={<Navigate to="/" replace />} />
        <Route path="/home" element={<Navigate to="/" replace />} />

        <Route path="/solicitudes" element={<Requests />} />
        <Route path="/solicitudes/:id" element={<SolicitudDetail />} />
        <Route path="/yourRequests" element={<YourRequests />} />
        <Route path="/respuestas" element={<Responses />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/contacto" element={<Navigate to="/contact" replace />} />
        <Route path="/perfil" element={<Profile />} />
        <Route path="/error" element={<ErrorPage />} />
        <Route path="/404" element={<Navigate to="/error" replace />} />
        <Route path="*" element={<ErrorPage />} />
      </Route>

      <Route path="/login" element={<Login />} />
      <Route path="/iniciar-sesion" element={<Navigate to="/login" replace />} />
      <Route path="/register" element={<Register />} />
      <Route path="/registro" element={<Navigate to="/register" replace />} />
    </Routes>
  );
}

export default AppRouter;
