package visual.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.containers.User;

public class UserPanel extends JPanel {

	private static final long serialVersionUID = -5194642801471406001L;

	private User user;
	
	private JLabel lblName;
	private JLabel lblAddress;
	private JPanel panStatus;
	
	public UserPanel(User user) {
		this.user = user;
		
		setLayout(null);
		
		setSize(new Dimension(250, 30));
		
		setAlignmentX(Component.CENTER_ALIGNMENT);
		
		lblName = new JLabel(user.getUsername());
		lblName.setAlignmentX(LEFT_ALIGNMENT);
		lblName.setBounds(0, 0, 250, 15);
		
		lblAddress = new JLabel(user.getAddress() + ":" + user.getPort());
		lblAddress.setAlignmentX(LEFT_ALIGNMENT);
		lblAddress.setBounds(0, 15, 250, 15);
		
		panStatus = new JPanel();
		panStatus.setBounds(220, 5, 20, 20);
		panStatus.setBackground(new Color(0,0,0));
		
		add(lblName);
		add(lblAddress);
		add(panStatus);
	}
	
	public void update() {
		lblName.setText(user.getUsername());
		lblAddress.setText(user.getAddress());
		panStatus.setBackground(new Color(user.getStatus(), user.getStatus(), user.getStatus()));
	}
	
	public User getUser() {
		return this.user;
	}
}