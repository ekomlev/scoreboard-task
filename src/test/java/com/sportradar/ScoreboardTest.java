package com.sportradar;

import com.sportradar.dto.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class ScoreboardTest {
    private Scoreboard scoreboard;
    private Clock clock;
    private Validator validator;
    private List<Match> matches;

    @BeforeEach
    void setUp() {
        clock = Clock.fixed(Instant.parse("2025-02-02T12:00:00Z"), ZoneId.of("UTC"));
        validator = mock(Validator.class);
        matches = new ArrayList<>();
        scoreboard = new Scoreboard(clock, validator, matches);
    }

    @Test
    void startMatch_ShouldAddMatchToList() {
        // Given
        doNothing().when(validator).validateTeams("Mexico", "Canada");
        doNothing().when(validator).validateMatchNotStarted(anyList(), eq("Mexico"), eq("Canada"));

        // When
        scoreboard.startMatch("Mexico", "Canada");

        // Then
        assertEquals(1, matches.size());
        assertEquals("Mexico", matches.get(0).homeTeam());
        assertEquals("Canada", matches.get(0).awayTeam());
    }

    @Test
    void startMatch_ShouldThrowException_WhenTeamsAreSame() {
        // Given
        doThrow(new IllegalArgumentException()).when(validator).validateTeams("Mexico", "Mexico");
        doNothing().when(validator).validateMatchNotStarted(anyList(), eq("Mexico"), eq("Mexico"));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("Mexico", "Mexico"));
    }

    @Test
    void startMatch_ShouldThrowException_WhenMatchAlreadyStarted() {
        // Given
        doNothing().when(validator).validateTeams("Mexico", "Canada");
        doThrow(new IllegalArgumentException()).when(validator).validateMatchNotStarted(anyList(), eq("Mexico"), eq("Canada"));

        // When
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("Mexico", "Canada"));
    }

    @Test
    void updateScore_ShouldNotThrowException_WhenScoresAndMatchAreValid() {
        // Given
        doNothing().when(validator).validateScores(1, 2);
        doNothing().when(validator).validateMatchExists(matches, "Mexico", "Canada");
        Match match = new Match("Mexico", "Canada", 0, 0, clock.instant());
        matches.add(match);
        scoreboard = new Scoreboard(clock, validator, matches);

        // When
        scoreboard.updateScore("Mexico", "Canada", 1, 2);

        // Then
        assertDoesNotThrow(() -> scoreboard.updateScore("Mexico", "Canada", 1, 2));
        assertEquals(1, matches.size());
        assertEquals(1, matches.get(0).homeScore());
        assertEquals(2, matches.get(0).awayScore());
    }

    @Test
    void updateScore_ShouldThrowException_WhenMatchDoesNotExist() {
        // Given
        doNothing().when(validator).validateScores(1, 2);
        doThrow(new IllegalArgumentException("Match not found")).when(validator).validateMatchExists(matches, "Mexico", "Canada");

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore("Mexico", "Canada", 1, 2));
    }

    @Test
    void updateScore_ShouldThrowException_WhenScoresAreInvalid() {
        // Given
        doNothing().when(validator).validateMatchExists(matches, "Mexico", "Canada");
        doThrow(new IllegalArgumentException("Invalid scores")).when(validator).validateScores(-1, 2);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore("Mexico", "Canada", -1, 2));
    }

    @Test
    void finishMatch_ShouldNotThrowException_WhenMatchExists() {
        // Given
        doNothing().when(validator).validateMatchExists(matches, "Mexico", "Canada");
        Match match = new Match("Mexico", "Canada", 0, 0, clock.instant());
        matches.add(match);

        // When
        scoreboard.finishMatch("Mexico", "Canada");

        // Then
        assertEquals(0, matches.size());
    }

    @Test
    void finishMatch_ShouldThrowException_WhenMatchDoesNotExist() {
        // Given
        doThrow(new IllegalArgumentException("Match not found")).when(validator).validateMatchExists(matches, "Mexico", "Canada");

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> scoreboard.finishMatch("Mexico", "Canada"));
    }
}