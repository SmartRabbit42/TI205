package visual.panels;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import data.Data;
import data.containers.Chat;
import visual.popups.ChatPopup;
import network.Network;
import visual.Client;
import visual.VisualConstants;
import visual.components.DLabel;

public class ChatPanel extends JButton {

	private static final long serialVersionUID = -5194642801471406001L;

	private Client client;
	private Network network;
	private Data data;
	
	private Chat chat;
	
	private DLabel lblName;
	
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
		setBackground(VisualConstants.epsilonPanelColor);
		
		Dimension chatDimension = new Dimension(250, 33);
		
		setPreferredSize(chatDimension);
		setMaximumSize(chatDimension);
		setMinimumSize(chatDimension);
		
		setAlignmentX(Component.CENTER_ALIGNMENT);
		
		setComponentPopupMenu(new ChatPopup(client, network, data, chat));
		
		lblName = new DLabel();
		lblName.setAlignmentX(LEFT_ALIGNMENT);
		lblName.setBounds(0, 0, 250, 15);
		
		JPanel panSeparator = new JPanel();
		panSeparator.setBackground(VisualConstants.deltaPanelColor);
		panSeparator.setBounds(0, 30, 250, 3);
		
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