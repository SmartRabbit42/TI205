package visual.components;

import javax.swing.JButton;

import visual.VisualConstants;

public class DButton extends JButton {

	private static final long serialVersionUID = -6366701348171799566L;

	public DButton() {
		super();
		
		initializeComponent();
	}
	
	public DButton(String text) {
		super(text);
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		setBackground(VisualConstants.COMP_BACK_COLOR);
		setForeground(VisualConstants.COMP_FORE_COLOR);
	}
}
