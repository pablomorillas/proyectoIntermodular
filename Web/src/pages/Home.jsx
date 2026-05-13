import RequestCard from '../components/RequestCard.jsx';
import requests from '../data/requests.js';

function Home() {
  // Using a specific image for the banner or just one from data
  const bannerImage = "https://images.unsplash.com/photo-1542601906990-24cbd03194e5?ixlib=rb-4.0.3&auto=format&fit=crop&w=1470&q=80"; // Placeholder green building

  return (
    <main className="home-container">
      <section className="hero-section">
        <div className="hero-text">
          <h1 className="hero-title">Productos más<br />interesantes</h1>
        </div>
        <div className="hero-banner-container">
          <img src={bannerImage} alt="Banner Building" className="hero-banner-img" />
          <button className="hero-overlay-btn">Ver más</button>
        </div>
      </section>

      <section>
        {/* The design shows the grid directly below. It doesn't show a section title for the grid, but I can add one if needed. 
                     The design shows "Productos más interesantes" as the main visual anchor. Maybe the grid refers to these products?
                     Actually, the 3 cards below are likely the "products".
                 */}
        <div className="cards-grid">
          {requests.slice(0, 3).map((req) => (
            <RequestCard
              key={req.id}
              imagen={req.imagen}
              titulo="Edificio industrial"
              descripcion={req.descripcion}
            />
          ))}
        </div>
      </section>
    </main>
  );
}

export default Home;