import GuestAccessNotice from "../components/GuestAccessNotice.jsx";

function YourRequests() {
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

export default YourRequests;
