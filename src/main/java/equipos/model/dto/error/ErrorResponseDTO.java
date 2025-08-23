package equipos.model.dto.error;

import lombok.Builder;

@Builder
public record ErrorResponseDTO(
        String message,
        int code
) {

}
