package ro.utcn.bank.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DeleteAccountScreen {
	private JFrame mainFrame;
	private JPanel controlPanel;

	private JTextField accountIdTextField;
	private JTextField clientIdTextField;

	private JButton cancelButton;
	private JButton deleteButton;

	private AppInterfaceButtonEvents buttonEventHandler;

	public DeleteAccountScreen(AppInterfaceButtonEvents buttonEventsHandler) {
		this.buttonEventHandler = buttonEventsHandler;

	}

	public void prepareComponents() {
		prepareGUI();
		prepareClientSelection();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Delete client");
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

	private void prepareClientSelection() {
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.setVisible(false);
				mainFrame.remove(controlPanel);
				buttonEventHandler.canceled();
			}
		});

		deleteButton = new JButton("Delete client");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (!(accountIdTextField.getText().matches("[0-9]+") && accountIdTextField.getText().length() > 0)
						|| (Integer.parseInt(accountIdTextField.getText())) < 0) {
					JOptionPane.showMessageDialog(mainFrame, "Account id is not in the good format!");
				} else if (!(clientIdTextField.getText().matches("[0-9]+") && clientIdTextField.getText().length() > 0)
						|| (Integer.parseInt(clientIdTextField.getText())) < 0) {
					JOptionPane.showMessageDialog(mainFrame, "User id is not in the good format!");
				} else {
					mainFrame.setVisible(false);
					mainFrame.remove(controlPanel);
					buttonEventHandler.accountDeleted(Integer.parseInt(accountIdTextField.getText()), Integer.parseInt(clientIdTextField.getText()));
				}
			}
		});

		accountIdTextField = new JTextField("Enter account id");
		clientIdTextField = new JTextField("Enter client id");
		
		controlPanel.add(clientIdTextField);
		controlPanel.add(accountIdTextField);
		controlPanel.add(cancelButton);

		controlPanel.add(deleteButton);
	}
}
