package frontend;

import javax.swing.*;
import java.awt.event.*;

public class AdminLogin extends JFrame implements ActionListener {
    private JLabel userLabel, passwordLabel;
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public AdminLogin() {
        setTitle("Admin Login");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 80, 25);
        add(userLabel);

        userTextField = new JTextField();
        userTextField.setBounds(120, 30, 140, 25);
        add(userTextField);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 70, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 70, 140, 25);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 120, 100, 25);
        loginButton.addActionListener(this);
        add(loginButton);
    }

    public void actionPerformed(ActionEvent e) {
        String username = userTextField.getText();
        String password = new String(passwordField.getPassword());

        
        if (username.equals("admin") && password.equals("password")) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            AdminHomepageUI obj = new AdminHomepageUI();
            obj.setVisible(true);
            dispose();
            
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
        }
    }

    public static void main(String[] args) {
        AdminLogin adminLogin = new AdminLogin();
        adminLogin.setVisible(true);
    }
}