package proyecto.intermodular.requestructure_api.api.dto;

import proyecto.intermodular.requestructure_api.domain.Cliente;

import java.util.List;

public record ClienteDto(
        Long id,
        String username,
        String email,
        String direccion,
        List<Long> solicitudesIds
) {
    public static ClienteDto fromDomain(Cliente cliente) {
        return new ClienteDto(
                cliente.getId(),
                cliente.getUsername(),
                cliente.getEmail(),
                cliente.getDireccion(),
                cliente.getSolicitudes().stream()
                        .map(solicitud -> solicitud.getId())
                        .toList()
        );
    }
}
