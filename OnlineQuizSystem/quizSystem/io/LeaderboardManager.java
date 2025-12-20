package quizsystem.io;

import quizsystem.model.LeaderboardEntry;
import java.io.*;
import java.util.*;

public class LeaderboardManager {

    public static Map<String, Integer> loadLeaderboardMap(String fileName) throws IOException {
        Map<String, Integer> map = new HashMap<>();
        File file = new File(fileName);
        if (!file.exists()) return map;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    try {
                        String user = parts[0].trim();
                        int score = Integer.parseInt(parts[1].trim());
                        map.put(user, score);
                    } catch (NumberFormatException ignored) { }
                }
            }
        }
        return map;
    }

    public static void saveLeaderboardMap(String fileName, Map<String, Integer> map) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, Integer> e : map.entrySet()) {
                bw.write(e.getKey() + ":" + e.getValue());
                bw.newLine();
            }
        }
    }

    public static void printSortedLeaderboard(Map<String, Integer> map) {
        List<LeaderboardEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            entries.add(new LeaderboardEntry(e.getKey(), e.getValue()));
        }
        entries.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
        if (entries.isEmpty()) {
            System.out.println("No entries in leaderboard yet.");
            return;
        }
        int rank = 1;
        for (LeaderboardEntry e : entries) {
            System.out.printf("%d. %s - %d%n", rank++, e.getUsername(), e.getScore());
        }
    }
}
