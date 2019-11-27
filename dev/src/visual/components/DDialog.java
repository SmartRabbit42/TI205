package visual.components;

import java.awt.Window;

import javax.swing.JDialog;

import visual.VisualConstants;


public class DDialog extends JDialog {

	private static final long serialVersionUID = -5561626758061177495L;

	public DDialog(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		getContentPane().setBackground(VisualConstants.BACK_COLOR);
		setResizable(false);
	}
	
	protected void adjust() {
		pack();
		setLocationRelativeTo(getParent());
	}
}
