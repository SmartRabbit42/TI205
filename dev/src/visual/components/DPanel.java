package visual.components;

import java.awt.Color;

import javax.swing.JPanel;

import visual.VisualConstants;

public class DPanel extends JPanel {

	private static final long serialVersionUID = 1029166407130479938L;
	
	private Color color = VisualConstants.alphaPanelColor;
	
	public DPanel() {
		super();
		
		initializeComponent();
	}
	
	public DPanel(Color color) {
		super();
		
		this.color = color;
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		setBackground(color);
	}
}
