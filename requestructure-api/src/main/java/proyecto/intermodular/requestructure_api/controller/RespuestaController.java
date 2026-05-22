package proyecto.intermodular.requestructure_api.controller;

import jakarta.validation.Valid;
import proyecto.intermodular.requestructure_api.api.dto.RespuestaDto;
import proyecto.intermodular.requestructure_api.api.dto.request.CreateRespuestaRequest;
import proyecto.intermodular.requestructure_api.api.dto.request.UpdateEstadoRespuestaRequest;
import proyecto.intermodular.requestructure_api.service.RespuestaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    private final RespuestaService respuestaService;

    public RespuestaController(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    @GetMapping
    public ResponseEntity<List<RespuestaDto>> findAll() {
        return ResponseEntity.ok(respuestaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(respuestaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RespuestaDto> create(@Valid @RequestBody CreateRespuestaRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(respuestaService.create(req));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<RespuestaDto> updateEstado(@PathVariable Long id,
                                                   @Valid @RequestBody UpdateEstadoRespuestaRequest req) {
        return ResponseEntity.ok(respuestaService.updateEstado(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        respuestaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
