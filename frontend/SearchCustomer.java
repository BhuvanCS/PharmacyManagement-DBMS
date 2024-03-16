package frontend;


import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class SearchCustomer extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JTable table;
    private CustomTableModelCust tableModel;

    public SearchCustomer() {
        searchField = new JTextField(30);
        searchButton = new JButton("Search");
        tableModel = new CustomTableModelCust();

        // Create table with initial data
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Set row height and cell spacing
        int horizontalSpacing = 20;
        int verticalSpacing = 10;
        table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(horizontalSpacing, verticalSpacing));

        // Set font for components
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        searchButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // Add action listener to search button
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText();
            	tableModel.setSearchTerm(searchTerm);
            }
        });

        // Layout components
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(searchField);
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        
            SearchCustomer frame = new SearchCustomer();
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        
    }
}

class CustomTableModelCust extends AbstractTableModel {
    private ArrayList<ArrayList<Object>> data;
    private ArrayList<String> columnNames;

    public CustomTableModelCust() {
        data = new ArrayList<>();
        columnNames = new ArrayList<>();

        // Populate table with initial data
        fetchInitialData();
    }

    private void fetchInitialData() {
        // Connect to MySQL Database, run query, get result set
        String url = "jdbc:mysql://pharmacy-database-management.ctau02a6kr5e.ap-south-1.rds.amazonaws.com:3306/mydb";
        String userid = "admin";
        String password = "samplepassword1234";
        String sql = "SELECT * FROM Customers";

        try (Connection connection = DriverManager.getConnection(url, userid, password);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            // Get column names
            for (int i = 1; i <= columns; i++) {
                columnNames.add(md.getColumnName(i));
            }

            // Get row data
            while (rs.next()) {
                ArrayList<Object> row = new ArrayList<>(columns);

                for (int i = 1; i <= columns; i++) {
                    row.add(rs.getObject(i));
                }

                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    public void setSearchTerm(String searchTerm) {
        data.clear();

        // Connect to MySQL Database, run query, get result set with filtered data
        String url = "jdbc:mysql://pharmacy-database-management.ctau02a6kr5e.ap-south-1.rds.amazonaws.com:3306/mydb";
        String userid = "admin";
        String password = "samplepassword1234";
        String sql = "";
       
    	sql = "SELECT * FROM Customers WHERE customerId LIKE ? OR name LIKE ? OR contact LIKE ? OR address LIKE ?";
        
       
        try (Connection connection = DriverManager.getConnection(url, userid, password);
        		
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            String searchPattern = "%" + searchTerm + "%";
           
        	stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);
            
            
            //stmt.setString(5, searchPattern);

            ResultSet rs = stmt.executeQuery();

            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            // Get column names (only if data is found)
            if (rs.next()) {
                columnNames.clear();
                for (int i = 1; i <= columns; i++) {
                    columnNames.add(md.getColumnName(i));
                }

                // Get row data
                do {
                    ArrayList<Object> row = new ArrayList<>(columns);
                    for (int i = 1; i <= columns; i++) {
                        row.add(rs.getObject(i));
                    }
                    data.add(row);
                } while (rs.next());
            }
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        fireTableDataChanged(); // Notify table of data change
    }
}
