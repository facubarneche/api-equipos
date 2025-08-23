package equipos.service;

import equipos.exception.NotFoundException;
import equipos.model.Team;
import equipos.model.dto.team.TeamRequestDTO;
import equipos.model.dto.team.TeamResponseDTO;
import equipos.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<TeamResponseDTO> getTeams() {
        return teamRepository.findAll().stream()
                .map(TeamResponseDTO::fromEntity)
                .toList();
    }

    public TeamResponseDTO getTeam(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new NotFoundException("Equipo no encontrado"));
        return TeamResponseDTO.fromEntity(team);
    }

    public List<TeamResponseDTO> getTeamByName(String name) {
        List<Team> team = teamRepository.findByNameContainingIgnoreCase(name);

        if (team.isEmpty()) {
            throw new NotFoundException("No se encontro ningun equipo.");
        }

        return team.stream()
                .map(TeamResponseDTO::fromEntity)
                .toList();
    }

    public TeamResponseDTO createTeam(TeamRequestDTO team) {
        Team newTeam = Team.fromDTO(team);
        teamRepository.save(newTeam);
        return TeamResponseDTO.fromEntity(newTeam);
    }

    public TeamResponseDTO editTeam(Long id, TeamRequestDTO team) {
        Team teamToEdit = teamRepository.findById(id).orElseThrow(() -> new NotFoundException("Equipo no encontrado"));
        teamToEdit.updateFromDTO(team);
        teamRepository.save(teamToEdit);
        return TeamResponseDTO.fromEntity(teamToEdit);
    }

    public void deleteTeam(Long id) {
        teamRepository.findById(id).orElseThrow(() -> new NotFoundException("Equipo no encontrado"));
        teamRepository.deleteById(id);
    }
}
