package visual.panels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;

import data.Data;
import data.containers.Chat;
import data.containers.Message;
import network.Network;
import visual.Client;
import visual.VisualConstants;
import visual.components.DLabel;
import visual.components.DPanel;

public class FlowPanel extends DPanel {

	private static final long serialVersionUID = -8959645122177154844L;

	private Client client;
	private Network network;
	private Data data;
	
	private Chat chat;
	
	private List<MessagePanel> messages;
	
	private DLabel lblName;
	private DPanel panMessages;
	
	public FlowPanel(Client client, Network network, Data data, Chat chat) {
		super(VisualConstants.betaPanelColor);
		
		this.client = client;
		this.network = network;
		this.data = data;
		
		this.chat = chat;
		
		messages = new ArrayList<MessagePanel>();
		
		initializeComponent();
		
		update();
	}
	
	private void initializeComponent() {
		setLayout(null);
		
		DPanel panHeader = new DPanel();
		panHeader.setBounds(0, 0, 550, 50);
		
		lblName = new DLabel();
		lblName.setForeground(VisualConstants.alphaForeColor);
		
		panHeader.add(lblName);
		
		panMessages = new DPanel(VisualConstants.gamaPanelColor);
		panMessages.setLayout(new BoxLayout(panMessages, BoxLayout.Y_AXIS));
		panMessages.setBounds(0, 50, 550, 500);
		
		add(panHeader);
		add(panMessages);
	}
	
	public void addMessage(Message msg) {
		MessagePanel newMessagePan = new MessagePanel(client, network, data, msg);
		messages.add(newMessagePan);
		
		panMessages.add(newMessagePan);
		
		revalidate();
		repaint();
	}
	
	public void update() {
		lblName.setText(chat.getName());
	}
	
	public Chat getChat() {
		return chat;
	}
}
