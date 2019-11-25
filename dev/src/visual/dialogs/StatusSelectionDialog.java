package visual.dialogs;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.*;

import data.Data;
import data.containers.User;
import network.Network;
import network.netMsg.messages.StatusUpdateMsg;
import visual.Client;

public class StatusSelectionDialog extends JDialog {

	private static final long serialVersionUID = 8514722385911569350L;

	private Client client;
	private Data data;
	private Network network;
	
	public StatusSelectionDialog(Client client, Data data, Network network) {
		super(client, Dialog.ModalityType.DOCUMENT_MODAL);
		
		this.client = client;
		this.data = data;
		this.network = network;
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel panButtons = new JPanel();
		panButtons.setLayout(new BoxLayout(panButtons, BoxLayout.X_AXIS));
		
		Dimension buttonSize = new Dimension(50, 50);
		
		JButton btnUnknown = new JButton("");
		btnUnknown.setBackground(Color.white);
		btnUnknown.setMinimumSize(buttonSize);
		btnUnknown.setPreferredSize(buttonSize);
		btnUnknown.setMaximumSize(buttonSize);
		btnUnknown.addActionListener(e -> selectStatus(User.Status.unknown));
		
		JButton btnLoading = new JButton("");
		btnLoading.setBackground(Color.blue);
		btnLoading.setMinimumSize(buttonSize);
		btnLoading.setPreferredSize(buttonSize);
		btnLoading.setMaximumSize(buttonSize);
		btnLoading.addActionListener(e -> selectStatus(User.Status.loading));
		
		JButton btnOffline = new JButton("");
		btnOffline.setBackground(Color.red);
		btnOffline.setMinimumSize(buttonSize);
		btnOffline.setPreferredSize(buttonSize);
		btnOffline.setMaximumSize(buttonSize);
		btnOffline.addActionListener(e -> selectStatus(User.Status.offline));
		
		JButton btnOnline = new JButton("");
		btnOnline.setBackground(Color.green);
		btnOnline.setMinimumSize(buttonSize);
		btnOnline.setPreferredSize(buttonSize);
		btnOnline.setMaximumSize(buttonSize);
		btnOnline.addActionListener(e -> selectStatus(User.Status.online));

		JButton btnBusy = new JButton("");
		btnBusy.setBackground(Color.orange);
		btnBusy.setMinimumSize(buttonSize);
		btnBusy.setPreferredSize(buttonSize);
		btnBusy.setMaximumSize(buttonSize);
		btnBusy.addActionListener(e -> selectStatus(User.Status.busy));

		JButton btnBlack = new JButton("");
		btnBlack.setBackground(Color.black);
		btnBlack.setMinimumSize(buttonSize);
		btnBlack.setPreferredSize(buttonSize);
		btnBlack.setMaximumSize(buttonSize);
		btnBlack.addActionListener(e -> selectStatus(User.Status.black));
		
		Dimension boxSize = new Dimension(5, 0);
		
		panButtons.add(Box.createRigidArea(boxSize));
		panButtons.add(btnUnknown);
		panButtons.add(Box.createRigidArea(boxSize));
		panButtons.add(btnLoading);
		panButtons.add(Box.createRigidArea(boxSize));
		panButtons.add(btnOffline);
		panButtons.add(Box.createRigidArea(boxSize));
		panButtons.add(btnOnline);
		panButtons.add(Box.createRigidArea(boxSize));
		panButtons.add(btnBusy);
		panButtons.add(Box.createRigidArea(boxSize));
		panButtons.add(btnBlack);
		panButtons.add(Box.createRigidArea(boxSize));
		
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(panButtons);
		add(Box.createRigidArea(new Dimension(0, 5)));
		
		pack();
		setLocationRelativeTo(this.getParent());
	}
	
	private void selectStatus(byte status) {
		StatusUpdateMsg sum = new StatusUpdateMsg();
		sum.setStatus(status);

		network.spreadMessage(data.getAddedUsers(), sum, true);
		network.spreadMessage(data.getKnownUsers(), sum, true);
		
		data.getLocalUser().setStatus(status);
		
		client.updateLocalUser();
		
		setVisible(false);
	}
}