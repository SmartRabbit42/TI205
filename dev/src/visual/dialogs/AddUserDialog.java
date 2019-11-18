package visual.dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;

import data.Data;
import data.containers.User;
import general.Helper;
import general.exceptions.InvalidParameterException;
import network.Network;
import network.netMsg.standart.AddUserMsg;
import visual.Client;

public class AddUserDialog extends JDialog {

	private static final long serialVersionUID = 8514722385911569350L;

	private Client client;
	private Data data;
	private Network network;
	
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
		
		JTextField txtUserAddress = new JTextField();
		txtUserAddress.setMaximumSize(new Dimension(500, 50));
		
		panUpper.add(lblTitle);
		panUpper.add(Box.createRigidArea(new Dimension(0,5)));
		panUpper.add(lblUserAddress);
		panUpper.add(txtUserAddress);
		panUpper.add(Box.createRigidArea(new Dimension(0,5)));
		
		JPanel panButtons = new JPanel();
		panButtons.setLayout(new BoxLayout(panButtons, BoxLayout.X_AXIS));
		
		JButton btnCancel = new JButton("cancel");
		
		JButton btnAdd = new JButton("add");
		
		panButtons.add(Box.createHorizontalGlue());
		panButtons.add(btnCancel);
		panButtons.add(Box.createRigidArea(new Dimension(10, 0)));
		panButtons.add(btnAdd);
		
		Container contentPane = getContentPane();
		contentPane.add(panUpper, BorderLayout.CENTER);
		contentPane.add(panButtons, BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(this.getParent());
		
		// Events
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				client.setEnabled(true);
				setVisible(false);
			}
		});
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					String fullAddress = txtUserAddress.getText();
					
					if (!fullAddress.matches(Helper.addressRegex))
						throw new InvalidParameterException();

					String[] aux = fullAddress.split(":");
					String address = aux[0];
					int port = Integer.parseInt(aux[1]);
					
					String token = Helper.generateNewToken();
					
					User newUser = new User();
					newUser.setId(token);
					newUser.setToken(token);
					newUser.setAddress(address);
					newUser.setPort(port);
					
					data.getUsers().add(newUser);
					
					AddUserMsg aumsg = new AddUserMsg();
					aumsg.setStatus(data.getLocalUser().getStatus());
					aumsg.setAddress(data.getLocalUser().getAddress());
					aumsg.setPort(data.getLocalUser().getPort());
					
					network.sendMessage(address, port, token, aumsg);

					setVisible(false);
				} catch (IOException e) {
					MessageDialog msg = new MessageDialog(client, "couldn't send addUserMsg");
					msg.setVisible(true);
				} catch (InvalidParameterException e) {
					MessageDialog msg = new MessageDialog(client, "invalid address");
					msg.setVisible(true);
				}
			}
		});
	}
}