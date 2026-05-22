package proyecto.intermodular.requestructure_api.controller;

import jakarta.validation.Valid;
import proyecto.intermodular.requestructure_api.api.dto.ComentarioRespuestaDto;
import proyecto.intermodular.requestructure_api.api.dto.request.CreateComentarioRequest;
import proyecto.intermodular.requestructure_api.service.ComentarioRespuestaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/respuestas/{respuestaId}/comentarios")
public class ComentarioRespuestaController {

    private final ComentarioRespuestaService comentarioService;

    public ComentarioRespuestaController(ComentarioRespuestaService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @PostMapping
    public ResponseEntity<ComentarioRespuestaDto> create(
            @PathVariable Long respuestaId,
            @Valid @RequestBody CreateComentarioRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioService.create(respuestaId, req));
    }
}
