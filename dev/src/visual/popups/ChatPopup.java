package visual.popups;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import data.Data;
import data.containers.Chat;
import network.Network;
import visual.Client;

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
		JMenuItem members = new JMenuItem("members");
		JMenuItem changeName = new JMenuItem("change name");
	    JMenuItem silence = new JMenuItem("silence");
	    JMenuItem leave = new JMenuItem("leave");
	    
	    add(members);
	    add(changeName);
	    add(silence);
	    add(leave);
	    
	    members.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e) {              
	    		// TODO
            }  
        });
	    changeName.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e) {              
	    		// TODO
            }  
        });
	    silence.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e) {              
	    		// TODO
            }  
        });  
	    leave.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e) {              
	    		// TODO
            }  
        });  
	}
}
