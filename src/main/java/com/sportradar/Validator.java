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
        //add validation to check if the match has not started yet
    }

    /**
     * Validates the team names
     *
     * @param homeTeam home team name
     * @param awayTeam away team name
     */
    protected void validateTeams(String homeTeam, String awayTeam) {
        //add validation to check if the team names are valid
    }
}