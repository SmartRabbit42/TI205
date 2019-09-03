package dolphin;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;

public class Client {

	private JFrame frmDolphin;
	
	private JPanel activePanel;
	
	private final Dimension expectedDimension = new Dimension(500, 600);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frmDolphin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Client() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDolphin = new JFrame();
		frmDolphin.setTitle("dolphin");
		frmDolphin.setBounds(100, 100, 500, 600);
		frmDolphin.setMinimumSize(expectedDimension);
		frmDolphin.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frmDolphin.setVisible(true);
		frmDolphin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = frmDolphin.getContentPane();
		contentPane.setLayout(new CardLayout(0, 0));
		
		
		// Entry
		JPanel panEntry = new JPanel();
		panEntry.setBackground(new Color(15, 100, 150, 255));
		panEntry.setLayout(new BorderLayout(0, 0));
		
		Box box = new Box(BoxLayout.Y_AXIS);
		
		JPanel panAuthentication = new JPanel();
		panAuthentication.setBackground(new Color(10, 50, 75, 255));
		panAuthentication.setPreferredSize(expectedDimension);
		panAuthentication.setMaximumSize(expectedDimension);
		panAuthentication.setMinimumSize(expectedDimension);
		
		
		
		
		box.add(Box.createVerticalGlue());
		box.add(panAuthentication);
		box.add(Box.createVerticalGlue());
		
		panEntry.add(box);
		
		frmDolphin.getContentPane().add(panEntry, "authentication");
		
		
		// Master
		JPanel panMaster = new JPanel();
		panMaster.setBackground(new Color(15, 100, 150, 255));
		panMaster.setLayout(new BorderLayout(0, 0));
		
		frmDolphin.getContentPane().add(panMaster, "master");
		
		
		// Start up
		activePanel = panEntry;
		
		// Events
	}
}
