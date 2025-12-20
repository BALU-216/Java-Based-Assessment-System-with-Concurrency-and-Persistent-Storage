package quizsystem.io;

import java.io.*;
import java.text.DecimalFormat;

public class ResultSaver {

    public static void saveResult(String username, int totalQ, int correct, int wrong,
                                  int skipped, int score, int totalMarks) {
        double percent = totalMarks == 0 ? 0.0 : ((double) score / totalMarks) * 100.0;
        String grade;
        // Matching the grade mapping you earlier requested: A (>=75), B(50-74), C(25-49), D(<25)
        if (percent >= 75) grade = "A";
        else if (percent >= 50) grade = "B";
        else if (percent >= 25) grade = "C";
        else grade = "D";

        DecimalFormat df = new DecimalFormat("0.00");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("result.txt"))) {
            bw.write("---------------------------------------------");
            bw.newLine();
            bw.write("User: " + username);
            bw.newLine();
            bw.write("Total Questions: " + totalQ);
            bw.newLine();
            bw.write("Correct Answers: " + correct);
            bw.newLine();
            bw.write("Wrong Answers: " + wrong);
            bw.newLine();
            bw.write("Skipped: " + skipped);
            bw.newLine();
            bw.write("Score: " + score + " / " + totalMarks);
            bw.newLine();
            bw.write("Percentage: " + df.format(percent) + "%");
            bw.newLine();
            bw.write("Grade: " + grade);
            bw.newLine();
            bw.write("---------------------------------------------");
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing result file: " + e.getMessage());
        }
    }
}
