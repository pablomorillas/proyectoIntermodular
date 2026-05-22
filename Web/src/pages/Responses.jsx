import { useEffect, useState } from "react";
import GuestAccessNotice from "../components/GuestAccessNotice.jsx";
import { useAuth } from "../context/AuthContext.jsx";
import { getRespuestas } from "../api/client.js";

function Responses() {
  const { isGuest, user } = useAuth();
  const [respuestas, setRespuestas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    if (isGuest) return;
    let cancelled = false;
    getRespuestas()
      .then((data) => {
        if (!cancelled) {
          const mine = data.filter((r) => r.clienteId === user.id);
          setRespuestas(mine);
          setLoading(false);
        }
      })
      .catch(() => {
        if (!cancelled) {
          setError("No se pudieron cargar las respuestas.");
          setLoading(false);
        }
      });
    return () => { cancelled = true; };
  }, [isGuest, user]);

  if (isGuest) {
    return (
      <main id="main-content" className="page-main">
        <section className="page-heading">
          <h2>Respuestas</h2>
          <p>Seguimiento de respuestas recibidas por tus peticiones.</p>
        </section>

        <GuestAccessNotice
          title="Inicia sesion para ver respuestas"
          description="Como invitado puedes explorar solicitudes abiertas, pero las respuestas pertenecen a cada usuario y se muestran al iniciar sesion."
        />
      </main>
    );
  }

  return (
    <main id="main-content" className="page-main">
      <section className="page-heading">
        <h2>Respuestas</h2>
        <p>Seguimiento de respuestas recibidas por tus peticiones.</p>
      </section>

      {loading && <p>Cargando...</p>}
      {error && <p className="auth-error" role="alert">{error}</p>}

      <section className="forum-section" aria-label="Respuestas recibidas">
        {!loading && !error && respuestas.length === 0 && (
          <p>Aun no tienes respuestas.</p>
        )}
        {!loading && !error && respuestas.map((r) => (
          <article key={r.id} className="card-simple" tabIndex="0" aria-label={`Respuesta de empresa ${r.empresaId}`}>
            <h2 className="card-simple-title">{r.contenido}</h2>
            <p className="card-simple-description">Estado: {r.estado}</p>
          </article>
        ))}
      </section>
    </main>
  );
}

export default Responses;
