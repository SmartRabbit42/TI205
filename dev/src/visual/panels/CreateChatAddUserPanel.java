package visual.panels;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import data.containers.User;

public class CreateChatAddUserPanel extends JPanel {

	private static final long serialVersionUID = -4976528431574221806L;

	
	private String[] users;
	private int width;
	
	public CreateChatAddUserPanel(String[] users, int width) {
		this.users = users;
		this.width = width;
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setSize(this.width, 30);
		
		JComboBox cbxUsers = new JComboBox(this.users);
		
		add(Box.createRigidArea(new Dimension(5, 0)));
		add(cbxUsers);
		add(Box.createHorizontalGlue());
		
		
	}
}
