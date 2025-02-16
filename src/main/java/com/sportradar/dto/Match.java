package com.sportradar.dto;

import java.time.Instant;

public record Match(String homeTeam, String awayTeam, int homeScore, int awayScore, Instant startTime) {
}