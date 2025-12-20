package quizsystem.main;

import quizsystem.io.QuestionLoader;
import quizsystem.io.LeaderboardManager;
import quizsystem.model.Question;
import quizsystem.service.QuizService;
import quizsystem.io.ResultSaver;
import quizsystem.exceptions.QuestionFormatException;

import java.io.*;
import java.util.*;

public class Main {
    private static final String QUESTION_FILE = "questions.txt";
    private static final String LEADERBOARD_FILE = "leaderboard.txt";

    private static String generateCandidateId() {
        long id = System.currentTimeMillis() % 1000000;
        return "U" + String.format("%06d", id);
    }

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("---------------------------------------------");
            System.out.println("             ONLINE QUIZ SYSTEM");
            System.out.println("---------------------------------------------");
            System.out.println("1. Start Quiz");
            System.out.println("2. View Leaderboard");
            System.out.println("3. Exit");
            System.out.println("---------------------------------------------");
            System.out.print("Enter your choice: ");
            try {
                String choice = br.readLine();
                if (choice == null) continue;
                switch (choice.trim()) {
                    case "1":
                        startQuizFlow(br);
                        break;
                    case "2":
                        viewLeaderboard();
                        break;
                    case "3":
                        System.out.println("Thank you!");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (IOException e) {
                System.out.println("Input error.");
            }
        }
    }

    private static void startQuizFlow(BufferedReader br) {
        try {
            // Load existing leaderboard to check unique usernames
            Map<String, Integer> leaderboard = LeaderboardManager.loadLeaderboardMap(LEADERBOARD_FILE);
            Set<String> usernames = new HashSet<>(leaderboard.keySet());

            System.out.print("\nEnter your name: ");
            String username = br.readLine();
            if (username == null) return;
            username = username.trim();
            while (username.isEmpty() || usernames.contains(username)) {
                if (username.isEmpty()) {
                    System.out.print("Name cannot be empty. Enter your name: ");
                } else {
                    System.out.print("Username already exists. Enter a different name: ");
                }
                username = br.readLine();
                if (username == null) return;
                username = username.trim();
            }

            String candidateId = generateCandidateId();

            System.out.println("Welcome, " + username + "!");
            System.out.println();

            // Welcome block exactly as requested (plain keyboard characters)
            System.out.println("---------------------------------------------");
            System.out.println("         WELCOME TO THE ONLINE QUIZ SYSTEM ");
            System.out.println("---------------------------------------------");
            System.out.println("Sharpen your mind and challenge yourself!");
            System.out.println("Answer smartly - every question tests your knowledge and speed.");
            System.out.println("Each correct answer rewards you with marks; think twice before skipping.");
            System.out.println("You have 60 seconds per question. Let's see how high you can score!");
            System.out.println("---------------------------------------------");
            System.out.println();

            // countdown using Thread.sleep() (3..2..1..)
            try {
                System.out.println("Starting your quiz in 3...");
                Thread.sleep(1000);
                System.out.println("2...");
                Thread.sleep(1000);
                System.out.println("1...");
                Thread.sleep(1000);
                System.out.println("Good luck, " + username + "!");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // show candidate id
            System.out.println("Your Candidate ID: " + candidateId);
            System.out.println();

            // Load questions in order
            List<Question> questions = QuestionLoader.loadQuestions(QUESTION_FILE);
            if (questions.isEmpty()) {
                System.out.println("No questions found in " + QUESTION_FILE);
                return;
            }

            // Start quiz
            QuizService quiz = new QuizService(questions);
            quiz.startQuiz(username);

            // Update leaderboard (keep best score)
            Map<String, Integer> map = LeaderboardManager.loadLeaderboardMap(LEADERBOARD_FILE);
            int currentScore = quiz.getScore();
            if (map.containsKey(username)) {
                int existing = map.get(username);
                if (currentScore > existing) map.put(username, currentScore);
            } else {
                map.put(username, currentScore);
            }
            LeaderboardManager.saveLeaderboardMap(LEADERBOARD_FILE, map);

            // Save result already handled inside QuizService via ResultSaver
            // Display leaderboard
            System.out.println();
            System.out.println("Leaderboard:");
            LeaderboardManager.printSortedLeaderboard(map);
            System.out.println("---------------------------------------------");
            System.out.println("Leaderboard and results updated.");
            System.out.println("[PROGRAM END]");

        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (QuestionFormatException e) {
            System.out.println("Error loading questions: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void viewLeaderboard() {
        try {
            Map<String, Integer> map = LeaderboardManager.loadLeaderboardMap(LEADERBOARD_FILE);
            System.out.println();
            System.out.println("Leaderboard:");
            LeaderboardManager.printSortedLeaderboard(map);
            System.out.println("---------------------------------------------");
        } catch (IOException e) {
            System.out.println("Error loading leaderboard: " + e.getMessage());
        }
    }
}
