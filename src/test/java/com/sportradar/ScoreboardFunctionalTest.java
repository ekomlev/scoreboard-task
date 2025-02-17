package com.sportradar;

import com.sportradar.dto.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreboardFunctionalTest {
    private Scoreboard scoreboard;
    private SortedSet<Match> matches;

    @BeforeEach
    void setUp() {
        Validator validator = new Validator();
        Clock clock = Clock.systemDefaultZone();
        matches = new TreeSet<>();
        scoreboard = new Scoreboard(clock, validator, matches);
    }

    @Test
    void shouldUpdateScoreSuccessfully() {
        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.updateScore("Spain", "Brazil", 10, 2);

        assertEquals(1, matches.size());
        assertEquals(10, matches.first().homeScore());
        assertEquals(2, matches.first().awayScore());
    }

    @Test
    void shouldFinishMatchSuccessfully() {
        scoreboard.startMatch("Argentina", "Australia");
        scoreboard.finishMatch("Argentina", "Australia");

        assertTrue(matches.isEmpty());
    }

    @Test
    void shouldReturnSummarySortedByTotalScoreAndStartTime() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 0, 5);

        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.updateScore("Spain", "Brazil", 10, 2);

        scoreboard.startMatch("Germany", "France");
        scoreboard.updateScore("Germany", "France", 2, 2);

        scoreboard.startMatch("Uruguay", "Italy");
        scoreboard.updateScore("Uruguay", "Italy", 6, 6);

        scoreboard.startMatch("Argentina", "Australia");
        scoreboard.updateScore("Argentina", "Australia", 3, 1);

        List<String> summary = scoreboard.getSummary();

        assertEquals(5, summary.size());
        assertEquals("Uruguay 6 - Italy 6", summary.get(0));
        assertEquals("Spain 10 - Brazil 2", summary.get(1));
        assertEquals("Mexico 0 - Canada 5", summary.get(2));
        assertEquals("Argentina 3 - Australia 1", summary.get(3));
        assertEquals("Germany 2 - France 2", summary.get(4));
    }
}