import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext.jsx";
import { useLogin } from "../hook/useLogin.js";
import logoImage from "../assets/images/logo-app-header-web.png";

function Login() {
  const { setUser } = useAuth();
  const navigate = useNavigate();
  const { login, loading, error: hookError } = useLogin();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    const data = await login(email, password);
    if (data) {
      const userData = {
        id: data.id,
        username: data.username,
        email: data.email,
        direccion: data.direccion,
      };
      setUser(userData);
      localStorage.setItem("requestructure_user", JSON.stringify(userData));
      navigate("/");
    } else {
      setError(hookError || "Email o contrasena incorrectos.");
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

              {(error || hookError) && <p className="auth-error" role="alert">{error || hookError}</p>}

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
