package com.sportradar.dto;

import java.time.Instant;

public record Match(String homeTeam, String awayTeam, int homeScore, int awayScore, Instant startTime) implements Comparable<Match> {

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

    /**
     * Calculates the total score of the match
     *
     * @return the total score of the match
     */
    public int totalScore() {
        return homeScore + awayScore;
    }

    @Override
    public int compareTo(Match other) {
        int scoreComparison = Integer.compare(other.totalScore(), this.totalScore());
        if (scoreComparison != 0) {
            return scoreComparison;
        }
        return other.startTime().compareTo(this.startTime());
    }

    @Override
    public String toString() {
        return homeTeam + " " + homeScore + " - " + awayTeam + " " + awayScore;
    }
}