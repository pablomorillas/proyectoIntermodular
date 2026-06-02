package proyecto.intermodular.requestructure_api.api.dto;

import proyecto.intermodular.requestructure_api.domain.EstadoSolicitud;
import proyecto.intermodular.requestructure_api.domain.Solicitud;

import java.time.LocalDateTime;
import java.util.List;

public record SolicitudDto(
        Long id,
        Long clienteId,
        String clienteUsername,
        String titulo,
        String contenido,
        LocalDateTime fechaHora,
        List<String> imagenes,
        List<ComentarioSolicitudDto> comentarios,
        boolean privada,
        EstadoSolicitud estado
) {
    public static SolicitudDto fromDomain(Solicitud solicitud) {
        return new SolicitudDto(
                solicitud.getId(),
                solicitud.getCliente().getId(),
                solicitud.getCliente().getUsername() != null ? solicitud.getCliente().getUsername() : "Usuario #" + solicitud.getCliente().getId(),
                solicitud.getTitulo(),
                solicitud.getContenido(),
                solicitud.getFechaHora(),
                solicitud.getImagenes(),
                solicitud.getComentarios().stream()
                        .filter(comentario -> comentario.getComentarioPadre() == null)
                        .map(ComentarioSolicitudDto::fromDomain)
                        .toList(),
                solicitud.isPrivada(),
                solicitud.getEstado()
        );
    }
}
