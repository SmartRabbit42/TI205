package visual.popups;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import data.Data;
import data.containers.Chat;
import data.containers.User;
import general.Colors;
import network.Network;
import network.netMsg.messages.LeaveChatMsg;
import visual.Client;
import visual.dialogs.ChangeChatNameDialog;

public class ChatPopup extends JPopupMenu {

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
		JMenuItem info = new JMenuItem("info");
		info.setBackground(Colors.buttonBackground);
		info.setForeground(Colors.buttonForeground);
		info.addActionListener(e -> infoClick());
		
		JMenuItem changeName = new JMenuItem("change name");
		changeName.setBackground(Colors.buttonBackground);
		changeName.setForeground(Colors.buttonForeground);
		changeName.addActionListener(e -> changeNameClick());
		
	    JMenuItem silence = new JMenuItem(chat.isSilenced() ? "silence" : "unsilence");
	    silence.setBackground(Colors.buttonBackground);
	    silence.setForeground(Colors.buttonForeground);
	    silence.addActionListener(e -> silenceClick());
	    
	    JMenuItem leave = new JMenuItem("leave");
	    leave.setBackground(Colors.buttonBackground);
	    leave.setForeground(Colors.buttonForeground);
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
