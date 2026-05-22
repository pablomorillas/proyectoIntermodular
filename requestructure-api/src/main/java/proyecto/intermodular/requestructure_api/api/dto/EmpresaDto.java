package proyecto.intermodular.requestructure_api.api.dto;

import proyecto.intermodular.requestructure_api.domain.Empresa;

public record EmpresaDto(
        Long id,
        String nombre,
        String email,
        String nif,
        String direccion
) {
    public static EmpresaDto fromDomain(Empresa empresa) {
        return new EmpresaDto(
                empresa.getId(),
                empresa.getNombre(),
                empresa.getEmail(),
                empresa.getNif(),
                empresa.getDireccion()
        );
    }
}
