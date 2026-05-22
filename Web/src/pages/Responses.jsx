import GuestAccessNotice from "../components/GuestAccessNotice.jsx";

function Responses() {
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

export default Responses;
