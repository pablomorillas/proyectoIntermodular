package proyecto.intermodular.requestructure_api.controller;

import jakarta.validation.Valid;
import proyecto.intermodular.requestructure_api.api.dto.EmpresaDto;
import proyecto.intermodular.requestructure_api.api.dto.request.CreateEmpresaRequest;
import proyecto.intermodular.requestructure_api.api.dto.request.UpdateEmpresaRequest;
import proyecto.intermodular.requestructure_api.service.EmpresaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public ResponseEntity<List<EmpresaDto>> findAll() {
        return ResponseEntity.ok(empresaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(empresaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EmpresaDto> create(@Valid @RequestBody CreateEmpresaRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDto> replace(@PathVariable Long id, @Valid @RequestBody UpdateEmpresaRequest req) {
        return ResponseEntity.ok(empresaService.replace(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        empresaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
