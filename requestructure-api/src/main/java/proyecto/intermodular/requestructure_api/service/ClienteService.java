package proyecto.intermodular.requestructure_api.service;

import proyecto.intermodular.requestructure_api.api.dto.ClienteDto;
import proyecto.intermodular.requestructure_api.api.dto.request.CreateClienteRequest;
import proyecto.intermodular.requestructure_api.api.dto.request.UpdateClienteRequest;
import proyecto.intermodular.requestructure_api.domain.Cliente;
import proyecto.intermodular.requestructure_api.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public List<ClienteDto> findAll() {
        return clienteRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClienteDto findById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id no puede ser nulo");
        }
        return clienteRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el cliente " + id));
    }

    @Transactional
    public ClienteDto create(CreateClienteRequest req) {
        if (req == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cuerpo no puede estar vacio");
        }
        if (clienteRepository.existsByEmail(req.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un cliente con ese email");
        }
        if (clienteRepository.existsByUsername(req.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un cliente con ese username");
        }

        Cliente cliente = new Cliente();
        cliente.setUsername(req.username().trim());
        cliente.setEmail(req.email().trim().toLowerCase());
        cliente.setDireccion(req.direccion());
        cliente.setPassword(req.password());

        Cliente saved = clienteRepository.save(cliente);
        return toDto(saved);
    }

    @Transactional
    public ClienteDto replace(Long id, UpdateClienteRequest req) {
        if (req == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cuerpo no puede estar vacio");
        }
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el cliente " + id));

        cliente.setUsername(req.username().trim());
        cliente.setEmail(req.email().trim().toLowerCase());
        cliente.setDireccion(req.direccion());
        cliente.setPassword(req.password());

        return toDto(cliente);
    }

    @Transactional
    public void delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el cliente " + id);
        }
        clienteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ClienteDto login(String email, String password) {
        Cliente cliente = clienteRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas"));
        if (!cliente.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas");
        }
        return toDto(cliente);
    }

    private ClienteDto toDto(Cliente cliente) {
        return new ClienteDto(
                cliente.getId(),
                cliente.getUsername(),
                cliente.getEmail(),
                cliente.getDireccion(),
                cliente.getSolicitudes().stream().map(s -> s.getId()).toList()
        );
    }
}
