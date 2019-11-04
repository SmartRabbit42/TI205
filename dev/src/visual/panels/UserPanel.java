package visual.panels;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.containers.User;

public class UserPanel extends JPanel {

	private static final long serialVersionUID = -5194642801471406001L;

	public UserPanel(User user) {
		setSize(250, 15);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel lblName = new JLabel(user.getUsername());
		
		JLabel lblAddress = new JLabel(user.getAddress() + ":" + user.getPort());
		
		add(lblName);
		add(lblAddress);
	}
}
