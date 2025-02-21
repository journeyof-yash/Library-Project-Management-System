import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class UserManagementView extends JFrame {
    private JTextField userIdField, userNameField;
    private JRadioButton newUserRadio, existingUserRadio;
    private JButton addUserButton, updateUserButton;

    public UserManagementView() {
        setTitle("Library Management System - User Management");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("User ID:"));
        userIdField = new JTextField();
        add(userIdField);

        add(new JLabel("User Name:"));
        userNameField = new JTextField();
        add(userNameField);

        newUserRadio = new JRadioButton("New User", true);
        existingUserRadio = new JRadioButton("Existing User");
        ButtonGroup userGroup = new ButtonGroup();
        userGroup.add(newUserRadio);
        userGroup.add(existingUserRadio);

        add(new JLabel("User Type:"));
        JPanel radioPanel = new JPanel();
        radioPanel.add(newUserRadio);
        radioPanel.add(existingUserRadio);
        add(radioPanel);

        addUserButton = new JButton("Add User");
        addUserButton.addActionListener(this::addUser);
        add(addUserButton);

        updateUserButton = new JButton("Update User");
        updateUserButton.addActionListener(this::updateUser);
        add(updateUserButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addUser(ActionEvent e) {
        modifyUser("INSERT INTO users (user_id, user_name) VALUES (?, ?)", "User added successfully!");
    }

    private void updateUser(ActionEvent e) {
        modifyUser("UPDATE users SET user_name = ? WHERE user_id = ?", "User updated successfully!");
    }

    private void modifyUser(String query, String successMessage) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagement", "root", "gyash801@")) {
            PreparedStatement stmt = conn.prepareStatement(query);
            if (query.startsWith("INSERT")) {
                stmt.setString(1, userIdField.getText().trim());
                stmt.setString(2, userNameField.getText().trim());
            } else {
                stmt.setString(1, userNameField.getText().trim());
                stmt.setString(2, userIdField.getText().trim());
            }
            int rows = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, rows > 0 ? successMessage : "Operation failed!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new UserManagementView();
    }
}
