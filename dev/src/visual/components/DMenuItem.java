package visual.components;

import javax.swing.JMenuItem;

import visual.VisualConstants;

public class DMenuItem extends JMenuItem {

	private static final long serialVersionUID = -1226240447116315437L;

	public DMenuItem() {
		super();
		
		intializeComponent();
	}
	
	public DMenuItem(String text) {
		super(text);
		
		intializeComponent();
	}
	
	private void intializeComponent() {
		setBackground(VisualConstants.ALPHA_PANEL_COLOR);
		setForeground(VisualConstants.COMP_FORE_COLOR);
	}
}
