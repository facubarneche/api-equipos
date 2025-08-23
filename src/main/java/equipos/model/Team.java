package equipos.model;

import equipos.model.dto.team.TeamRequestDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String league;
    private String country;

    public Team() {
    }

    public Team(String name, String league, String country) {
        this.name = name;
        this.league = league;
        this.country = country;
    }

    public void updateFromDTO(TeamRequestDTO dto) {
        this.name = dto.name();
        this.league = dto.league();
        this.country = dto.country();
    }

    public static Team fromDTO(TeamRequestDTO dto) {
        return new Team(dto.name(), dto.league(), dto.country());
    }

}