import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class FinePaymentView extends JFrame {
    private JTextField serialNoField, fineAmountField, remarksField;
    private JCheckBox finePaidCheckbox;
    private JButton confirmPaymentButton;
    private String username;

    public FinePaymentView() {

        setTitle("Library Management System - Fine Payment");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Serial No:"));
        serialNoField = new JTextField();
        add(serialNoField);

        add(new JLabel("Fine Amount:"));
        fineAmountField = new JTextField();
        fineAmountField.setEditable(false);
        add(fineAmountField);

        add(new JLabel("Remarks:"));
        remarksField = new JTextField();
        add(remarksField);

        add(new JLabel("Fine Paid:"));
        finePaidCheckbox = new JCheckBox();
        add(finePaidCheckbox);

        confirmPaymentButton = new JButton("Confirm Payment");
        confirmPaymentButton.addActionListener(this::confirmPayment);
        add(confirmPaymentButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }




    private void confirmPayment(ActionEvent e) {
        if (!finePaidCheckbox.isSelected()) {
            JOptionPane.showMessageDialog(null, "Please check the Fine Paid checkbox to proceed!");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root", "gyash801@")) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE issued_books SET fine_paid = ? WHERE serial_no = ?");
            stmt.setBoolean(1, true);
            stmt.setString(2, serialNoField.getText().trim());
            int rows = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, rows > 0 ? "Fine paid successfully!" : "No record found for this Serial No.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new FinePaymentView();
    }
}
