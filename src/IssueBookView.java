import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IssueBookView extends JFrame {
    private JTextField bookIdField, bookNameField, authorField, issueDateField, returnDateField;
    private JButton issueButton;

    public IssueBookView() {
        setTitle("Library Management System - Issue Book");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        add(bookIdField);

        add(new JLabel("Book Name:"));
        bookNameField = new JTextField();
        add(bookNameField);

        add(new JLabel("Author:"));
        authorField = new JTextField();
        authorField.setEditable(false);
        add(authorField);

        add(new JLabel("Issue Date:"));
        issueDateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
        issueDateField.setEditable(false);
        add(issueDateField);

        add(new JLabel("Return Date:"));
        returnDateField = new JTextField();
        add(returnDateField);

        issueButton = new JButton("Issue Book");
        add(issueButton);

        issueButton.addActionListener(this::issueBook);
        bookIdField.addActionListener(e -> fetchBookDetails());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fetchBookDetails() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagement", "root", "gyash801@")) {
            PreparedStatement stmt = conn.prepareStatement("SELECT book_name, author FROM books WHERE book_id = ?");
            stmt.setString(1, bookIdField.getText().trim());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                bookNameField.setText(rs.getString("book_name"));
                authorField.setText(rs.getString("author"));
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, 15);
                returnDateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
            } else {
                JOptionPane.showMessageDialog(null, "Book not found!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    private void issueBook(ActionEvent e) {
        if (bookIdField.getText().trim().isEmpty() || bookNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "gyash801@")) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO issued_books (book_id, book_name, author, issue_date, return_date) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, bookIdField.getText().trim());
            stmt.setString(2, bookNameField.getText().trim());
            stmt.setString(3, authorField.getText().trim());
            stmt.setString(4, issueDateField.getText().trim());
            stmt.setString(5, returnDateField.getText().trim());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Book Issued Successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to issue book.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new IssueBookView();
    }
}
