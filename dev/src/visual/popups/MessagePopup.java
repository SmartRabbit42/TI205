package visual.popups;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.Box;

import data.containers.Message;
import visual.Client;
import visual.components.DMenuItem;
import visual.components.DPopupMenu;

public class MessagePopup extends DPopupMenu {

	private static final long serialVersionUID = 3700438134286442845L;

	private Client client;
	
	private Message message;
	
	public MessagePopup(Client client, Message message) {
		this.client = client;
		
		this.message = message;
		
		initializeComponent();
	 }
	
	private void initializeComponent() {		
		DMenuItem copy = new DMenuItem("copy");
		copy.addActionListener(e -> copyClick());
		
		DMenuItem viewSender = new DMenuItem("view message sender");
		viewSender.addActionListener(e -> viewSenderClick());
		
		DMenuItem delete = new DMenuItem("delete");
		delete.addActionListener(e -> deleteClick());

	    Dimension separatorDimension = new Dimension(0, 3);
	    
	    add(copy);
	    add(Box.createRigidArea(separatorDimension));
	    add(viewSender);
	    add(Box.createRigidArea(separatorDimension));
	    add(delete);
	}
	
	private void copyClick() {
		StringSelection data = new StringSelection(message.getContent());
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(data, data);
	}
	
	private void viewSenderClick() {
		// TODO
	}
	
	private void deleteClick() {
		message.getChat().getMessages().remove(message);
		
		client.removeMessage(message);
	}
}
