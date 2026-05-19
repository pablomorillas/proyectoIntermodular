function Contact() {
  return (
    <main id="main-content" className="page-main">
      <section className="page-heading">
        <h2>Contacto</h2>
        <p>
          Si necesitas ayuda con una solicitud o quieres publicar un nuevo proyecto,
          escríbenos y te responderemos lo antes posible.
        </p>
      </section>

      <section className="forum-section" aria-label="Informacion de contacto">
        <div className="cards-grid">
          <article className="card-simple" aria-label="Correo de soporte">
            <h2 className="card-simple-title">Soporte</h2>
            <p className="card-simple-description">soporte@requestructure.com</p>
          </article>

          <article className="card-simple" aria-label="Telefono de atencion">
            <h2 className="card-simple-title">Telefono</h2>
            <p className="card-simple-description">+34 900 123 456</p>
          </article>

          <article className="card-simple" aria-label="Horario de atencion">
            <h2 className="card-simple-title">Horario</h2>
            <p className="card-simple-description">Lunes a Viernes, 9:00 a 18:00</p>
          </article>
        </div>
      </section>
    </main>
  );
}

export default Contact;
