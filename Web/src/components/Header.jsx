import { Link } from "react-router-dom";
import Nav from "./Nav.jsx";
import logoImage from "../assets/images/logo-app-header-web.png";
import searchImage from "../assets/images/icon-search.png";
import userImage from "../assets/images/icon-user-hires.png";

function Header() {
  return (
    <header className="header-shell">
      <div className="header-top">
        <Link to="/" className="brand-mark-link" aria-label="Ir a inicio">
          <div className="brand-mark">
            <img src={logoImage} alt="Logo de la aplicacion" className="brand-logo" />
          </div>
        </Link>

        <div className="search-box">
          <input
            type="text"
            placeholder="Buscar"
            className="search-input-main"
            aria-label="Buscar"
          />
          <span className="search-icon" aria-hidden="true">
            <img src={searchImage} alt="" />
          </span>
        </div>

        <button type="button" className="user-btn" aria-label="Perfil de usuario">
          <img src={userImage} alt="" />
        </button>
      </div>

      <Nav />
    </header>
  );
}

export default Header;