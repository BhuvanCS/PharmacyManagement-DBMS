package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateMedicine extends JFrame {
    private JTextField medIdField;
    private JTextField nameField;
    private JTextField manufacturerField;
    private JTextField stockField;
    private JTextField priceField;
    private JTextField expDateField;
    private JTextField packSizeField;

    public UpdateMedicine() {
        setTitle("Update Medicine");
        setSize(400, 300);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Components
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel medIdLabel = new JLabel("Medicine ID:");
        medIdField = new JTextField(10);
        JButton searchButton = new JButton("Search");
        topPanel.add(medIdLabel);
        topPanel.add(medIdField);
        topPanel.add(searchButton);

        JPanel centerPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel manufacturerLabel = new JLabel("Manufacturer:");
        manufacturerField = new JTextField();
        JLabel stockLabel = new JLabel("Stock:");
        stockField = new JTextField();
        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField();
        JLabel expDateLabel = new JLabel("Expiry Date:");
        expDateField = new JTextField();
        JLabel packSizeLabel = new JLabel("Pack Size:");
        packSizeField = new JTextField();
        centerPanel.add(nameLabel);
        centerPanel.add(nameField);
        centerPanel.add(manufacturerLabel);
        centerPanel.add(manufacturerField);
        centerPanel.add(stockLabel);
        centerPanel.add(stockField);
        centerPanel.add(priceLabel);
        centerPanel.add(priceField);
        centerPanel.add(expDateLabel);
        centerPanel.add(expDateField);
        centerPanel.add(packSizeLabel);
        centerPanel.add(packSizeField);

        JPanel buttonPanel = new JPanel();
        JButton updateButton = new JButton("Update");
        buttonPanel.add(updateButton);
        
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Update medicine details in the database
                String medId = medIdField.getText();
                String name = nameField.getText();
                String manufacturer = manufacturerField.getText();
                int stock = Integer.parseInt(stockField.getText());
                double price = Double.parseDouble(priceField.getText());
                String expDate = expDateField.getText();
                String packSize = packSizeField.getText();

                try {
                    // MySQL connection parameters
                    String url = "jdbc:mysql://pharmacy-database-management.ctau02a6kr5e.ap-south-1.rds.amazonaws.com:3306/mydb";
                    String user = "admin";
                    String password = "samplepassword1234";

                    // Establishing connection to the database
                    Connection connection = DriverManager.getConnection(url, user, password);
                    String updateQuery = "CALL UpdateMedicineDetails(?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                    preparedStatement.setString(2, name);
                    preparedStatement.setString(3, manufacturer);
                    preparedStatement.setInt(4, stock);
                    preparedStatement.setDouble(5, price);
                    preparedStatement.setString(6, expDate);
                    preparedStatement.setString(7, packSize);
                    preparedStatement.setString(1, medId);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Medicine updated successfully!");
                        clearData();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to update medicine.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error updating medicine: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Search button action
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Fetch medicine details from the database based on MedicineID
                String medId = medIdField.getText();

                try {
                    // MySQL connection parameters
                    String url = "jdbc:mysql://pharmacy-database-management.ctau02a6kr5e.ap-south-1.rds.amazonaws.com:3306/mydb";
                    String user = "admin";
                    String password = "samplepassword1234";

                    // Establishing connection to the database
                    Connection connection = DriverManager.getConnection(url, user, password);
                    String selectQuery = "SELECT * FROM Medicines WHERE medID=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                    preparedStatement.setString(1, medId);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        nameField.setText(resultSet.getString("name"));
                        manufacturerField.setText(resultSet.getString("manufacturer"));
                        stockField.setText(String.valueOf(resultSet.getInt("stock")));
                        priceField.setText(String.valueOf(resultSet.getDouble("price")));
                        expDateField.setText(resultSet.getString("expDate"));
                        packSizeField.setText(resultSet.getString("packSize"));
                    } else {
                        JOptionPane.showMessageDialog(null, "Medicine not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error fetching medicine details: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    public void clearData() {
    	nameField.setText("");
        manufacturerField.setText("");
        stockField.setText("");
        priceField.setText("");
        expDateField.setText("");
        packSizeField.setText("");
        medIdField.setText("");
    }
    public static void main(String[] args) {
		new UpdateMedicine().setVisible(true);
	}
}

