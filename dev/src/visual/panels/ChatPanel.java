package visual.panels;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import data.Data;
import data.containers.Chat;
import visual.popups.ChatPopup;
import network.Network;
import visual.Client;

public class ChatPanel extends JPanel {

	private static final long serialVersionUID = -5194642801471406001L;

	private Client client;
	private Network network;
	private Data data;
	
	private Chat chat;
	
	private JLabel lblName;
	
	public ChatPanel(Client client, Network network, Data data, Chat chat) {
		this.client = client;
		this.network = network;
		this.data = data;
		
		this.chat = chat;
		
		initializeComponents();
	}

	private void initializeComponents() {
		setLayout(null);
		
		setSize(new Dimension(250, 30));
		
		setAlignmentX(Component.CENTER_ALIGNMENT);
		
		setComponentPopupMenu(new ChatPopup(client, network, data, null));
		
		lblName = new JLabel();
		lblName.setAlignmentX(LEFT_ALIGNMENT);
		lblName.setBounds(0, 0, 250, 15);
		
		add(lblName);
		
		update();
	}
	
	public void update() {
		lblName.setText(chat.getName());
	}
	
	public Chat getChat() {
		return chat;
	}
}