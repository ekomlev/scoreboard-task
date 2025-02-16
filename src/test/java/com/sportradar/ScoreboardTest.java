package com.sportradar;

import com.sportradar.dto.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
}