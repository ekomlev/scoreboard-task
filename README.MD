# Live Football World Cup Scoreboard Library

## Overview

Provides functionality to manage a live scoreboard for ongoing football matches during the World Cup. It supports operations to start new matches, update scores, finish matches, and retrieve a summary of matches in progress.

## Features

1. **Start a New Match**: Adds a new match to the scoreboard with an initial score of 0-0.
    - Parameters:
        - Home team
        - Away team

2. **Update Score**: Updates the score of an ongoing match.
    - Parameters:
        - Home team score
        - Away team score

3. **Finish Match**: Removes a match from the scoreboard.

4. **Get Summary**: Retrieves a summary of matches in progress, ordered by their total score. Matches with the same total score are ordered by the most recently started match.

## Installation

To include this library in your project, add the following dependency to your `pom.xml` if you are using Maven:

```xml
<dependency>
    <groupId>com.sportradar</groupId>
    <artifactId>scoreboard</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage

### Starting a New Match

```java
Scoreboard scoreboard = new Scoreboard(clock, validator, matches);
scoreboard.startMatch("HomeTeam", "AwayTeam");
```

### Updating the Score

```java
scoreboard.updateScore("HomeTeam", "AwayTeam", homeScore, awayScore);
```

### Finishing a Match

```java
scoreboard.finishMatch("HomeTeam", "AwayTeam");
```

### Getting the Summary

```java
List<String> summary = scoreboard.getSummary();
for (String matchSummary : summary) {
    System.out.println(matchSummary);
}
```
#### Example

For example, if the following matches are started in the specified order and their scores are respectively updated:

1. Mexico 0 - Canada 5
2. Spain 10 - Brazil 2
3. Germany 2 - France 2
4. Uruguay 6 - Italy 6
5. Argentina 3 - Australia 1

The summary should be as follows:

1. Uruguay 6 - Italy 6
2. Spain 10 - Brazil 2
3. Mexico 0 - Canada 5
4. Argentina 3 - Australia 1
5. Germany 2 - France 2

## Running Tests

To run the tests for this library, use the following command:

```sh
mvn test
```