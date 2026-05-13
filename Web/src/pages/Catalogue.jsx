import RequestCard from '../components/RequestCard.jsx';
import requests from '../data/requests.js';

function Catalogue() {
    return (<>
        <div>
            <h1>Catálogo</h1>
            <p>Aquí aparecerán las peticiones que hayan decidido publicar los usuarios.</p>
        </div>

        <div className="cards-grid">
            {requests.map((req) => (
                <RequestCard
                    key={req.id}
                    imagen={req.imagen}
                    titulo={req.titulo}
                    descripcion={req.descripcion}
                />
            ))}
        </div>
    </>
    );
}

export default Catalogue;