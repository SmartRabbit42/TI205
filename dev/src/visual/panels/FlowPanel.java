package visual.panels;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;

import data.containers.Chat;
import data.containers.Message;
import visual.Client;
import visual.VisualConstants;
import visual.components.DLabel;
import visual.components.DPanel;
import visual.components.DScrollPane;

public class FlowPanel extends DPanel {

	private static final long serialVersionUID = -8959645122177154844L;

	private Client client;
	
	private Chat chat;
	
	private List<MessagePanel> messages;
	
	private DLabel lblName;
	private DPanel panMessages;
	
	public FlowPanel(Client client, Chat chat) {
		super(VisualConstants.ALPHA_PANEL_COLOR);
		
		this.client = client;
		
		this.chat = chat;
		
		messages = new ArrayList<MessagePanel>();
		
		initializeComponent();
		
		update();
	}
	
	private void initializeComponent() {
		setLayout(null);
		
		DPanel panHeader = new DPanel(VisualConstants.DELTA_PANEL_COLOR);
		panHeader.setBounds(0, 0, 550, 50);
		
		lblName = new DLabel("", new Font("Arial", Font.BOLD, 24));
		lblName.setForeground(VisualConstants.ALPHA_FORE_COLOR);
		
		panHeader.add(lblName);
		
		panMessages = new DPanel(VisualConstants.BETA_PANEL_COLOR);
		panMessages.setLayout(new BoxLayout(panMessages, BoxLayout.Y_AXIS));
		
		DScrollPane scpMessages = new DScrollPane(panMessages);
		scpMessages.setBounds(0, 50, 550, 500);
		
		add(panHeader);
		add(scpMessages); 
	}
	
	public void addMessage(Message msg) {
		MessagePanel newMessagePan = new MessagePanel(client, msg);
		messages.add(newMessagePan);
		
		panMessages.add(newMessagePan);
		
		revalidate();
		repaint();
	}
	
	public void removeMessage(Message msg) {
		for (int i = 0; i < messages.size(); i++) {
			MessagePanel message = messages.get(i);
			if (message.getMessage().equals(msg)) {
				messages.remove(message);
				panMessages.remove(i);
				break;
			}
		}
		
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
