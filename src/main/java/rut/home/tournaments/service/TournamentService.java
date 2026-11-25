package rut.home.tournaments.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import rut.home.tournaments.dto.AddTournamentDto;
import rut.home.tournaments.dto.PageResponse;
import rut.home.tournaments.dto.TournamentCreateRequest;
import rut.home.tournaments.dto.TournamentSummary;
import rut.home.tournaments.models.entities.Tournament;

import java.util.List;
import java.util.UUID;

public interface TournamentService {

    List<TournamentSummary> allTournaments();

    TournamentSummary tournamentDetails(String tournamentName);

    TournamentSummary tournamentDetails(UUID id);

    TournamentSummary addTournament(AddTournamentDto form);

    TournamentSummary updateTournament(UUID id, AddTournamentDto source);



    Page<TournamentSummary> allTournamentsPaginated(Pageable pageable);

    void addTournaments(List<AddTournamentDto> forms);

    void updateTournament(String tournamentTitle, AddTournamentDto source);

    void removeTournament(String tournamentTitle);

    void removeTournament(UUID id);
}
