import RequestCard from "../components/RequestCard.jsx";
import { useSolicitudes } from "../hook/useSolicitudes";

function Requests() {
  const { data: solicitudes, loading, error } = useSolicitudes();

  return (
    <main id="main-content" className="page-main">
      <section className="page-heading">
        <h2>Solicitudes</h2>
        <p>Publicaciones abiertas de toda la comunidad en formato foro.</p>
      </section>

      <section className="forum-section" aria-label="Solicitudes del foro">
        <div className="forum-section-head">
          <h2>Solicitudes</h2>
          <span>{solicitudes.length} publicaciones</span>
        </div>

        {loading && <p>Cargando...</p>}
        {error && <p className="auth-error" role="alert">{error}</p>}

        <div className="cards-grid">
          {!loading && !error && solicitudes.map((req) => (
            <RequestCard
              key={req.id}
              titulo={req.titulo}
              descripcion={req.contenido}
              imagen={req.imagenes?.[0] || ""}
            />
          ))}
        </div>
      </section>
    </main>
  );
}

export default Requests;
