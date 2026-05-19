import { Link } from "react-router-dom";
import logoImage from "../assets/images/logo-app-header-web.png";

function Register() {
  return (
    <main className="login-page" id="main-content">
      <section className="login-layout" aria-label="Crear cuenta">
        <div className="login-visual" role="img" aria-label="Edificios modernos" />

        <div className="auth-panel-single">
          <article className="auth-card" aria-label="Formulario de registro">
            <img src={logoImage} alt="Logo de la aplicacion" className="auth-logo" />
            <h2>Crear cuenta</h2>

            <form className="auth-form" onSubmit={(event) => event.preventDefault()}>
              <label htmlFor="register-username">Nombre de usuario</label>
              <input id="register-username" name="registerUsername" type="text" autoComplete="username" />

              <label htmlFor="register-email">Correo electronico</label>
              <input id="register-email" name="email" type="email" autoComplete="email" />

              <label htmlFor="register-password">Contrasena</label>
              <input
                id="register-password"
                name="registerPassword"
                type="password"
                autoComplete="new-password"
              />

              <label htmlFor="register-password-repeat">Confirmar contrasena</label>
              <input
                id="register-password-repeat"
                name="registerPasswordRepeat"
                type="password"
                autoComplete="new-password"
              />

              <button type="submit" className="auth-btn">
                Registrarse
              </button>
            </form>

            <p className="auth-note">
              Tienes ya una cuenta? <Link to="/login">Inicia sesion</Link>
            </p>
          </article>
        </div>
      </section>
    </main>
  );
}

export default Register;
