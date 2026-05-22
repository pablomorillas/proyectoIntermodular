import { NavLink } from "react-router-dom";

function Nav() {
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
            to="/login"
            className={({ isActive }) => `nav-link${isActive ? " active" : ""}`}
          >
            Entrar
          </NavLink>
        </li>
      </ul>
    </nav>
  );
}

export default Nav;
