package visual.popups;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import data.Data;
import data.containers.User;
import visual.dialogs.ChangeAddressDialog;
import visual.dialogs.ChangeUsernameDialog;
import network.Network;
import visual.Client;

public class UserPopup extends JPopupMenu {

	private static final long serialVersionUID = 3700438134286442845L;

	private Client client;
	private Network network;
	private Data data;
	
	private User user;
	
	public UserPopup(Client client, Network network, Data data, User user) {
		this.client = client;
		this.network = network;
		this.data = data;
		
		this.user = user;
		
		initializeComponent();
	 }
	
	private void initializeComponent() {
		JMenuItem profile = new JMenuItem("profile");
		JMenuItem changeUsername = new JMenuItem("change username");
	    JMenuItem changeAddress = new JMenuItem("change address");
	    JMenuItem delete = new JMenuItem("delete");
	    
	    add(profile);
	    add(changeUsername);
	    add(changeAddress);
	    add(delete);
	    
	    profile.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e) {              
	    		// TODO
            }  
        });
	    changeUsername.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e) {              
	    		ChangeUsernameDialog changeUsernameDialog = new ChangeUsernameDialog(client, user);
	    		changeUsernameDialog.setVisible(true);
            }  
        });
	    changeAddress.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e) {              
	    		ChangeAddressDialog changeAddressDialog = new ChangeAddressDialog(client, network, data, user);
	    		changeAddressDialog.setVisible(true);
            }  
        });  
	    delete.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e) {              
	    		// TODO
            }  
        });  
	}
}
