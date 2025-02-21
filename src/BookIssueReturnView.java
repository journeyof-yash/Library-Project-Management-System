import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookIssueReturnView extends JFrame {
    private JTextField bookTitleField, authorField, issueDateField, returnDateField, serialNoField;
    private JButton issueBookButton, returnBookButton;

    public BookIssueReturnView() {
        setTitle("Library Management System - Book Issue & Return");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Book Title:"));
        bookTitleField = new JTextField();
        add(bookTitleField);

        add(new JLabel("Author:"));
        authorField = new JTextField();
        authorField.setEditable(false);
        add(authorField);

        add(new JLabel("Issue Date:"));
        issueDateField = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        issueDateField.setEditable(false);
        add(issueDateField);

        add(new JLabel("Return Date:"));
        returnDateField = new JTextField(LocalDate.now().plusDays(15).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        add(returnDateField);

        add(new JLabel("Serial No:"));
        serialNoField = new JTextField();
        add(serialNoField);

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
        modifyBookTransaction("INSERT INTO issued_books (title, author, issue_date, return_date, serial_no) VALUES (?, ?, ?, ?, ?)", "Book issued successfully!");
    }

    private void returnBook(ActionEvent e) {
        modifyBookTransaction("DELETE FROM issued_books WHERE serial_no = ?", "Book returned successfully!");
    }

    private void modifyBookTransaction(String query, String successMessage) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root", "gyash801@")) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, bookTitleField.getText().trim());
            stmt.setString(2, authorField.getText().trim());
            stmt.setString(3, issueDateField.getText().trim());
            stmt.setString(4, returnDateField.getText().trim());
            stmt.setString(5, serialNoField.getText().trim());
            int rows = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, rows > 0 ? successMessage : "Operation failed!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new BookIssueReturnView();
    }
}
