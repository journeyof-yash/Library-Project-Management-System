import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DashboardView extends JFrame {
    private String role; // Stores user role (Admin/User)

    public DashboardView(String role) {
        this.role = role;
        setTitle("Library Management System - " + role + " Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 3, 10, 10)); // 3x3 layout with spacing

        // Buttons for common functionalities
        JButton viewBooksButton = new JButton("View Books");
        JButton issueBookButton = new JButton("Issue a Book");
        JButton returnBookButton = new JButton("Return a Book");
        JButton membershipButton = new JButton("Manage Memberships");
        JButton logoutButton = new JButton("Logout");

        // Admin-only functionalities
        JButton manageUsersButton = new JButton("Manage Users");
        JButton manageBooksButton = new JButton("Manage Books");
        JButton reportsButton = new JButton("Generate Reports");
        JButton roleManagementButton = new JButton("Manage Roles");

        // Action listeners for navigation
        viewBooksButton.addActionListener(e -> openViewBooks());
        issueBookButton.addActionListener(e -> openIssueBook());
        returnBookButton.addActionListener(e -> openReturnBook());
        membershipButton.addActionListener(e -> openMembershipManagement());
        logoutButton.addActionListener(e -> logout());

        if ("Admin".equalsIgnoreCase(role)) {
            manageUsersButton.addActionListener(e -> openUserManagement());
            manageBooksButton.addActionListener(e -> openBookManagement());
            reportsButton.addActionListener(e -> openReportsView());
            roleManagementButton.addActionListener(e -> openRoleManagement());

            // Add admin buttons
            add(manageUsersButton);
            add(manageBooksButton);
            add(reportsButton);
            add(roleManagementButton);
        } else {
            add(issueBookButton);
            add(returnBookButton);
        }

        // Common buttons for both User & Admin
        add(viewBooksButton);
        add(membershipButton);
        add(logoutButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Navigation Methods
    private void openViewBooks() {
        new ReportsView(); // Opens the book report window
    }

    private void openIssueBook() {
        new IssuedBooksView(); // Opens the issue book window
    }

    private void openReturnBook() {
        JOptionPane.showMessageDialog(this, "Return Book Feature Coming Soon!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void openMembershipManagement() {
        new MembershipManagementView(); // Opens membership management window
    }

    private void openUserManagement() {
        new UserManagementView(); // Opens user management window
    }

    private void openBookManagement() {
        JOptionPane.showMessageDialog(this, "Book Management Feature Coming Soon!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void openReportsView() {
        new ReportsView(); // Opens reports window
    }

    private void openRoleManagement() {
        new RoleManagementView(); // Opens role management window
    }

    private void logout() {
        JOptionPane.showMessageDialog(this, "Logging out...");
        dispose();
        new LoginView(); // Redirects to login screen
    }

    public static void main(String[] args) {
        new DashboardView("Admin"); // Test Admin Dashboard
        // new DashboardView("User"); // Test User Dashboard
    }
}
