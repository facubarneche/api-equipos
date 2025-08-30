package equipos.model;

import equipos.model.dto.team.TeamRequestDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String league;
    private String country;

    public void updateFromDTO(TeamRequestDTO dto) {
        this.name = dto.name();
        this.league = dto.league();
        this.country = dto.country();
    }

    public static Team fromDTO(TeamRequestDTO dto) {
        return Team.builder()
                .name(dto.name())
                .league(dto.league())
                .country(dto.country())
                .build();
    }

}