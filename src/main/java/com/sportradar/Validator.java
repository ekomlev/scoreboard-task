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

    /**
     * Validates the scores
     *
     * @param homeScore home team score
     * @param awayScore away team score
     */
    public void validateScores(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
    }

    /**
     * Validates if the match exists
     *
     * @param matches  list of matches
     * @param homeTeam home team name
     * @param awayTeam away team name
     */
    public void validateMatchExists(List<Match> matches, String homeTeam, String awayTeam) {
        if (isMatchNotFound(matches, homeTeam, awayTeam)) {
            throw new IllegalArgumentException("Match not found");
        }
    }

    private boolean isMatchStarted(List<Match> matches, String homeTeam, String awayTeam) {
        return matches.stream()
                .anyMatch(match -> match.homeTeam().equals(homeTeam) && match.awayTeam().equals(awayTeam));
    }

    private boolean isMatchNotFound(List<Match> matches, String homeTeam, String awayTeam) {
        return matches.stream()
                .noneMatch(m -> m.isMatch(homeTeam, awayTeam));
    }
}