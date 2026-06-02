package proyecto.intermodular.requestructure_api.service;

import proyecto.intermodular.requestructure_api.api.dto.ComentarioSolicitudDto;
import proyecto.intermodular.requestructure_api.api.dto.request.CreateComentarioRequest;
import proyecto.intermodular.requestructure_api.domain.ComentarioSolicitud;
import proyecto.intermodular.requestructure_api.domain.Solicitud;
import proyecto.intermodular.requestructure_api.repository.ComentarioSolicitudRepository;
import proyecto.intermodular.requestructure_api.repository.SolicitudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class ComentarioSolicitudService {

    private final ComentarioSolicitudRepository comentarioRepository;
    private final SolicitudRepository solicitudRepository;

    public ComentarioSolicitudService(ComentarioSolicitudRepository comentarioRepository, SolicitudRepository solicitudRepository) {
        this.comentarioRepository = comentarioRepository;
        this.solicitudRepository = solicitudRepository;
    }

    @Transactional
    public ComentarioSolicitudDto create(Long solicitudId, CreateComentarioRequest req) {
        if (req == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cuerpo no puede estar vacio");
        }
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la solicitud " + solicitudId));

        ComentarioSolicitud comentario = new ComentarioSolicitud();
        comentario.setSolicitud(solicitud);
        comentario.setAutor(req.autor());
        comentario.setContenido(req.contenido());
        comentario.setFechaHora(LocalDateTime.now());

        if (req.comentarioPadreId() != null) {
            ComentarioSolicitud padre = comentarioRepository.findById(req.comentarioPadreId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el comentario padre " + req.comentarioPadreId()));
            comentario.setComentarioPadre(padre);
            padre.getRespuestas().add(comentario);
        } else {
            solicitud.getComentarios().add(comentario);
        }

        ComentarioSolicitud saved = comentarioRepository.save(comentario);
        return ComentarioSolicitudDto.fromDomain(saved);
    }
}
