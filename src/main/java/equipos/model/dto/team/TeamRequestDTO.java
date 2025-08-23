package equipos.model.dto.team;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record TeamRequestDTO(
        @NotBlank
        String name,

        @NotBlank
        String league,

        @NotBlank
        String country
) {
}
