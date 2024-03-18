package frontend;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;
import java.sql.*;
import java.util.*;

public class MedicineTable extends JFrame
{
	private JTextField searchField;
    private JButton searchButton;
    public MedicineTable()
    {
    	searchField = new JTextField(30);
        searchButton = new JButton("Search");
        
        ArrayList columnNames = new ArrayList();
        ArrayList data = new ArrayList();

        //  Connect to an MySQL Database, run query, get result set
        String url = "jdbc:mysql://pharmacy-database-management.ctau02a6kr5e.ap-south-1.rds.amazonaws.com:3306/mydb";
        String userid = "admin";
        String password = "samplepassword1234";
        String sql = "SELECT * FROM Medicines";

        // Java SE 7 has try-with-resources
        // This will ensure that the sql objects are closed when the program 
        // is finished with them
        try (Connection connection = DriverManager.getConnection( url, userid, password );
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery( sql ))
        {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            //  Get column names
            for (int i = 1; i <= columns; i++)
            {
                columnNames.add( md.getColumnName(i) );
            }

            //  Get row data
            while (rs.next())
            {
                ArrayList row = new ArrayList(columns);

                for (int i = 1; i <= columns; i++)
                {
                    row.add( rs.getObject(i) );
                }

                data.add( row );
            }
        }
        catch (SQLException e)
        {
            System.out.println( e.getMessage() );
        }

        // Create Vectors and copy over elements from ArrayLists to them
        // Vector is deprecated but I am using them in this example to keep 
        // things simple - the best practice would be to create a custom defined
        // class which inherits from the AbstractTableModel class
        Vector columnNamesVector = new Vector();
        Vector dataVector = new Vector();

        for (int i = 0; i < data.size(); i++)
        {
            ArrayList subArray = (ArrayList)data.get(i);
            Vector subVector = new Vector();
            for (int j = 0; j < subArray.size(); j++)
            {
                subVector.add(subArray.get(j));
            }
            dataVector.add(subVector);
        }

        for (int i = 0; i < columnNames.size(); i++ )
            columnNamesVector.add(columnNames.get(i));

        //  Create table with database data    
        JTable table = new JTable(dataVector, columnNamesVector)
        {
            public Class getColumnClass(int column)
            {
                for (int row = 0; row < getRowCount(); row++)
                {
                    Object o = getValueAt(row, column);

                    if (o != null)
                    {
                        return o.getClass();
                    }
                }

                return Object.class;
            }
        };
        int horizontalSpacing = 20;
        int verticalSpacing = 10;
        table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(horizontalSpacing, verticalSpacing));

        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        searchButton.setBorder(BorderFactory.createEmptyBorder(2, 10, 5, 10));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(searchField);
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
       
    }

    public static void main(String[] args)
    {
        MedicineTable frame = new MedicineTable();
        //frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frame.pack();
        frame.setVisible(true);
    }
}

