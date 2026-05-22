import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext.jsx";
import logoImage from "../assets/images/logo-app-header-web.png";

function Register() {
  const { register } = useAuth();
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordRepeat, setPasswordRepeat] = useState("");
  const [direccion, setDireccion] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");

    if (password !== passwordRepeat) {
      setError("Las contrasenas no coinciden.");
      return;
    }
    if (password.length < 4) {
      setError("La contrasena debe tener al menos 4 caracteres.");
      return;
    }

    setLoading(true);
    try {
      await register({
        username,
        email,
        password,
        direccion: direccion || "Sin especificar",
      });
      navigate("/login");
    } catch (err) {
      setError(err.message || "No se pudo registrar. Intenta con otro email.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <main className="login-page" id="main-content">
      <section className="login-layout" aria-label="Crear cuenta">
        <div className="login-visual" role="img" aria-label="Edificios modernos" />

        <div className="auth-panel-single">
          <article className="auth-card" aria-label="Formulario de registro">
            <img src={logoImage} alt="Logo de la aplicacion" className="auth-logo" />
            <h2>Crear cuenta</h2>

            <form className="auth-form" onSubmit={handleSubmit}>
              <label htmlFor="register-username">Nombre de usuario</label>
              <input
                id="register-username"
                name="registerUsername"
                type="text"
                autoComplete="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />

              <label htmlFor="register-email">Correo electronico</label>
              <input
                id="register-email"
                name="email"
                type="email"
                autoComplete="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />

              <label htmlFor="register-direccion">Direccion</label>
              <input
                id="register-direccion"
                name="direccion"
                type="text"
                autoComplete="street-address"
                value={direccion}
                onChange={(e) => setDireccion(e.target.value)}
              />

              <label htmlFor="register-password">Contrasena</label>
              <input
                id="register-password"
                name="registerPassword"
                type="password"
                autoComplete="new-password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />

              <label htmlFor="register-password-repeat">Confirmar contrasena</label>
              <input
                id="register-password-repeat"
                name="registerPasswordRepeat"
                type="password"
                autoComplete="new-password"
                value={passwordRepeat}
                onChange={(e) => setPasswordRepeat(e.target.value)}
                required
              />

              {error && <p className="auth-error" role="alert">{error}</p>}

              <button type="submit" className="auth-btn" disabled={loading}>
                {loading ? "Registrando..." : "Registrarse"}
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
