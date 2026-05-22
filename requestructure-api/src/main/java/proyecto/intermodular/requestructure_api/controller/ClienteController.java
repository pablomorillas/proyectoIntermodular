package proyecto.intermodular.requestructure_api.controller;

import jakarta.validation.Valid;
import proyecto.intermodular.requestructure_api.api.dto.ClienteDto;
import proyecto.intermodular.requestructure_api.api.dto.request.CreateClienteRequest;
import proyecto.intermodular.requestructure_api.api.dto.request.UpdateClienteRequest;
import proyecto.intermodular.requestructure_api.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDto>> findAll() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ClienteDto> create(@Valid @RequestBody CreateClienteRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> replace(@PathVariable Long id, @Valid @RequestBody UpdateClienteRequest req) {
        return ResponseEntity.ok(clienteService.replace(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
