import Home from "../pages/Home";
import { Routes, Route, Navigate } from "react-router-dom";
import Content from "./Content";
import YourRequests from "../pages/YourRequests";
import Requests from "../pages/Requests";
import Responses from "../pages/Responses";

function AppRouter() {
  return (
    <Routes>
      <Route element={<Content />}>
        <Route path="/" element={<Home />} />
        <Route path="/inicio" element={<Navigate to="/" replace />} />
        <Route path="/home" element={<Navigate to="/" replace />} />

        <Route path="/solicitudes" element={<Requests />} />
        <Route path="/yourRequests" element={<YourRequests />} />
        <Route path="/respuestas" element={<Responses />} />
      </Route>
    </Routes>
  );
}

export default AppRouter;
