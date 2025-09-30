import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
public class StudentGradeTrackerGUI extends JFrame {
    private ArrayList<String> studentNames = new ArrayList<>();
    private ArrayList<Integer> studentGrades = new ArrayList<>();
    private JTextField nameField = new JTextField(15);
    private JTextField gradeField = new JTextField(5);
    private JTextArea displayArea = new JTextArea(10, 30);
    public StudentGradeTrackerGUI() {
        setTitle("Student Grade Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Grade:"));
        inputPanel.add(gradeField);
        JButton addButton = new JButton("Add Student");
        JButton displayButton = new JButton("Display All");
        JButton statsButton = new JButton("Show Stats");
        JButton searchButton = new JButton("Search");
        inputPanel.add(addButton);
        inputPanel.add(displayButton);
        inputPanel.add(statsButton);
        inputPanel.add(searchButton);
        add(inputPanel, BorderLayout.NORTH);
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        addButton.addActionListener(e -> addStudent());
        displayButton.addActionListener(e -> displayStudents());
        statsButton.addActionListener(e -> showStatistics());
        searchButton.addActionListener(e -> searchStudent());
        pack();
        setLocationRelativeTo(null); // center window
        setVisible(true);
    }
    private void addStudent() {
        String name = nameField.getText().trim();
        String gradeText = gradeField.getText().trim();
        if (name.isEmpty() || gradeText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and grade.");
            return;
        }
        try {
            int grade = Integer.parseInt(gradeText);
            studentNames.add(name);
            studentGrades.add(grade);
            displayArea.append(name + " added with grade " + grade + "\n");
            nameField.setText("");
            gradeField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Grade must be a number.");
        }
    }
    private void displayStudents() {
        displayArea.setText("--- Student List ---\n");
        for (int i = 0; i < studentNames.size(); i++) {
            displayArea.append(studentNames.get(i) + " - Grade: " + studentGrades.get(i) + "\n");
        }
    }
    private void showStatistics() {
        if (studentGrades.isEmpty()) {
            displayArea.setText("No grades available.");
            return;
        }
        int sum = 0;
        for (int grade : studentGrades) {
            sum += grade;
        }
        double average = (double) sum / studentGrades.size();
        int highest = Collections.max(studentGrades);
        int lowest = Collections.min(studentGrades);

        displayArea.setText("Average Grade: " + average + "\n" +
                            "Highest Grade: " + highest + "\n" +
                            "Lowest Grade: " + lowest + "\n");
    }
    private void searchStudent() {
        String searchName = JOptionPane.showInputDialog(this, "Enter student name to search:");
        if (searchName != null && !searchName.trim().isEmpty()) {
            int index = studentNames.indexOf(searchName.trim());
            if (index != -1) {
                JOptionPane.showMessageDialog(this, searchName + "'s grade is: " + studentGrades.get(index));
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentGradeTrackerGUI::new);
    }
}
