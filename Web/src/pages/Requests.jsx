import { useSearchParams } from "react-router-dom";
import RequestCard from "../components/RequestCard.jsx";
import { useSolicitudes } from "../hook/useSolicitudes";

function Requests() {
  const { data: solicitudes, loading, error } = useSolicitudes();
  const [searchParams] = useSearchParams();
  const search = searchParams.get("search") || "";

  const filtered = search
    ? solicitudes.filter((s) =>
        s.titulo?.toLowerCase().includes(search.toLowerCase()) ||
        s.contenido?.toLowerCase().includes(search.toLowerCase())
      )
    : solicitudes;

  return (
    <main id="main-content" className="page-main">
      <section className="page-heading">
        <h2>Solicitudes</h2>
        <p>Publicaciones abiertas de toda la comunidad en formato foro.</p>
      </section>

      <section className="forum-section" aria-label="Solicitudes del foro">
        <div className="forum-section-head">
          <h2>Solicitudes</h2>
          <span>{filtered.length} publicaciones</span>
          {search && (
            <span className="search-tag">Filtrando por: "{search}"</span>
          )}
        </div>

        {loading && <p>Cargando...</p>}
        {error && <p className="auth-error" role="alert">{error}</p>}

        {!loading && !error && filtered.length === 0 && (
          <p>No se encontraron solicitudes.</p>
        )}

        <div className="cards-grid">
          {!loading && !error && filtered.map((req) => (
            <RequestCard
              key={req.id}
              id={req.id}
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
