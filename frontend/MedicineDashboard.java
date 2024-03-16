package frontend;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MedicineDashboard extends JFrame {
	static String[] userActions = {"Add Medicine", "Delete Medicine", "Search Medicine", "Update Medicine"};
	
	
	
	public MedicineDashboard() {
		
		 // Create a panel to hold the heading label
        JPanel headingPanel = new JPanel();
        
        // Create a label for the heading
        JLabel headingLabel = new JLabel("Medicine Management Module");
        headingLabel.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 30));
        EmptyBorder padding = new EmptyBorder(15,0,30,0);
        headingLabel.setBorder(padding);
        
        // Add the heading label to the panel
        headingPanel.add(headingLabel);

        // Create a panel for the grid layout
        JPanel gridPanel = new JPanel(new GridLayout(2,2));
        
        // Create and add the buttons to the grid panel
        for (int i = 0; i < 4; i++) {
        	final int actionIndex = i;
            JButton button = new JButton(userActions[i]);
            button.setFont(new Font("Segoe UI", Font.BOLD, 20));
            button.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				navigate(actionIndex);
    			}
    		});
            gridPanel.add(button);
            
        }
        
        
        // Set the layout manager of the frame to BorderLayout
        setLayout(new BorderLayout());
        
        // Add the heading panel to the top of the frame
        add(headingPanel, BorderLayout.NORTH);
        
        // Add the grid panel to the center of the frame
        add(gridPanel, BorderLayout.CENTER);
        
        // Set the size and behavior of the frame
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    public static void main(String[] args) {
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MedicineDashboard frame = new MedicineDashboard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
    public  void navigate(int actionIndex) {
    	switch(actionIndex) {
    	case 0:
    		//call
    		new AddMedicineForm().setVisible(true);
    		break;
    	case 1:
    		//call
    		new DeleteMedicine().setVisible(true);
    		break;
    	case 2:
    		//call
    		MedicineTable frame = new MedicineTable();
    		frame.pack();
    		frame.setVisible(true);
    		break;
    	case 3:
    		new UpdateMedicine().setVisible(true);
    		break;
    	}
    }
}

