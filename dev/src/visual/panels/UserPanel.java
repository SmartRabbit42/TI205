package visual.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

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
		
		initializeComponents();
	}

	private void initializeComponents() {
		setLayout(null);
		
		setSize(new Dimension(250, 30));
		
		setAlignmentX(Component.CENTER_ALIGNMENT);
		
		lblName = new JLabel();
		lblName.setAlignmentX(LEFT_ALIGNMENT);
		lblName.setBounds(0, 0, 250, 15);
		
		lblAddress = new JLabel();
		lblAddress.setAlignmentX(LEFT_ALIGNMENT);
		lblAddress.setBounds(0, 15, 250, 15);
		
		panStatus = new JPanel();
		panStatus.setBounds(220, 5, 20, 20);
		panStatus.setBackground(new Color(0,0,0));
		
		add(lblName);
		add(lblAddress);
		add(panStatus);
		
		update();
	}
	
	public void update() {
		lblName.setText(user.getUsername());
		lblAddress.setText(user.getAddress() + ":" + user.getPort());
		switch (user.getStatus()) {
			default:
			case User.Status.unknown:
				panStatus.setBackground(Color.white);
				break;
			case User.Status.offline:
				panStatus.setBackground(Color.red);
				break;
			case User.Status.online:
				panStatus.setBackground(Color.green);
				break;
			case User.Status.busy:
				panStatus.setBackground(Color.orange);
				break;
			case User.Status.occupied:
				panStatus.setBackground(Color.yellow);
		}
	}
	
	public User getUser() {
		return user;
	}
}