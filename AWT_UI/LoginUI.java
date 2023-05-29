package AWT_UI;

import src.connectDatabase;
import src.getDatabase;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginUI extends JFrame implements ActionListener {

    // Components
    private JLabel usernameLabel;
    public static JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JButton signUpBtn;
    private JLabel errorLabel;

    public LoginUI() {
        usernameLabel = new JLabel("Username");
        usernameField = new JTextField(2);

        passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField(2);
        passwordField.setEchoChar('*');

        loginBtn = new JButton("Login");
        signUpBtn = new JButton("Sign Up");

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginBtn);
        buttonPanel.add(signUpBtn);

        // Add components to panel
        JPanel panel = new JPanel(new GridLayout(6, 1));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(errorLabel);
        panel.add(buttonPanel);

        JPanel mainPanel = new JPanel(new BorderLayout()); 
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(panel);
        
        // Add action listeners to buttons
        loginBtn.addActionListener(this);
        signUpBtn.addActionListener(this);

        //windows properties
        setBackground(Color.YELLOW);
        setTitle("Login");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        add(mainPanel);
        setVisible(true);
    }

    // Action listener for buttons
    public void actionPerformed(ActionEvent e) {
        // Authenticate user
        if (e.getSource() == loginBtn) {
            Boolean authenticated = getDatabase.checkLoginDetails(usernameField.getText(), new String(passwordField.getPassword()));
            if (authenticated) {
                openMainUI();
            } else {
                errorLabel.setText("Invalid username or password");
            }
        } else if (e.getSource() == signUpBtn) {
            new SignUpUI();
            this.dispose();
        }
    }

    private void openMainUI() {
        MainUI mainUI;
        try {
            mainUI = new MainUI();
            mainUI.setVisible(true);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        this.dispose();
    }

    public static void main(String[] args) {
        new connectDatabase();
        new LoginUI();
    }
}
