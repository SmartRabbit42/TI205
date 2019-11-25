package visual.popups;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import data.Data;
import data.containers.User;
import visual.dialogs.ChangeAddressDialog;
import visual.dialogs.ChangeUsernameDialog;
import network.Network;
import visual.Client;
import visual.VisualConstants;
import visual.components.DMenuItem;

public class UserPopup extends JPopupMenu {

	private static final long serialVersionUID = 3700438134286442845L;

	private Client client;
	private Network network;
	private Data data;
	
	private User user;
	
	public UserPopup(Client client, Network network, Data data, User user) {
		this.client = client;
		this.network = network;
		this.data = data;
		
		this.user = user;
		
		initializeComponent();
	 }
	
	private void initializeComponent() {
		setBackground(VisualConstants.backColor);
		
		DMenuItem info = new DMenuItem("info");
		info.addActionListener(e -> infoClick());
		
		DMenuItem changeUsername = new DMenuItem("change username");
		changeUsername.addActionListener(e -> changeUsernameClick());
		
		DMenuItem changeAddress = new DMenuItem("change address");
	    changeAddress.addActionListener(e -> changeAddressClick());
	    
	    DMenuItem delete = new DMenuItem("delete");
	    delete.addActionListener(e -> deleteClick());

	    Dimension separatorDimension = new Dimension(0, 3);
	    
	    add(info);
	    add(Box.createRigidArea(separatorDimension));
	    add(changeUsername);
	    add(Box.createRigidArea(separatorDimension));
	    add(changeAddress);
	    add(Box.createRigidArea(separatorDimension));
	    add(delete);
	}
	
	private void infoClick() {
		// TODO
	}
	
	private void changeUsernameClick() {
		JDialog cud = new ChangeUsernameDialog(client, user);
		cud.setVisible(true);
	}
	
	private void changeAddressClick() {
		JDialog cad = new ChangeAddressDialog(client, network, data, user);
		cad.setVisible(true);
	}

	private void deleteClick() {
		int dialogResult = JOptionPane.showConfirmDialog (null,
				String.format("delete %s from your computer?", user.getUsername()),
				"confirmation",
				JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION){
			client.removeUser(user);
			
			data.getAddedUsers().remove(user);
			data.getKnownUsers().add(user);
		}
	}
}
