package com.sportradar;

import com.sportradar.dto.Match;
import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
public class Scoreboard {
    private final Validator validator;
    private final Clock clock;
    private final List<Match> matches;

    public Scoreboard(Clock clock, Validator validator, List<Match> matches) {
        this.clock = clock;
        this.validator = validator;
        this.matches = Objects.requireNonNullElseGet(matches, ArrayList::new);
    }

    /**
     * Starts a new match between two teams
     *
     * @param homeTeam the name of the home team
     * @param awayTeam the name of the away team
     */
    public void startMatch(String homeTeam, String awayTeam) {
        log.info("Starting a new match between {} and {}", homeTeam, awayTeam);

        validator.validateTeams(homeTeam, awayTeam);
        validator.validateMatchNotStarted(matches, homeTeam, awayTeam);
        Match newMatch = new Match(homeTeam, awayTeam, 0, 0, clock.instant());
        matches.add(newMatch);
    }

    /**
     * Updates the score of a match
     *
     * @param homeTeam  the name of the home team
     * @param awayTeam  the name of the away team
     * @param homeScore the new home team score
     * @param awayScore the new away team score
     */
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        validator.validateScores(homeScore, awayScore);
        validator.validateMatchExists(matches, homeTeam, awayTeam);

        Match match = findMatch(homeTeam, awayTeam);
        matches.remove(match);
        Match updatedMatch = match.updateScore(homeScore, awayScore);
        matches.add(updatedMatch);
    }

    /**
     * Finishes a match between two teams
     *
     * @param homeTeam the name of the home team
     * @param awayTeam the name of the away team
     */
    public void finishMatch(String homeTeam, String awayTeam) {
        validator.validateMatchExists(matches, homeTeam, awayTeam);
        matches.remove(findMatch(homeTeam, awayTeam));
    }

    /**
     * Returns the summary of all matches
     *
     * @return the summary of all matches
     */
    public List<String> getSummary() {
        return matches.stream()
                .sorted(Comparator.comparingInt(Match::totalScore).reversed()
                        .thenComparing(Comparator.comparing(Match::startTime).reversed()))
                .map(Match::toString)
                .toList();
    }

    private Match findMatch(String homeTeam, String awayTeam) {
        return matches.stream()
                .filter(m -> m.isMatch(homeTeam, awayTeam))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Match not found"));
    }
}