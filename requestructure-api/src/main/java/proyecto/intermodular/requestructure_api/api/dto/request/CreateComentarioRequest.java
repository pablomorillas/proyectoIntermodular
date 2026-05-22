package proyecto.intermodular.requestructure_api.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateComentarioRequest(
        @NotBlank @Size(max = 80) String autor,
        @NotBlank @Size(max = 1500) String contenido
) {
}
