package visual.popups;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import data.Data;
import data.containers.Message;
import general.Colors;
import network.Network;
import visual.Client;

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
		JMenuItem info = new JMenuItem("info");
		info.setBackground(Colors.buttonBackground);
		info.setForeground(Colors.buttonForeground);
		info.addActionListener(e -> infoClick());
		
		JMenuItem changeName = new JMenuItem("change name");
		changeName.setBackground(Colors.buttonBackground);
		changeName.setForeground(Colors.buttonForeground);
		changeName.addActionListener(e -> changeNameClick());
		
	    JMenuItem silence = new JMenuItem("unsilence");
	    silence.setBackground(Colors.buttonBackground);
	    silence.setForeground(Colors.buttonForeground);
	    silence.addActionListener(e -> silenceClick());
	    
	    JMenuItem leave = new JMenuItem("leave");
	    leave.setBackground(Colors.buttonBackground);
	    leave.setForeground(Colors.buttonForeground);
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
