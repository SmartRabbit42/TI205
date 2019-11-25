package visual.panels;

import java.awt.Component;
import java.awt.Dimension;

import data.Data;
import data.containers.Message;
import network.Network;
import visual.Client;
import visual.VisualConstants;
import visual.components.DLabel;
import visual.components.DPanel;
import visual.popups.MessagePopup;

public class MessagePanel extends DPanel {

	private static final long serialVersionUID = -5194642801471406001L;

	private Client client;
	private Network network;
	private Data data;
	
	private Message message;
	
	private DLabel lblSender;
	private DLabel lblContent;
	
	public MessagePanel(Client client, Network network, Data data, Message message) {
		super(VisualConstants.gamaPanelColor);
		
		this.client = client;
		this.network = network;
		this.data = data;
		
		this.message = message;
		
		initializeComponents();
		
		update();
	}

	private void initializeComponents() {
		setLayout(null);
		
		Dimension messageDimension = new Dimension(550, 33);
		
		setMinimumSize(messageDimension);
		setPreferredSize(messageDimension);
		setMaximumSize(messageDimension);
		
		setAlignmentX(Component.CENTER_ALIGNMENT);
		
		setComponentPopupMenu(new MessagePopup(client, network, data, message));
		
		lblSender = new DLabel();
		lblSender.setAlignmentX(LEFT_ALIGNMENT);
		lblSender.setForeground(VisualConstants.alphaForeColor);
		lblSender.setBounds(0, 0, 550, 15);
		
		lblContent = new DLabel();
		lblContent.setAlignmentX(LEFT_ALIGNMENT);
		lblContent.setForeground(VisualConstants.betaForeColor);
		lblContent.setBounds(0, 15, 550, 15);
		
		DPanel panSeparator = new DPanel();
		panSeparator.setBounds(0, 30, 550, 3);
		
		add(lblSender);
		add(lblContent);
		add(panSeparator);
	}
	
	public void update() {
		lblSender.setText(message.getSender().getUsername());
		lblContent.setText(message.getContent());
	}
	
	public Message getMessage() {
		return message;
	}
}