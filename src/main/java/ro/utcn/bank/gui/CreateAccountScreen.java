package ro.utcn.bank.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class CreateAccountScreen {
	private JFrame mainFrame;
	private JPanel controlPanel;

	private JTextField clientIdTextField;
	private JTextField accountIdTextField;

	private JButton cancelButton;
	private JButton createButton;

	private AppInterfaceButtonEvents buttonEventHandler;

	private ButtonGroup buttonGroup;

	public CreateAccountScreen(AppInterfaceButtonEvents buttonEventsHandler) {
		this.buttonEventHandler = buttonEventsHandler;

	}

	public void prepareComponents() {
		this.prepareGUI();
		this.placeElements();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Create new account");
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

		JRadioButton one = new JRadioButton("Saving Account", true);
		JRadioButton two = new JRadioButton("Spending Account");
		one.setActionCommand("Saving Account");
		two.setActionCommand("Spending Account");
		buttonGroup = new ButtonGroup();
		buttonGroup.add(one);
		buttonGroup.add(two);

		clientIdTextField = new JTextField("ClientId");
		accountIdTextField = new JTextField("AccountId");

		cancelButton = new JButton("Cancel");
		createButton = new JButton("Create client");

		controlPanel.add(one);
		controlPanel.add(two);
		controlPanel.add(clientIdTextField);
		controlPanel.add(accountIdTextField);
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
				if (!(clientIdTextField.getText().matches("[0-9]+") && clientIdTextField.getText().length() > 0)
						|| (Integer.parseInt(clientIdTextField.getText())) < 0) {
					JOptionPane.showMessageDialog(mainFrame, "Client Id is not in good format!");
				} else if (!(accountIdTextField.getText().matches("[0-9]+")
						&& accountIdTextField.getText().length() > 0)
						|| (Integer.parseInt(accountIdTextField.getText())) < 0) {
					JOptionPane.showMessageDialog(mainFrame, "Account Id is not in good format!");
				} else {
					String accountType = "SavingAccount";
					
					if (buttonGroup.getSelection().getActionCommand() == "Spending Account") {
						accountType = "SpendingAccount";
					}

					buttonEventHandler.newAccountCreated(Integer.parseInt(clientIdTextField.getText()), Integer.parseInt(accountIdTextField.getText()),
							accountType);
				}

			}
		});
	}
}
