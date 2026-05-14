import RequestCard from "../components/RequestCard.jsx";
import requests from "../data/requests.js";

function Home() {
  const featuredRequests = requests.slice(0, 3);

  return (
    <main id="main-content" className="page-main home-page">
      <section className="hero-section" aria-label="Destacado">
        <div className="hero-copy">
          <h2>Productos mas interesantes</h2>
        </div>

        <div className="hero-media">
          <div className="hero-image-placeholder" role="img" aria-label="Imagen destacada pendiente" />
          <button type="button" className="hero-action-btn">
            Ver mas
          </button>
        </div>
      </section>

      <section className="cards-grid" aria-label="Productos destacados">
        {featuredRequests.map((request) => (
          <RequestCard
            key={request.id}
            titulo={request.titulo}
            descripcion={request.descripcion}
            imagen={request.imagen}
          />
        ))}
      </section>
    </main>
  );
}

export default Home;
