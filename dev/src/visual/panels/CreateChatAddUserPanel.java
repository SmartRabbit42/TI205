package visual.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;

import data.containers.User;
import visual.VisualConstants;
import visual.components.DPanel;

public class CreateChatAddUserPanel extends DPanel {

	private static final long serialVersionUID = -4976528431574221806L;

	private List<String> names;
	
	private List<User> users;
	
	JComboBox<String> cbxUsers;

	public CreateChatAddUserPanel(List<User> users) {
		this.names = new ArrayList<String>();
		
		this.users = users;
		
		for (User user : users)
			names.add(user.getUsername());
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setAlignmentX(Component.CENTER_ALIGNMENT);
		
		cbxUsers = new JComboBox<String>(names.toArray(new String[names.size()]));
		cbxUsers.setBackground(VisualConstants.COMP_BACK_COLOR);
		cbxUsers.setForeground(VisualConstants.COMP_FORE_COLOR);
		
		add(Box.createRigidArea(new Dimension(5, 0)));
		add(cbxUsers);
		add(Box.createRigidArea(new Dimension(5, 0)));
	}
	
	public User getUser() {
		String username = (String) cbxUsers.getSelectedItem();

		for (User user : users)
			if (user.getUsername().equals(username))
				return user;
		
		return null;
	}
}
