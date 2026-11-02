import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // Create a hidden parent frame so the popup stays on top
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);

        // Show a test pop-up input form
        String input = JOptionPane.showInputDialog(
                frame,
                "GitHub Sync Test:\nEnter your name or a test message:",
                "POS System - Co-Editing Test",
                JOptionPane.QUESTION_MESSAGE
        );

        // If user entered text, display a confirmation dialog
        if (input != null && !input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Success! Hello, " + input + "!\nCo-editing test is working properly.",
                    "Test Complete",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    frame,
                    "Test window closed without input.",
                    "Notice",
                    JOptionPane.WARNING_MESSAGE
            );
        }

        System.exit(0);
    }
}