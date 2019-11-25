package visual.popups;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import data.Data;
import data.containers.Message;
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
		info.addActionListener(e -> infoClick());
		
		JMenuItem changeName = new JMenuItem("change name");
		changeName.addActionListener(e -> changeNameClick());
		
	    JMenuItem silence = new JMenuItem("unsilence");
	    silence.addActionListener(e -> silenceClick());
	    
	    JMenuItem leave = new JMenuItem("leave");
	    leave.addActionListener(e -> leaveClick());

	    add(info);
	    add(changeName);
	    add(silence);
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
