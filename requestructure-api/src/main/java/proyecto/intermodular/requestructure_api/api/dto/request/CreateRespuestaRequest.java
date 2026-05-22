package proyecto.intermodular.requestructure_api.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateRespuestaRequest(
        @NotNull @Positive Long solicitudId,
        @NotNull @Positive Long empresaId,
        @NotNull @Positive Long clienteId,
        @NotBlank @Size(max = 3000) String contenido
) {
}
