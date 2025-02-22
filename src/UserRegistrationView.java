import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserRegistrationView extends JFrame {
    private JTextField usernameField, nameField;
    private JPasswordField passwordField;
    private JRadioButton userRadio, adminRadio;
    private JButton registerButton;

    public UserRegistrationView() {
        setTitle("User Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Full Name:"));
        nameField = new JTextField();
        add(nameField);

        // Role selection
        add(new JLabel("Register as:"));
        userRadio = new JRadioButton("User", true);
        adminRadio = new JRadioButton("Admin");
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(userRadio);
        roleGroup.add(adminRadio);
        JPanel radioPanel = new JPanel();
        radioPanel.add(userRadio);
        radioPanel.add(adminRadio);
        add(radioPanel);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this::registerUser);
        add(registerButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void registerUser(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String fullName = nameField.getText().trim();
        String role = userRadio.isSelected() ? "User" : "Admin";

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Hash password before storing
        String hashedPassword = hashPassword(password);

        String query = "INSERT INTO users (username, password, user_name, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root", "gyash801@")) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, fullName);
            stmt.setString(4, role);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new LoginView(); // Redirect to login after successful registration
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            JOptionPane.showMessageDialog(this, "Password hashing failed!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static void main(String[] args) {
        new UserRegistrationView();
    }
}
