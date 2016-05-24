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

public class MainScreen {
	private JFrame mainFrame;
	private JPanel controlPanel;

	private JButton insertSynonim;
	private JButton deleteWord;
	private JButton showSynonims;

	private JButton searchWord;

	private AppInterfaceButtonEvents buttonEventsHandler;

	InsertSynonimScreen insertSynonimScreen;
	DeleteWordScreen deleteWordScreen;

	ShowSynonimsScreen showSynonimsScreen;
	SearchWordScreen searchWordScreen;
	private TableScreen tableScreen;

	public MainScreen(AppInterfaceButtonEvents buttonEventsHandler) {
		this.buttonEventsHandler = buttonEventsHandler;
		this.prepareGUI();
		this.placeButtons();
	}

	public void prepareGUI() {
		mainFrame = new JFrame("DICTIONARY");
		mainFrame.setSize(1200, 1200);
		mainFrame.setLayout(new GridLayout(1, 1));
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				buttonEventsHandler.saveDictionary();
				System.exit(0);
			}
		});

		controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(8, 1));

		mainFrame.add(controlPanel);

		mainFrame.setVisible(true);
	}

	/*
	 * placeButtons(): Add buttons on the controlPanel
	 */
	private void placeButtons() {

		insertSynonimScreen = new InsertSynonimScreen(buttonEventsHandler);
		deleteWordScreen = new DeleteWordScreen(buttonEventsHandler);
		showSynonimsScreen = new ShowSynonimsScreen(buttonEventsHandler);
		searchWordScreen = new SearchWordScreen(buttonEventsHandler);

		insertSynonim = new JButton("Add synonim");
		deleteWord = new JButton("Delete word");
		showSynonims = new JButton("Show synonims");
		searchWord = new JButton("Search for word");

		tableScreen = new TableScreen(buttonEventsHandler);

		controlPanel.add(insertSynonim);
		controlPanel.add(deleteWord);
		controlPanel.add(showSynonims);
		controlPanel.add(searchWord);

		insertSynonim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableButtons(false);
				insertSynonimScreen.prepareComponents();
			}
		});
		deleteWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableButtons(false);
				deleteWordScreen.prepareComponents();

			}
		});
		showSynonims.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableButtons(false);
				showSynonimsScreen.prepareComponents();
			}
		});
		searchWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableButtons(false);
				searchWordScreen.prepareComponents();
			}
		});

		mainFrame.setVisible(true);
	}

	public void enableButtons(boolean enable) {
		insertSynonim.setEnabled(enable);
		deleteWord.setEnabled(enable);
		showSynonims.setEnabled(enable);

		searchWord.setEnabled(enable);
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(mainFrame, message);
	}
}
