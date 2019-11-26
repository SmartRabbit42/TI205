package visual.components;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import visual.VisualConstants;

public class DTextField extends JTextField {

	private static final long serialVersionUID = -1785042376065990403L;

	private String placeholder;
	
	private boolean placeholderShown;
	
	public DTextField() {
		super();

		initializeComponent();
	}
	
	public DTextField(String placeholder) {
		super();
		
		this.placeholder = placeholder;
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		setBackground(VisualConstants.COMP_BACK_COLOR);
		setForeground(VisualConstants.COMP_PLACEHOLDER_COLOR);
		setText(placeholder);
		addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		    	if (placeholder == null)
		    		return;
		    	
		        if (getText().equals(placeholder)) {
		            setText("");
		            setForeground(VisualConstants.COMP_FORE_COLOR);
		            placeholderShown = false;
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		    	if (placeholder == null)
		    		return;
		    	
		        if (getText().isEmpty()) {
		            setText(placeholder);
		            setForeground(VisualConstants.COMP_PLACEHOLDER_COLOR);
		            placeholderShown = true;
		        }
		    }
	   	});
	}

	public boolean isPlaceholderShown() {
		return placeholderShown;
	}
}