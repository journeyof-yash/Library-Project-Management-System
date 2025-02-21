import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardView extends JFrame {
    public DashboardView() {
        setTitle("Library Management System - Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        JButton addBookButton = new JButton("Add Book");
        JButton issueBookButton = new JButton("Issue Book");
        JButton returnBookButton = new JButton("Return Book");
        JButton manageUsersButton = new JButton("Manage Users");
        JButton viewReportsButton = new JButton("View Reports");
        JButton logoutButton = new JButton("Logout");

        add(addBookButton);
        add(issueBookButton);
        add(returnBookButton);
        add(manageUsersButton);
        add(viewReportsButton);
        add(logoutButton);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginView();
                dispose();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new DashboardView();
    }
}
