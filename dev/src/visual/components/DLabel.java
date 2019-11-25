package visual.components;

import javax.swing.JLabel;

import general.Colors;

public class DLabel extends JLabel {

	private static final long serialVersionUID = -7938264165072962935L;

	public DLabel(String text) {
		super(text);
		
		initializeComponent();
	}
	
	private void initializeComponent(){
		setForeground(Colors.foreground1);
	}
}