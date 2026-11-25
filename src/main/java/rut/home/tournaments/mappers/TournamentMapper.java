package rut.home.tournaments.mappers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import rut.home.tournaments.dto.*;

@Component
public class TournamentMapper {
    public AddTournamentDto toCommand(TournamentCreateRequest source) {
        AddTournamentDto result = new AddTournamentDto();
        result.setTitle(source.title());
        result.setDescription(source.description());
        result.setMaxPlayers(source.maxPlayers());
        result.setPrizeFund(source.prizeFund());
        result.setStartsAt(source.startsAt());
        return result;
    }

    public TournamentDetailsResponse toDetails(TournamentSummary source) {
        return new TournamentDetailsResponse(source.id(), source.title(), source.description(),
                source.maxPlayers(), source.playerCount(), source.prizeFund(), source.startsAt(), source.status());
    }

    public TournamentSummaryResponse toSummary(TournamentSummary source) {
        return new TournamentSummaryResponse(source.id(), source.title(), source.playerCount(),
                source.prizeFund(), source.status());
    }

    public PageResponse<TournamentSummaryResponse> toPage(Page<TournamentSummary> source) {
        return new PageResponse<>(source.getContent().stream().map(this::toSummary).toList(),
                source.getNumber(), source.getSize(), source.getTotalElements(),
                source.getTotalPages(), source.isFirst(), source.isLast());
    }
}
