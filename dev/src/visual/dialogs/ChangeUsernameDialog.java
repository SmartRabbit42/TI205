package visual.dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import data.containers.User;
import general.exceptions.InvalidParameterException;
import visual.Client;

public class ChangeUsernameDialog extends JDialog {

	private static final long serialVersionUID = 8514722385911569350L;

	private Client client;
	
	private User user;
	
	public ChangeUsernameDialog(Client client, User user) {
		super(client, Dialog.ModalityType.DOCUMENT_MODAL);
		
		this.client = client;
		
		this.user = user;
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		JPanel panUpper = new JPanel();
		panUpper.setLayout(new BoxLayout(panUpper, BoxLayout.Y_AXIS));
		
		JLabel lblTitle = new JLabel("Change username");
		
		JLabel lblUsername = new JLabel("new username:");
		
		JTextField txtUsername = new JTextField();
		txtUsername.setMaximumSize(new Dimension(500, 50));
		
		panUpper.add(lblTitle);
		panUpper.add(Box.createRigidArea(new Dimension(0,5)));
		panUpper.add(lblUsername);
		panUpper.add(txtUsername);
		panUpper.add(Box.createRigidArea(new Dimension(0,5)));
		
		JPanel panButtons = new JPanel();
		panButtons.setLayout(new BoxLayout(panButtons, BoxLayout.X_AXIS));
		
		JButton btnCancel = new JButton("cancel");
		
		JButton btnChange = new JButton("change");
		
		panButtons.add(Box.createHorizontalGlue());
		panButtons.add(btnCancel);
		panButtons.add(Box.createRigidArea(new Dimension(10, 0)));
		panButtons.add(btnChange);
		
		Container contentPane = getContentPane();
		contentPane.add(panUpper, BorderLayout.CENTER);
		contentPane.add(panButtons, BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(this.getParent());
		
		// Events
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				client.setEnabled(true);
				setVisible(false);
			}
		});
		btnChange.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					user.setUsername(txtUsername.getText());
					
					client.updateUser(user);
					
					setVisible(false);
				} catch (InvalidParameterException e) {
					MessageDialog msg = new MessageDialog(client, "invalid username");
					msg.setVisible(true);
				}
			}
		});
	}
}