package Client;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UniversityClient {
    private static Socket socket;
    private static BufferedReader input;
    private static PrintWriter output;


    private static JFrame mainFrame;
    private static CardLayout cardLayout;
    private static JPanel mainPanel;


    private static JTextField userField;
    private static JPasswordField passField;


    private static JTextArea displayArea;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            connectToServer();
            setupGUI();
        });
    }


    private static void connectToServer() {
        try {
            socket = new Socket("localhost", 5000);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);


            input.readLine();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "فشل الاتصال بالسيرفر: " + e.getMessage(), "خطأ", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }


    private static void setupGUI() {
        mainFrame = new JFrame("University Course Registration System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 500);
        mainFrame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);


        mainPanel.add(createLoginPanel(), "LOGIN_SCREEN");
        mainPanel.add(createMenuPanel(), "MENU_SCREEN");

        mainFrame.add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN_SCREEN"); // البداية بشاشة الـ Login
        mainFrame.setVisible(true);
    }


    private static JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("University Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Username:"), gbc);
        userField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(userField, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);
        passField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(loginButton, gbc);


        loginButton.addActionListener(e -> handleLogin());

        return panel;
    }


    private static JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        JButton viewBtn = new JButton("View Courses");
        JButton registerBtn = new JButton("Register Course");
        JButton dropBtn = new JButton("Drop Course");
        JButton myCoursesBtn = new JButton("My Courses");
        JButton exitBtn = new JButton("Exit");

        buttonPanel.add(viewBtn);
        buttonPanel.add(registerBtn);
        buttonPanel.add(dropBtn);
        buttonPanel.add(myCoursesBtn);
        buttonPanel.add(exitBtn);
        panel.add(buttonPanel, BorderLayout.NORTH);


        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        panel.add(scrollPane, BorderLayout.CENTER);


        viewBtn.addActionListener(e -> sendRequest("VIEW_COURSES", true));
        myCoursesBtn.addActionListener(e -> sendRequest("MY_COURSES", true));

        registerBtn.addActionListener(e -> {
            String code = JOptionPane.showInputDialog(mainFrame, "Enter Course Code to Register:");
            if (code != null && !code.trim().isEmpty()) {
                sendRequest("REGISTER," + code.trim(), false);
            }
        });

        dropBtn.addActionListener(e -> {
            String code = JOptionPane.showInputDialog(mainFrame, "Enter Course Code to Drop:");
            if (code != null && !code.trim().isEmpty()) {
                sendRequest("DROP," + code.trim(), false);
            }
        });

        exitBtn.addActionListener(e -> {
            try { socket.close(); } catch (IOException ex) {}
            System.exit(0);
        });

        return panel;
    }


    private static void handleLogin() {
        String username = userField.getText().trim();
        String password = new String(passField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter both username and password.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            output.println("LOGIN," + username + "," + password);
            String response = input.readLine();

            if ("LOGIN_SUCCESS".equals(response)) {

                cardLayout.show(mainPanel, "MENU_SCREEN");
                displayArea.setText("Welcome " + username + "!\nSelect an option from the top menu.");
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Invalid Login. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            displayArea.setText("Error during login: " + e.getMessage());
        }
    }


    private static void sendRequest(String command, boolean isMultiLine) {
        try {
            output.println(command);
            displayArea.setText("");

            if (isMultiLine) {
                String line;
                StringBuilder sb = new StringBuilder();
                while (!(line = input.readLine()).equals("END")) {
                    sb.append(line).append("\n");
                }
                displayArea.setText(sb.toString());
            } else {

                String response = input.readLine();
                displayArea.setText(response);
            }
        } catch (IOException e) {
            displayArea.setText("Connection error: " + e.getMessage());
        }
    }
}