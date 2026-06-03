import { useState } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import { useSolicitud } from "../hook/useSolicitud";
import { useAuth } from "../context/AuthContext.jsx";
import { createComentarioSolicitud, deleteSolicitud } from "../services/Service";

function SolicitudDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { data: solicitud, loading, error, refetch } = useSolicitud(id);
  const { user, isGuest } = useAuth();

  const [commentText, setCommentText] = useState("");
  const [commentError, setCommentError] = useState("");
  const [sending, setSending] = useState(false);

  const [replyingTo, setReplyingTo] = useState(null);
  const [replyText, setReplyText] = useState("");
  const [replyError, setReplyError] = useState("");
  const [sendingReply, setSendingReply] = useState(false);

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

  async function handleSubmitReply(e, comentarioPadreId) {
    e.preventDefault();
    setReplyError("");
    if (!replyText.trim()) {
      setReplyError("Escribe una respuesta antes de publicar.");
      return;
    }
    try {
      setSendingReply(true);
      await createComentarioSolicitud(id, {
        autor: user?.username || "Anonimo",
        contenido: replyText.trim(),
        comentarioPadreId,
      });
      setReplyText("");
      setReplyingTo(null);
      refetch();
    } catch (err) {
      setReplyError(err.message || "No se pudo publicar la respuesta.");
    } finally {
      setSendingReply(false);
    }
  }

  function renderComentario(comentario, isReply = false) {
    return (
      <article
        key={comentario.id}
        style={{
          marginBottom: 12,
          padding: 12,
          background: isReply ? "#efefef" : "#f7f7f7",
          borderRadius: 6,
          marginLeft: isReply ? 24 : 0,
        }}
      >
        <p style={{ margin: 0, fontWeight: 700, fontSize: "0.85rem" }}>{comentario.autor}</p>
        <p style={{ margin: "4px 0 0", fontSize: "0.9rem" }}>{comentario.contenido}</p>
        <p style={{ margin: "4px 0 0", fontSize: "0.75rem", color: "#888" }}>
          {new Date(comentario.fechaHora).toLocaleString()}
        </p>

        {!isGuest && user?.username === comentario.autor && (
          <button
            type="button"
            onClick={() => alert("Funcionalidad de eliminar comentario pendiente de implementar en backend.")}
            style={{
              marginTop: 6,
              marginRight: 12,
              background: "none",
              border: "none",
              color: "var(--color-error)",
              cursor: "pointer",
              fontSize: "0.85rem",
              fontWeight: 700,
              padding: 0,
            }}
          >
            Eliminar
          </button>
        )}

        {!isGuest && replyingTo !== comentario.id && (
          <button
            type="button"
            onClick={() => { setReplyingTo(comentario.id); setReplyText(""); setReplyError(""); }}
            style={{
              marginTop: 6,
              background: "none",
              border: "none",
              color: "var(--color-primary)",
              cursor: "pointer",
              fontSize: "0.85rem",
              fontWeight: 700,
              padding: 0,
            }}
          >
            Responder
          </button>
        )}

        {!isGuest && replyingTo === comentario.id && (
          <form onSubmit={(e) => handleSubmitReply(e, comentario.id)} style={{ marginTop: 10 }}>
            <textarea
              value={replyText}
              onChange={(e) => setReplyText(e.target.value)}
              placeholder="Escribe tu respuesta..."
              rows={3}
              style={{ width: "100%", padding: 10, borderRadius: 6, border: "1px solid #ccc", fontFamily: "inherit", fontSize: "0.95rem", resize: "vertical" }}
              maxLength={1500}
            />
            {replyError && <p className="auth-error" role="alert" style={{ marginTop: 8 }}>{replyError}</p>}
            <div style={{ display: "flex", gap: 10, marginTop: 8 }}>
              <button type="submit" className="auth-btn" disabled={sendingReply} style={{ width: "auto", padding: "0 18px", height: 36, fontSize: "0.85rem" }}>
                {sendingReply ? "Publicando..." : "Publicar respuesta"}
              </button>
              <button
                type="button"
                onClick={() => setReplyingTo(null)}
                style={{ background: "#ddd", border: "none", borderRadius: 6, padding: "0 18px", cursor: "pointer", fontWeight: 700, fontSize: "0.85rem" }}
              >
                Cancelar
              </button>
            </div>
          </form>
        )}

        {comentario.respuestas && comentario.respuestas.length > 0 && (
          <div style={{ marginTop: 10 }}>
            {comentario.respuestas.map((respuesta) => renderComentario(respuesta, true))}
          </div>
        )}
      </article>
    );
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

          {user && solicitud.clienteId === user.id && (
            <div style={{ marginTop: 12 }}>
              <button
                type="button"
                onClick={async () => {
                  if (window.confirm("¿Seguro que quieres eliminar esta solicitud? Esta accion no se puede deshacer.")) {
                    try {
                      await deleteSolicitud(solicitud.id);
                      navigate("/solicitudes");
                    } catch (err) {
                      alert(err.message || "No se pudo eliminar la solicitud.");
                    }
                  }
                }}
                style={{
                  background: "var(--color-error)",
                  color: "#fff",
                  border: "none",
                  borderRadius: 6,
                  padding: "8px 16px",
                  cursor: "pointer",
                  fontWeight: 700,
                  fontSize: "0.9rem",
                }}
              >
                Eliminar solicitud
              </button>
            </div>
          )}

          <p className="card-simple-description" style={{ marginTop: 16 }}>
            {solicitud.contenido}
          </p>

          {solicitud.comentarios && solicitud.comentarios.length > 0 && (
            <section style={{ marginTop: 24, borderTop: "1px solid #d0d0d0", paddingTop: 16 }} aria-label="Comentarios">
              <h3 style={{ margin: "0 0 12px", fontSize: "1.1rem" }}>Comentarios</h3>
              {solicitud.comentarios.map((comentario) => renderComentario(comentario))}
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
