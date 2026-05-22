package proyecto.intermodular.requestructure_api.service;

import proyecto.intermodular.requestructure_api.api.dto.EmpresaDto;
import proyecto.intermodular.requestructure_api.api.dto.request.CreateEmpresaRequest;
import proyecto.intermodular.requestructure_api.api.dto.request.UpdateEmpresaRequest;
import proyecto.intermodular.requestructure_api.domain.Empresa;
import proyecto.intermodular.requestructure_api.repository.EmpresaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Transactional(readOnly = true)
    public List<EmpresaDto> findAll() {
        return empresaRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public EmpresaDto findById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id no puede ser nulo");
        }
        return empresaRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la empresa " + id));
    }

    @Transactional
    public EmpresaDto create(CreateEmpresaRequest req) {
        if (req == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cuerpo no puede estar vacio");
        }
        Empresa empresa = new Empresa();
        empresa.setNombre(req.nombre());
        empresa.setEmail(req.email());
        empresa.setNif(req.nif());
        empresa.setDireccion(req.direccion());

        Empresa saved = empresaRepository.save(empresa);
        return toDto(saved);
    }

    @Transactional
    public EmpresaDto replace(Long id, UpdateEmpresaRequest req) {
        if (req == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cuerpo no puede estar vacio");
        }
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la empresa " + id));

        empresa.setNombre(req.nombre());
        empresa.setEmail(req.email());
        empresa.setNif(req.nif());
        empresa.setDireccion(req.direccion());

        return toDto(empresa);
    }

    @Transactional
    public void delete(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la empresa " + id);
        }
        empresaRepository.deleteById(id);
    }

    private EmpresaDto toDto(Empresa empresa) {
        return new EmpresaDto(
                empresa.getId(),
                empresa.getNombre(),
                empresa.getEmail(),
                empresa.getNif(),
                empresa.getDireccion()
        );
    }
}
