package quizsystem.service;

import quizsystem.model.Question;
import quizsystem.io.ResultSaver;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class QuizService {
    private List<Question> questions;
    private int score = 0, correct = 0, wrong = 0, skipped = 0;
    private List<Integer> userAnswers;
    private final int totalMarks;

    public QuizService(List<Question> questions) {
        this.questions = new ArrayList<>(questions); // preserve order
        this.userAnswers = new LinkedList<>();
        int sum = 0;
        for (Question q : questions) sum += q.getMarks();
        this.totalMarks = sum;
    }

    public void startQuiz(String username) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println();

        for (Question q : questions) {
            System.out.println("---------------------------------------------");
            System.out.println("Question " + q.getId() + ": " + q.getText());
            String[] opts = q.getOptions();
            System.out.println("A) " + opts[0]);
            System.out.println("B) " + opts[1]);
            System.out.println("C) " + opts[2]);
            System.out.println("D) " + opts[3]);
            System.out.print("Enter your answer (1-4) or any other key to skip: ");

            String input = null;
            AtomicBoolean timedOut = new AtomicBoolean(false);

            try {
                for (int t = 60; t >= 0; t--) {
                    // only show timer messages every 5 seconds to reduce clutter
                    if (t % 5 == 0 && t != 60) {
                        System.out.println("[TIMER] Time remaining: " + t + " seconds");
                    }
                    if (br.ready()) { 
                        input = br.readLine();
                        break;
                    }
                    if (t == 0) {
                        timedOut.set(true);
                        break;
                    }
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                // ignore interruptions
            }

            if (timedOut.get()) {
                System.out.println();
                System.out.println("[TIMEUP] Time is up for this question!");
                System.out.println("Press ENTER to continue...");
                try {
                    br.readLine(); // wait for user to press enter; treat as skip
                } catch (IOException ignored) {}
                skipped++;
                userAnswers.add(0);
                System.out.println("---------------------------------------------");
                continue;
            }

            if (input == null || input.trim().isEmpty()) {
                skipped++;
                userAnswers.add(0);
                System.out.println("You skipped this question.");
            } else {
                input = input.trim();
                try {
                    int ans = Integer.parseInt(input);
                    if (ans >= 1 && ans <= 4) {
                        char opt = (char) ('A' + ans - 1);
                        System.out.println("You selected: " + opt + ") " + opts[ans - 1]);
                        userAnswers.add(ans);

                        if (ans == q.getCorrectOptionIndex()) {
                            correct++;
                            score += q.getMarks();
                        } else {
                            wrong++;
                        }
                    } else {
                        skipped++;
                        userAnswers.add(0);
                        System.out.println("Invalid input. Marked as skipped.");
                    }
                } catch (NumberFormatException ex) {
                    skipped++;
                    userAnswers.add(0);
                    System.out.println("Invalid input. Marked as skipped.");
                }
            }

            // Pause briefly so the user sees the confirmation before next question
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } // end for

        System.out.println();
        System.out.println("========== QUIZ COMPLETED ==========");
        System.out.println(" Correct Answers : " + correct);
        System.out.println(" Wrong Answers   : " + wrong);
        System.out.println(" Skipped         : " + skipped);
        System.out.println(" Total Score     : " + score);
        System.out.println("=====================================");

        showReviewSection();
        showPerformanceSummary();

        // Save the result to result.txt
        ResultSaver.saveResult(username, questions.size(), correct, wrong, skipped, score, totalMarks);
    }

    private void showReviewSection() {
        System.out.println();
        System.out.println("Review Section:");
        System.out.println("---------------------------------------------");
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            int userAns = 0;
            if (i < userAnswers.size()) userAns = userAnswers.get(i);

            System.out.println("Q" + q.getId() + ". " + q.getText());
            if (userAns == 0) {
                System.out.println("Your Answer   : Not Answered / Skipped");
            } else {
                char opt = (char) ('A' + userAns - 1);
                System.out.println("Your Answer   : " + opt + ") " + q.getOptions()[userAns - 1]);
            }
            System.out.println("Correct Answer: " + q.getCorrectOption());
            System.out.println("---------------------------------------------");
        }
    }

    private void showPerformanceSummary() {
        double percentage = totalMarks == 0 ? 0.0 : ((double) score / totalMarks) * 100.0;
        String rating;
        if (percentage >= 90) rating = "*****";
        else if (percentage >= 75) rating = "****";
        else if (percentage >= 50) rating = "***";
        else if (percentage >= 25) rating = "**";
        else rating = "*";

        System.out.println();
        System.out.println("---------------------------------------------");
        System.out.println("           QUIZ PERFORMANCE SUMMARY");
        System.out.println("---------------------------------------------");
        System.out.println("Your Rating : " + rating);
        System.out.printf("Your Accuracy: %.2f%%%n", percentage);
        if (percentage >= 75) {
            System.out.println("Great job! You have strong knowledge - keep learning!");
        } else {
            System.out.println("Keep practicing to improve your accuracy.");
        }
        System.out.println("---------------------------------------------");
        System.out.println("Thank you for playing the Online Quiz System!");
        System.out.println("We hope you enjoyed testing your knowledge.");
        System.out.println("---------------------------------------------");
    }

    public int getScore() {
        return score;
    }
}
