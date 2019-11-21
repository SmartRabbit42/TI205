package visual.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import data.Data;
import data.containers.User;
import visual.popups.UserPopup;
import network.Network;
import visual.Client;

public class UserPanel extends JPanel {

	private static final long serialVersionUID = -5194642801471406001L;

	private Client client;
	private Network network;
	private Data data;
	
	private User user;
	
	private JLabel lblName;
	private JLabel lblAddress;
	private JPanel panStatus;
	
	public UserPanel(Client client, Network network, Data data, User user) {
		this.client = client;
		this.network = network;
		this.data = data;
		
		this.user = user;
		
		initializeComponents();
	}

	private void initializeComponents() {
		setLayout(null);
		
		setSize(new Dimension(250, 30));
		
		setAlignmentX(Component.CENTER_ALIGNMENT);
		
		setComponentPopupMenu(new UserPopup(client, network, data, user));
		
		lblName = new JLabel();
		lblName.setAlignmentX(LEFT_ALIGNMENT);
		lblName.setBounds(0, 0, 250, 15);
		
		lblAddress = new JLabel();
		lblAddress.setAlignmentX(LEFT_ALIGNMENT);
		lblAddress.setBounds(0, 15, 250, 15);
		
		panStatus = new JPanel();
		panStatus.setBounds(220, 5, 20, 20);
		panStatus.setBackground(new Color(0,0,0));
		
		add(lblName);
		add(lblAddress);
		add(panStatus);
		
		update();
	}
	
	public void update() {
		lblName.setText(user.getUsername());
		lblAddress.setText(user.getFullAddress());
		switch (user.getStatus()) {
			default:
			case User.Status.unknown:
				panStatus.setBackground(Color.white);
				break;
			case User.Status.offline:
				panStatus.setBackground(Color.red);
				break;
			case User.Status.online:
				panStatus.setBackground(Color.green);
				break;
			case User.Status.busy:
				panStatus.setBackground(Color.orange);
				break;
			case User.Status.typing:
				panStatus.setBackground(Color.yellow);
		}
	}
	
	public User getUser() {
		return user;
	}
}