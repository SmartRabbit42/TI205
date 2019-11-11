package visual.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import data.containers.User;

public class CreateChatAddUserPanel extends JPanel {

	private static final long serialVersionUID = -4976528431574221806L;

	private ArrayList<User> users;
	private String[] names;
	private int width;
	
	public CreateChatAddUserPanel(ArrayList<User> users, int width) {
		this.users = users;
		this.width = width;
		this.names = new String[users.size()];
		
		for (int i = 0; i < users.size(); i++) {
			this.names[i] = users.get(i).getUsername();
		}
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setSize(this.width, 30);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JComboBox<String> cbxUsers = new JComboBox<String>(this.names);
		
		add(Box.createRigidArea(new Dimension(5, 0)));
		add(cbxUsers);
		add(Box.createHorizontalGlue());
		
	}
}
