package frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;

public class AddMedicineForm extends JFrame {
    private JTextField medIdField, nameField, manufacturerField, stockField, priceField, expDateField, packSizeField;

    public AddMedicineForm() {
        setTitle("Add a Medicine");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create labels and text fields
        JLabel medIdLabel = new JLabel("Medicine ID:");
        medIdLabel.setBorder(new EmptyBorder(0,10,0,0));
        
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBorder(new EmptyBorder(0,10,0,0));
        
        JLabel manufacturerLabel = new JLabel("Manufacturer:");
        manufacturerLabel.setBorder(new EmptyBorder(0,10,0,0));
        
        JLabel stockLabel = new JLabel("Stock:");
        stockLabel.setBorder(new EmptyBorder(0,10,0,0));
        
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBorder(new EmptyBorder(0,10,0,0));
        
        JLabel expDateLabel = new JLabel("Expiry Date:");
        expDateLabel.setBorder(new EmptyBorder(0,10,0,0));
        
        JLabel packSizeLabel = new JLabel("Pack Size:");
        packSizeLabel.setBorder(new EmptyBorder(0,10,0,0));

        medIdField = new JTextField(20);
        nameField = new JTextField(20);
        manufacturerField = new JTextField(20);
        stockField = new JTextField(20);
        priceField = new JTextField(20);
        expDateField = new JTextField(20);
        packSizeField = new JTextField(20);

        // Create button
        JButton addButton = new JButton("Add Medicine");

        // Add action listener to the button
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addMedicine();
            }
        });

        // Set layout
        setLayout(new GridLayout(8, 2));

        // Add components to the frame
        add(medIdLabel);
        add(medIdField);
        add(nameLabel);
        add(nameField);
        add(manufacturerLabel);
        add(manufacturerField);
        add(stockLabel);
        add(stockField);
        add(priceLabel);
        add(priceField);
        add(expDateLabel);
        add(expDateField);
        add(packSizeLabel);
        add(packSizeField);
        add(addButton);

        setVisible(true);
    }

    private void addMedicine() {
        // Get input values
        String medId = medIdField.getText();
        String name = nameField.getText();
        String manufacturer = manufacturerField.getText();
        String stock = stockField.getText();
        String price = priceField.getText();
        String expDate = expDateField.getText();
        String packSize = packSizeField.getText();

        // Validate mandatory fields
        if (medId.isEmpty() || name.isEmpty() || manufacturer.isEmpty() || stock.isEmpty() || price.isEmpty() || expDate.isEmpty() || packSize.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are mandatory!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

//        LocalDate currentDate = LocalDate.now();
//        LocalDate expiryDate = LocalDate.parse(expDate);
//        assert !expiryDate.isBefore(currentDate) : "Expiry date cannot be before the current date.";
        // Perform database insertion
        try {
            String url = "jdbc:mysql://pharmacy-database-management.ctau02a6kr5e.ap-south-1.rds.amazonaws.com:3306/mydb";
            String userid = "admin";
            String password = "samplepassword1234";

            Connection connection = DriverManager.getConnection(url, userid, password);
            String sql = "INSERT INTO Medicines (medId, name, manufacturer, stock, price, expDate, packSize) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, medId);
            statement.setString(2, name);
            statement.setString(3, manufacturer);
            statement.setString(4, stock);
            statement.setString(5, price);
            statement.setString(6, expDate);
            statement.setString(7, packSize);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Medicine added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add medicine!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        medIdField.setText("");
        nameField.setText("");
        manufacturerField.setText("");
        stockField.setText("");
        priceField.setText("");
        expDateField.setText("");
        packSizeField.setText("");
    }

    public static void main(String[] args) {
       new AddMedicineForm().setVisible(true);
    }
}
