package proyecto.intermodular.requestructure_api.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;
import proyecto.intermodular.requestructure_api.api.dto.ClienteDto;
import proyecto.intermodular.requestructure_api.api.dto.EmpresaDto;
import proyecto.intermodular.requestructure_api.api.dto.RespuestaDto;
import proyecto.intermodular.requestructure_api.api.dto.SolicitudDto;
import proyecto.intermodular.requestructure_api.repository.ClienteRepository;
import proyecto.intermodular.requestructure_api.repository.EmpresaRepository;
import proyecto.intermodular.requestructure_api.repository.RespuestaRepository;
import proyecto.intermodular.requestructure_api.repository.SolicitudRepository;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
@Transactional(readOnly = true)
public class RequestructureController {
    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;
    private final SolicitudRepository solicitudRepository;
    private final RespuestaRepository respuestaRepository;

    public RequestructureController(
            ClienteRepository clienteRepository,
            EmpresaRepository empresaRepository,
            SolicitudRepository solicitudRepository,
            RespuestaRepository respuestaRepository
    ) {
        this.clienteRepository = clienteRepository;
        this.empresaRepository = empresaRepository;
        this.solicitudRepository = solicitudRepository;
        this.respuestaRepository = respuestaRepository;
    }

    @GetMapping("/clientes")
    public List<ClienteDto> getClientes() {
        return clienteRepository.findAll().stream()
                .map(ClienteDto::fromDomain)
                .toList();
    }

    @GetMapping("/empresas")
    public List<EmpresaDto> getEmpresas() {
        return empresaRepository.findAll().stream()
                .map(EmpresaDto::fromDomain)
                .toList();
    }

    @GetMapping("/solicitudes")
    public List<SolicitudDto> getSolicitudes() {
        return solicitudRepository.findAll().stream()
                .map(SolicitudDto::fromDomain)
                .toList();
    }

    @GetMapping("/respuestas")
    public List<RespuestaDto> getRespuestas() {
        return respuestaRepository.findAll().stream()
                .map(RespuestaDto::fromDomain)
                .toList();
    }
}
