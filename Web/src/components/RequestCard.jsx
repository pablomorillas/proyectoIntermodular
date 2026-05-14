function RequestCard({ imagen, titulo, descripcion }) {
  return (
    <article tabIndex="0" className="card-simple" aria-label={titulo}>
      <figure className="card-simple-img-container">
        {imagen ? (
          <img
            src={imagen}
            alt={`Imagen de ${titulo}`}
            loading="lazy"
            className="card-simple-img"
          />
        ) : (
          <div className="card-image-placeholder" aria-hidden="true">
            <span>Placeholder</span>
          </div>
        )}
      </figure>

      <h2 className="card-simple-title">{titulo}</h2>

      {descripcion ? <p className="card-simple-description">{descripcion}</p> : null}
    </article>
  );
}

export default RequestCard;
