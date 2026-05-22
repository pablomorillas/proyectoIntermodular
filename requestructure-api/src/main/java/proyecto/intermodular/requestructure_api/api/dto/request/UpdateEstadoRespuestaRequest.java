package proyecto.intermodular.requestructure_api.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateEstadoRespuestaRequest(
        @NotBlank String estado
) {
}
