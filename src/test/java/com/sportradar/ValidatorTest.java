package com.sportradar;

import com.sportradar.dto.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorTest {
    private Validator validator;
    private List<Match> matches;

    @BeforeEach
    void setUp() {
        validator = new Validator();
        matches = new ArrayList<>();
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
}