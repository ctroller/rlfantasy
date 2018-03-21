package ch.christiantroller.rlfantasy;

public class Player {
    private final String name;
    private final int points;
    private final String position;

    public Player(String name, int points, String position) {
        this.name = name;
        this.points = points;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return String.format("%s, %d pts%s", name, points, position != null ? " [" + position + "]" : "");
    }
}