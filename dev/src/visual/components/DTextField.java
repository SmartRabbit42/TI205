package visual.components;

import javax.swing.JTextField;

import general.Colors;

public class DTextField extends JTextField {

	private static final long serialVersionUID = -1785042376065990403L;

	public DTextField(String placeHolder) {
		super(placeHolder);
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		setBackground(Colors.textFieldBackground);
		setForeground(Colors.textFieldForeground);
	}
}
