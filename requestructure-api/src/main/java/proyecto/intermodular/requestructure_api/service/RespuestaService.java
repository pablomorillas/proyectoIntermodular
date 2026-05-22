package proyecto.intermodular.requestructure_api.service;

import proyecto.intermodular.requestructure_api.api.dto.RespuestaDto;
import proyecto.intermodular.requestructure_api.api.dto.request.CreateRespuestaRequest;
import proyecto.intermodular.requestructure_api.api.dto.request.UpdateEstadoRespuestaRequest;
import proyecto.intermodular.requestructure_api.domain.Cliente;
import proyecto.intermodular.requestructure_api.domain.Empresa;
import proyecto.intermodular.requestructure_api.domain.EstadoRespuesta;
import proyecto.intermodular.requestructure_api.domain.Respuesta;
import proyecto.intermodular.requestructure_api.domain.Solicitud;
import proyecto.intermodular.requestructure_api.repository.ClienteRepository;
import proyecto.intermodular.requestructure_api.repository.EmpresaRepository;
import proyecto.intermodular.requestructure_api.repository.RespuestaRepository;
import proyecto.intermodular.requestructure_api.repository.SolicitudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;
    private final SolicitudRepository solicitudRepository;
    private final EmpresaRepository empresaRepository;
    private final ClienteRepository clienteRepository;

    public RespuestaService(RespuestaRepository respuestaRepository, SolicitudRepository solicitudRepository,
                            EmpresaRepository empresaRepository, ClienteRepository clienteRepository) {
        this.respuestaRepository = respuestaRepository;
        this.solicitudRepository = solicitudRepository;
        this.empresaRepository = empresaRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public List<RespuestaDto> findAll() {
        return respuestaRepository.findAll().stream()
                .map(RespuestaDto::fromDomain)
                .toList();
    }

    @Transactional(readOnly = true)
    public RespuestaDto findById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id no puede ser nulo");
        }
        return respuestaRepository.findById(id)
                .map(RespuestaDto::fromDomain)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la respuesta " + id));
    }

    @Transactional
    public RespuestaDto create(CreateRespuestaRequest req) {
        if (req == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cuerpo no puede estar vacio");
        }
        Solicitud solicitud = solicitudRepository.findById(req.solicitudId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la solicitud " + req.solicitudId()));
        Empresa empresa = empresaRepository.findById(req.empresaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la empresa " + req.empresaId()));
        Cliente cliente = clienteRepository.findById(req.clienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el cliente " + req.clienteId()));

        Respuesta respuesta = new Respuesta();
        respuesta.setSolicitud(solicitud);
        respuesta.setEmpresa(empresa);
        respuesta.setCliente(cliente);
        respuesta.setContenido(req.contenido());
        respuesta.setEstado(EstadoRespuesta.EN_ESPERA);
        respuesta.setFechaHora(LocalDateTime.now());

        solicitud.getRespuestas().add(respuesta);
        empresa.getRespuestas().add(respuesta);
        cliente.getRespuestas().add(respuesta);

        Respuesta saved = respuestaRepository.save(respuesta);
        return RespuestaDto.fromDomain(saved);
    }

    @Transactional
    public RespuestaDto updateEstado(Long id, UpdateEstadoRespuestaRequest req) {
        if (req == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cuerpo no puede estar vacio");
        }
        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la respuesta " + id));
        try {
            respuesta.setEstado(EstadoRespuesta.valueOf(req.estado().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado no valido: " + req.estado());
        }
        return RespuestaDto.fromDomain(respuesta);
    }

    @Transactional
    public void delete(Long id) {
        if (!respuestaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la respuesta " + id);
        }
        respuestaRepository.deleteById(id);
    }
}
