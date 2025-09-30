import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class ChatBotGUI extends JFrame {

    private JTextArea chatArea;
    private JTextField userInput;
    private JButton sendButton;

    // Rule-based responses (can be extended with NLP)
    private Map<String, String> responses;

    public ChatBotGUI() {
        setTitle("AI ChatBot - CodeAlpha");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Chat display area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(chatArea);

        // Input field
        userInput = new JTextField();
        userInput.setFont(new Font("Arial", Font.PLAIN, 16));

        // Send button
        sendButton = new JButton("Send");

        // Panel for input + button
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(userInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Initialize responses
        responses = new HashMap<>();
        responses.put("hello", "Hello! How can I help you today?");
        responses.put("hi", "Hi there! Ask me anything.");
        responses.put("how are you", "I'm just a chatbot, but I'm doing great!");
        responses.put("joke", "Why don’t programmers like nature? Too many bugs!");
        responses.put("creator", "I was created by you for the CodeAlpha project.");
        responses.put("weather", "I can’t fetch real weather yet, but I hope it’s sunny!");
        responses.put("your name", "I am CodeAlpha Java ChatBot .");
        responses.put("bye", "Goodbye! Have a wonderful day!");
        responses.put("help", "I can answer questions like 'hello', 'your name', 'bye'.");

        // Button click action
        sendButton.addActionListener(e -> processInput());
        userInput.addActionListener(e -> processInput()); // press Enter
    }

    private void processInput() {
        String input = userInput.getText().trim().toLowerCase();
        if (input.contains("joke") || input.contains("funny")) {
               chatArea.append("Bot: Here’s a joke — Why do Java developers wear glasses? Because they can’t C#!\n\n");
               userInput.setText("");
               return;
    }
        if (input.isEmpty()) return;

        chatArea.append("You: " + input + "\n");

        // Get response
        String response = responses.getOrDefault(input, 
            "Sorry, I didn't understand that. Try asking something else.");
        chatArea.append("Bot: " + response + "\n\n");

        userInput.setText(""); // Clear input
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatBotGUI bot = new ChatBotGUI();
            bot.setVisible(true);
        });
    }
}
