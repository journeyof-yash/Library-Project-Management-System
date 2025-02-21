import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class FineManagementView extends JFrame {
    private JTextField transactionIdField, fineAmountField;
    private JCheckBox finePaidCheckBox;
    private JButton payFineButton;

    public FineManagementView() {
        setTitle("Library Management System - Fine Management");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Transaction ID:"));
        transactionIdField = new JTextField();
        add(transactionIdField);

        add(new JLabel("Fine Amount (Auto-filled):"));
        fineAmountField = new JTextField();
        fineAmountField.setEditable(false);
        add(fineAmountField);

        add(new JLabel("Fine Paid:"));
        finePaidCheckBox = new JCheckBox();
        add(finePaidCheckBox);

        payFineButton = new JButton("Pay Fine");
        payFineButton.addActionListener(this::payFine);
        add(payFineButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void payFine(ActionEvent e) {
        if (!finePaidCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(null, "Please select the fine paid checkbox before proceeding.");
            return;
        }
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root", "gyash801@")) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE transactions SET fine_paid = ? WHERE transaction_id = ?");
            stmt.setBoolean(1, true);
            stmt.setString(2, transactionIdField.getText().trim());
            int rows = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, rows > 0 ? "Fine payment successful!" : "Transaction not found!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new FineManagementView();
    }
}
