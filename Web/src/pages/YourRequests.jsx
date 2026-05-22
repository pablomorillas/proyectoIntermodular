import { useEffect, useState } from "react";
import GuestAccessNotice from "../components/GuestAccessNotice.jsx";
import RequestCard from "../components/RequestCard.jsx";
import { useAuth } from "../context/AuthContext.jsx";
import { getSolicitudes } from "../api/client.js";

function YourRequests() {
  const { isGuest, user } = useAuth();
  const [solicitudes, setSolicitudes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    if (isGuest) return;
    let cancelled = false;
    getSolicitudes()
      .then((data) => {
        if (!cancelled) {
          const mine = data.filter((s) => s.clienteId === user.id);
          setSolicitudes(mine);
          setLoading(false);
        }
      })
      .catch(() => {
        if (!cancelled) {
          setError("No se pudieron cargar tus solicitudes.");
          setLoading(false);
        }
      });
    return () => { cancelled = true; };
  }, [isGuest, user]);

  if (isGuest) {
    return (
      <main id="main-content" className="page-main">
        <section className="page-heading">
          <h2>Tus solicitudes</h2>
          <p>Espacio reservado para consultar y crear tus propias publicaciones.</p>
        </section>

        <GuestAccessNotice
          title="Acceso reservado"
          description="Estas navegando como invitado. Puedes ver las solicitudes publicas, pero necesitas iniciar sesion para crear o revisar tus solicitudes."
        />
      </main>
    );
  }

  return (
    <main id="main-content" className="page-main">
      <section className="page-heading">
        <h2>Tus solicitudes</h2>
        <p>Espacio reservado para consultar y crear tus propias publicaciones.</p>
      </section>

      {loading && <p>Cargando...</p>}
      {error && <p className="auth-error" role="alert">{error}</p>}

      <section className="cards-grid" aria-label="Tus solicitudes">
        {!loading && !error && solicitudes.length === 0 && (
          <p>Aun no has creado ninguna solicitud.</p>
        )}
        {!loading && !error && solicitudes.map((req) => (
          <RequestCard
            key={req.id}
            titulo={req.titulo}
            descripcion={req.contenido}
            imagen={req.imagenes?.[0] || ""}
          />
        ))}
      </section>
    </main>
  );
}

export default YourRequests;
