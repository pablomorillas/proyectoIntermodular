import { Link } from "react-router-dom";

function Footer() {
  return (
    <footer className="footer-shell">
      <div className="footer-inner">
        <section className="footer-block footer-brand" aria-label="Marca">
          <h2>Requestructure</h2>
        </section>

        <nav className="footer-block footer-nav" aria-label="Enlaces de pie de pagina">
          <Link to="/" className="footer-link">
            Inicio
          </Link>
          <Link to="/solicitudes" className="footer-link">
            Solicitudes
          </Link>
          <Link to="/contact" className="footer-link">
            Contacto
          </Link>
        </nav>

        <section className="footer-block footer-copy" aria-label="Informacion legal">
          <p>&copy; {new Date().getFullYear()} Requestructure. Todos los derechos reservados.</p>
        </section>
      </div>
    </footer>
  );
}

export default Footer;
