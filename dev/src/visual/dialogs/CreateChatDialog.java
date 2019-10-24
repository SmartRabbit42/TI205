package visual.dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class CreateChatDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	public CreateChatDialog(JFrame parent) {
		super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
		
		initializeComponent();
	}
	
	private void initializeComponent() {
		JPanel panList = new JPanel();
		panList.setLayout(new BoxLayout(panList, BoxLayout.PAGE_AXIS));
		
		JLabel label = new JLabel("Create Chat");
		
		JScrollPane listScroller = new JScrollPane();
		listScroller.setAlignmentX(LEFT_ALIGNMENT);

		panList.add(label);
		panList.add(Box.createRigidArea(new Dimension(0,5)));
		panList.add(listScroller);

		JPanel panButtons = new JPanel();
		panButtons.setLayout(new BoxLayout(panButtons, BoxLayout.LINE_AXIS));
		
		JButton btnCancel = new JButton("cancel");
		
		JButton btnCreate = new JButton("create");
		
		panButtons.add(Box.createHorizontalGlue());
		panButtons.add(btnCancel);
		panButtons.add(Box.createRigidArea(new Dimension(10, 0)));
		panButtons.add(btnCreate);

		Container contentPane = getContentPane();
		contentPane.add(panList, BorderLayout.CENTER);
		contentPane.add(panButtons, BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(this.getParent());
		
		// Events
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}
		});
		btnCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}
		});
	}
}