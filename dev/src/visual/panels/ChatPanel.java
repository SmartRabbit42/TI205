package visual.panels;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.Data;
import data.containers.Chat;
import general.Colors;
import visual.popups.ChatPopup;
import network.Network;
import visual.Client;

public class ChatPanel extends JButton {

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
		
		update();
	}

	private void initializeComponents() {
		setLayout(null);
		setBackground(Colors.prefab);
		
		Dimension chatDimension = new Dimension(250, 33);
		
		setPreferredSize(chatDimension);
		setMaximumSize(chatDimension);
		setMinimumSize(chatDimension);
		
		setAlignmentX(Component.CENTER_ALIGNMENT);
		
		setComponentPopupMenu(new ChatPopup(client, network, data, chat));
		
		lblName = new JLabel();
		lblName.setAlignmentX(LEFT_ALIGNMENT);
		lblName.setForeground(Colors.foreground1);
		lblName.setBounds(0, 0, 250, 15);
		
		JPanel panSeparator = new JPanel();
		panSeparator.setBounds(0, 30, 250, 3);
		panSeparator.setBackground(Colors.body);
		
		add(lblName);
		add(panSeparator);		
	}
	
	public void update() {
		lblName.setText(chat.getName());
	}
	
	public Chat getChat() {
		return chat;
	}
}