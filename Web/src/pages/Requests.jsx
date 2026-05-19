import RequestCard from "../components/RequestCard.jsx";
import requests from "../data/requests.js";

function Requests() {
  const generalRequests = requests.filter((req) => req.tipo === "general");

  return (
    <main id="main-content" className="page-main">
      <section className="page-heading">
        <h2>Solicitudes</h2>
        <p>Publicaciones abiertas de toda la comunidad en formato foro.</p>
      </section>

      <section className="forum-section" aria-label="Solicitudes del foro">
        <div className="forum-section-head">
          <h2>Solicitudes</h2>
          <span>{generalRequests.length} publicaciones</span>
        </div>

        <div className="cards-grid">
          {generalRequests.map((req) => (
            <RequestCard
              key={req.id}
              titulo={req.titulo}
              descripcion={req.descripcion}
              imagen={req.imagen}
            />
          ))}
        </div>
      </section>
    </main>
  );
}

export default Requests;
