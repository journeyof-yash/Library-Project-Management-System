import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class RoleManagementView extends JFrame {
    private JComboBox<String> roleSelection;
    private JTextField userIdField;
    private JButton updateRoleButton;

    public RoleManagementView() {
        setTitle("Library Management System - User Role Management");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("User ID:"));
        userIdField = new JTextField();
        add(userIdField);

        add(new JLabel("Select Role:"));
        roleSelection = new JComboBox<>(new String[]{"User", "Admin"});
        add(roleSelection);

        updateRoleButton = new JButton("Update Role");
        updateRoleButton.addActionListener(this::updateUserRole);
        add(updateRoleButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateUserRole(ActionEvent e) {
        String userId = userIdField.getText().trim();
        String role = (String) roleSelection.getSelectedItem();

        if (userId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "User ID is required!");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root", "gyash801@")) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE users SET role = ? WHERE user_id = ?");
            stmt.setString(1, role);
            stmt.setString(2, userId);
            int rows = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, rows > 0 ? "User role updated successfully!" : "User not found!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new RoleManagementView();
    }
}
