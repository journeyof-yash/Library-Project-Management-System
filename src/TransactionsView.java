import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class TransactionsView extends JFrame {
    private JTextField transactionIdField, userIdField, bookIdField, issueDateField, returnDateField;
    private JButton issueBookButton, returnBookButton;

    public TransactionsView() {
        setTitle("Library Management System - Transactions");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Transaction ID:"));
        transactionIdField = new JTextField();
        add(transactionIdField);

        add(new JLabel("User ID:"));
        userIdField = new JTextField();
        add(userIdField);

        add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        add(bookIdField);

        add(new JLabel("Issue Date (YYYY-MM-DD):"));
        issueDateField = new JTextField();
        add(issueDateField);

        add(new JLabel("Return Date (YYYY-MM-DD):"));
        returnDateField = new JTextField();
        add(returnDateField);

        issueBookButton = new JButton("Issue Book");
        issueBookButton.addActionListener(this::issueBook);
        add(issueBookButton);

        returnBookButton = new JButton("Return Book");
        returnBookButton.addActionListener(this::returnBook);
        add(returnBookButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void issueBook(ActionEvent e) {
        modifyTransaction("INSERT INTO transactions (transaction_id, user_id, book_id, issue_date, return_date) VALUES (?, ?, ?, ?, ?)", "Book issued successfully!");
    }

    private void returnBook(ActionEvent e) {
        modifyTransaction("UPDATE transactions SET return_date = ? WHERE transaction_id = ?", "Book returned successfully!");
    }

    private void modifyTransaction(String query, String successMessage) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root", "gyash801@")) {
            PreparedStatement stmt = conn.prepareStatement(query);
            if (query.startsWith("INSERT")) {
                stmt.setString(1, transactionIdField.getText().trim());
                stmt.setString(2, userIdField.getText().trim());
                stmt.setString(3, bookIdField.getText().trim());
                stmt.setString(4, issueDateField.getText().trim());
                stmt.setString(5, returnDateField.getText().trim());
            } else {
                stmt.setString(1, returnDateField.getText().trim());
                stmt.setString(2, transactionIdField.getText().trim());
            }
            int rows = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, rows > 0 ? successMessage : "Operation failed!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new TransactionsView();
    }
}
