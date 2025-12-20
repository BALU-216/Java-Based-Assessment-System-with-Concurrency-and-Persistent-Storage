# Java-Based-Assessment-System-with-Concurrency-and-Persistent-Storage
A backend-focused console assessment system implemented in Java, showcasing object-oriented design, concurrent timer execution, structured file I/O, and leaderboard persistence. Built with separation of concerns and maintainability as core principles.


# Online Quiz System (Java)

A **console-based Online Quiz System** developed using **Java**, demonstrating core concepts such as **Object-Oriented Programming (OOP)**, **Multithreading**, **File Handling**, **Collections**, and **Custom Exception Handling**.

This project allows users to attempt quizzes, view scores, review answers, and check a leaderboard.

---

## 📌 Features

- Console-based interactive quiz system
- Multiple-choice questions loaded from a file
- 60-second timer per question using threads
- Automatic score calculation and grading
- Review section showing correct answers
- Leaderboard with sorted top scores
- Result summary saved to a file
- Custom exception handling for invalid data

---

## 🛠️ Technologies Used

- Java (Core Java)
- OOP Concepts
- Multithreading
- File Handling
- Java Collections Framework

---

## 📂 Project Structure

<img width="495" height="735" alt="image" src="https://github.com/user-attachments/assets/76a0ff64-2db6-4fc5-a7e4-dc4db233bdea" />



---

## ▶️ How to Run the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/Online-Quiz-System-Java.git

2. Navigate to the project folder:
   cd OnlineQuizSystem

3. Compile the project:
   javac quizsystem/main/Main.java

4. Run the program:
   java quizsystem.main.Main


📄 **Input Files Format**

questions.txt
id|question|option1|option2|option3|option4|correctOption|marks

Example:
1|Which class is used to read characters from a file?|FileReader|Scanner|InputStream|Reader|1|2


🏆 **Leaderboard**

Stored in leaderboard.txt

Displays sorted scores

Automatically updates after each quiz attempt

📊 **Result Summary**

Stored in result.txt

**Includes:**

Total questions

Correct / Wrong / Skipped

Score and percentage

Grade

🎓 **Academic Details**

Course: 24CS202 – Java Programming

Project Type: Home Assignment

Level: B.Tech – CSE (2nd Year)

🚀 **Future Enhancements**

GUI using JavaFX or Swing

Database integration

Randomized questions

Difficulty levels

Web-based version

📜 **License**

This project is for educational purposes only.

---
