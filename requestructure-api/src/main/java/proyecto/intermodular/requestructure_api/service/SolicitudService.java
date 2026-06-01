package proyecto.intermodular.requestructure_api.service;

import proyecto.intermodular.requestructure_api.api.dto.SolicitudDto;
import proyecto.intermodular.requestructure_api.api.dto.request.CreateSolicitudRequest;
import proyecto.intermodular.requestructure_api.api.dto.request.UpdateSolicitudRequest;
import proyecto.intermodular.requestructure_api.domain.Cliente;
import proyecto.intermodular.requestructure_api.domain.EstadoSolicitud;
import proyecto.intermodular.requestructure_api.domain.Solicitud;
import proyecto.intermodular.requestructure_api.repository.ClienteRepository;
import proyecto.intermodular.requestructure_api.repository.SolicitudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final ClienteRepository clienteRepository;

    public SolicitudService(SolicitudRepository solicitudRepository, ClienteRepository clienteRepository) {
        this.solicitudRepository = solicitudRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public List<SolicitudDto> findAll() {
        return solicitudRepository.findAll().stream()
                .map(SolicitudDto::fromDomain)
                .toList();
    }

    @Transactional(readOnly = true)
    public SolicitudDto findById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id no puede ser nulo");
        }
        return solicitudRepository.findById(id)
                .map(SolicitudDto::fromDomain)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la solicitud " + id));
    }

    @Transactional(readOnly = true)
    public List<SolicitudDto> findPublicas() {
        return solicitudRepository.findAll().stream()
                .filter(s -> !s.isPrivada())
                .map(SolicitudDto::fromDomain)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SolicitudDto> findByClienteId(Long clienteId) {
        return solicitudRepository.findAll().stream()
                .filter(s -> s.getCliente().getId().equals(clienteId))
                .map(SolicitudDto::fromDomain)
                .toList();
    }

    @Transactional
    public SolicitudDto create(CreateSolicitudRequest req) {
        if (req == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cuerpo no puede estar vacio");
        }
        Cliente cliente = clienteRepository.findById(req.clienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el cliente " + req.clienteId()));

        Solicitud solicitud = new Solicitud();
        solicitud.setCliente(cliente);
        solicitud.setTitulo(req.titulo());
        solicitud.setContenido(req.contenido());
        solicitud.setImagenes(req.imagenes() != null ? req.imagenes() : List.of());
        solicitud.setPrivada(req.privada());
        solicitud.setFechaHora(LocalDateTime.now());
        solicitud.setEstado(EstadoSolicitud.ABIERTA);

        cliente.getSolicitudes().add(solicitud);
        Solicitud saved = solicitudRepository.save(solicitud);
        return SolicitudDto.fromDomain(saved);
    }

    @Transactional
    public SolicitudDto replace(Long id, UpdateSolicitudRequest req) {
        if (req == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cuerpo no puede estar vacio");
        }
        Solicitud solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la solicitud " + id));

        solicitud.setTitulo(req.titulo());
        solicitud.setContenido(req.contenido());
        solicitud.setImagenes(req.imagenes() != null ? req.imagenes() : List.of());
        solicitud.setPrivada(req.privada());
        solicitud.setEstado(req.estado());

        return SolicitudDto.fromDomain(solicitud);
    }

    @Transactional
    public void delete(Long id) {
        if (!solicitudRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la solicitud " + id);
        }
        solicitudRepository.deleteById(id);
    }
}
