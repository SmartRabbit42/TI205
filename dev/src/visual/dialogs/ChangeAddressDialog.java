package visual.dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;

import data.Data;
import data.containers.User;
import general.Helper;
import general.exceptions.InvalidParameterException;
import general.exceptions.MessageNotSentException;
import network.Network;
import network.netMsg.messages.ConnectMsg;
import visual.Client;
import visual.VisualConstants;
import visual.components.DButton;
import visual.components.DDialog;
import visual.components.DLabel;
import visual.components.DPanel;
import visual.components.DTextField;

public class ChangeAddressDialog extends DDialog {

	private static final long serialVersionUID = 8514722385911569350L;

	private Client client;
	private Network network;
	private Data data;
	
	private User user;
	
	private DTextField txtUserAddress;
	
	public ChangeAddressDialog(Client client, Network network, Data data, User user) {
		super(client, Dialog.ModalityType.DOCUMENT_MODAL);
		
		this.client = client;
		this.network = network;
		this.data = data;
		
		this.user = user;
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		setBackground(VisualConstants.BACK_COLOR);
		
		DPanel panUpper = new DPanel();
		panUpper.setLayout(new BoxLayout(panUpper, BoxLayout.Y_AXIS));
		
		DLabel lblTitle = new DLabel("Change user address");
		
		DLabel lblUserAddress = new DLabel("user address:");
		
		txtUserAddress = new DTextField();
		txtUserAddress.setMaximumSize(new Dimension(500, 50));
		
		panUpper.add(lblTitle);
		panUpper.add(Box.createRigidArea(new Dimension(0,5)));
		panUpper.add(lblUserAddress);
		panUpper.add(txtUserAddress);
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
		
		adjust();
	}
	
	private void btnChangeClick() {
		try {
			String fullAddress = txtUserAddress.getText();
			
			if (!fullAddress.matches(Helper.addressRegex))
				throw new InvalidParameterException("invalid address");

			if (fullAddress.equals(data.getLocalUser().getFullAddress()))
				throw new InvalidParameterException("trying to add local user");
			
			for (User user : data.getAddedUsers())
				if (fullAddress.equals(user.getFullAddress()))
					throw new InvalidParameterException("user already added");
			
			String[] aux = fullAddress.split(":");
			String address = aux[0];
			int port = Integer.parseInt(aux[1]);

			user.setAddress(address);
			user.setPort(port);
			
			ConnectMsg cmsg = new ConnectMsg();
			cmsg.setStatus(data.getLocalUser().getStatus());
			cmsg.setAddress(data.getLocalUser().getAddress());
			cmsg.setPort(data.getLocalUser().getPort());
			
			network.sendMessage(user, cmsg);

			setVisible(false);
		} catch (MessageNotSentException e) {
			 JOptionPane.showMessageDialog(client,
				        "couldn't send connectMsg",
				        "error",
				        JOptionPane.INFORMATION_MESSAGE);
		} catch (InvalidParameterException e) {
			JOptionPane.showMessageDialog(client,
			        e.getMessage(),
			        "error",
			        JOptionPane.INFORMATION_MESSAGE);
		}
	}
}