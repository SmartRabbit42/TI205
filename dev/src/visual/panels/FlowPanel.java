package visual.panels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.Data;
import data.containers.Chat;
import data.containers.Message;
import general.Colors;
import network.Network;
import visual.Client;

public class FlowPanel extends JPanel {

	private static final long serialVersionUID = -8959645122177154844L;

	private Client client;
	private Network network;
	private Data data;
	
	private Chat chat;
	
	private List<MessagePanel> messages;
	
	private JLabel lblName;
	private JPanel panMessages;
	
	public FlowPanel(Client client, Network network, Data data, Chat chat) {
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
		
		JPanel panHeader = new JPanel();
		panHeader.setBounds(0, 0, 550, 50);
		panHeader.setBackground(Colors.header);
		
		lblName = new JLabel();
		lblName.setForeground(Colors.body);
		
		panHeader.add(lblName);
		
		panMessages = new JPanel();
		panMessages.setLayout(new BoxLayout(panMessages, BoxLayout.Y_AXIS));
		panMessages.setBounds(0, 50, 550, 500);
		panMessages.setBackground(Colors.body);
		
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
