import { Link } from "react-router-dom";
import logoImage from "../assets/images/logo-app-header-web.png";
import errorIllustration from "../assets/images/error-illustration.png";

function ErrorPage() {
  return (
    <main className="error-page" id="main-content">
      <section className="error-card" aria-label="Pagina no encontrada">
        <img src={logoImage} alt="Logo" className="error-logo" />

        <h2>Ha habido un error</h2>

        <img src={errorIllustration} alt="" aria-hidden="true" className="error-illustration-image" />

        <p>
          Ha habido un error al cargar la pagina,
          <br />
          si el problema persiste contactanos.
        </p>

        <div className="error-actions">
          <Link to="/contact" className="error-btn" role="button">
            Contactanos
            <span className="material-symbols-outlined error-btn-icon" aria-hidden="true">
              call
            </span>
          </Link>
          
          <Link to="/" className="error-btn" role="button">
            Volver al inicio
            <span className="material-symbols-outlined error-btn-icon" aria-hidden="true">
              home
            </span>
          </Link>
        </div>
      </section>
    </main>
  );
}

export default ErrorPage;
