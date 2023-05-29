package AWT_UI;

import src.getDatabase;
import src.setDatabase;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignUpUI extends JFrame implements ActionListener {

    // Components
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel emailLabel;
    private JTextField emailField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JLabel retypePasswordLabel;
    private JPasswordField retypePasswordField;
    private JCheckBox isAdminCheckbox;
    private JButton signUpBtn;
    private JLabel errorLabel;

    public SignUpUI() {
        usernameLabel = new JLabel("Username");
        usernameField = new JTextField(20);

        emailLabel = new JLabel("Email");
        emailField = new JTextField(20);

        passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField(20);
        passwordField.setEchoChar('*');

        retypePasswordLabel = new JLabel("Retype Password");
        retypePasswordField = new JPasswordField(20);
        retypePasswordField.setEchoChar('*');

        isAdminCheckbox = new JCheckBox("I am an administrator");

        signUpBtn = new JButton("Sign Up");

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);

        // Add components to panel
        JPanel panel = new JPanel(new GridLayout(7, 1));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(retypePasswordLabel);
        panel.add(retypePasswordField);
        panel.add(isAdminCheckbox);
        panel.add(errorLabel);
        panel.add(signUpBtn);

        JPanel mainPanel = new JPanel(new BorderLayout()); 
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(panel);
        
        // Add action listeners to buttons
        signUpBtn.addActionListener(this);

        //windows properties
        setBackground(Color.YELLOW);
        setTitle("Sign up");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        add(mainPanel);
        setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpBtn) {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String retypePassword = new String(retypePasswordField.getPassword());
            boolean isAdmin = isAdminCheckbox.isSelected();
            boolean isTaken = getDatabase.checkUsername(username);
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || retypePassword.isEmpty()) {
                errorLabel.setText("Please fill in all fields");
                return;
            }

            if (!password.equals(retypePassword)) {
                errorLabel.setText("Passwords do not match");
                return;
            }

            if (!email.contains("@") && !email.contains(".")) {
                errorLabel.setText("Invalid email address");
                return;
            }

            if (isTaken) {
                errorLabel.setText("Username is already taken");
                return;
            }

            setDatabase.addLoginDetails(username, password, isAdmin);
            
            // Open login UI window
            LoginUI loginUI = new LoginUI();
            loginUI.setVisible(true);
            this.dispose();
        }
    }
}
