package proyecto.intermodular.requestructure_api.controller;

import jakarta.validation.Valid;
import proyecto.intermodular.requestructure_api.api.dto.ComentarioSolicitudDto;
import proyecto.intermodular.requestructure_api.api.dto.request.CreateComentarioRequest;
import proyecto.intermodular.requestructure_api.service.ComentarioSolicitudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solicitudes/{solicitudId}/comentarios")
public class ComentarioSolicitudController {

    private final ComentarioSolicitudService comentarioService;

    public ComentarioSolicitudController(ComentarioSolicitudService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @PostMapping
    public ResponseEntity<ComentarioSolicitudDto> create(
            @PathVariable Long solicitudId,
            @Valid @RequestBody CreateComentarioRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioService.create(solicitudId, req));
    }
}
