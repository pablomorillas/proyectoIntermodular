import Home from "../pages/Home";
import { Routes, Route, Navigate } from "react-router-dom";
import Content from "./Content";
import Catalogue from "../pages/Catalogue";

function AppRouter() {
    return (
        <Routes>
            <Route element={<Content />}>
                <Route path="/" element={<Home />} />
                <Route path="/home" element={<Navigate to="/" />} />
                <Route path="/catalogue" element={<Catalogue/>} />
            </Route>
        </Routes>
    );
}

export default AppRouter;