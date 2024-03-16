package frontend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteMedicine extends JFrame {
    private JTable table;

    public DeleteMedicine() {
        setTitle("Delete Medicine");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            // MySQL connection parameters
            String url = "jdbc:mysql://pharmacy-database-management.ctau02a6kr5e.ap-south-1.rds.amazonaws.com:3306/mydb";
            String user = "admin";
            String password = "samplepassword1234";

            // Establishing connection to the database
            Connection connection = DriverManager.getConnection(url, user, password);
            //final Connection c2 = connection;
            Statement statement = connection.createStatement();

            // Fetching data from MySQL and creating a table model
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Medicines");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount+1];
            columnNames[columnCount] = "Delete";
            
            for (int i = 1; i <= columnCount; i++) {
            	
                columnNames[i - 1] = metaData.getColumnName(i);
                
            }
            System.out.println("Hello");
            final DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            while (resultSet.next()) {
                Object[] row = new Object[columnCount + 1];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                    
                }
                JButton deleteButton = new JButton("Delete");
                final String medId = String.valueOf(resultSet.getObject(1));
                deleteButton.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		
                		int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this medicine?",
                                "Confirm", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                        	deleteMedicine(medId, model);
                        }
                            
                        }
                		
                });
                row[columnCount] = deleteButton;
                model.addRow(row);
            }

            // Creating the JTable with the table model
            table = new JTable(model);
           
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to the database: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteMedicine(String medId, DefaultTableModel model) {
    	String url = "jdbc:mysql://pharmacy-database-management.ctau02a6kr5e.ap-south-1.rds.amazonaws.com:3306/mydb";
        String user = "admin";
        String password = "samplepassword1234";
    	try {
    		Connection c = DriverManager.getConnection(url, user, password);
            String deleteQuery = "DELETE FROM Medicines WHERE medID = ?";
            PreparedStatement preparedStatement = c.prepareStatement(deleteQuery);
            preparedStatement.setString(1, medId);
            preparedStatement.executeUpdate();
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).equals(medId)) {
                    model.removeRow(i);
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "Medicine deleted successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to delete medicine: " + ex.getMessage());
        }
    }
    

    public static void main(String[] args) {
         new DeleteMedicine().setVisible(true);
    }
}

