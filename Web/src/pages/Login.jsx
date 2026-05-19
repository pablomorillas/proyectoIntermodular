import { Link } from "react-router-dom";
import logoImage from "../assets/images/logo-app-header-web.png";

function Login() {
  return (
    <main className="login-page" id="main-content">
      <section className="login-layout" aria-label="Inicio de sesion">
        <div className="login-visual" role="img" aria-label="Edificios modernos" />

        <div className="auth-panel-single">
          <article className="auth-card" aria-label="Formulario de inicio de sesion">
            <img src={logoImage} alt="Logo de la aplicacion" className="auth-logo" />
            <h2>Inicio de sesion</h2>

            <form className="auth-form" onSubmit={(event) => event.preventDefault()}>
              <label htmlFor="login-username">Nombre de usuario</label>
              <input id="login-username" name="username" type="text" autoComplete="username" />

              <label htmlFor="login-password">Contrasena</label>
              <input
                id="login-password"
                name="password"
                type="password"
                autoComplete="current-password"
              />

              <button type="submit" className="auth-btn">
                Iniciar sesion
              </button>
            </form>

            <p className="auth-note">
              No tienes una cuenta? <Link to="/register">Registrate</Link>
            </p>
          </article>
        </div>
      </section>
    </main>
  );
}

export default Login;
