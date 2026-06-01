import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext.jsx";
import { useCreateSolicitud } from "../hook/useCreateSolicitud.js";

function CreateRequest() {
  const navigate = useNavigate();
  const { user, isGuest } = useAuth();
  const { addSolicitud, loading, error: hookError } = useCreateSolicitud();

  const [titulo, setTitulo] = useState("");
  const [contenido, setContenido] = useState("");
  const [imagen, setImagen] = useState("");
  const [privada, setPrivada] = useState(false);
  const [error, setError] = useState("");

  if (isGuest) {
    return (
      <main id="main-content" className="page-main">
        <section className="page-heading">
          <h2>Crear solicitud</h2>
          <p>Debes iniciar sesion para crear una nueva solicitud.</p>
        </section>
        <div className="auth-card" style={{ maxWidth: 500, margin: "0 auto" }}>
          <p className="auth-error" role="alert">Acceso reservado a usuarios registrados.</p>
        </div>
      </main>
    );
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");

    if (!titulo.trim() || !contenido.trim()) {
      setError("El titulo y la descripcion son obligatorios.");
      return;
    }

    const ok = await addSolicitud({
      clienteId: user.id,
      titulo: titulo.trim(),
      contenido: contenido.trim(),
      imagenes: imagen.trim() ? [imagen.trim()] : [],
      privada,
    });

    if (ok) {
      navigate("/yourRequests");
    } else {
      setError(hookError || "No se pudo crear la solicitud.");
    }
  }

  return (
    <main id="main-content" className="page-main">
      <section className="page-heading">
        <h2>Crear solicitud</h2>
        <p>Publica una nueva solicitud para que las empresas puedan responder.</p>
      </section>

      <div className="auth-card" style={{ maxWidth: 700, margin: "0 auto" }}>
        <form className="auth-form" onSubmit={handleSubmit}>
          <label htmlFor="create-title">Titulo</label>
          <input
            id="create-title"
            type="text"
            value={titulo}
            onChange={(e) => setTitulo(e.target.value)}
            placeholder="Ej: Reforma integral de cocina"
            required
            maxLength={160}
          />

          <label htmlFor="create-content">Descripcion</label>
          <textarea
            id="create-content"
            value={contenido}
            onChange={(e) => setContenido(e.target.value)}
            placeholder="Describe tu solicitud con detalle..."
            required
            maxLength={3000}
            rows={6}
            style={{ width: "100%", padding: 10, borderRadius: 6, border: "1px solid #ccc", fontFamily: "inherit", fontSize: "0.95rem", resize: "vertical" }}
          />

          <label htmlFor="create-image">URL de imagen (opcional)</label>
          <input
            id="create-image"
            type="url"
            value={imagen}
            onChange={(e) => setImagen(e.target.value)}
            placeholder="https://ejemplo.com/imagen.jpg"
          />

          <label htmlFor="create-private" style={{ marginTop: 8 }}>Visibilidad</label>
          <div
            style={{
              display: "flex",
              alignItems: "center",
              gap: 10,
              padding: "10px 12px",
              border: "1px solid #ccc",
              borderRadius: 6,
              background: "#fff",
              cursor: "pointer",
            }}
            onClick={() => setPrivada((v) => !v)}
          >
            <input
              id="create-private"
              type="checkbox"
              checked={privada}
              onChange={(e) => setPrivada(e.target.checked)}
              style={{ width: 18, height: 18, cursor: "pointer", margin: 0 }}
            />
            <span style={{ fontSize: "0.95rem", color: "#1f1f1f" }}>
              Solicitud privada (solo tu podras verla)
            </span>
          </div>

          {(error || hookError) && (
            <p className="auth-error" role="alert">{error || hookError}</p>
          )}

          <button type="submit" className="auth-btn" disabled={loading}>
            {loading ? "Creando..." : "Crear solicitud"}
          </button>
        </form>
      </div>
    </main>
  );
}

export default CreateRequest;
