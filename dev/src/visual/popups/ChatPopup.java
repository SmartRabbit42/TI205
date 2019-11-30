package visual.popups;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import data.Data;
import data.containers.Chat;
import data.containers.User;
import network.Network;
import network.netMsg.messages.LeaveChatMsg;
import visual.Client;
import visual.VisualConstants;
import visual.components.DMenuItem;
import visual.components.DPopupMenu;
import visual.dialogs.ChangeChatNameDialog;

public class ChatPopup extends DPopupMenu {

	private static final long serialVersionUID = 3700438134286442845L;

	private Client client;
	private Network network;
	private Data data;
	
	private Chat chat;
	
	public ChatPopup(Client client, Network network, Data data, Chat chat) {
		this.client = client;
		this.network = network;
		this.data = data;
		
		this.chat = chat;
		
		initializeComponent();
	 }
	
	private void initializeComponent() {
		setBackground(VisualConstants.BACK_COLOR);
		
		DMenuItem info = new DMenuItem("info");
		info.addActionListener(e -> infoClick());
		
		DMenuItem changeName = new DMenuItem("change name");
		changeName.addActionListener(e -> changeNameClick());
		
		DMenuItem silence = new DMenuItem(chat.isSilenced() ? "silence" : "unsilence");
	    silence.addActionListener(e -> silenceClick());
	    
	    DMenuItem leave = new DMenuItem("leave");
	    leave.addActionListener(e -> leaveClick());

	    Dimension separatorDimension = new Dimension(0, 3);
	    
	    add(info);
	    add(Box.createRigidArea(separatorDimension));
	    add(changeName);
	    add(Box.createRigidArea(separatorDimension));
	    add(silence);
	    add(Box.createRigidArea(separatorDimension));
	    add(leave);
	}
	
	private void infoClick() {
		// TODO
	}
	
	private void changeNameClick() {
		JDialog ccnd = new ChangeChatNameDialog(client, network, chat);
		ccnd.setVisible(true);
	}
	
	private void silenceClick() {
		chat.setSilenced(!chat.isSilenced());
	}
	
	private void leaveClick() {
		int dialogResult = JOptionPane.showConfirmDialog (null,
				String.format("leave %s chat?", chat.getName()),
				"confirmation",
				JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION){
			data.getChats().remove(chat);
			for (User member : chat.getMembers())
				if(member.getChats().size() == 1)
					data.getKnownUsers().remove(member);
			
			LeaveChatMsg lcm = new LeaveChatMsg();
			lcm.setChatId(chat.getId());
			
			network.spreadMessage(chat.getMembers(), lcm, true);
			
			client.removeChat(chat);
		}
	}
}
