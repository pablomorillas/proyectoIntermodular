import { useSearchParams } from "react-router-dom";
import { useSolicitudes } from "../hook/useSolicitudes";
import { useAuth } from "../context/AuthContext.jsx";
import GuestAccessNotice from "../components/GuestAccessNotice.jsx";
import RequestCard from "../components/RequestCard.jsx";

function YourRequests() {
  const { isGuest, user } = useAuth();
  const { data: solicitudes, loading, error } = useSolicitudes();
  const [searchParams] = useSearchParams();
  const search = searchParams.get("search") || "";

  let mine = isGuest ? [] : solicitudes.filter((s) => s.clienteId === user?.id);

  if (search) {
    mine = mine.filter(
      (s) =>
        s.titulo?.toLowerCase().includes(search.toLowerCase()) ||
        s.contenido?.toLowerCase().includes(search.toLowerCase())
    );
  }

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
        {!loading && !error && mine.length === 0 && (
          <p>
            {search
              ? "No se encontraron solicitudes que coincidan con tu busqueda."
              : "Aun no has creado ninguna solicitud."}
          </p>
        )}
        {!loading && !error && mine.map((req) => (
          <RequestCard
            key={req.id}
            id={req.id}
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
