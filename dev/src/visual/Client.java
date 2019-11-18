package visual;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import data.Data;
import data.containers.Message;
import data.containers.User;
import general.exceptions.EmptyDataFileException;
import general.exceptions.InvalidParameterException;
import data.containers.Chat;
import network.Network;
import network.netMsg.standart.DisconnectMsg;
import visual.dialogs.*;
import visual.panels.*;

/*
 * TODO:
 * 	 create chat dialog
 *   status selection
 *   config options
 *   messaging system
 *   reorganize visual
 *   criptograph
 */

public class Client extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private Client instance;
	
	private Data data;
	private Network network;
	
	private File dataFile;
	
	private JPanel panChats;
	private JPanel panUsers;
	private JLabel lblUsername;
	private JLabel lblAddress;
	
	private ArrayList<UserPanel> users;
	private ArrayList<ChatPanel> chats;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frmDolphin = new Client();
					frmDolphin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// Initialization
	public Client() {
		instance = this;
		data = new Data();
		network = new Network(instance, data);
		
		initializeForm();
		initializeEntry();
		initializeMaster();
	}

	private void initializeForm() {
		setTitle("dolphin");
		setBounds(100, 100, 500, 600);
		setMinimumSize(new Dimension(516, 638));
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout());
		
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	try {
            		if (network.running) {
            			while (dataFile == null)
                			selectDataFile();
            			
            			DisconnectMsg dmsg = new DisconnectMsg();
            			network.spreadMessage(dmsg);
            			
            			data.dump(dataFile);
            			network.shut();
            		}
				} catch (IOException e1) { 
					e1.printStackTrace();
				}
            	System.exit(0);
            }
        });
	}

	private void initializeEntry() {
		JPanel panEntry = new JPanel();
		panEntry.setBackground(new Color(20, 100, 152, 255));
		panEntry.setLayout(new BorderLayout(0, 0));
		
		Box entryBox = new Box(BoxLayout.Y_AXIS);
		
		JPanel panAuthentication = new JPanel();
		panAuthentication.setBackground(new Color(15, 75, 114, 255));
		panAuthentication.setPreferredSize(new Dimension(500, 600));
		panAuthentication.setMaximumSize(new Dimension(500, 600));
		panAuthentication.setMinimumSize(new Dimension(500, 600));
		panAuthentication.setLayout(null);
		
		JLabel lblDolphin = new JLabel("Dolphin");
		lblDolphin.setFont(new Font("Arial", Font.BOLD, 40));
		lblDolphin.setForeground(Color.WHITE);
		lblDolphin.setHorizontalAlignment(SwingConstants.CENTER);
		lblDolphin.setBounds(0, 15, 500, 70);
		
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

		panAuthentication.add(lblDolphin);
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
		
		getContentPane().add(panEntry, "entry");
		
		// Events
		btnDataFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				selectDataFile();
				if (dataFile != null)
					lblSelectedFile.setText(dataFile.getName());
			}
		});
		btnAuthentication.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {	
				try {
					// TODO
					if (dataFile != null)
						data.load(dataFile);
					else {
						data.init(txtUsername.getText());
					}
					
					network.start();
					
					updateLocalUser();
					for (Chat chat : data.getChats())
						addChat(chat);
					for (User user : data.getUsers())
						addUser(user);
					
					setMinimumSize(new Dimension(816, 638));
					((CardLayout) getContentPane().getLayout()).show(getContentPane(), "master");
				} catch (IOException e) {
					// TODO
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO
					e.printStackTrace();
				} catch (EmptyDataFileException e) {
					// TODO
					e.printStackTrace();
				} catch (InvalidParameterException e) {
					// TODO
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initializeMaster() {
		JPanel panMaster = new JPanel();
		panMaster.setBackground(new Color(20, 100, 152, 255));
		panMaster.setLayout(new BorderLayout(0, 0));
		
		Box box = new Box(BoxLayout.Y_AXIS);
		
		JPanel panMain = new JPanel();
		panMain.setPreferredSize(new Dimension(800, 600));
		panMain.setMaximumSize(new Dimension(800, 600));
		panMain.setMinimumSize(new Dimension(800, 600));
		panMain.setLayout(null);
		
		JPanel panMessages = new JPanel();
		panMessages.setBounds(250, 0, 550, 600);
		panMessages.setLayout(null);
		
		JPanel panMessageFlow = new JPanel();
		panMessageFlow.setBounds(0, 0, 550, 550);
		panMessageFlow.setBackground(new Color(15, 75, 114, 255));
		panMessageFlow.setLayout(null);
		
		JPanel panSendMessage = new JPanel();
		panSendMessage.setBounds(0, 550, 550, 50);
		panSendMessage.setBackground(new Color(10, 50, 76, 255));
		panSendMessage.setLayout(null);
		
		JTextField txtMessage = new JTextField();
		txtMessage.setBounds(10, 10, 490, 30);
		
		JButton btnMessage = new JButton();
		btnMessage.setBounds(510, 10, 30, 30);
		
		panSendMessage.add(txtMessage);
		panSendMessage.add(btnMessage);
		
		panMessages.add(panMessageFlow);
		panMessages.add(panSendMessage);
		
		JPanel panAside = new JPanel();
		panAside.setBounds(0, 0, 250, 600);
		panAside.setLayout(null);
		
		JPanel panHeader = new JPanel();
		panHeader.setBounds(0, 0, 250, 65);
		panHeader.setBackground(new Color(5, 25, 38, 255));
		panHeader.setLayout(null);
		
		lblUsername = new JLabel();
		lblUsername.setBounds(10, 5, 200, 40);
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setFont(new Font("Arial", Font.BOLD, 30));
		lblUsername.setVerticalAlignment(SwingConstants.TOP);
		
		lblAddress = new JLabel();
		lblAddress.setBounds(10, 40, 200, 20);
		lblAddress.setForeground(new Color(20, 100, 152, 255));
		lblAddress.setFont(new Font("Arial", Font.ITALIC, 15));
		lblAddress.setVerticalAlignment(SwingConstants.TOP);
		
		JButton btnConfigurations = new JButton("");
		btnConfigurations.setBounds(212, 20, 25, 25);
		
		panHeader.add(lblUsername);
		panHeader.add(lblAddress);
		panHeader.add(btnConfigurations);
		
		JPanel panBody = new JPanel();
		panBody.setLayout(null);
		panBody.setBounds(0, 65, 250, 535);
		
		JPanel panButtons = new JPanel();
		panButtons.setLayout(null);
		panButtons.setBounds(0, 0, 250, 20);
		
		JButton btnChats = new JButton("chats");
		btnChats.setBackground(new Color(100, 100, 100));
		btnChats.setBounds(0, 0, 125, 20);
		
		JButton btnUsers = new JButton("users");
		btnUsers.setBounds(125, 0, 125, 20);
		
		panButtons.add(btnChats);
		panButtons.add(btnUsers);
		
		JPanel panTabs = new JPanel();
		panTabs.setLayout(new CardLayout());
		panTabs.setBackground(new Color(10,10,10));
		panTabs.setBounds(0, 20, 250, 515);
		
		chats = new ArrayList<ChatPanel>();
		
		panChats = new JPanel();
		panChats.setBackground(new Color(10, 50, 76, 255));
		panChats.setLayout(new BoxLayout(panChats, BoxLayout.Y_AXIS));
		
		JButton btnCreateChat = new JButton("+");
		btnCreateChat.setSize(new Dimension(30, 30));
		btnCreateChat.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panChats.add(btnCreateChat);
		
		users = new ArrayList<UserPanel>();
		
		panUsers = new JPanel();
		panUsers.setBackground(new Color(10, 50, 76, 255));
		panUsers.setLayout(new BoxLayout(panUsers, BoxLayout.Y_AXIS));
		
		JButton btnAddUser = new JButton("+");
		btnAddUser.setSize(new Dimension(30, 30));
		btnAddUser.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panUsers.add(btnAddUser);
		panUsers.add(Box.createVerticalGlue());
		
		panTabs.add(panChats, "chats");
		panTabs.add(panUsers, "users");
		
		panBody.add(panButtons);
		panBody.add(panTabs);
		
		panAside.add(panHeader);
		panAside.add(panBody);
		
		panMain.add(panMessages);
		panMain.add(panAside);
		
		box.add(Box.createVerticalGlue());
		box.add(panMain);
		box.add(Box.createVerticalGlue());
		
		panMaster.add(box);
		    
		getContentPane().add(panMaster, "master");
		
		// Events
		btnChats.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {	
				btnChats.setBackground(new Color(100, 100, 100));
				btnUsers.setBackground(null);
				((CardLayout) panTabs.getLayout()).show(panTabs, "chats");
			}
		});
		btnUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {	
				btnChats.setBackground(null);
				btnUsers.setBackground(new Color(100, 100, 100));
				((CardLayout) panTabs.getLayout()).show(panTabs, "users");
			}
		});
		btnCreateChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {	
				CreateChatDialog dialog = new CreateChatDialog(instance, network, data);
				dialog.setVisible(true);
			}
		});
		btnAddUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {	
				AddUserDialog dialog = new AddUserDialog(instance, network, data);
				dialog.setVisible(true);
			}
		});
	}

	// Methods
	public void addMessage(Message msg, Chat chat) {
		
	}
	
	public void updateLocalUser() {
		User localUser = data.getLocalUser();
		
		lblUsername.setText(localUser.getUsername());
		lblAddress.setText(localUser.getAddress() + ":" + localUser.getPort());
	}

	public void addUser(User user) {
		UserPanel userPan = new UserPanel(instance, network, data, user);
		
		users.add(userPan);
		panUsers.add(userPan, 0);
	}
	
	public void updateUser(User user) {
		for (UserPanel userPan : users) {
			if (userPan.getUser().equals(user)) {
				userPan.update();
				return;
			}
		}
	}
	
	public void addChat(Chat chat) {
		ChatPanel chatPan = new ChatPanel(instance, network, data, chat);
		
		chats.add(chatPan);
		panChats.add(chatPan, 0);
	}
	
	private void selectDataFile() {
		final JFileChooser fc = new JFileChooser();
		
		fc.setDialogTitle("select data file");
		fc.setFileFilter(new FileNameExtensionFilter("dolphin files", "dolphin"));
		
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION)
        	dataFile = fc.getSelectedFile();
	}
}
