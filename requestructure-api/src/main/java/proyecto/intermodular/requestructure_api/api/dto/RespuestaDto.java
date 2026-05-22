package proyecto.intermodular.requestructure_api.api.dto;

import proyecto.intermodular.requestructure_api.domain.EstadoRespuesta;
import proyecto.intermodular.requestructure_api.domain.Respuesta;

import java.time.LocalDateTime;
import java.util.List;

public record RespuestaDto(
        Long id,
        Long solicitudId,
        Long empresaId,
        Long clienteId,
        EstadoRespuesta estado,
        String contenido,
        List<ComentarioRespuestaDto> comentarios,
        LocalDateTime fechaHora
) {
    public static RespuestaDto fromDomain(Respuesta respuesta) {
        return new RespuestaDto(
                respuesta.getId(),
                respuesta.getSolicitud().getId(),
                respuesta.getEmpresa().getId(),
                respuesta.getCliente().getId(),
                respuesta.getEstado(),
                respuesta.getContenido(),
                respuesta.getComentarios().stream()
                        .filter(comentario -> comentario.getComentarioPadre() == null)
                        .map(ComentarioRespuestaDto::fromDomain)
                        .toList(),
                respuesta.getFechaHora()
        );
    }
}
