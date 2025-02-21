import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class MembershipManagementView extends JFrame {
    private JTextField membershipIdField, userIdField;
    private JRadioButton sixMonthsRadio, oneYearRadio, twoYearsRadio;
    private JButton addMembershipButton, updateMembershipButton;

    public MembershipManagementView() {
        setTitle("Library Management System - Membership Management");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Membership ID:"));
        membershipIdField = new JTextField();
        add(membershipIdField);

        add(new JLabel("User ID:"));
        userIdField = new JTextField();
        add(userIdField);

        sixMonthsRadio = new JRadioButton("6 Months", true);
        oneYearRadio = new JRadioButton("1 Year");
        twoYearsRadio = new JRadioButton("2 Years");
        ButtonGroup durationGroup = new ButtonGroup();
        durationGroup.add(sixMonthsRadio);
        durationGroup.add(oneYearRadio);
        durationGroup.add(twoYearsRadio);

        add(new JLabel("Membership Duration:"));
        JPanel radioPanel = new JPanel();
        radioPanel.add(sixMonthsRadio);
        radioPanel.add(oneYearRadio);
        radioPanel.add(twoYearsRadio);
        add(radioPanel);

        addMembershipButton = new JButton("Add Membership");
        addMembershipButton.addActionListener(this::addMembership);
        add(addMembershipButton);

        updateMembershipButton = new JButton("Update Membership");
        updateMembershipButton.addActionListener(this::updateMembership);
        add(updateMembershipButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addMembership(ActionEvent e) {
        modifyMembership("INSERT INTO memberships (membership_id, user_id, duration) VALUES (?, ?, ?)", "Membership added successfully!");
    }

    private void updateMembership(ActionEvent e) {
        modifyMembership("UPDATE memberships SET duration = ? WHERE membership_id = ?", "Membership updated successfully!");
    }

    private void modifyMembership(String query, String successMessage) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagement", "root", "gyash801@")) {
            PreparedStatement stmt = conn.prepareStatement(query);
            String duration = sixMonthsRadio.isSelected() ? "6 Months" : oneYearRadio.isSelected() ? "1 Year" : "2 Years";
            if (query.startsWith("INSERT")) {
                stmt.setString(1, membershipIdField.getText().trim());
                stmt.setString(2, userIdField.getText().trim());
                stmt.setString(3, duration);
            } else {
                stmt.setString(1, duration);
                stmt.setString(2, membershipIdField.getText().trim());
            }
            int rows = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, rows > 0 ? successMessage : "Operation failed!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new MembershipManagementView();
    }
}
