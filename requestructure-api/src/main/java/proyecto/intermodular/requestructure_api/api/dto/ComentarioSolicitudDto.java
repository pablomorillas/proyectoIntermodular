package proyecto.intermodular.requestructure_api.api.dto;

import proyecto.intermodular.requestructure_api.domain.ComentarioSolicitud;

import java.time.LocalDateTime;
import java.util.List;

public record ComentarioSolicitudDto(
        Long id,
        Long solicitudId,
        String autor,
        String contenido,
        LocalDateTime fechaHora,
        List<ComentarioSolicitudDto> respuestas
) {
    public static ComentarioSolicitudDto fromDomain(ComentarioSolicitud comentario) {
        return new ComentarioSolicitudDto(
                comentario.getId(),
                comentario.getSolicitud().getId(),
                comentario.getAutor(),
                comentario.getContenido(),
                comentario.getFechaHora(),
                comentario.getRespuestas().stream()
                        .map(ComentarioSolicitudDto::fromDomain)
                        .toList()
        );
    }
}
