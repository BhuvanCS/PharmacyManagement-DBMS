package frontend;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddCustomerForm extends JFrame {
    private JTextField  nameField, contactField, addressField;

    public AddCustomerForm() {
        setTitle("Register a New Customer");
        setSize(400, 200);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create labels and text fields
        
        JLabel nameLabel = new JLabel("Customer Name:");
        nameLabel.setBorder(new EmptyBorder(0,10,0,0));
        nameField = new JTextField(20);
        
        JLabel contactLabel = new JLabel("Customer Contact:");
        contactLabel.setBorder(new EmptyBorder(0,10,0,0));
        contactField = new JTextField(20);
        
        JLabel addressLabel = new JLabel("Customer Address:");
        addressLabel.setBorder(new EmptyBorder(0,10,0,0));
        addressField = new JTextField(20);
        
        
        
        contactField = new JTextField(20);
        addressField = new JTextField(20);
        

        // Create button
        JButton addButton = new JButton("Add Customer");

        // Add action listener to the button
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });

        // Set layout
        setLayout(new GridLayout(4, 2));

        // Add components to the frame
        
        add(nameLabel);
        add(nameField);
        add(contactLabel);
        add(contactField);
        add(addressLabel);
        add(addressField);
        
        add(addButton);

        setVisible(true);
    }

    private void addCustomer() {
        // Get input values
        
        String name = nameField.getText();
        String contact = contactField.getText();
        String address = addressField.getText();
        String customerId = "C"+contact.substring(3);

        // Validate mandatory fields
        if ( name.isEmpty() || contact.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "All fields are mandatory!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Perform database insertion
        try {
            String url = "jdbc:mysql://pharmacy-database-management.ctau02a6kr5e.ap-south-1.rds.amazonaws.com:3306/mydb";
            String userid = "admin";
            String password = "samplepassword1234";

            Connection connection = DriverManager.getConnection(url, userid, password);
            String sql = "INSERT INTO Customers (customerId, name, contact, address) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, customerId);
            statement.setString(2, name);
            statement.setString(3, contact);
            statement.setString(4, address);
           

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Customer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add Customer!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
       
        nameField.setText("");
        contactField.setText("");
        addressField.setText("");
        
    }

    public static void main(String[] args) {
       new AddCustomerForm().setVisible(true);
    }
}
