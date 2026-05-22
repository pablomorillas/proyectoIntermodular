package proyecto.intermodular.requestructure_api.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateSolicitudRequest(
        @NotNull @Positive Long clienteId,
        @NotBlank @Size(max = 160) String titulo,
        @NotBlank @Size(max = 3000) String contenido,
        List<@NotBlank @Size(max = 500) String> imagenes,
        boolean privada
) {
}
