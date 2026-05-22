import { Link } from "react-router-dom";

function GuestAccessNotice({ title, description }) {
  return (
    <section className="guest-notice" aria-label={title}>
      <span className="material-symbols-outlined guest-notice-icon" aria-hidden="true">
        lock
      </span>
      <h2>{title}</h2>
      <p>{description}</p>
      <div className="guest-notice-actions">
        <Link to="/login" className="primary-link-btn">
          Iniciar sesion
        </Link>
        <Link to="/solicitudes" className="secondary-link-btn">
          Ver solicitudes publicas
        </Link>
      </div>
    </section>
  );
}

export default GuestAccessNotice;
