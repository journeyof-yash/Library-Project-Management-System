import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdminDashboard extends JFrame {
    private JButton manageUsersButton, manageBooksButton, issuedBooksButton, viewReportsButton, logoutButton;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        manageUsersButton = new JButton("Manage Users");
        manageUsersButton.addActionListener(this::openUserManagement);
        add(manageUsersButton);

        manageBooksButton = new JButton("Manage Books");
        manageBooksButton.addActionListener(this::openBookManagement);
        add(manageBooksButton);

        issuedBooksButton = new JButton("View Issued Books");
        issuedBooksButton.addActionListener(this::viewIssuedBooks);
        add(issuedBooksButton);

        viewReportsButton = new JButton("View Reports");
        viewReportsButton.addActionListener(this::viewReports);
        add(viewReportsButton);

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this::logout);
        add(logoutButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openUserManagement(ActionEvent e) {
        new UserManagementView();
    }

    private void openBookManagement(ActionEvent e) {
        new BookManagementView();
    }

    private void viewIssuedBooks(ActionEvent e) {
        new IssuedBooksView();
    }

    private void viewReports(ActionEvent e) {
        new ReportsView();
    }

    private void logout(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Logging out...");
        dispose();
        new LoginView();
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}
