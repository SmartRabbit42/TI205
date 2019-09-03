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
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;

public class Client {

	private JFrame frmDolphin;
	
	private JPanel activePanel;
	
	private final Dimension expectedDimension = new Dimension(500, 600);
	private JTextField txtUsername;

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
		panAuthentication.setLayout(null);
		
		JLabel lblAuthentication = new JLabel("Authentication");
		lblAuthentication.setFont(new Font("Arial", Font.BOLD, 40));
		lblAuthentication.setForeground(Color.WHITE);
		lblAuthentication.setHorizontalAlignment(SwingConstants.CENTER);
		lblAuthentication.setBounds(0, 15, 500, 70);
		panAuthentication.add(lblAuthentication);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Arial", Font.BOLD, 20));
		lblUsername.setBounds(0, 120, 500, 30);
		panAuthentication.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(100, 160, 300, 30);
		panAuthentication.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblDataFile = new JLabel("data file");
		lblDataFile.setForeground(Color.WHITE);
		lblDataFile.setFont(new Font("Arial", Font.BOLD, 20));
		lblDataFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataFile.setBounds(0, 220, 500, 30);
		panAuthentication.add(lblDataFile);
		
		JButton btnDataFile = new JButton("select data file");
		btnDataFile.setForeground(Color.BLACK);
		btnDataFile.setFont(new Font("Arial", Font.BOLD, 15));
		btnDataFile.setBounds(150, 260, 200, 30);
		panAuthentication.add(btnDataFile);
		
		JButton btnAuthentication = new JButton("authenticate");
		btnAuthentication.setFont(new Font("Arial", Font.BOLD, 40));
		btnAuthentication.setForeground(new Color(0, 0, 0));
		btnAuthentication.setBounds(100, 400, 300, 100);
		panAuthentication.add(btnAuthentication);
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
