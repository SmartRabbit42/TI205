package visual.dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;

import visual.Client;

public class MessageDialog extends JDialog {
	
	private static final long serialVersionUID = 6284474537916278984L;

	public MessageDialog (Client client, String message) {
		super(client, Dialog.ModalityType.DOCUMENT_MODAL);
		
		initializeComponent(message);
	}
	
	private void initializeComponent(String message) {
		JLabel lblMessage = new JLabel(message);
		
		Container contentPane = getContentPane();
		contentPane.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.NORTH);
		contentPane.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.WEST);
		contentPane.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.SOUTH);
		contentPane.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.EAST);
		contentPane.add(lblMessage, BorderLayout.CENTER);
		
		pack();
		setLocationRelativeTo(getParent());
	}
}
