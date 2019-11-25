package visual.components;

import javax.swing.JButton;

import general.Colors;

public class DButton extends JButton {

	private static final long serialVersionUID = -6366701348171799566L;

	public DButton(String text) {
		super(text);
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		setBackground(Colors.buttonBackground);
		setForeground(Colors.buttonForeground);
	}
}
