import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class ReportsView extends JFrame {
    private JTextArea reportArea;
    private JButton generateReportButton;

    public ReportsView() {
        setTitle("Library Management System - Reports");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(this::generateReport);
        add(generateReportButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void generateReport(ActionEvent e) {
        StringBuilder report = new StringBuilder();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagement", "root", "gyash801@")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");
            report.append("Books Report:\n");
            while (rs.next()) {
                report.append("Book ID: ").append(rs.getInt("book_id"))
                        .append(", Title: ").append(rs.getString("title"))
                        .append(", Author: ").append(rs.getString("author"))
                        .append(", Status: ").append(rs.getString("status"))
                        .append("\n");
            }
            rs = stmt.executeQuery("SELECT * FROM users");
            report.append("\nUsers Report:\n");
            while (rs.next()) {
                report.append("User ID: ").append(rs.getInt("user_id"))
                        .append(", Name: ").append(rs.getString("user_name"))
                        .append("\n");
            }
            reportArea.setText(report.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ReportsView();
    }
}
