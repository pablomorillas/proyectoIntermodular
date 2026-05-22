import { NavLink } from "react-router-dom";
import { useAuth } from "../context/AuthContext.jsx";

function Nav() {
  const { isGuest } = useAuth();

  return (
    <nav className="nav-main" aria-label="Navegacion principal">
      <ul className="nav-list">
        <li className="nav-item">
          <NavLink to="/" end className={({ isActive }) => `nav-link${isActive ? " active" : ""}`}>
            Inicio
          </NavLink>
        </li>

        <li className="nav-item">
          <NavLink
            to="/solicitudes"
            className={({ isActive }) => `nav-link${isActive ? " active" : ""}`}
          >
            Solicitudes
          </NavLink>
        </li>

        <li className="nav-item">
          <NavLink
            to="/yourRequests"
            className={({ isActive }) => `nav-link${isActive ? " active" : ""}`}
          >
            Tus solicitudes
          </NavLink>
        </li>

        <li className="nav-item">
          <NavLink
            to="/respuestas"
            className={({ isActive }) => `nav-link${isActive ? " active" : ""}`}
          >
            Respuestas
          </NavLink>
        </li>

        <li className="nav-item nav-item-login">
          <NavLink
            to={isGuest ? "/login" : "/perfil"}
            className={({ isActive }) => `nav-link${isActive ? " active" : ""}`}
          >
            {isGuest ? "Entrar" : "Perfil"}
          </NavLink>
        </li>
      </ul>
    </nav>
  );
}

export default Nav;
