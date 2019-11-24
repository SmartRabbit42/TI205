package visual.dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.*;

import data.Data;
import data.containers.User;
import general.Helper;
import general.exceptions.InvalidParameterException;
import general.exceptions.MessageNotSentException;
import network.Network;
import network.netMsg.messages.AddMsg;
import visual.Client;

public class AddUserDialog extends JDialog {

	private static final long serialVersionUID = 8514722385911569350L;

	private Client client;
	private Data data;
	private Network network;
	
	private JTextField txtUserAddress;
	
	public AddUserDialog(Client client, Network network, Data data) {
		super(client, Dialog.ModalityType.DOCUMENT_MODAL);
		
		this.client = client;
		this.network = network;
		this.data = data;
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		JPanel panUpper = new JPanel();
		panUpper.setLayout(new BoxLayout(panUpper, BoxLayout.Y_AXIS));
		
		JLabel lblTitle = new JLabel("Add User");
		
		JLabel lblUserAddress = new JLabel("user address:");
		
		txtUserAddress = new JTextField();
		txtUserAddress.setMaximumSize(new Dimension(500, 50));
		
		panUpper.add(lblTitle);
		panUpper.add(Box.createRigidArea(new Dimension(0,5)));
		panUpper.add(lblUserAddress);
		panUpper.add(txtUserAddress);
		panUpper.add(Box.createRigidArea(new Dimension(0,5)));
		
		JPanel panButtons = new JPanel();
		panButtons.setLayout(new BoxLayout(panButtons, BoxLayout.X_AXIS));
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(e -> setVisible(false));
		
		JButton btnAdd = new JButton("add");
		btnAdd.addActionListener(e -> btnAddClick());
		
		panButtons.add(Box.createHorizontalGlue());
		panButtons.add(btnCancel);
		panButtons.add(Box.createRigidArea(new Dimension(10, 0)));
		panButtons.add(btnAdd);
		
		Container contentPane = getContentPane();
		contentPane.add(panUpper, BorderLayout.CENTER);
		contentPane.add(panButtons, BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(this.getParent());
	}
	
	private void btnAddClick() {
		try {
			String fullAddress = txtUserAddress.getText();
			
			if (!fullAddress.matches(Helper.addressRegex))
				throw new InvalidParameterException("invalid address");

			if (fullAddress.equals(data.getLocalUser().getFullAddress()))
				throw new InvalidParameterException("trying to add local user");
			
			for (User user : data.getUsers())
				if (fullAddress.equals(user.getFullAddress()))
					throw new InvalidParameterException("user already added");
			
			String[] aux = fullAddress.split(":");
			String address = aux[0];
			int port = Integer.parseInt(aux[1]);
			
			String token = Helper.generateToken();
			
			User newUser = new User();
			newUser.setId(token);
			newUser.setToken(token);
			newUser.setStatus(User.Status.loading);
			newUser.setAddress(address);
			newUser.setPort(port);
			
			data.getUsers().add(newUser);
			
			AddMsg aumsg = new AddMsg();
			aumsg.setAddress(data.getLocalUser().getAddress());
			aumsg.setPort(data.getLocalUser().getPort());
			aumsg.setStatus(data.getLocalUser().getStatus());
			aumsg.setUsername(data.getLocalUser().getUsername());
			
			network.sendMessage(newUser, aumsg);

			client.addUser(newUser);
			
			setVisible(false);
		} catch (MessageNotSentException e) {
			 JOptionPane.showMessageDialog(client,
				        "couldn't send addMsg",
				        "error",
				        JOptionPane.INFORMATION_MESSAGE);
		} catch (InvalidParameterException e) {
			JOptionPane.showMessageDialog(client,
			        e.getMessage(),
			        "error",
			        JOptionPane.INFORMATION_MESSAGE);
		}
	}
}