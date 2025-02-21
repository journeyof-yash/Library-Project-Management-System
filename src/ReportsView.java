import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ReportsView extends JFrame {
    private JTabbedPane tabbedPane;
    private JTextArea booksReportArea, usersReportArea, membershipsReportArea, issuedBooksReportArea;

    public ReportsView() {
        setTitle("Library Management System - Reports");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        booksReportArea = createReportTab("📚 Books Report", "SELECT book_id, title, author, status FROM books");
        usersReportArea = createReportTab("👤 Users Report", "SELECT user_id, user_name, role FROM users");
        membershipsReportArea = createReportTab("🎫 Memberships Report", "SELECT membership_id, user_id, duration FROM memberships");
        issuedBooksReportArea = createReportTab("📖 Issued Books Report", "SELECT issue_id, book_id, user_id, issue_date, return_date FROM issued_books");

        tabbedPane.addTab("Books", new JScrollPane(booksReportArea));
        tabbedPane.addTab("Users", new JScrollPane(usersReportArea));
        tabbedPane.addTab("Memberships", new JScrollPane(membershipsReportArea));
        tabbedPane.addTab("Issued Books", new JScrollPane(issuedBooksReportArea));

        add(tabbedPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JTextArea createReportTab(String title, String query) {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(fetchReportData(title, query));
        return textArea;
    }

    private String fetchReportData(String title, String query) {
        StringBuilder report = new StringBuilder(title + ":\n\n");
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root", "gyash801@");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    report.append(rs.getMetaData().getColumnName(i)).append(": ").append(rs.getString(i)).append("  |  ");
                }
                report.append("\n");
            }
        } catch (SQLException ex) {
            return "Error fetching report: " + ex.getMessage();
        }
        return report.toString().isEmpty() ? "No data available" : report.toString();
    }

    public static void main(String[] args) {
        new ReportsView();
    }
}
