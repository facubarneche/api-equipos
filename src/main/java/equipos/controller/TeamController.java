package equipos.controller;

import equipos.model.dto.team.TeamRequestDTO;
import equipos.model.dto.team.TeamResponseDTO;
import equipos.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/equipos")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    @Operation(summary = "Obtiene todos los equipos", description = "Obtiene la lista completa de equipos")
    public ResponseEntity<List<TeamResponseDTO>> getTeams() {
        List<TeamResponseDTO> teams = teamService.getTeams();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un equipo", description = "Obtiene un equipo por medio del id")
    public ResponseEntity<TeamResponseDTO> getTeam(@PathVariable Long id) {
        TeamResponseDTO team = teamService.getTeam(id);
        return ResponseEntity.ok(team);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Obtiene los equipos por su nombre", description = "Obtiene los equipos por contención filtrando por su respectivo nombre")
    public ResponseEntity<List<TeamResponseDTO>> getTeamByName(@RequestParam String nombre) {
        List<TeamResponseDTO> team = teamService.getTeamByName(nombre);
        return ResponseEntity.ok(team);
    }

    @PostMapping
    @Operation(summary = "Crea nuevo equipo", description = "Crea un nuevo equipo")
    public ResponseEntity<TeamResponseDTO> createTeam(@Valid @RequestBody TeamRequestDTO team) {
        TeamResponseDTO newTeam = teamService.createTeam(team);
        URI location = URI.create("/equipos/" + newTeam.id());
        return ResponseEntity.created(location).body(newTeam);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edita nuevo equipo", description = "Edita un equipo por medio del id")
    public ResponseEntity<TeamResponseDTO> editTeam(@PathVariable Long id, @RequestBody TeamRequestDTO team) {
        TeamResponseDTO editedTeam = teamService.editTeam(id, team);
        return ResponseEntity.ok(editedTeam);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina nuevo equipo", description = "Elimina un equipo por medio del id")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}