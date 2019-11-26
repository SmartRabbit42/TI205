package visual.popups;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JPopupMenu;

import data.Data;
import data.containers.Message;
import network.Network;
import visual.Client;
import visual.VisualConstants;
import visual.components.DMenuItem;

//TODO

public class MessagePopup extends JPopupMenu {

	private static final long serialVersionUID = 3700438134286442845L;

	private Client client;
	private Network network;
	private Data data;
	
	private Message message;
	
	public MessagePopup(Client client, Network network, Data data, Message message) {
		this.client = client;
		this.network = network;
		this.data = data;
		
		this.message = message;
		
		initializeComponent();
	 }
	
	private void initializeComponent() {
		setBackground(VisualConstants.BACK_COLOR);
		
		DMenuItem info = new DMenuItem("info");
		info.addActionListener(e -> infoClick());
		
		DMenuItem changeName = new DMenuItem("change name");
		changeName.addActionListener(e -> changeNameClick());
		
		DMenuItem silence = new DMenuItem("unsilence");
	    silence.addActionListener(e -> silenceClick());
	    
	    DMenuItem leave = new DMenuItem("leave");
	    leave.addActionListener(e -> leaveClick());

	    Dimension separatorDimension = new Dimension(0, 3);
	    
	    add(info);
	    add(Box.createRigidArea(separatorDimension));
	    add(changeName);
	    add(Box.createRigidArea(separatorDimension));
	    add(silence);
	    add(Box.createRigidArea(separatorDimension));
	    add(leave);
	}
	
	private void infoClick() {
		
	}
	
	private void changeNameClick() {
		
	}
	
	private void silenceClick() {
		
	}
	
	private void leaveClick() {
	}
}
