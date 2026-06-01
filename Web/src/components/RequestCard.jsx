import { Link } from "react-router-dom";

function RequestCard({ id, imagen, titulo, descripcion }) {
  const inner = (
    <>
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
    </>
  );

  if (id) {
    return (
      <Link to={`/solicitudes/${id}`} className="card-simple" style={{ textDecoration: "none", display: "block" }} aria-label={titulo}>
        {inner}
      </Link>
    );
  }

  return (
    <article tabIndex="0" className="card-simple" aria-label={titulo}>
      {inner}
    </article>
  );
}

export default RequestCard;
