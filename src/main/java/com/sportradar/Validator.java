package com.sportradar;

import com.sportradar.dto.Match;

import java.util.List;

public class Validator {

    /**
     * Validates if the match has not started yet
     *
     * @param matches  list of matches
     * @param homeTeam home team name
     * @param awayTeam away team name
     */
    protected void validateMatchNotStarted(List<Match> matches, String homeTeam, String awayTeam) {
        if (isMatchStarted(matches, homeTeam, awayTeam)) {
            throw new IllegalArgumentException("Match has already started");
        }
    }

    /**
     * Validates the team names
     *
     * @param homeTeam home team name
     * @param awayTeam away team name
     */
    protected void validateTeams(String homeTeam, String awayTeam) {
        if (homeTeam == null || awayTeam == null || homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Invalid team names");
        }
    }

    private boolean isMatchStarted(List<Match> matches, String homeTeam, String awayTeam) {
        return matches.stream()
                .anyMatch(match -> match.homeTeam().equals(homeTeam) && match.awayTeam().equals(awayTeam));
    }
}