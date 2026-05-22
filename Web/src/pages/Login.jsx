import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext.jsx";
import logoImage from "../assets/images/logo-app-header-web.png";

function Login() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    setLoading(true);
    try {
      const ok = await login(email, password);
      if (ok) {
        navigate("/");
      } else {
        setError("Email o contrasena incorrectos.");
      }
    } catch {
      setError("No se pudo conectar con el servidor.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <main className="login-page" id="main-content">
      <section className="login-layout" aria-label="Inicio de sesion">
        <div className="login-visual" role="img" aria-label="Edificios modernos" />

        <div className="auth-panel-single">
          <article className="auth-card" aria-label="Formulario de inicio de sesion">
            <img src={logoImage} alt="Logo de la aplicacion" className="auth-logo" />
            <h2>Inicio de sesion</h2>

            <form className="auth-form" onSubmit={handleSubmit}>
              <label htmlFor="login-email">Correo electronico</label>
              <input
                id="login-email"
                name="email"
                type="email"
                autoComplete="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />

              <label htmlFor="login-password">Contrasena</label>
              <input
                id="login-password"
                name="password"
                type="password"
                autoComplete="current-password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />

              {error && <p className="auth-error" role="alert">{error}</p>}

              <button type="submit" className="auth-btn" disabled={loading}>
                {loading ? "Cargando..." : "Iniciar sesion"}
              </button>
            </form>

            <Link to="/" className="guest-login-link">
              Entrar como invitado
            </Link>

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
