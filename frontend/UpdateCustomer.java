package frontend;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateCustomer extends JFrame {
    private JTextField custIdField;
    private JTextField nameField;
    private JTextField contactField;
    private JTextField addressField;
 

    public UpdateCustomer() {
        setTitle("Update Customer Details");
        setSize(400, 300);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Components
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel custIdLabel = new JLabel("Customer ID:");
        custIdField = new JTextField(10);
        JButton searchButton = new JButton("Search");
        topPanel.add(custIdLabel);
        topPanel.add(custIdField);
        topPanel.add(searchButton);

        JPanel centerPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel nameLabel = new JLabel("Customer Name:");
        nameField = new JTextField();
        JLabel contactLabel = new JLabel("Contact Number:");
        contactField = new JTextField();
        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField();
       
        centerPanel.add(nameLabel);
        centerPanel.add(nameField);
        centerPanel.add(contactLabel);
        centerPanel.add(contactField);
        centerPanel.add(addressLabel);
        centerPanel.add(addressField);
        

        JPanel buttonPanel = new JPanel();
        JButton updateButton = new JButton("Update Details");
        buttonPanel.add(updateButton);
        
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Update medicine details in the database
                String custId = custIdField.getText();
                String name = nameField.getText();
                String contact = contactField.getText();
                String address = addressField.getText();
                

                try {
                    // MySQL connection parameters
                    String url = "jdbc:mysql://pharmacy-database-management.ctau02a6kr5e.ap-south-1.rds.amazonaws.com:3306/mydb";
                    String user = "admin";
                    String password = "samplepassword1234";

                    // Establishing connection to the database
                    Connection connection = DriverManager.getConnection(url, user, password);
                    String updateQuery = "UPDATE Customers SET name=?, contact=?, address=? WHERE customerID=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, contact);
                    preparedStatement.setString(3, address);
                    preparedStatement.setString(4, custId);
                    
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Customer updated successfully!");
                        clearData();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to update Customer.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error updating Customer: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Search button action
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Fetch medicine details from the database based on MedicineID
                String custId = custIdField.getText();

                try {
                    // MySQL connection parameters
                    String url = "jdbc:mysql://pharmacy-database-management.ctau02a6kr5e.ap-south-1.rds.amazonaws.com:3306/mydb";
                    String user = "admin";
                    String password = "samplepassword1234";

                    // Establishing connection to the database
                    Connection connection = DriverManager.getConnection(url, user, password);
                    String selectQuery = "SELECT * FROM Customers WHERE customerID=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                    preparedStatement.setString(1, custId);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        nameField.setText(resultSet.getString("name"));
                        contactField.setText(resultSet.getString("contact"));
                        addressField.setText(resultSet.getString("address"));
                       
                    } else {
                        JOptionPane.showMessageDialog(null, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error fetching Customer details: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    public void clearData() {
    	nameField.setText("");
        contactField.setText("");
        addressField.setText("");
        custIdField.setText("");
    }
    public static void main(String[] args) {
		new UpdateCustomer().setVisible(true);
	}
}

