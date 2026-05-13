function RequestCard({ imagen, titulo, descripcion }) {
    return (
        <article
            tabIndex="0"
            className="card-simple"
            aria-label={titulo}
        >
            <figure className="card-simple-img-container">
                <img
                    src={imagen}
                    alt={`Imagen de ${titulo}`}
                    loading="lazy"
                    className="card-simple-img"
                />
            </figure>

            <h2 className="card-simple-title">
                {titulo}
            </h2>

            {/* Description is hidden in the visual design, but kept in DOM if needed or removed. 
                For now I'll remove it from valid rendering to match the clean look 
            */}
        </article>
    );
}

export default RequestCard;