package frontend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CustomerSales extends JFrame {
    private JTextField customerIdField;
    private JTable salesTable;
    private JLabel totalSalesLabel;

    public CustomerSales() {
        setTitle("View Customer Sale");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Top panel with customer ID field and search button
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel customerIdLabel = new JLabel("Customer ID:");
        customerIdField = new JTextField(10);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerId = customerIdField.getText().trim();
                if (!customerId.isEmpty()) {
                    fetchSalesData(customerId);
                    calculateTotalSales(customerId);
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a Customer ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        topPanel.add(customerIdLabel);
        topPanel.add(customerIdField);
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);

        // Table to display sales data
        salesTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(salesTable);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JLabel totalSalesTextLabel = new JLabel("Lifetime Sales Amount Total: ");
        totalSalesLabel = new JLabel();
        bottomPanel.add(totalSalesTextLabel);
        bottomPanel.add(totalSalesLabel);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void fetchSalesData(String customerId) {
        // MySQL connection parameters
        String url = "jdbc:mysql://pharmacy-database-management.ctau02a6kr5e.ap-south-1.rds.amazonaws.com:3306/mydb";
        String user = "admin";
        String password = "samplepassword1234";

        // Fetching data from MySQL
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT s.saleID, s.saleDate, m.name, m.price, s.quantity " +
                           "FROM Sales s " +
                           "JOIN Medicines m ON s.medID = m.medID " +
                           "JOIN Customers c ON s.customerID = c.customerID " +
                           "WHERE c.customerID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Populate the table with fetched data
            DefaultTableModel model = new DefaultTableModel(new String[]{"Sale ID", "Sale Date", "Medicine Name", "Price", "Quantity"}, 0);
            while (resultSet.next()) {
                String saleId = resultSet.getString("saleID");
                String saleDate = resultSet.getString("saleDate");
                String medicineName = resultSet.getString("Name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                model.addRow(new Object[]{saleId, saleDate, medicineName, price, quantity});
            }
            salesTable.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching sales data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateTotalSales(String customerId) {
        // MySQL connection parameters
        String url = "jdbc:mysql://pharmacy-database-management.ctau02a6kr5e.ap-south-1.rds.amazonaws.com:3306/mydb";
        String user = "admin";
        String password = "samplepassword1234";

        // Fetching total sales amount from MySQL
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT SUM(s.quantity * m.price) AS TotalSales " +
                    "FROM Sales s " +
                    "JOIN Medicines m ON s.medID = m.medID " +
                    "JOIN Customers c ON s.customerID = c.customerID " +
                    "WHERE c.CustomerID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double totalSales = resultSet.getDouble("TotalSales");
                totalSalesLabel.setText(String.format("INR %.2f", totalSales));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error calculating total sales: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
         new CustomerSales().setVisible(true);
    }
}
