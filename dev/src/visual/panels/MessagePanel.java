package visual.panels;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import data.containers.Message;

public class MessagePanel extends JPanel {

	private static final long serialVersionUID = -5194642801471406001L;

	
	private Message message;
	
	private JLabel lblSender;
	private JLabel lblContent;
	
	public MessagePanel(Message message) {
	
		this.message = message;
		
		initializeComponents();
	}

	private void initializeComponents() {
		setLayout(null);
		
		setSize(new Dimension(550, 30));
		
		setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//setComponentPopupMenu(new UserPopup(client, network, data, user));
		
		lblSender = new JLabel();
		lblSender.setAlignmentX(LEFT_ALIGNMENT);
		lblSender.setBounds(0, 0, 550, 15);
		
		lblContent = new JLabel();
		lblContent.setAlignmentX(LEFT_ALIGNMENT);
		lblContent.setBounds(0, 15, 550, 15);
		
		add(lblSender);
		add(lblContent);
		
		update();
	}
	
	public void update() {
		lblSender.setText(message.getSender().getUsername());
		lblContent.setText(message.getContent());
	}
	
	public Message getMessage() {
		return message;
	}
}