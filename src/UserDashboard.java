import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UserDashboard extends JFrame {
    public UserDashboard() {
        setTitle("User Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns, spacing

        // Buttons for different actions
        JButton viewBooksButton = new JButton("View Books");
        JButton issueBookButton = new JButton("Issue a Book");
        JButton checkMembershipButton = new JButton("Check Membership");
        JButton borrowedBooksButton = new JButton("View Borrowed Books");
        JButton logoutButton = new JButton("Logout");

        // Add action listeners to open respective views
        viewBooksButton.addActionListener(e -> openViewBooks());
        issueBookButton.addActionListener(e -> openIssueBook());
        checkMembershipButton.addActionListener(e -> openMembershipStatus());
        borrowedBooksButton.addActionListener(e -> openBorrowedBooks());
        logoutButton.addActionListener(e -> logout());

        // Add buttons to frame
        add(viewBooksButton);
        add(issueBookButton);
        add(checkMembershipButton);
        add(borrowedBooksButton);
        add(logoutButton);

        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    private void openViewBooks() {
        new ReportsView(); // Opens the books report view
    }

    private void openIssueBook() {
        new IssuedBooksView(); // Opens the issue book window
    }

    private void openMembershipStatus() {
        new MembershipManagementView(); // Opens the membership management window
    }

    private void openBorrowedBooks() {
        JOptionPane.showMessageDialog(this, "Feature Coming Soon!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void logout() {
        JOptionPane.showMessageDialog(this, "Logging out...");
        dispose();
        new LoginView(); // Redirects back to login screen
    }

    public static void main(String[] args) {
        new UserDashboard();
    }
}
