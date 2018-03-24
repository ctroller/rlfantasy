package ch.christiantroller.rlfantasy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Player {
    private static final String PLACEHOLDER_NAME = "{name}";
    private static final String PLACEHOLDER_POINTS = "{points}";
    private static final String PLACEHOLDER_POSITION = "{position}";
    private static final String DEFAULT_FORMAT = "{name}, {points} pts{position}";
    private static final String PROPERTY_NAME = "player_format";

    private static final Map<String, Player> FLYWEIGHT = new ConcurrentHashMap<>();

    private final String name;
    private final int points;
    private final String position;
    private static final String FORMAT = Main.getProperty(PROPERTY_NAME, DEFAULT_FORMAT);

    private Player(String name, int points, String position) {
        this.name = name;
        this.points = points;
        this.position = position;
    }

    public static Player of( String name, int points, String position )
    {
        return FLYWEIGHT.computeIfAbsent( name + '/' + points + '/' + position, key -> new Player( name, points, position ) );
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return FORMAT.replace(PLACEHOLDER_NAME, name)
                .replace(PLACEHOLDER_POINTS, String.valueOf(points))
                .replace(PLACEHOLDER_POSITION, position != null ? " [" + position + "]" : "");
    }
}