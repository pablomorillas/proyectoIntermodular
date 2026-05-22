import { useAuth } from "../context/AuthContext.jsx";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import logoImage from "../assets/images/logo-app-header-web.png";

function Profile() {
  const { user, logout, isGuest } = useAuth();
  const navigate = useNavigate();

  if (isGuest) {
    return (
      <main id="main-content" className="page-main">
        <section className="page-heading">
          <h2>Perfil</h2>
        </section>
        <section className="guest-notice" aria-label="Acceso restringido">
          <span className="material-symbols-outlined guest-notice-icon" aria-hidden="true">
            lock
          </span>
          <h2>Acceso restringido</h2>
          <p>Debes iniciar sesion para ver tu perfil.</p>
          <div className="guest-notice-actions">
            <Link to="/login" className="primary-link-btn">
              Iniciar sesion
            </Link>
            <Link to="/" className="secondary-link-btn">
              Volver al inicio
            </Link>
          </div>
        </section>
      </main>
    );
  }

  function handleLogout() {
    logout();
    navigate("/");
  }

  return (
    <main id="main-content" className="page-main">
      <section className="page-heading">
        <h2>Perfil</h2>
        <p>Gestiona tu cuenta y preferencias.</p>
      </section>

      <section className="auth-card" style={{ maxWidth: 520, margin: "0 auto" }}>
        <img
          src={logoImage}
          alt="Logo de la aplicacion"
          className="auth-logo"
          style={{ width: 64, height: 64 }}
        />
        <h2>{user.username}</h2>

        <div className="auth-form" style={{ textAlign: "left", gap: 12 }}>
          <p>
            <strong>Email:</strong> {user.email}
          </p>
          <p>
            <strong>Direccion:</strong> {user.direccion}
          </p>
        </div>

        <button type="button" className="auth-btn" onClick={handleLogout}>
          Cerrar sesion
        </button>
      </section>
    </main>
  );
}

export default Profile;
