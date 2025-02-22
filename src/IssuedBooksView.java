import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class IssuedBooksView extends JFrame {
    private JTextArea issuedBooksArea;

    public IssuedBooksView() {
        setTitle("Issued Books");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        issuedBooksArea = new JTextArea();
        issuedBooksArea.setEditable(false);
        add(new JScrollPane(issuedBooksArea), BorderLayout.CENTER);

        fetchIssuedBooks();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fetchIssuedBooks() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root", "gyash801@")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT b.title, u.user_name, ib.issue_date, ib.return_date FROM issued_books ib JOIN books b ON ib.book_id = b.book_id JOIN users u ON ib.user_id = u.user_id");

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("Book: ").append(rs.getString("title"))
                        .append(" | Issued To: ").append(rs.getString("user_name"))
                        .append(" | Issue Date: ").append(rs.getString("issue_date"))
                        .append(" | Return Date: ").append(rs.getString("return_date") != null ? rs.getString("return_date") : "Not Returned")
                        .append("\n");
            }
            issuedBooksArea.setText(sb.toString());
        } catch (SQLException ex) {
            issuedBooksArea.setText("Error fetching issued books: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new IssuedBooksView();
    }
}
