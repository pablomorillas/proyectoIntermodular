import { useState } from "react";
import { useParams, Link } from "react-router-dom";
import { useSolicitud } from "../hook/useSolicitud";
import { useAuth } from "../context/AuthContext.jsx";
import { createComentarioSolicitud } from "../services/Service";

function SolicitudDetail() {
  const { id } = useParams();
  const { data: solicitud, loading, error, refetch } = useSolicitud(id);
  const { user, isGuest } = useAuth();
  const [commentText, setCommentText] = useState("");
  const [commentError, setCommentError] = useState("");
  const [sending, setSending] = useState(false);

  async function handleSubmitComment(e) {
    e.preventDefault();
    setCommentError("");
    if (!commentText.trim()) {
      setCommentError("Escribe un comentario antes de publicar.");
      return;
    }
    try {
      setSending(true);
      await createComentarioSolicitud(id, {
        autor: user?.username || "Anonimo",
        contenido: commentText.trim(),
      });
      setCommentText("");
      refetch();
    } catch (err) {
      setCommentError(err.message || "No se pudo publicar el comentario.");
    } finally {
      setSending(false);
    }
  }

  return (
    <main id="main-content" className="page-main">
      <section className="page-heading">
        <h2>Detalle de la solicitud</h2>
        <Link to="/solicitudes" className="secondary-link-btn" style={{ marginTop: 8, display: "inline-flex" }}>
          Volver a solicitudes
        </Link>
      </section>

      {loading && <p>Cargando...</p>}
      {error && <p className="auth-error" role="alert">{error}</p>}

      {!loading && !error && solicitud && (
        <article className="card-simple" style={{ maxWidth: 800, margin: "0 auto", textAlign: "left" }} aria-label={solicitud.titulo}>
          {solicitud.imagenes?.[0] && (
            <figure className="card-simple-img-container">
              <img
                src={solicitud.imagenes[0]}
                alt={`Imagen de ${solicitud.titulo}`}
                className="card-simple-img"
              />
            </figure>
          )}

          <h2 className="card-simple-title">{solicitud.titulo}</h2>

          <div style={{ marginTop: 8, fontSize: "0.85rem", color: "#555" }}>
            <p><strong>Autor:</strong> {solicitud.clienteUsername || `Usuario #${solicitud.clienteId}`}</p>
            <p><strong>Fecha:</strong> {new Date(solicitud.fechaHora).toLocaleString()}</p>
            <p><strong>Estado:</strong> {solicitud.estado || "Desconocido"}</p>
            <p><strong>Visibilidad:</strong> {solicitud.privada ? "Privada (solo tu y las empresas)" : "Publica"}</p>
          </div>

          <p className="card-simple-description" style={{ marginTop: 16 }}>
            {solicitud.contenido}
          </p>

          {solicitud.comentarios && solicitud.comentarios.length > 0 && (
            <section style={{ marginTop: 24, borderTop: "1px solid #d0d0d0", paddingTop: 16 }} aria-label="Comentarios">
              <h3 style={{ margin: "0 0 12px", fontSize: "1.1rem" }}>Comentarios</h3>
              {solicitud.comentarios.map((comentario) => (
                <article key={comentario.id} style={{ marginBottom: 12, padding: 12, background: "#f7f7f7", borderRadius: 6 }}>
                  <p style={{ margin: 0, fontWeight: 700, fontSize: "0.85rem" }}>{comentario.autor}</p>
                  <p style={{ margin: "4px 0 0", fontSize: "0.9rem" }}>{comentario.contenido}</p>
                  <p style={{ margin: "4px 0 0", fontSize: "0.75rem", color: "#888" }}>
                    {new Date(comentario.fechaHora).toLocaleString()}
                  </p>
                </article>
              ))}
            </section>
          )}

          {!isGuest && (
            <section style={{ marginTop: 24, borderTop: "1px solid #d0d0d0", paddingTop: 16 }}>
              <h3 style={{ margin: "0 0 12px", fontSize: "1.1rem" }}>Anadir comentario</h3>
              <form onSubmit={handleSubmitComment}>
                <textarea
                  value={commentText}
                  onChange={(e) => setCommentText(e.target.value)}
                  placeholder="Escribe tu comentario..."
                  rows={4}
                  style={{ width: "100%", padding: 10, borderRadius: 6, border: "1px solid #ccc", fontFamily: "inherit", fontSize: "0.95rem", resize: "vertical" }}
                  maxLength={1500}
                />
                {commentError && <p className="auth-error" role="alert" style={{ marginTop: 8 }}>{commentError}</p>}
                <button type="submit" className="auth-btn" disabled={sending} style={{ marginTop: 10, width: "auto", padding: "0 24px" }}>
                  {sending ? "Publicando..." : "Publicar comentario"}
                </button>
              </form>
            </section>
          )}

          {isGuest && (
            <section style={{ marginTop: 24, borderTop: "1px solid #d0d0d0", paddingTop: 16 }}>
              <p className="auth-error" role="alert">
                Debes <Link to="/login" style={{ textDecoration: "underline", color: "var(--color-primary)" }}>iniciar sesion</Link> para comentar.
              </p>
            </section>
          )}
        </article>
      )}
    </main>
  );
}

export default SolicitudDetail;
