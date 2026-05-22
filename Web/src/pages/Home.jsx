import { useEffect, useState } from "react";
import RequestCard from "../components/RequestCard.jsx";
import { getSolicitudesPublicas } from "../api/client.js";

function Home() {
  const [solicitudes, setSolicitudes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    let cancelled = false;
    getSolicitudesPublicas()
      .then((data) => {
        if (!cancelled) {
          setSolicitudes(data.slice(0, 3));
          setLoading(false);
        }
      })
      .catch(() => {
        if (!cancelled) {
          setError("No se pudieron cargar las solicitudes.");
          setLoading(false);
        }
      });
    return () => { cancelled = true; };
  }, []);

  const featuredRequests = solicitudes;

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
