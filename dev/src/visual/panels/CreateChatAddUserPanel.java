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

	private String[] names;
	
	JComboBox<String> cbxUsers;

	public CreateChatAddUserPanel(ArrayList<User> users) {
		this.names = new String[users.size()];
		
		for (int i = 0; i < users.size(); i++) {
			names[i] = users.get(i).getUsername();
		}
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setAlignmentX(Component.CENTER_ALIGNMENT);
		
		cbxUsers = new JComboBox<String>(names);
		
		add(Box.createRigidArea(new Dimension(5, 0)));
		add(cbxUsers);
		add(Box.createHorizontalGlue());
	}
	
	public String getSelectedUsername() {
		return (String) cbxUsers.getSelectedItem();
	}
}
