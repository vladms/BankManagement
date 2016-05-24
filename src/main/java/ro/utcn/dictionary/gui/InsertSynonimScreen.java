package ro.utcn.dictionary.gui;



import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InsertSynonimScreen {
	private JFrame mainFrame;
	private JPanel controlPanel;
	
	private JTextField wordTextField;
	private JTextField synonimTextField;

	private JButton cancelButton;
	private JButton createButton;

	private AppInterfaceButtonEvents buttonEventHandler;

	public InsertSynonimScreen(AppInterfaceButtonEvents buttonEventsHandler) {
		this.buttonEventHandler = buttonEventsHandler;
	
	}
	
	public void prepareComponents(){
		this.prepareGUI();
		this.placeElements();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Insert new synonim");
		mainFrame.setSize(500, 400);
		mainFrame.setLayout(new GridLayout(1, 1));
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(5, 1));
		
		mainFrame.add(controlPanel);
		mainFrame.setVisible(true);
	}
	


	/*
	 * placeElements(): Add elements on the controlPanel
	 */
	private void placeElements() {

		wordTextField = new JTextField("Word");
		synonimTextField = new JTextField("Synonim");

		cancelButton = new JButton("Cancel");
		createButton = new JButton("Insert synonim");

		controlPanel.add(wordTextField);
		controlPanel.add(synonimTextField);

		controlPanel.add(createButton);
		controlPanel.add(cancelButton);

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.setVisible(false);
				buttonEventHandler.canceled();
			}
		});

		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int errorNumber = 0;
				if (wordTextField.getText().length() <= 0 || synonimTextField.getText().length() <= 0){
					errorNumber = 1;
				}
				if (wordTextField.getText().contains(" ") || synonimTextField.getText().contains(" ") ){
					errorNumber = 2;
				}
				
				
				
				if (errorNumber == 0) {
					buttonEventHandler.newSynonimInserted(wordTextField.getText(), synonimTextField.getText());
					mainFrame.setVisible(false);
				} else {
					String errorMessage;
					switch (errorNumber) {
					case 1:
						errorMessage = "TextFields can't be empty!";
						break;
					case 2:
						errorMessage = "No whitespaces are allowed in textFields!";
						break;
					default:
						errorMessage = "Error";
						break;
					}
					JOptionPane.showMessageDialog(mainFrame, errorMessage);
				}

			}
		});
	}
}
