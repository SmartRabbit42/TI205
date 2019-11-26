package visual.panels;

import java.awt.Component;
import java.awt.Dimension;

import data.Data;
import data.containers.User;
import visual.popups.UserPopup;
import network.Network;
import visual.Client;
import visual.VisualConstants;
import visual.components.DLabel;
import visual.components.DPanel;

public class UserPanel extends DPanel {

	private static final long serialVersionUID = -5194642801471406001L;

	private Client client;
	private Network network;
	private Data data;
	
	private User user;
	
	private DLabel lblName;
	private DLabel lblAddress;
	private DPanel panStatus;
	
	public UserPanel(Client client, Network network, Data data, User user) {
		super(VisualConstants.EPSILON_PANEL_COLOR);
		
		this.client = client;
		this.network = network;
		this.data = data;
		
		this.user = user;
		
		initializeComponents();
		
		update();
	}

	private void initializeComponents() {
		setLayout(null);
		
		Dimension userDimension = new Dimension(250, 33);
		
		setPreferredSize(userDimension);
		setMaximumSize(userDimension);
		setMinimumSize(userDimension);
		
		setAlignmentX(Component.CENTER_ALIGNMENT);
		
		setComponentPopupMenu(new UserPopup(client, network, data, user));
		
		lblName = new DLabel();
		lblName.setAlignmentX(LEFT_ALIGNMENT);
		lblName.setForeground(VisualConstants.ALPHA_FORE_COLOR);
		lblName.setBounds(0, 0, 250, 15);
		
		lblAddress = new DLabel();
		lblAddress.setAlignmentX(LEFT_ALIGNMENT);
		lblAddress.setForeground(VisualConstants.BETA_FORE_COLOR);
		lblAddress.setBounds(0, 15, 250, 15);
		
		panStatus = new DPanel();
		panStatus.setBounds(220, 5, 20, 20);
		
		DPanel panSeparator = new DPanel();
		panSeparator.setBackground(VisualConstants.DELTA_PANEL_COLOR);
		panSeparator.setBounds(0, 30, 250, 3);
		
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
			case UNKNOWN:
				panStatus.setBackground(VisualConstants.STATUS_UNKNOWN_COLOR);
				break;
			case LOADING:
				panStatus.setBackground(VisualConstants.STATUS_LOADING_COLOR);
				break;
			case OFFLINE:
				panStatus.setBackground(VisualConstants.STATUS_OFFLINE_COLOR);
				break;
			case ONLINE:
				panStatus.setBackground(VisualConstants.STATUS_ONLINE_COLOR);
				break;
			case BUSY:
				panStatus.setBackground(VisualConstants.STATUS_BUSY_COLOR);
				break;
			case BLACK:
				panStatus.setBackground(VisualConstants.STATUS_BLACK_COLOR);
				break;
		}
	}
	
	public User getUser() {
		return user;
	}
}