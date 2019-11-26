package visual;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import data.Data;
import data.containers.Message;
import data.containers.User;
import data.containers.Chat;
import general.exceptions.DataNotLoadedException;
import general.exceptions.DataNotSavedException;
import general.exceptions.InvalidParameterException;
import general.exceptions.NetworkUnableToShutException;
import general.exceptions.NetworkUnableToStartException;
import network.Network;
import network.netMsg.messages.DisconnectMsg;
import network.netMsg.messages.MessageMsg;
import visual.components.DButton;
import visual.components.DLabel;
import visual.components.DPanel;
import visual.components.DScrollPane;
import visual.components.DTextField;
import visual.dialogs.*;
import visual.panels.*;

/*
 * TODO:
 *   message Popup
 *   criptograph
 *   reorganize visual
 *   config options
 */

public class Client extends JFrame {
	
	private static final long serialVersionUID = -4444444444444444444L;

	private Client instance;
	
	private Data data;
	private Network network;
	
	private File dataFile;
	
	private DPanel panChats;
	private DPanel panUsers;
	private DPanel panFlows;
	
	private Chat activeChat;
	
	private DLabel lblUsername;
	private DLabel lblAddress;
	private DButton btnStatus;
	
	private ArrayList<UserPanel> users;
	private ArrayList<ChatPanel> chats;
	private ArrayList<FlowPanel> flows;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout());
		
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	try {
            		if (network.running) {
            			int dialogResult = JOptionPane.showConfirmDialog (null,
            					"save activities?",
            					"confirmation",
            					JOptionPane.YES_NO_OPTION);
            			if(dialogResult == JOptionPane.YES_OPTION) {
            				if (dataFile == null)
            					selectDataFile();
            				
            				data.dump(dataFile);
            			}
 
            			DisconnectMsg dmsg = new DisconnectMsg();
            			network.spreadMessage(data.getAddedUsers(), dmsg, false);
            			network.spreadMessage(data.getKnownUsers(), dmsg, false);
            			
            			network.shut();
            		}
				} catch (NetworkUnableToShutException e1) {
					// TODO
					e1.printStackTrace();
				} catch (DataNotSavedException e1) {
					// TODO
					e1.printStackTrace();
				}
            	System.exit(0);
            }
        });
	}

	private void initializeEntry() {
		DPanel panEntry = new DPanel(VisualConstants.ALPHA_PANEL_COLOR);
		panEntry.setLayout(new BorderLayout(0, 0));
		
		Box entryBox = new Box(BoxLayout.Y_AXIS);
		
		DPanel panAuthentication = new DPanel(VisualConstants.BETA_PANEL_COLOR);
		panAuthentication.setPreferredSize(new Dimension(500, 600));
		panAuthentication.setMaximumSize(new Dimension(500, 600));
		panAuthentication.setMinimumSize(new Dimension(500, 600));
		panAuthentication.setLayout(null);
		
		DLabel lblDolphin = new DLabel("Dolphin", new Font("Arial", Font.BOLD, 40));
		lblDolphin.setHorizontalAlignment(SwingConstants.CENTER);
		lblDolphin.setBounds(0, 15, 500, 70);
		
		DLabel lblUsername = new DLabel("username", new Font("Arial", Font.BOLD, 20));
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(0, 120, 500, 30);
		
		DTextField txtUsername = new DTextField();
		txtUsername.setBounds(100, 160, 300, 30);
		
		DLabel lblDataFile = new DLabel("data file");
		lblDataFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataFile.setBounds(0, 220, 500, 30);
		
		DButton btnDataFile = new DButton("select data file");
		btnDataFile.setFont(new Font("Arial", Font.BOLD, 15));
		btnDataFile.setBounds(150, 260, 200, 30);

		DLabel lblSelectedFile = new DLabel("none");
		lblSelectedFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectedFile.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSelectedFile.setBounds(150, 300, 200, 20);
		
		DButton btnAuthentication = new DButton("authenticate");
		btnAuthentication.setFont(new Font("Arial", Font.BOLD, 40));
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
					for (Chat chat : data.getChats()) {
						addChat(chat);
						for (Message message : chat.getMessages())
							addMessage(message, chat);
					}
					for (User user : data.getAddedUsers())
						addUser(user);
					
					setMinimumSize(new Dimension(816, 638));
					((CardLayout) getContentPane().getLayout()).show(getContentPane(), "master");
				} catch (InvalidParameterException e) {
					// TODO
					e.printStackTrace();
				} catch (NetworkUnableToStartException e) {
					// TODO
					e.printStackTrace();
				} catch (DataNotLoadedException e) {
					// TODO
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initializeMaster() {
		DPanel panMaster = new DPanel(VisualConstants.ALPHA_PANEL_COLOR);
		panMaster.setLayout(new BorderLayout(0, 0));
		
		Box box = new Box(BoxLayout.Y_AXIS);
		
		DPanel panMain = new DPanel(VisualConstants.BETA_PANEL_COLOR);
		panMain.setPreferredSize(new Dimension(800, 600));
		panMain.setMaximumSize(new Dimension(800, 600));
		panMain.setMinimumSize(new Dimension(800, 600));
		panMain.setLayout(null);
		
		DPanel panMessages = new DPanel(VisualConstants.BETA_PANEL_COLOR);
		panMessages.setBounds(250, 0, 550, 600);
		panMessages.setLayout(null);
		
		flows = new ArrayList<FlowPanel>();
		
		panFlows = new DPanel(VisualConstants.BETA_PANEL_COLOR);
		panFlows.setBounds(0, 0, 550, 550);
		panFlows.setLayout(new CardLayout());
		
		DPanel panSendMessage = new DPanel(VisualConstants.DELTA_PANEL_COLOR);
		panSendMessage.setBounds(0, 550, 550, 50);
		panSendMessage.setLayout(null);
		
		DTextField txtMessage = new DTextField("write message");
		txtMessage.setBounds(10, 10, 490, 30);
		
		DButton btnMessage = new DButton();
		btnMessage.setBounds(510, 10, 30, 30);
		
		panSendMessage.add(txtMessage);
		panSendMessage.add(btnMessage);
		
		panMessages.add(panFlows);
		panMessages.add(panSendMessage);
		
		DPanel panAside = new DPanel(VisualConstants.BETA_PANEL_COLOR);
		panAside.setBounds(0, 0, 250, 600);
		panAside.setLayout(null);
		
		DPanel panHeader = new DPanel(VisualConstants.GAMA_PANEL_COLOR);
		panHeader.setBounds(0, 0, 250, 65);
		panHeader.setLayout(null);
		
		lblUsername = new DLabel();
		lblUsername.setBounds(10, 5, 200, 40);
		lblUsername.setForeground(VisualConstants.ALPHA_FORE_COLOR);
		lblUsername.setFont(new Font("Arial", Font.BOLD, 30));
		lblUsername.setVerticalAlignment(SwingConstants.TOP);
		
		lblAddress = new DLabel();
		lblAddress.setBounds(10, 40, 200, 20);
		lblAddress.setForeground(VisualConstants.BETA_FORE_COLOR);
		lblAddress.setFont(new Font("Arial", Font.ITALIC, 15));
		lblAddress.setVerticalAlignment(SwingConstants.TOP);
		
		DButton btnConfigurations = new DButton("");
		btnConfigurations.setBounds(212, 5, 25, 25);
		
		btnStatus = new DButton("");
		btnStatus.setBounds(212, 35, 25, 25);
		
		panHeader.add(lblUsername);
		panHeader.add(lblAddress);
		panHeader.add(btnConfigurations);
		panHeader.add(btnStatus);
		
		DPanel panBody = new DPanel(VisualConstants.DELTA_PANEL_COLOR);
		panBody.setLayout(null);
		panBody.setBounds(0, 65, 250, 535);
		
		DPanel panButtons = new DPanel(VisualConstants.DELTA_PANEL_COLOR);
		panButtons.setLayout(null);
		panButtons.setBounds(0, 0, 250, 20);
		
		DButton btnChats = new DButton("chats");
		btnChats.setBackground(VisualConstants.SELECTED_COMP_BACK_COLOR);
		btnChats.setBounds(0, 0, 125, 20);
		
		DButton btnUsers = new DButton("users");
		btnUsers.setBounds(125, 0, 125, 20);
		
		panButtons.add(btnChats);
		panButtons.add(btnUsers);
		
		DPanel panTabs = new DPanel(VisualConstants.DELTA_PANEL_COLOR);
		panTabs.setLayout(new CardLayout());
		panTabs.setBounds(0, 20, 250, 515);
		
		chats = new ArrayList<ChatPanel>();
		
		panChats = new DPanel(VisualConstants.DELTA_PANEL_COLOR);
		panChats.setLayout(new BoxLayout(panChats, BoxLayout.Y_AXIS));
		
		DButton btnCreateChat = new DButton("+");
		btnCreateChat.setSize(new Dimension(30, 30));
		btnCreateChat.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panChats.add(btnCreateChat);
		panChats.add(Box.createVerticalGlue());
		
		DScrollPane scpChats = new DScrollPane(panChats);
		
		users = new ArrayList<UserPanel>();
		
		panUsers = new DPanel(VisualConstants.DELTA_PANEL_COLOR);
		panUsers.setLayout(new BoxLayout(panUsers, BoxLayout.Y_AXIS));
		
		DButton btnAddUser = new DButton("+");
		btnAddUser.setSize(new Dimension(30, 30));
		btnAddUser.setAlignmentX(Component.CENTER_ALIGNMENT);

		panUsers.add(btnAddUser);
		panUsers.add(Box.createVerticalGlue());
		
		DScrollPane scpUsers = new DScrollPane(panUsers);
		
		panTabs.add(scpChats, "chats");
		panTabs.add(scpUsers, "users");
		
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
		btnConfigurations.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}
		});
		btnStatus.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				StatusSelectionDialog ssd = new StatusSelectionDialog(instance, data, network);
				ssd.setVisible(true);
			}
		});
		btnChats.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {	
				btnChats.setBackground(VisualConstants.SELECTED_COMP_BACK_COLOR);
				btnUsers.setBackground(VisualConstants.COMP_BACK_COLOR);
				((CardLayout) panTabs.getLayout()).show(panTabs, "chats");
			}
		});
		btnUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {	
				btnChats.setBackground(VisualConstants.COMP_BACK_COLOR);
				btnUsers.setBackground(VisualConstants.SELECTED_COMP_BACK_COLOR);
				((CardLayout) panTabs.getLayout()).show(panTabs, "users");
			}
		});
		btnCreateChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {	
				JDialog ccd = new CreateChatDialog(instance, network, data);
				ccd.setVisible(true);
			}
		});
		btnAddUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {	
				JDialog aud = new AddUserDialog(instance, network, data);
				aud.setVisible(true);
			}
		});
		btnMessage.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (activeChat == null)
					return;
				
				if (txtMessage.isPlaceholderShown())
					return; 
				
				String content = txtMessage.getText();
				
				if (content.isEmpty())
					return;
				
				Date now = new Date();
				
				Message message = new Message();
				message.setContent(content);
				message.setSender(data.getLocalUser());
				message.setTime(now);
				message.setChat(activeChat);
				
				MessageMsg mm = new MessageMsg();
				mm.setChatId(activeChat.getId());
				mm.setTime(now.getTime());
				mm.setContent(content);
				
				txtMessage.setText("");
				
				network.spreadMessage(activeChat.getMembers(), mm, true);
				
				activeChat.getMessages().add(message);
				
				addMessage(message, activeChat);
			}
		});
	}

	// Methods
	public void updateLocalUser() {
		User localUser = data.getLocalUser();
		
		lblUsername.setText(localUser.getUsername());
		lblAddress.setText(localUser.getFullAddress());
		
		switch (localUser.getStatus()) {
			default:
			case UNKNOWN:
				btnStatus.setBackground(VisualConstants.STATUS_UNKNOWN_COLOR);
				break;
			case LOADING:
				btnStatus.setBackground(VisualConstants.STATUS_LOADING_COLOR);
				break;
			case OFFLINE:
				btnStatus.setBackground(VisualConstants.STATUS_OFFLINE_COLOR);
				break;
			case ONLINE:
				btnStatus.setBackground(VisualConstants.STATUS_ONLINE_COLOR);
				break;
			case BUSY:
				btnStatus.setBackground(VisualConstants.STATUS_BUSY_COLOR);
				break;
			case BLACK:
				btnStatus.setBackground(VisualConstants.STATUS_BLACK_COLOR);
				break;
		}
	}

	public void addUser(User user) {
		UserPanel userPan = new UserPanel(instance, network, data, user);
		
		users.add(userPan);
		panUsers.add(userPan, 0);
		
		panUsers.revalidate();
		panUsers.repaint();
	}
	
	public void updateUser(User user) {
		for (UserPanel userPan : users)
			if (userPan.getUser().equals(user)) {
				userPan.update();
				break;
			}
		
		panUsers.revalidate();
		panUsers.repaint();
	}
	
	public void removeUser(User user) {
		for (UserPanel userPan : users)
			if (userPan.getUser().equals(user)) {
				panUsers.remove(userPan);
				users.remove(userPan);
				break;
			}
		
		panUsers.revalidate();
		panUsers.repaint();
	}
	
	public void addChat(Chat chat) {
		ChatPanel chatPan = new ChatPanel(instance, network, data, chat);
		FlowPanel flowPan = new FlowPanel(instance, network, data, chat);
		
		chats.add(chatPan);
		flows.add(flowPan);

		panChats.add(chatPan, 0);
		panFlows.add(flowPan, chat.getId());
		
		if(activeChat == null)
			changeActiveChat(chat);
					
		chatPan.addActionListener(e -> changeActiveChat(chat));
		
		panChats.revalidate();
		panChats.repaint();
		panFlows.revalidate();
		panFlows.repaint();
	}
	
	public void updateChat(Chat chat) {
		for (ChatPanel chatPan : chats) 
			if (chatPan.getChat().equals(chat)) {
				chatPan.update();
				break;
			}
		panChats.revalidate();
		panChats.repaint();
		
		for (FlowPanel flowPan : flows)
			if (flowPan.getChat().equals(chat)) {
				flowPan.update();
				break;
			}
		panFlows.revalidate();
		panFlows.repaint();
	}
	
	public void removeChat(Chat chat) {
		for (ChatPanel chatPan : chats) 
			if (chatPan.getChat().equals(chat)) {
				panChats.remove(chatPan);
				chats.remove(chatPan);
				break;
			}
		panChats.revalidate();
		panChats.repaint();
		
		for (FlowPanel flowPan : flows)
			if (flowPan.getChat().equals(chat)) {
				panFlows.remove(flowPan);
				flows.remove(flowPan);
				break;
			}
		panFlows.revalidate();
		panFlows.repaint();
	}
	
	public void addMessage(Message msg, Chat chat) {
		for (FlowPanel flowPan : flows)
			if (flowPan.getChat().equals(chat)) {
				flowPan.addMessage(msg);
				return;
			}
	}
	
	private void changeActiveChat(Chat chat) {
		for (ChatPanel chatPan : chats) 
			if (chatPan.getChat().equals(activeChat)) {
				chatPan.setBackground(VisualConstants.EPSILON_PANEL_COLOR);
				break;
			}
		
		for (ChatPanel chatPan : chats) 
			if (chatPan.getChat().equals(chat)) {
				chatPan.setBackground(VisualConstants.SELECTED_COMP_BACK_COLOR);
				break;
			}
		
		panChats.revalidate();
		panChats.repaint();
		
		activeChat = chat;
		((CardLayout) panFlows.getLayout()).show(panFlows, chat.getId());
		
		panFlows.revalidate();
		panFlows.repaint();
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
