package proyecto.intermodular.requestructure_api.api.dto;

import proyecto.intermodular.requestructure_api.domain.ComentarioRespuesta;

import java.time.LocalDateTime;
import java.util.List;

public record ComentarioRespuestaDto(
        Long id,
        Long respuestaId,
        String autor,
        String contenido,
        LocalDateTime fechaHora,
        List<ComentarioRespuestaDto> respuestas
) {
    public static ComentarioRespuestaDto fromDomain(ComentarioRespuesta comentario) {
        return new ComentarioRespuestaDto(
                comentario.getId(),
                comentario.getRespuesta().getId(),
                comentario.getAutor(),
                comentario.getContenido(),
                comentario.getFechaHora(),
                comentario.getRespuestas().stream()
                        .map(ComentarioRespuestaDto::fromDomain)
                        .toList()
        );
    }
}
