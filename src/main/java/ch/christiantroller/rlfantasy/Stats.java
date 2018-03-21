package ch.christiantroller.rlfantasy;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Stats {
    private final String owner;
    private final List<WeekStats> stats;

    public Stats(String owner, List<WeekStats> stats) {
        this.owner = owner;
        this.stats = stats;
    }

    public int getTotalPoints() {
        return stats.stream()
                .mapToInt(WeekStats::getPoints)
                .sum();
    }

    public String getTeamName() {
        return stats.get(0)
                .getTeamName();
    }

    public Player getOverallTopPlayer() {
        Map<String, Integer> points = stats.stream()
                .map(WeekStats::getPlayers)
                .flatMap(List::stream)
                .collect(Collectors.toMap(Player::getName, Player::getPoints, (s1, s2) -> s1 + s2, HashMap::new));

        return points.entrySet()
                .stream()
                .map(s -> new Player(s.getKey(), s.getValue(), null))
                .max(Comparator.comparing(Player::getPoints))
                .orElse(null);
    }

    public String getOwner() {
        return owner;
    }

    public List<WeekStats> getStats() {
        return stats;
    }
}