import RequestCard from "../components/RequestCard.jsx";
import { useSolicitudes } from "../hook/useSolicitudes";

function Home() {
  const { data: solicitudes, loading, error } = useSolicitudes();
  const featuredRequests = solicitudes.slice(0, 3);

  return (
    <main id="main-content" className="page-main home-page">
      <section className="hero-section" aria-label="Destacado">
        <div className="hero-copy">
          <h2>Solicitudes destacadas</h2>
        </div>

        <div className="hero-media">
          <div className="hero-image-placeholder" role="img" aria-label="Imagen destacada pendiente" />
          <button type="button" className="hero-action-btn">
            Ver mas
          </button>
        </div>
      </section>

      <section className="cards-grid" aria-label="Productos destacados">
        {loading && <p>Cargando...</p>}
        {error && <p className="auth-error" role="alert">{error}</p>}
        {!loading && !error && featuredRequests.map((request) => (
          <RequestCard
            key={request.id}
            titulo={request.titulo}
            descripcion={request.contenido}
            imagen={request.imagenes?.[0] || ""}
          />
        ))}
      </section>
    </main>
  );
}

export default Home;
