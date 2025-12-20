package quizsystem.model;

public class Question {
    private int id;
    private String text;
    private String[] options;
    private int correctOptionIndex;
    private int marks;

    public Question(int id, String text, String[] options, int correctOptionIndex, int marks) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
        this.marks = marks;
    }

    public int getId() { return id; }
    public String getText() { return text; }
    public String[] getOptions() { return options; }
    public int getCorrectOptionIndex() { return correctOptionIndex; }
    public int getMarks() { return marks; }

    public String getCorrectOption() {
        int idx = Math.max(1, Math.min(4, correctOptionIndex));
        char opt = (char) ('A' + idx - 1);
        return opt + ") " + options[idx - 1];
    }
}
