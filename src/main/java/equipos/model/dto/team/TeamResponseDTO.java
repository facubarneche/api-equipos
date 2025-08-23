package equipos.model.dto.team;

import equipos.model.Team;


public record TeamResponseDTO(Long id, String name, String league, String country) {
    public static TeamResponseDTO fromEntity(Team team) {
        return new TeamResponseDTO(
                team.getId(),
                team.getName(),
                team.getLeague(),
                team.getCountry()
        );
    }
}
