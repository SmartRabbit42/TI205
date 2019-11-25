package visual.panels;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import data.Data;
import data.containers.User;
import general.Colors;
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
		
		update();
	}

	private void initializeComponents() {
		setLayout(null);
		setBackground(Colors.prefab);
		
		Dimension userDimension = new Dimension(250, 33);
		
		setPreferredSize(userDimension);
		setMaximumSize(userDimension);
		setMinimumSize(userDimension);
		
		setAlignmentX(Component.CENTER_ALIGNMENT);
		
		setComponentPopupMenu(new UserPopup(client, network, data, user));
		
		lblName = new JLabel();
		lblName.setAlignmentX(LEFT_ALIGNMENT);
		lblName.setForeground(Colors.foreground1);
		lblName.setBounds(0, 0, 250, 15);
		
		lblAddress = new JLabel();
		lblAddress.setAlignmentX(LEFT_ALIGNMENT);
		lblAddress.setForeground(Colors.foreground1);
		lblAddress.setBounds(0, 15, 250, 15);
		
		panStatus = new JPanel();
		panStatus.setBounds(220, 5, 20, 20);
		
		JPanel panSeparator = new JPanel();
		panSeparator.setBounds(0, 30, 250, 3);
		panSeparator.setBackground(Colors.body);
		
		add(lblName);
		add(lblAddress);
		add(panStatus);
		add(panSeparator);
	}
	
	public void update() {
		lblName.setText(user.getUsername());
		lblAddress.setText(user.getFullAddress());
		switch (user.getStatus()) {
			default:
			case User.Status.unknown:
				panStatus.setBackground(Colors.statusUnknown);
				break;
			case User.Status.loading:
				panStatus.setBackground(Colors.statusLoading);
				break;
			case User.Status.offline:
				panStatus.setBackground(Colors.statusOffline);
				break;
			case User.Status.online:
				panStatus.setBackground(Colors.statusOnline);
				break;
			case User.Status.busy:
				panStatus.setBackground(Colors.statusBusy);
				break;
			case User.Status.black:
				panStatus.setBackground(Colors.statusBlack);
				break;
		}
	}
	
	public User getUser() {
		return user;
	}
}