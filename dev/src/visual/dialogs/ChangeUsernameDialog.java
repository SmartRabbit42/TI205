package visual.dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;

import data.containers.User;
import general.exceptions.InvalidParameterException;
import visual.Client;
import visual.VisualConstants;
import visual.components.DButton;
import visual.components.DDialog;
import visual.components.DLabel;
import visual.components.DPanel;
import visual.components.DTextField;

public class ChangeUsernameDialog extends DDialog {

	private static final long serialVersionUID = 8514722385911569350L;

	private Client client;
	
	private User user;
	
	private DTextField txtUsername;
	
	public ChangeUsernameDialog(Client client, User user) {
		super(client, Dialog.ModalityType.DOCUMENT_MODAL);
		
		this.client = client;
		
		this.user = user;
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		setBackground(VisualConstants.backColor);
		
		DPanel panUpper = new DPanel();
		panUpper.setLayout(new BoxLayout(panUpper, BoxLayout.Y_AXIS));
		
		DLabel lblTitle = new DLabel("Change username");
		
		DLabel lblUsername = new DLabel("new username:");
		
		txtUsername = new DTextField();
		txtUsername.setMaximumSize(new Dimension(500, 50));
		
		panUpper.add(lblTitle);
		panUpper.add(Box.createRigidArea(new Dimension(0,5)));
		panUpper.add(lblUsername);
		panUpper.add(txtUsername);
		panUpper.add(Box.createRigidArea(new Dimension(0,5)));
		
		DPanel panButtons = new DPanel();
		panButtons.setLayout(new BoxLayout(panButtons, BoxLayout.X_AXIS));
		
		DButton btnCancel = new DButton("cancel");
		btnCancel.addActionListener(e -> setVisible(false));
		
		DButton btnChange = new DButton("change");
		btnChange.addActionListener(e -> btnChangeClick());
		
		panButtons.add(Box.createHorizontalGlue());
		panButtons.add(btnCancel);
		panButtons.add(Box.createRigidArea(new Dimension(10, 0)));
		panButtons.add(btnChange);
		
		Container contentPane = getContentPane();
		contentPane.add(panUpper, BorderLayout.CENTER);
		contentPane.add(panButtons, BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(this.getParent());
	}
	
	private void btnChangeClick() {
		try {
			user.setUsername(txtUsername.getText());
			
			client.updateUser(user);
			
			setVisible(false);
		} catch (InvalidParameterException e) {
			JOptionPane.showMessageDialog(client,
			        e.getMessage(),
			        "error",
			        JOptionPane.INFORMATION_MESSAGE);
		}
	}
}