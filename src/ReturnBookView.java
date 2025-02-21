import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.text.SimpleDateFormat;

public class ReturnBookView extends JFrame {
    private JTextField bookIdField, bookNameField, authorField, issueDateField, returnDateField, fineField;
    private JButton returnButton;

    public ReturnBookView() {
        setTitle("Library Management System - Return Book");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        add(bookIdField);

        add(new JLabel("Book Name:"));
        bookNameField = new JTextField();
        bookNameField.setEditable(false);
        add(bookNameField);

        add(new JLabel("Author:"));
        authorField = new JTextField();
        authorField.setEditable(false);
        add(authorField);

        add(new JLabel("Issue Date:"));
        issueDateField = new JTextField();
        issueDateField.setEditable(false);
        add(issueDateField);

        add(new JLabel("Return Date:"));
        returnDateField = new JTextField();
        add(returnDateField);

        add(new JLabel("Fine:"));
        fineField = new JTextField();
        fineField.setEditable(false);
        add(fineField);

        returnButton = new JButton("Return Book");
        add(returnButton);

        returnButton.addActionListener(this::returnBook);
        bookIdField.addActionListener(e -> fetchBookDetails());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fetchBookDetails() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagement", "root", "gyash801@")) {
            PreparedStatement stmt = conn.prepareStatement("SELECT book_name, author, issue_date, return_date FROM issued_books WHERE book_id = ?");
            stmt.setString(1, bookIdField.getText().trim());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                bookNameField.setText(rs.getString("book_name"));
                authorField.setText(rs.getString("author"));
                issueDateField.setText(rs.getString("issue_date"));
                returnDateField.setText(rs.getString("return_date"));
                calculateFine();
            } else {
                JOptionPane.showMessageDialog(null, "Book not found!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    private void calculateFine() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date returnDate = sdf.parse(returnDateField.getText());
            java.util.Date currentDate = new java.util.Date();

            long diff = currentDate.getTime() - returnDate.getTime();
            long daysLate = diff / (1000 * 60 * 60 * 24);

            if (daysLate > 0) {
                fineField.setText("Rs. " + (daysLate * 10));
            } else {
                fineField.setText("No fine");
            }
        } catch (Exception ex) {
            fineField.setText("Error calculating fine");
        }
    }

    private void returnBook(ActionEvent e) {
        if (bookIdField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Book ID.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root", "gyash801@")) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM issued_books WHERE book_id = ?");
            stmt.setString(1, bookIdField.getText().trim());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Book Returned Successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to return book.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ReturnBookView();
    }
}
