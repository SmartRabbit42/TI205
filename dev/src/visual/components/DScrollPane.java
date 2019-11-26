package visual.components;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

import visual.VisualConstants;

public class DScrollPane extends JScrollPane {

	private static final long serialVersionUID = 5995349715954059402L;

	public DScrollPane(DPanel panel) {
		super(panel);
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		setBackground(VisualConstants.COMP_BACK_COLOR);
		setForeground(VisualConstants.COMP_BACK_COLOR);
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
		setAlignmentX(LEFT_ALIGNMENT);

		JScrollBar bar = new JScrollBar();
		
		JButton none = new JButton();
		none.setPreferredSize(new Dimension(0, 0));
		
		bar.setBackground(VisualConstants.COMP_BACK_COLOR);
		bar.setPreferredSize(new Dimension(5, 10));
		bar.setForeground(VisualConstants.COMP_BACK_COLOR);
		bar.setUnitIncrement(20);
		bar.setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = VisualConstants.COMP_FORE_COLOR;
			}
			
			@Override 
			protected JButton createIncreaseButton(int orientation){
				return none;
			}
			
			@Override 
			protected JButton createDecreaseButton(int orientation){
				return none;
			}
		});
		
		setVerticalScrollBar(bar);
	}
}
