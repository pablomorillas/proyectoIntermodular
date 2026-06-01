import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useRegister } from "../hook/useRegister.js";
import logoImage from "../assets/images/logo-app-header-web.png";

function Register() {
  const navigate = useNavigate();
  const { register, loading, error: hookError } = useRegister();
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordRepeat, setPasswordRepeat] = useState("");
  const [direccion, setDireccion] = useState("");
  const [error, setError] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");

    if (password !== passwordRepeat) {
      setError("Las contrasenas no coinciden.");
      return;
    }
    if (password.length < 6) {
      setError("La contrasena debe tener al menos 6 caracteres.");
      return;
    }

    const ok = await register({
      username,
      email,
      password,
      direccion: direccion || "Sin especificar",
    });

    if (ok) {
      navigate("/login");
    } else {
      setError(hookError || "No se pudo registrar.");
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

              {(error || hookError) && <p className="auth-error" role="alert">{error || hookError}</p>}

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
