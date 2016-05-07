package ro.utcn.bank.gui;



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

public class CreateClientScreen {
	private JFrame mainFrame;
	private JPanel controlPanel;
	
	private JTextField cnpTextField;
	private JTextField nameTextField;
	private JTextField ageTextField;

	private JButton cancelButton;
	private JButton createButton;

	private AppInterfaceButtonEvents buttonEventHandler;

	public CreateClientScreen(AppInterfaceButtonEvents buttonEventsHandler) {
		this.buttonEventHandler = buttonEventsHandler;
	
	}
	
	public void prepareComponents(){
		this.prepareGUI();
		this.placeElements();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Create new client");
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

		cnpTextField = new JTextField("CNP");
		nameTextField = new JTextField("Name");
		ageTextField = new JTextField("Age");

		cancelButton = new JButton("Cancel");
		createButton = new JButton("Create client");

		controlPanel.add(cnpTextField);
		controlPanel.add(nameTextField);
		controlPanel.add(ageTextField);
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
				if (nameTextField.getText().length() == 0) {
					errorNumber = 4;
				}
				if (!(ageTextField.getText().matches("[0-9]+") && ageTextField.getText().length() > 0
						|| (Integer.parseInt(ageTextField.getText())) < 0)
						|| (Integer.parseInt(ageTextField.getText())) < 0) {
					errorNumber = 2;
				}
				if (!(cnpTextField.getText().matches("[0-9]+") && cnpTextField.getText().length() > 0)
						|| (Integer.parseInt(cnpTextField.getText())) < 0) {
					errorNumber = 3;
				}
				
				if (errorNumber == 0) {
					buttonEventHandler.newClientCreated(cnpTextField.getText(), nameTextField.getText(),
							Integer.parseInt(ageTextField.getText()));
					mainFrame.setVisible(false);
				} else {
					String errorMessage;
					switch (errorNumber) {
					case 2:
						errorMessage = "CNP should be numeric and positive!";
						break;
					case 3:
						errorMessage = "Age should be numeric and positive!";
						break;
					case 4:
						errorMessage = "Name should not be blank!";
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
