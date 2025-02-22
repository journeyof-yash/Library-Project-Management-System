import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class BookManagementView extends JFrame {
    private JTextField bookIdField, titleField, authorField, genreField;
    private JButton addBookButton, updateBookButton, deleteBookButton;

    public BookManagementView() {
        setTitle("Manage Books");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        add(bookIdField);

        add(new JLabel("Title:"));
        titleField = new JTextField();
        add(titleField);

        add(new JLabel("Author:"));
        authorField = new JTextField();
        add(authorField);

        add(new JLabel("Genre:"));
        genreField = new JTextField();
        add(genreField);

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
        modifyBook("INSERT INTO books (title, author, genre, status) VALUES (?, ?, ?, 'Available')", "Book added successfully!");
    }

    private void updateBook(ActionEvent e) {
        modifyBook("UPDATE books SET title = ?, author = ?, genre = ? WHERE book_id = ?", "Book updated successfully!");
    }

    private void deleteBook(ActionEvent e) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root", "gyash801@")) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE book_id = ?");
            stmt.setInt(1, Integer.parseInt(bookIdField.getText().trim()));
            int rows = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, rows > 0 ? "Book deleted successfully!" : "Book not found!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    private void modifyBook(String query, String successMessage) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root", "gyash801@")) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, titleField.getText().trim());
            stmt.setString(2, authorField.getText().trim());
            stmt.setString(3, genreField.getText().trim());
            if (query.startsWith("UPDATE")) {
                stmt.setInt(4, Integer.parseInt(bookIdField.getText().trim()));
            }
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
