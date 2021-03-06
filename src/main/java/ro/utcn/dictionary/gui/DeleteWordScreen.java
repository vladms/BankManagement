package ro.utcn.dictionary.gui;

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

public class DeleteWordScreen {
	private JFrame mainFrame;
	private JPanel controlPanel;

	private JTextField wordTextField;

	private JButton cancelButton;
	private JButton deleteButton;

	private AppInterfaceButtonEvents buttonEventHandler;

	public DeleteWordScreen(AppInterfaceButtonEvents buttonEventsHandler) {
		this.buttonEventHandler = buttonEventsHandler;

	}

	public void prepareComponents() {
		prepareGUI();
		prepareWordSelection();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Delete word from dictionary");
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

	private void prepareWordSelection() {
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.setVisible(false);
				mainFrame.remove(controlPanel);
				buttonEventHandler.canceled();
			}
		});

		deleteButton = new JButton("Delete word");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (wordTextField.getText().length() <= 0 || wordTextField.getText().contains(" ")) {
					JOptionPane.showMessageDialog(mainFrame, "Word is not in the good format!");
				} else {
					mainFrame.setVisible(false);
					mainFrame.remove(controlPanel);
					buttonEventHandler.wordDeleted(wordTextField.getText());
					
				}
			}
		});

		wordTextField = new JTextField("Enter word you want to be deleted");

		controlPanel.add(wordTextField);
		controlPanel.add(cancelButton);
		controlPanel.add(deleteButton);
	}

}
