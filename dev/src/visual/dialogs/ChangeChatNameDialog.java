package visual.dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.*;

import data.containers.Chat;
import general.exceptions.InvalidParameterException;
import network.Network;
import network.netMsg.messages.ChangeChatNameMsg;
import visual.Client;

public class ChangeChatNameDialog extends JDialog {

	private static final long serialVersionUID = 8514722385911569350L;

	private Client client;
	private Network network;
	
	private Chat chat;
	
	private JTextField txtName;
	
	public ChangeChatNameDialog(Client client, Network network, Chat chat) {
		super(client, Dialog.ModalityType.DOCUMENT_MODAL);
		
		this.client = client;
		this.network = network;
		
		this.chat = chat;
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		JPanel panUpper = new JPanel();
		panUpper.setLayout(new BoxLayout(panUpper, BoxLayout.Y_AXIS));
		
		JLabel lblTitle = new JLabel("Change chat name");
		
		JLabel lblName = new JLabel("new name:");
		
		txtName = new JTextField();
		txtName.setMaximumSize(new Dimension(500, 50));
		
		panUpper.add(lblTitle);
		panUpper.add(Box.createRigidArea(new Dimension(0,5)));
		panUpper.add(lblName);
		panUpper.add(txtName);
		panUpper.add(Box.createRigidArea(new Dimension(0,5)));
		
		JPanel panButtons = new JPanel();
		panButtons.setLayout(new BoxLayout(panButtons, BoxLayout.X_AXIS));
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(e -> setVisible(false));
		
		JButton btnChange = new JButton("change");
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
			chat.setName(txtName.getText());
			
			ChangeChatNameMsg ccnm = new ChangeChatNameMsg();
			ccnm.setChatId(chat.getId());
			ccnm.setChatName(chat.getName());
			
			network.spreadMessage(chat.getMembers(), ccnm, true);
			
			client.updateChat(chat);
			
			setVisible(false);
		} catch (InvalidParameterException e) {
			JOptionPane.showMessageDialog(client,
			        e.getMessage(),
			        "error",
			        JOptionPane.INFORMATION_MESSAGE);
		}
	}
}