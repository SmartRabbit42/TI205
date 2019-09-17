package visual;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import data.Data;
import network.Network;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class Client {

	private JFrame frmDolphin;
	
	private File historyFile;

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
		frmDolphin.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frmDolphin.setVisible(true);
		frmDolphin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDolphin.getContentPane().setLayout(new CardLayout());
		
		
		// Entry
		JPanel panEntry = new JPanel();
		panEntry.setBackground(new Color(15, 100, 150, 255));
		panEntry.setLayout(new BorderLayout(0, 0));
		
		Box entryBox = new Box(BoxLayout.Y_AXIS);
		
		JPanel panAuthentication = new JPanel();
		panAuthentication.setBackground(new Color(10, 50, 75, 255));
		panAuthentication.setPreferredSize(new Dimension(500, 600));
		panAuthentication.setMaximumSize(new Dimension(500, 600));
		panAuthentication.setMinimumSize(new Dimension(500, 600));
		panAuthentication.setLayout(null);
		
		JLabel lblAuthentication = new JLabel("Authentication");
		lblAuthentication.setFont(new Font("Arial", Font.BOLD, 40));
		lblAuthentication.setForeground(Color.WHITE);
		lblAuthentication.setHorizontalAlignment(SwingConstants.CENTER);
		lblAuthentication.setBounds(0, 15, 500, 70);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Arial", Font.BOLD, 20));
		lblUsername.setBounds(0, 120, 500, 30);
		
		JTextField txtUsername = new JTextField();
		txtUsername.setBounds(100, 160, 300, 30);
		txtUsername.setColumns(10);
		
		JLabel lblDataFile = new JLabel("data file");
		lblDataFile.setForeground(Color.WHITE);
		lblDataFile.setFont(new Font("Arial", Font.BOLD, 20));
		lblDataFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataFile.setBounds(0, 220, 500, 30);
		
		JButton btnDataFile = new JButton("select data file");
		btnDataFile.setForeground(Color.BLACK);
		btnDataFile.setFont(new Font("Arial", Font.BOLD, 15));
		btnDataFile.setBounds(150, 260, 200, 30);

		JLabel lblSelectedFile = new JLabel("none");
		lblSelectedFile.setForeground(Color.WHITE);
		lblSelectedFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectedFile.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSelectedFile.setBounds(150, 300, 200, 20);
		
		JButton btnAuthentication = new JButton("authenticate");
		btnAuthentication.setFont(new Font("Arial", Font.BOLD, 40));
		btnAuthentication.setForeground(new Color(0, 0, 0));
		btnAuthentication.setBounds(100, 400, 300, 100);

		panAuthentication.add(lblAuthentication);
		panAuthentication.add(lblUsername);
		panAuthentication.add(txtUsername);
		panAuthentication.add(lblDataFile);
		panAuthentication.add(btnDataFile);
		panAuthentication.add(lblSelectedFile);
		panAuthentication.add(btnAuthentication);
		
		entryBox.add(Box.createVerticalGlue());
		entryBox.add(panAuthentication);
		entryBox.add(Box.createVerticalGlue());
		
		panEntry.add(entryBox);
		
		frmDolphin.getContentPane().add(panEntry, "authentication");
		
		
		// Master
		JPanel panMaster = new JPanel();
		panMaster.setBackground(new Color(15, 100, 150, 255));
		panMaster.setLayout(new BorderLayout(0, 0));
		
		Box masterBox = new Box(BoxLayout.Y_AXIS);
		
		JPanel panMessages = new JPanel();
		panMessages.setBackground(new Color(10, 50, 75, 255));
		panMessages.setPreferredSize(new Dimension(800, 600));
		panMessages.setMaximumSize(new Dimension(800, 600));
		panMessages.setMinimumSize(new Dimension(800, 600));
		panMessages.setLayout(null);
		
		masterBox.add(Box.createVerticalGlue());
		masterBox.add(panMessages);
		masterBox.add(Box.createVerticalGlue());
		
		panMaster.add(masterBox);
		
		frmDolphin.getContentPane().add(panMaster, "master");
		
		
		
		// Start up
		frmDolphin.setMinimumSize(new Dimension(500, 600));
		
		// Events
		btnDataFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("select history file");
				fc.setFileFilter(new FileNameExtensionFilter("dolphin files", "dolphin"));
				
		        int returnVal = fc.showOpenDialog(frmDolphin);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        	Data.loadHistory(fc.getSelectedFile());
		            lblSelectedFile.setText(historyFile.getName());
		        }
			}
		});
		btnAuthentication.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					Network.start(txtUsername.getText());
				} catch (IOException e) {
					// TODO
					e.printStackTrace();
				}

				changeToMainPanel();
			}
		});
	}
	
	private void changeToMainPanel() {
		frmDolphin.setMinimumSize(new Dimension(800, 600));
		
		((CardLayout) frmDolphin.getContentPane().getLayout()).show(frmDolphin.getContentPane(), "master");
	}
}
