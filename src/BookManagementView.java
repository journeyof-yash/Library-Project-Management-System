import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class BookManagementView extends JFrame {
    private JTextField bookTitleField, authorField, isbnField;
    private JButton addBookButton, updateBookButton, deleteBookButton;

    public BookManagementView() {
        setTitle("Library Management System - Book Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Book Title:"));
        bookTitleField = new JTextField();
        add(bookTitleField);

        add(new JLabel("Author:"));
        authorField = new JTextField();
        add(authorField);

        add(new JLabel("ISBN:"));
        isbnField = new JTextField();
        add(isbnField);

        addBookButton = new JButton("Add Book");
        addBookButton.addActionListener(this::addBook);
        add(addBookButton);

        updateBookButton = new JButton("Update Book");
        updateBookButton.addActionListener(this::updateBook);
        add(updateBookButton);

        deleteBookButton = new JButton("Delete Book");
        deleteBookButton.addActionListener(this::deleteBook);
        add(deleteBookButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addBook(ActionEvent e) {
        modifyBook("INSERT INTO books (title, author, isbn) VALUES (?, ?, ?)", "Book added successfully!");
    }

    private void updateBook(ActionEvent e) {
        modifyBook("UPDATE books SET author = ?, isbn = ? WHERE title = ?", "Book updated successfully!");
    }

    private void deleteBook(ActionEvent e) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root", "gyash801@")) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE title = ?");
            stmt.setString(1, bookTitleField.getText().trim());
            int rows = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, rows > 0 ? "Book deleted successfully!" : "Book not found!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    private void modifyBook(String query, String successMessage) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "password")) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, bookTitleField.getText().trim());
            stmt.setString(2, authorField.getText().trim());
            stmt.setString(3, isbnField.getText().trim());
            int rows = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, rows > 0 ? successMessage : "Operation failed!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new BookManagementView();
    }
}
