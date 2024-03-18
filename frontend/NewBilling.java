package frontend;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class NewBilling extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField custPhoneText;
	private JTextField saleIdText;
	private JTextField custIdField;
	private JTextField medIdText;
	private JTextField priceText;
	private JTextField qtyText;
	private JTextField subTotText;

	String url = "jdbc:mysql://pharmacy-database-management.ctau02a6kr5e.ap-south-1.rds.amazonaws.com:3306/mydb";
    String userid = "admin";
    String password = "samplepassword1234";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewBilling frame = new NewBilling();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NewBilling() {
		setTitle("New Billing");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel custPhoneLabel = new JLabel("Customer Phone Number:");
		custPhoneLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		custPhoneLabel.setBounds(10, 11, 178, 30);
		contentPane.add(custPhoneLabel);
		
		custPhoneText = new JTextField();
		custPhoneText.setFont(new Font("Tahoma", Font.PLAIN, 13));
		custPhoneText.setBounds(189, 17, 170, 20);
		contentPane.add(custPhoneText);
		custPhoneText.setColumns(10);
		
		JButton btnCustSearch = new JButton("Search");
		btnCustSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String custId = getCustomerId(custPhoneText.getText());
				custIdField.setText(custId);
			}
		});
		btnCustSearch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCustSearch.setBounds(389, 16, 89, 23);
		contentPane.add(btnCustSearch);
		
		JLabel saleIdLabel = new JLabel("Sale ID:");
		saleIdLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		saleIdLabel.setBounds(69, 60, 75, 30);
		contentPane.add(saleIdLabel);
		
		saleIdText = new JTextField();
		saleIdText.setFont(new Font("Tahoma", Font.PLAIN, 13));
		saleIdText.setColumns(10);
		saleIdText.setBounds(183, 65, 116, 20);
		contentPane.add(saleIdText);
		
		JButton btnGenerateId = new JButton("Generate ID");
		btnGenerateId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String saleId = generateUniqueSaleId();
				saleIdText.setText(saleId);
			}
		});
		btnGenerateId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnGenerateId.setBounds(325, 64, 128, 23);
		contentPane.add(btnGenerateId);
		
		JLabel lblCustomerId = new JLabel("Customer ID:");
		lblCustomerId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCustomerId.setBounds(69, 91, 89, 30);
		contentPane.add(lblCustomerId);
		
		custIdField = new JTextField();
		custIdField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		custIdField.setColumns(10);
		custIdField.setBounds(183, 97, 116, 20);
		contentPane.add(custIdField);
		
		JLabel lblMedicineId = new JLabel("Medicine ID:");
		lblMedicineId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMedicineId.setBounds(69, 127, 75, 30);
		contentPane.add(lblMedicineId);
		
		medIdText = new JTextField();
		medIdText.setFont(new Font("Tahoma", Font.PLAIN, 13));
		medIdText.setColumns(10);
		medIdText.setBounds(183, 132, 116, 20);
		contentPane.add(medIdText);
		
		JButton btnSearchMed = new JButton("Search");
		btnSearchMed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double medPrice = getMedPrice(medIdText.getText());
				priceText.setText(String.valueOf(medPrice));
			}
		});
		btnSearchMed.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSearchMed.setBounds(325, 131, 128, 23);
		contentPane.add(btnSearchMed);
		
		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPrice.setBounds(69, 163, 89, 30);
		contentPane.add(lblPrice);
		
		priceText = new JTextField();
		priceText.setFont(new Font("Tahoma", Font.PLAIN, 13));
		priceText.setColumns(10);
		priceText.setBounds(183, 169, 116, 20);
		contentPane.add(priceText);
		
		JLabel lblQuantity = new JLabel("Quantity:");
		lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblQuantity.setBounds(69, 197, 89, 30);
		contentPane.add(lblQuantity);
		
		qtyText = new JTextField();
		qtyText.setFont(new Font("Tahoma", Font.PLAIN, 13));
		qtyText.setColumns(10);
		qtyText.setBounds(183, 203, 116, 20);
		contentPane.add(qtyText);
		
		JLabel lblSubtotal = new JLabel("SubTotal:");
		lblSubtotal.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSubtotal.setBounds(69, 234, 89, 30);
		contentPane.add(lblSubtotal);
		
		subTotText = new JTextField();
		subTotText.setFont(new Font("Tahoma", Font.PLAIN, 13));
		subTotText.setColumns(10);
		subTotText.setBounds(183, 240, 116, 20);
		contentPane.add(subTotText);
		
		JButton btnAddSale = new JButton("Add Sale");
		btnAddSale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String query = "INSERT INTO Sales (saleId, saleDate, quantity, subTotal, customerId, medId) VALUES (?, ?, ?, ?, ?, ?)";
				
				String saleId = saleIdText.getText();
				String saleDate = String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				String customerId = custIdField.getText();
				String medicineId = medIdText.getText();
				double subTotal = Double.valueOf(subTotText.getText());
				int quantity = Integer.valueOf(qtyText.getText());
				try (Connection connection = DriverManager.getConnection(url, userid, password);
			             PreparedStatement statement = connection.prepareStatement(query)) {
			            statement.setString(1, saleId);
			            statement.setString(2, saleDate);
			            statement.setString(5, customerId);
			            statement.setString(6, medicineId);
			            statement.setDouble(4, subTotal);
			            statement.setInt(3, quantity);

			            int rowsInserted = statement.executeUpdate();
			            if (rowsInserted > 0) {
			                JOptionPane.showMessageDialog(null, "Sale added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			                //clearFields();
			            } else {
			                JOptionPane.showMessageDialog(null, "Failed to add sale!", "Error", JOptionPane.ERROR_MESSAGE);
			            }
			        } catch (SQLException e1) {
			            e1.printStackTrace();
			        }
			}
		});
		btnAddSale.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAddSale.setBounds(189, 295, 116, 23);
		contentPane.add(btnAddSale);
		
		JButton btnCalculateSubtotal = new JButton("Calculate Subtotal");
		btnCalculateSubtotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double subtotal = Integer.valueOf(qtyText.getText()) * Double.valueOf(priceText.getText());
				subTotText.setText(String.valueOf(subtotal));
			}
		});
		btnCalculateSubtotal.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCalculateSubtotal.setBounds(325, 202, 153, 23);
		contentPane.add(btnCalculateSubtotal);
	}
	private String getCustomerId(String phone) {
		String query = "SELECT customerId FROM Customers WHERE contact = ?";
		String customerId = "";
        try (Connection connection = DriverManager.getConnection(url, userid, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, phone);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    customerId = resultSet.getString("customerId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerId;
	}
	private double getMedPrice(String medId) {
		String query = "SELECT price FROM Medicines WHERE medId = ?";
		double medPrice = 0;
        try (Connection connection = DriverManager.getConnection(url, userid, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, medId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    medPrice = resultSet.getDouble("price");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medPrice;
	}
	private String generateUniqueSaleId() {
	    
	    String saleId;
	    // Generate a random alphanumeric string
	    String randomString = generateRandomString();
	    
	    while (checkExistsInDatabase("SALE"+randomString)) {
	        randomString = generateRandomString();
	    }
	    
	    saleId = "SALE" + randomString;
	    
	    return saleId;
	}

	// Method to generate a random alphanumeric string
	private String generateRandomString() {
	    
	    int randomNumber = (int) (Math.random() * 1000);
	    
	    String randomString = String.format("%03d", randomNumber);
	    return randomString;
	}

	// Method to check if the generated saleId exists in the database
	private boolean checkExistsInDatabase(String saleId) {
		boolean exists = false;
	    String query = "SELECT COUNT(*) AS count FROM Sales WHERE saleId = ?";
	    
	    try (Connection connection = DriverManager.getConnection(url, userid, password);
	         PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setString(1, saleId);
	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                int count = resultSet.getInt("count");
	                exists = count > 0;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return exists;
	}
}
