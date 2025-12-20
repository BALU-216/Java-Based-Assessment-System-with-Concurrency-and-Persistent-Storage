package quizsystem.io;

import quizsystem.model.Question;
import quizsystem.exceptions.QuestionFormatException;
import java.io.*;
import java.util.*;

public class QuestionLoader {
    public static List<Question> loadQuestions(String fileName) throws IOException, QuestionFormatException {
        List<Question> questions = new ArrayList<>();
        Set<Integer> ids = new HashSet<>();

        File file = new File(fileName);
        if (!file.exists()) throw new FileNotFoundException("Question file not found: " + fileName);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length != 8) {
                    throw new QuestionFormatException("Malformed question at line " + lineNo);
                }
                try {
                    int id = Integer.parseInt(parts[0].trim());
                    if (ids.contains(id)) continue;
                    ids.add(id);

                    String text = parts[1].trim();
                    String[] options = new String[4];
                    for (int i = 0; i < 4; i++) options[i] = parts[i + 2].trim();
                    int correct = Integer.parseInt(parts[6].trim());
                    int marks = Integer.parseInt(parts[7].trim());

                    questions.add(new Question(id, text, options, correct, marks));
                } catch (NumberFormatException ex) {
                    throw new QuestionFormatException("Number format error at line " + lineNo);
                }
            }
        }
        return questions;
    }
}
