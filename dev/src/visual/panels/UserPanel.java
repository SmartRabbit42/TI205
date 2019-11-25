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
		super(VisualConstants.epsilonPanelColor);
		
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
		lblName.setForeground(VisualConstants.alphaForeColor);
		lblName.setBounds(0, 0, 250, 15);
		
		lblAddress = new DLabel();
		lblAddress.setAlignmentX(LEFT_ALIGNMENT);
		lblAddress.setForeground(VisualConstants.betaForeColor);
		lblAddress.setBounds(0, 15, 250, 15);
		
		panStatus = new DPanel();
		panStatus.setBounds(220, 5, 20, 20);
		
		DPanel panSeparator = new DPanel();
		panSeparator.setBackground(VisualConstants.deltaPanelColor);
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
			case User.Status.unknown:
				panStatus.setBackground(VisualConstants.statusUnknownColor);
				break;
			case User.Status.loading:
				panStatus.setBackground(VisualConstants.statusLoadingColor);
				break;
			case User.Status.offline:
				panStatus.setBackground(VisualConstants.statusOfflineColor);
				break;
			case User.Status.online:
				panStatus.setBackground(VisualConstants.statusOnlineColor);
				break;
			case User.Status.busy:
				panStatus.setBackground(VisualConstants.statusBusyColor);
				break;
			case User.Status.black:
				panStatus.setBackground(VisualConstants.statusBlackColor);
				break;
		}
	}
	
	public User getUser() {
		return user;
	}
}