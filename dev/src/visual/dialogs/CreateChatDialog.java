package visual.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

import visual.panels.*;
import data.Data;
import data.containers.Chat;
import data.containers.User;
import general.exceptions.InvalidParameterException;
import visual.Client;

public class CreateChatDialog extends JDialog {
	
	private static final long serialVersionUID = 8544625027506291911L;

	protected static final String CreateChatAddUserPanel = null;

	private Client client;
	private Data data;

	private JPanel panUsers;
	private ArrayList<CreateChatAddUserPanel> createChatAddUserPanels;
	
	public CreateChatDialog(Client parent, Data data) {
		super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
		
		this.client = parent;
		this.data = data;
		this.createChatAddUserPanels = new ArrayList<CreateChatAddUserPanel>();
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		JPanel panList = new JPanel();
		panList.setLayout(new BoxLayout(panList, BoxLayout.Y_AXIS));
		
		JLabel lblTitle = new JLabel("Create Chat");
		
		JLabel lblName = new JLabel("chat name:");
		
		JTextField txtName = new JTextField();
		txtName.setMaximumSize(new Dimension(500, 50));

		panUsers = new JPanel();
		panUsers.setLayout(new BoxLayout(panUsers, BoxLayout.Y_AXIS));
		
		JButton btnAddUser = new JButton("+");
		btnAddUser.setSize(new Dimension(30, 30));
		btnAddUser.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panUsers.add(btnAddUser);
		
		JScrollPane jspUsers = new JScrollPane(panUsers);
		jspUsers.setAlignmentX(LEFT_ALIGNMENT);
		jspUsers.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspUsers.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		panList.add(lblTitle);
		panList.add(Box.createRigidArea(new Dimension(0,5)));
		panList.add(lblName);
		panList.add(txtName);
		panList.add(Box.createRigidArea(new Dimension(0,5)));
		panList.add(jspUsers);
		panList.add(Box.createRigidArea(new Dimension(0,5)));

		JPanel panButtons = new JPanel();
		panButtons.setLayout(new BoxLayout(panButtons, BoxLayout.X_AXIS));
		
		JButton btnCancel = new JButton("cancel");
		
		JButton btnCreate = new JButton("create");
		
		panButtons.add(Box.createHorizontalGlue());
		panButtons.add(btnCancel);
		panButtons.add(Box.createRigidArea(new Dimension(10, 0)));
		panButtons.add(btnCreate);

		Container contentPane = getContentPane();
		contentPane.add(panList, BorderLayout.CENTER);
		contentPane.add(panButtons, BorderLayout.PAGE_END);
		
		adjust();
		
		// Events
		btnAddUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				CreateChatAddUserPanel createChatAddUserPanel = new CreateChatAddUserPanel(data.getUsers());
				panUsers.add(createChatAddUserPanel, createChatAddUserPanels.size());
				createChatAddUserPanels.add(createChatAddUserPanel);
				adjust();
			}
		});
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				setVisible(false);
			}
		});
		btnCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					Chat newChat = new Chat(txtName.getText());
					newChat.setMembers(findUsers());
					client.addChat(newChat);
					setVisible(false);
				} catch (InvalidParameterException e) { 
					MessageDialog msg = new MessageDialog(client, "Invalid chat name");
					msg.setVisible(true);
				}
			}
		});
	}
	
	private ArrayList<User> findUsers(){
		ArrayList<User> users = new ArrayList<User>();
		
		for (CreateChatAddUserPanel ccaup : createChatAddUserPanels) {
			try {
				User user = new User(ccaup.getSelectedUsername());
				for (User u : data.getUsers())
					if (u.equals(user))
						if (!users.contains(u))
							users.add(u);
			} catch (InvalidParameterException e) { }
		}
		
		return users;
	}
	
	private void adjust() {
		pack();
		setLocationRelativeTo(client);
	}
}