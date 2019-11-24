package visual.panels;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.containers.Chat;
import data.containers.Message;

public class FlowPanel extends JPanel {

	private static final long serialVersionUID = -8959645122177154844L;

	private Chat chat;
	
	private List<MessagePanel> messages;
	
	private JLabel lblName;
	private JPanel panMessages;
	
	public FlowPanel(Chat chat) {
		this.chat = chat;
		
		messages = new ArrayList<MessagePanel>();
		
		initializeComponent();
		
		update();
	}
	
	private void initializeComponent() {
		setLayout(null);
		
		JPanel panHeader = new JPanel();
		panHeader.setBounds(0, 0, 550, 50);
		
		lblName = new JLabel();
		
		panHeader.add(lblName);
		
		panMessages = new JPanel();
		panMessages.setLayout(new BoxLayout(panMessages, BoxLayout.Y_AXIS));
		panMessages.setBounds(0, 50, 550, 500);
		panMessages.setBackground(new Color(15, 75, 114, 255));
		
		add(panHeader);
		add(panMessages);
	}
	
	public void addMessage(Message msg) {
		MessagePanel newMessagePan = new MessagePanel(msg);
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
