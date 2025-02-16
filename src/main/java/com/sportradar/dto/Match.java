package com.sportradar.dto;

import java.time.Instant;

public record Match(String homeTeam, String awayTeam, int homeScore, int awayScore, Instant startTime) {

    /**
     * Updates the score of the match
     *
     * @param homeScore the new home team score
     * @param awayScore the new away team score
     * @return a new match with the updated score
     */
    public Match updateScore(int homeScore, int awayScore) {
        return new Match(homeTeam, awayTeam, homeScore, awayScore, startTime);
    }

    /**
     * Checks if the match is between the given teams
     *
     * @param homeTeam the name of the home team
     * @param awayTeam the name of the away team
     * @return true if the match is between the given teams
     */
    public boolean isMatch(String homeTeam, String awayTeam) {
        return this.homeTeam.equals(homeTeam) && this.awayTeam.equals(awayTeam);
    }
}