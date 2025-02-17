package com.sportradar;

import com.sportradar.dto.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorTest {
    private Validator validator;
    private SortedSet<Match> matches;

    @BeforeEach
    void setUp() {
        validator = new Validator();
        matches = new TreeSet<>();
    }

    @Test
    void validateTeams_ShouldThrowException_WhenTeamsAreSame() {
        assertThrows(IllegalArgumentException.class, () -> validator.validateTeams("Mexico", "Mexico"));
    }

    @Test
    void validateTeams_ShouldThrowException_WhenHomeTeamIsNull() {
        assertThrows(IllegalArgumentException.class, () -> validator.validateTeams(null, "Canada"));
    }

    @Test
    void validateTeams_ShouldThrowException_WhenAwayTeamIsNull() {
        assertThrows(IllegalArgumentException.class, () -> validator.validateTeams("Mexico", null));
    }

    @Test
    void validateTeams_ShouldNotThrowException_WhenTeamsAreDifferent() {
        assertDoesNotThrow(() -> validator.validateTeams("Mexico", "Canada"));
    }

    @Test
    void validateMatchNotStarted_ShouldThrowException_WhenMatchAlreadyStarted() {
        matches.add(new Match("Mexico", "Canada", 0, 0, Instant.now()));
        assertThrows(IllegalArgumentException.class, () -> validator.validateMatchNotStarted(matches, "Mexico", "Canada"));
    }

    @Test
    void validateMatchNotStarted_ShouldNotThrowException_WhenMatchNotStarted() {
        assertDoesNotThrow(() -> validator.validateMatchNotStarted(matches, "Mexico", "Canada"));
    }

    @Test
    void validateScores_ShouldNotThrowException_WhenScoresAreValid() {
        assertDoesNotThrow(() -> validator.validateScores(1, 2));
    }

    @Test
    void validateScores_ShouldThrowException_WhenHomeScoreIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> validator.validateScores(-1, 2));
    }

    @Test
    void validateScores_ShouldThrowException_WhenAwayScoreIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> validator.validateScores(1, -2));
    }

    @Test
    void validateMatchExists_ShouldNotThrowException_WhenMatchExists() {
        matches.add(new Match("Mexico", "Canada", 0, 0, Instant.parse("2025-02-02T12:00:00Z")));
        assertDoesNotThrow(() -> validator.validateMatchExists(matches, "Mexico", "Canada"));
    }

    @Test
    void validateMatchExists_ShouldThrowException_WhenMatchDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> validator.validateMatchExists(matches, "Mexico", "Canada"));
    }
}