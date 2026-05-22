package proyecto.intermodular.requestructure_api.controller;

import jakarta.validation.Valid;
import proyecto.intermodular.requestructure_api.api.dto.SolicitudDto;
import proyecto.intermodular.requestructure_api.api.dto.request.CreateSolicitudRequest;
import proyecto.intermodular.requestructure_api.api.dto.request.UpdateSolicitudRequest;
import proyecto.intermodular.requestructure_api.service.SolicitudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @GetMapping
    public ResponseEntity<List<SolicitudDto>> findAll() {
        return ResponseEntity.ok(solicitudService.findAll());
    }

    @GetMapping("/publicas")
    public ResponseEntity<List<SolicitudDto>> findPublicas() {
        return ResponseEntity.ok(solicitudService.findPublicas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(solicitudService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SolicitudDto> create(@Valid @RequestBody CreateSolicitudRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitudService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitudDto> replace(@PathVariable Long id, @Valid @RequestBody UpdateSolicitudRequest req) {
        return ResponseEntity.ok(solicitudService.replace(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        solicitudService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
