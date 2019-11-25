package visual.components;

import javax.swing.JTextField;

import visual.VisualConstants;

public class DTextField extends JTextField {

	private static final long serialVersionUID = -1785042376065990403L;

	public DTextField() {
		super();
		
		initializeComponent();
	}
	
	public DTextField(String placeHolder) {
		super(placeHolder);
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		setBackground(VisualConstants.compBackColor);
		setForeground(VisualConstants.compForeColor);
	}
}
