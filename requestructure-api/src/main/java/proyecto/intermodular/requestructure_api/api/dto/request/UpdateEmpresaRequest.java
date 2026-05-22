package proyecto.intermodular.requestructure_api.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateEmpresaRequest(
        @NotBlank @Size(max = 120) String nombre,
        @NotBlank @Email @Size(max = 120) String email,
        @NotBlank @Size(max = 20) String nif,
        @NotBlank @Size(max = 200) String direccion
) {
}
