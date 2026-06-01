import { useAuth } from "../context/AuthContext.jsx";
import { useRespuestas } from "../hook/useRespuestas";
import GuestAccessNotice from "../components/GuestAccessNotice.jsx";

function Responses() {
  const { isGuest, user } = useAuth();
  const { data: respuestas, loading, error } = useRespuestas();

  const mine = isGuest ? [] : respuestas.filter((r) => r.clienteId === user?.id);

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
        {!loading && !error && mine.length === 0 && (
          <p>Aun no tienes respuestas.</p>
        )}
        {!loading && !error && mine.map((r) => (
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
