package frontend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewBill extends JFrame {
    private JTextField saleIdField;
    private JTable salesTable;
    private JLabel totalSalesLabel;

    public ViewBill() {
        setTitle("View Bill");
        setSize(600, 400);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Top panel with customer ID field and search button
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel saleIdLabel = new JLabel("Sale ID:");
        saleIdField = new JTextField(10);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String saleId = saleIdField.getText().trim();
                if (!saleId.isEmpty()) {
                    fetchSalesData(saleId);
                    calculateTotalSales(saleId);
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a Customer ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        topPanel.add(saleIdLabel);
        topPanel.add(saleIdField);
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);

        // Table to display sales data
        salesTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(salesTable);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JLabel totalSalesTextLabel = new JLabel("Grand Total: ");
        totalSalesLabel = new JLabel();
        bottomPanel.add(totalSalesTextLabel);
        bottomPanel.add(totalSalesLabel);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void fetchSalesData(String saleId) {
        // MySQL connection parameters
        String url = "jdbc:mysql://pharmacy-database-management.ctau02a6kr5e.ap-south-1.rds.amazonaws.com:3306/mydb";
        String user = "admin";
        String password = "samplepassword1234";

        // Fetching data from MySQL
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT s.saleId, s.saleDate, m.name, m.price, s.quantity, s.subTotal\n"
            		+ "FROM Sales s\n"
            		+ "JOIN Medicines m ON s.medId = m.medId\n"
            		+ "WHERE s.saleId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, saleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Populate the table with fetched data
            DefaultTableModel model = new DefaultTableModel(new String[]{"Sale ID", "Sale Date", "Medicine Name", "Price", "Quantity", "Subtotal"}, 0);
            while (resultSet.next()) {
                String sId = resultSet.getString("saleID");
                String saleDate = resultSet.getString("saleDate");
                String medicineName = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                double subTotal = resultSet.getDouble("subTotal");
                model.addRow(new Object[]{sId, saleDate, medicineName, price, quantity, subTotal});
            }
            salesTable.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching sales data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateTotalSales(String saleId) {
        // MySQL connection parameters
        String url = "jdbc:mysql://pharmacy-database-management.ctau02a6kr5e.ap-south-1.rds.amazonaws.com:3306/mydb";
        String user = "admin";
        String password = "samplepassword1234";

        // Fetching total sales amount from MySQL
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT SUM(s.subTotal) AS TotalSales\n" 
                    + "FROM Sales s\n" 
                    + "JOIN Medicines m ON s.medID = m.medID\n" 
                    + "JOIN Customers c ON s.customerID = c.customerID\n" 
                    + "WHERE s.saleID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, saleId);
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
         new ViewBill().setVisible(true);
    }
}

