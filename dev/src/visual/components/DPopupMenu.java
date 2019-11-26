package visual.components;

import javax.swing.BorderFactory;
import javax.swing.JPopupMenu;

import visual.VisualConstants;

public class DPopupMenu extends JPopupMenu {

	private static final long serialVersionUID = 7648915281920816212L;

	public DPopupMenu() {
		initializeComponent();
	}
	
	private void initializeComponent() {
		setBackground(VisualConstants.BACK_COLOR);
		setBorder(BorderFactory.createEmptyBorder());
	}
}
