package ch.christiantroller.rlfantasy;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class Main {
    /**
     * start date of rlfantasy. adjust if needed.
     */
    private static final LocalDate START_DATE = LocalDate.of(2018, 3, 17);

    /**
     * the date right now. wow.
     */
    private static final LocalDate NOW = LocalDate.now();

    /**
     * okhttp client instance
     */
    private static final OkHttpClient CLIENT = new OkHttpClient();

    /**
     * base url to fetch data.
     */
    private static final String BASE_URL = "https://fantasy.rocket-league.com/team/%s/%d";

    public static void main(String... args) throws Exception {
        List<Stats> stats = getAllStats();

        // sort by total points over all weeks
        stats.sort(Comparator.comparing(Stats::getTotalPoints)
                .reversed());

        prettyPrintStats(stats);
    }

    private static void prettyPrintStats(List<Stats> stats) throws IOException {
        String template = new String(Files.readAllBytes(Paths.get("output.txt")));

        StringBuilder output = new StringBuilder();
        int i = 1;
        int maxStatsNumberLen = String.valueOf(stats.size())
                .length();
        for (Stats stat : stats) {
            int curStatsNumberLen = String.valueOf(i)
                    .length();
            String align = new String(new char[maxStatsNumberLen - curStatsNumberLen + 1])
                    .replace("\0", " ");
            Player player = stat.getOverallTopPlayer();
            output.append("\n");
            output.append(i)
                    .append(".")
                    .append(align)
                    .append(stat.getTeamName())
                    .append(" - ")
                    .append(stat.getOwner())
                    .append(" (")
                    .append(stat.getTotalPoints())
                    .append(")");
            output.append(", Overall MVP: ")
                    .append(player);
            output.append("\n        Weekly MVP: ");
            int j = 1;
            int max = stat.getStats().size();

            for (WeekStats st : stat.getStats()) {
                Player mvp = st.getTopPlayer();

                output.append("(")
                        .append(j)
                        .append(") ")
                        .append(mvp);

                if (j < max) {
                    output.append(" | \n");
                }

                ++j;
            }

            ++i;
        }

        System.out.print(template.replace("{DATA}", output.toString()));
    }

    private static List<Stats> getAllStats() throws IOException {
        return Files.readAllLines(Paths.get("participants.txt"))
                .stream()
                .map(owner -> new Stats(owner, getAllStats(owner)))
                .collect(Collectors.toList());
    }

    @NotNull
    private static List<WeekStats> getAllStats(String owner) {
        List<WeekStats> returnValue = new ArrayList<>();

        long weekDiff = ChronoUnit.WEEKS.between(START_DATE, NOW) + 1;

        for (int i = 1; i <= weekDiff; ++i) {
            try {
                WeekStats stats = getStats(owner, i);
                if (stats != null) {
                    returnValue.add(stats);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                break;
            }
        }

        return returnValue;
    }

    private static WeekStats getStats(String owner, int week) throws Exception {
        Request request = new Request.Builder()
                .url(String.format(BASE_URL, owner, week))
                .header("User-Agent", "trox is stealing your data; https://twitter.com/drtrox; Mozilla/5.0")
                .build();

        try (Response response = CLIENT.newCall(request)
                .execute()) {


            if (response.code() == 404) {
                throw new Exception(String.format("404 - Can't get stats for owner %s in week %d.", owner, week));
            }

            Document doc = Jsoup.parse(response.body().string());

            String teamName = doc.getElementsByClass("rlg-grid")
                    .get(0)
                    .getElementsByTag("h1")
                    .get(0)
                    .text()
                    .replaceAll(" - Week [0-9]", "");

            Elements els = doc.getElementsByClass("rlg-fantasy-myteam__value");
            Elements playerCards = doc.getElementsByClass("rlg-fantasy-player-card");

            List<Player> players = new ArrayList<>();
            for (Element playerCard : playerCards) {
                String playerName = playerCard.getElementsByClass("rlg-fantasy-player-card__name")
                        .get(0)
                        .text();
                String position = playerCard.getElementsByTag("p")
                        .get(0)
                        .text();
                position = position.substring(position.indexOf("| ") + 2);
                int points = Integer.parseInt(playerCard.getElementsByTag("p")
                        .get(1)
                        .text()
                        .replaceAll("Points: ", ""));

                players.add(new Player(playerName, points, position));
            }

            return new WeekStats(teamName, Integer.parseInt(els.get(0)
                    .text()), players);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
