import { Link, useNavigate, useLocation } from "react-router-dom";
import { useState, useEffect } from "react";
import Nav from "./Nav.jsx";
import { useAuth } from "../context/AuthContext.jsx";
import logoImage from "../assets/images/logo-app-header-web.png";
import searchImage from "../assets/images/icon-search.png";
import userImage from "../assets/images/icon-user-hires.png";

function Header() {
  const { user, isGuest, logout } = useAuth();
  const [menuOpen, setMenuOpen] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const navigate = useNavigate();
  const location = useLocation();

  // Sincronizar input con URL cuando cambia desde fuera
  useEffect(() => {
    const params = new URLSearchParams(location.search);
    setSearchTerm(params.get("search") || "");
  }, [location.search]);

  // Cerrar dropdown al hacer click fuera
  useEffect(() => {
    if (!menuOpen) return;
    const handleClickOutside = (e) => {
      const wrapper = document.querySelector(".user-menu-wrapper");
      if (wrapper && !wrapper.contains(e.target)) {
        setMenuOpen(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, [menuOpen]);

  const handleSearch = (e) => {
    if (e.key === "Enter") {
      const term = searchTerm.trim();
      if (term) {
        navigate(`/solicitudes?search=${encodeURIComponent(term)}`);
      } else {
        navigate("/solicitudes");
      }
    }
  };

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
            placeholder="Buscar solicitudes..."
            className="search-input-main"
            aria-label="Buscar solicitudes"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            onKeyDown={handleSearch}
          />
          <span className="search-icon" aria-hidden="true">
            <img src={searchImage} alt="" />
          </span>
        </div>

        {isGuest ? (
          <Link to="/login" className="user-btn" aria-label="Iniciar sesion">
            <img src={userImage} alt="" />
          </Link>
        ) : (
          <div className="user-menu-wrapper">
            <button
              type="button"
              className="user-btn"
              aria-label={`Menu de usuario de ${user.username}`}
              aria-expanded={menuOpen}
              onClick={() => setMenuOpen((v) => !v)}
            >
              <span className="user-name">{user.username}</span>
              <img src={userImage} alt="" />
            </button>
            {menuOpen && (
              <div className="user-dropdown">
                <Link to="/perfil" onClick={() => setMenuOpen(false)}>
                  Perfil
                </Link>
                <button type="button" onClick={() => { logout(); setMenuOpen(false); }}>
                  Cerrar sesion
                </button>
              </div>
            )}
          </div>
        )}
      </div>

      <Nav />
    </header>
  );
}

export default Header;
