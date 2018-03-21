package ch.christiantroller.rlfantasy;

import java.util.Comparator;
import java.util.List;

class WeekStats {
    private final String teamName;
    private int points;
    private final List<Player> players;

    public WeekStats(String teamName, int points, List<Player> players) {
        this.teamName = teamName;
        this.points = points;
        this.players = players;
    }

    public int getPoints() {
        return points;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getTeamName() {
        return teamName;
    }

    public Player getTopPlayer() {
        return players.stream()
                .max(Comparator.comparing(Player::getPoints))
                .orElse(null);
    }
}