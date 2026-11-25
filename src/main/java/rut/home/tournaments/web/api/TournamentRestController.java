package rut.home.tournaments.web.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rut.home.tournaments.dto.PageResponse;
import rut.home.tournaments.dto.TournamentCreateRequest;
import rut.home.tournaments.dto.TournamentDetailsResponse;
import rut.home.tournaments.dto.TournamentSummaryResponse;
import rut.home.tournaments.mappers.TournamentMapper;
import rut.home.tournaments.service.TournamentService;

import java.util.UUID;

@RestController
@RequestMapping(path="/api/tournaments", produces = MediaType.APPLICATION_JSON_VALUE)
public class TournamentRestController {
    private final TournamentService tournamentService;
    private final TournamentMapper tournamentMapper;

    public TournamentRestController(TournamentService tournamentService, TournamentMapper tournamentMapper) {
        this.tournamentService = tournamentService;
        this.tournamentMapper = tournamentMapper;
    }

    @GetMapping
    public PageResponse<TournamentSummaryResponse> tournaments(
            @RequestParam(defaultValue = "0") @Min(0) @Max(1000000) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        var request = PageRequest.of(page, size, Sort.by("title").and(Sort.by("id")));
        return tournamentMapper.toPage(tournamentService.allTournamentsPaginated(request));
    }

    @GetMapping("/{id}")
    public TournamentDetailsResponse tournament(@PathVariable UUID id) {
        return tournamentMapper.toDetails(tournamentService.tournamentDetails(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TournamentDetailsResponse> create(@Valid @RequestBody TournamentCreateRequest request) {
        var created = tournamentMapper.toDetails(tournamentService.addTournament(tournamentMapper.toCommand(request)));
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TournamentDetailsResponse replace(@PathVariable UUID id,
                                             @Valid @RequestBody TournamentCreateRequest request) {
        return tournamentMapper.toDetails(tournamentService.updateTournament(id, tournamentMapper.toCommand(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        tournamentService.removeTournament(id);
        return ResponseEntity.noContent().build();
    }

}
