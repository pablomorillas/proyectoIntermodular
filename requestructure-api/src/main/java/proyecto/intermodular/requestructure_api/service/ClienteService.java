package proyecto.intermodular.requestructure_api.service;

import proyecto.intermodular.requestructure_api.api.dto.ClienteDto;
import proyecto.intermodular.requestructure_api.api.dto.request.CreateClienteRequest;
import proyecto.intermodular.requestructure_api.api.dto.request.UpdateClienteRequest;
import proyecto.intermodular.requestructure_api.config.PasswordHasher;
import proyecto.intermodular.requestructure_api.domain.Cliente;
import proyecto.intermodular.requestructure_api.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

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
        cliente.setPassword(PasswordHasher.hash(req.password()));

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

        String newEmail = req.email().trim().toLowerCase();
        String newUsername = req.username().trim();

        if (!newEmail.equals(cliente.getEmail()) && clienteRepository.existsByEmail(newEmail)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un cliente con ese email");
        }
        if (!newUsername.equals(cliente.getUsername()) && clienteRepository.existsByUsername(newUsername)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un cliente con ese username");
        }

        cliente.setUsername(newUsername);
        cliente.setEmail(newEmail);
        cliente.setDireccion(req.direccion());
        cliente.setPassword(PasswordHasher.hash(req.password()));

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
        String normalizedEmail = email.trim().toLowerCase();
        logger.info("Login attempt for email: {}", normalizedEmail);
        Cliente cliente = clienteRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas"));
        String storedHash = cliente.getPassword();
        boolean matches = PasswordHasher.matches(password, storedHash);
        logger.info("Login hash check for {}: rawLen={}, storedHashPrefix={}, matches={}",
                normalizedEmail, password.length(),
                storedHash != null ? storedHash.substring(0, Math.min(10, storedHash.length())) : "null",
                matches);
        if (!matches) {
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
