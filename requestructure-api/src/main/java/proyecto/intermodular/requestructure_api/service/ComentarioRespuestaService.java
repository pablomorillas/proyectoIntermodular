package proyecto.intermodular.requestructure_api.service;

import proyecto.intermodular.requestructure_api.api.dto.ComentarioRespuestaDto;
import proyecto.intermodular.requestructure_api.api.dto.request.CreateComentarioRequest;
import proyecto.intermodular.requestructure_api.domain.ComentarioRespuesta;
import proyecto.intermodular.requestructure_api.domain.Respuesta;
import proyecto.intermodular.requestructure_api.repository.ComentarioRespuestaRepository;
import proyecto.intermodular.requestructure_api.repository.RespuestaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class ComentarioRespuestaService {

    private final ComentarioRespuestaRepository comentarioRepository;
    private final RespuestaRepository respuestaRepository;

    public ComentarioRespuestaService(ComentarioRespuestaRepository comentarioRepository, RespuestaRepository respuestaRepository) {
        this.comentarioRepository = comentarioRepository;
        this.respuestaRepository = respuestaRepository;
    }

    @Transactional
    public ComentarioRespuestaDto create(Long respuestaId, CreateComentarioRequest req) {
        if (req == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cuerpo no puede estar vacio");
        }
        Respuesta respuesta = respuestaRepository.findById(respuestaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la respuesta " + respuestaId));

        ComentarioRespuesta comentario = new ComentarioRespuesta();
        comentario.setRespuesta(respuesta);
        comentario.setAutor(req.autor());
        comentario.setContenido(req.contenido());
        comentario.setFechaHora(LocalDateTime.now());

        respuesta.getComentarios().add(comentario);
        ComentarioRespuesta saved = comentarioRepository.save(comentario);
        return ComentarioRespuestaDto.fromDomain(saved);
    }
}
