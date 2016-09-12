package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import Server.Interests;
import utility.FontLibrary;

public class InterestPopup extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton searchButton;
	private LinkedList<JCheckBox> interestButtons;
	private final Color color = new Color(250,243,233);
	private final Font font = FontLibrary.getTrueTypeFont(18);
	public Vector<Integer> selectedInterests;
	
	public InterestPopup(Vector<Integer> selectedInterests) {
				
		this.selectedInterests = selectedInterests;
		initializeComponents();
		createGUI();
		addEvents();
		
		setVisible(true);
	}
	
	private void initializeComponents() {
		
		interestButtons = new LinkedList<JCheckBox>();
		
		for(int i = 0; i < Interests.CATEGORIES.length; i++) {
			
			JCheckBox jcb = new JCheckBox(Interests.CATEGORIES[i]);
			interestButtons.add(jcb);
		}
		
		searchButton = new CuteButton(0, "Search");
	}
	
	private void createGUI() {
		
		setSize(250, 250);
		setLayout(new GridLayout(5, 2));
		setBackground(color);
		
		for(JCheckBox jcb : interestButtons) {
			jcb.setFont(font);
			jcb.setBackground(color);
			jcb.setOpaque(false);
			add(jcb);
		}
		add(new JLabel(""));
		add(searchButton);
		searchButton.setFont(font);
	}
	
	private void addEvents() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedInterests = new Vector<Integer>();
				
				int count = 0;
				for(JCheckBox jcb : interestButtons) {
					if(jcb.isSelected()) {
						selectedInterests.add(count);
					}
					count++;
				}
				InterestPopup.this.setModal(false);
				InterestPopup.this.setVisible(false);
				InterestPopup.this.dispose();
			}
		});
	}
	
}
