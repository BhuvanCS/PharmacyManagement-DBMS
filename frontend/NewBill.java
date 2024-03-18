package frontend;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewBill extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField custPhoneText;
	private JTextField saleIdText;
	private JTextField textField;
	private JTextField medIdText;
	private JTextField priceText;
	private JTextField qtyText;
	private JTextField subTotText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewBill frame = new NewBill();
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
	public NewBill() {
		setTitle("New Billing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 623, 377);
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
			}
		});
		btnGenerateId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnGenerateId.setBounds(325, 64, 128, 23);
		contentPane.add(btnGenerateId);
		
		JLabel lblCustomerId = new JLabel("Customer ID:");
		lblCustomerId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCustomerId.setBounds(69, 91, 89, 30);
		contentPane.add(lblCustomerId);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textField.setColumns(10);
		textField.setBounds(183, 97, 116, 20);
		contentPane.add(textField);
		
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
		btnAddSale.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAddSale.setBounds(189, 295, 116, 23);
		contentPane.add(btnAddSale);
		
		JButton btnCalculateSubtotal = new JButton("Calculate Subtotal");
		btnCalculateSubtotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCalculateSubtotal.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCalculateSubtotal.setBounds(325, 202, 153, 23);
		contentPane.add(btnCalculateSubtotal);
	}
}
