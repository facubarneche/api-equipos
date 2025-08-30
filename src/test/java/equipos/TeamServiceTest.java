package equipos;

import equipos.exception.NotFoundException;
import equipos.model.Team;
import equipos.model.dto.team.TeamRequestDTO;
import equipos.model.dto.team.TeamResponseDTO;
import equipos.repository.TeamRepository;
import equipos.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    private Team team1;
    private Team team2;

    @BeforeEach
    public void setUp() {
        this.team1 = Team.builder()
                .name("Real Madrid")
                .league("La Liga")
                .country("España")
                .build();
        this.team2 = Team.builder()
                .name("FC Barcelona")
                .league("La Liga")
                .country("España")
                .build();
    }

    @Test
    public void getTeamsTest() {
        when(teamRepository.findAll()).thenReturn(List.of(team1, team2));

        List<TeamResponseDTO> result = teamService.getTeams();

        assertEquals("Real Madrid", result.get(0).name());
        assertEquals("La Liga", result.get(1).league());
        verify(teamRepository).findAll();
    }

    @Test
    public void getTeamTest() {
        Long id = 1L;
        when(teamRepository.findById(id)).thenReturn(Optional.of(team1));

        TeamResponseDTO result = teamService.getTeam(id);

        assertEquals("Real Madrid", result.name());
        verify(teamRepository).findById(id);
    }

    @Test
    public void getTeamNotFoundExceptionTest() {
        Long id = 99L;
        when(teamRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> teamService.getTeam(id));
        verify(teamRepository).findById(id);
    }

    @Test
    public void getTeamByName() {
        String stringSearched = "real MA";
        when(teamRepository.findByNameContainingIgnoreCase(stringSearched)).thenReturn(List.of(team1));

        List<TeamResponseDTO> result = teamService.getTeamByName(stringSearched);

        assertEquals("Real Madrid", result.get(0).name());
        assertEquals("España", result.get(0).country());
        verify(teamRepository).findByNameContainingIgnoreCase(stringSearched);
    }

    @Test
    public void getTeamByNameNotFoundExceptionTest() {
        String stringSearched = "real FC";
        when(teamRepository.findByNameContainingIgnoreCase(stringSearched)).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> teamService.getTeamByName(stringSearched));
        verify(teamRepository).findByNameContainingIgnoreCase(stringSearched);
    }

    @Test
    public void createTeamTest() {
        TeamRequestDTO newTeamDTO = new TeamRequestDTO("River Plate", "Liga Argentina", "Argentina");
        when(teamRepository.save(any(Team.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TeamResponseDTO result = teamService.createTeam(newTeamDTO);

        assertEquals("River Plate", result.name());
        verify(teamRepository).save(any(Team.class));
    }


    @Test
    public void editTeamTest() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team2));

        TeamRequestDTO update = new TeamRequestDTO("NewName", "NewLeague", "NewCountry");
        TeamResponseDTO result = teamService.editTeam(1L, update);

        assertEquals("NewName", result.name());
        verify(teamRepository).findById(1L);
        verify(teamRepository).save(team2);
    }

    @Test
    public void editTeamNotFoundExceptionTest() {
        Long id = 99L;
        TeamRequestDTO update = new TeamRequestDTO("River Plate", "Liga Argentina", "Argentina");
        when(teamRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> teamService.editTeam(id, update));
        verify(teamRepository).findById(id);
    }

    @Test
    void deleteTeam() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team2));

        teamService.deleteTeam(1L);

        verify(teamRepository).findById(1L);
        verify(teamRepository).deleteById(1L);
    }

    @Test
    void deleteTeamNotFoundExceptionTest() {
        Long id = 99L;
        when(teamRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> teamService.deleteTeam(id));
        verify(teamRepository).findById(id);
        verify(teamRepository, never()).deleteById(anyLong());
    }
}
