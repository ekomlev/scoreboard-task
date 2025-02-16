package com.sportradar;

import com.sportradar.dto.Match;
import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
import java.util.ArrayList;
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
}