package proyecto.intermodular.requestructure_api.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateClienteRequest(
        @NotBlank @Size(max = 80) String username,
        @NotBlank @Email @Size(max = 120) String email,
        @NotBlank @Size(max = 200) String direccion,
        @NotBlank @Size(min = 4, max = 255) String password
) {
}
