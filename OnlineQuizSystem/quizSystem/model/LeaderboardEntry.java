package quizsystem.model;

public class LeaderboardEntry {
    private String username;
    private int score;

    public LeaderboardEntry(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() { return username; }
    public int getScore() { return score; }

    @Override
    public String toString() { return username + " - " + score; }
}
