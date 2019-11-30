package visual.components;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import visual.VisualConstants;

public class DTextField extends JTextField {

	private static final long serialVersionUID = -1785042376065990403L;

	private String placeholder;
	
	private boolean placeholderShown;
	
	private Font font = new Font("Arial", Font.BOLD, 11);
	
	public DTextField() {
		super();

		initializeComponent();
	}
	
	public DTextField(Font font) {
		super();

		this.font = font;
		
		initializeComponent();
	}
	
	public DTextField(String placeholder) {
		super();
		
		this.placeholder = placeholder;
		
		placeholderShown = true;
		
		initializeComponent();
	}
	
	public DTextField(Font font, String placeholder) {
		super();

		this.font = font;
		this.placeholder = placeholder;
		
		placeholderShown = true;
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		setBackground(VisualConstants.COMP_BACK_COLOR);
		setForeground(VisualConstants.COMP_FORE_COLOR);
		setText(placeholder);
		
		if (isPlaceholderShown())
			setFont(new Font(font.getFontName(), Font.ITALIC, font.getSize()));
		else
			setFont(font);
		
		
		addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		    	if (placeholder == null)
		    		return;
		    	
		        if (isPlaceholderShown()) {
		            setText("");
		            setFont(font);
		            setForeground(VisualConstants.COMP_FORE_COLOR);
		            placeholderShown = false;
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		    	if (placeholder == null)
		    		return;
		    	
		        if (getText().isEmpty() && !isPlaceholderShown()) {
		            setText(placeholder);
		            setFont(new Font(font.getFontName(), Font.ITALIC, font.getSize()));
		            setForeground(VisualConstants.COMP_PLACEHOLDER_COLOR);
		            placeholderShown = true;
		        }
		    }
	   	});
	}

	public boolean isPlaceholderShown() {
		return placeholderShown;
	}

	public String getPlaceholder() {
		return placeholder;
	}
	
	@Override
	public String getText() {
		if (isPlaceholderShown())
			return "";
		
		return super.getText();
	}
}