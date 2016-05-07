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

import ro.utcn.bank.model.Account;
import ro.utcn.bank.model.Person;

public class MainScreen {
	private JFrame mainFrame;
	private JPanel controlPanel;

	private JButton insertClient;
	private JButton deleteClient;
	private JButton showClients;

	private JButton insertAccount;
	private JButton deleteAccount;
	private JButton showAccounts;

	private JButton retreatMoney;
	private JButton addMoney;
	
	private AppInterfaceButtonEvents buttonEventsHandler;
	CreateClientScreen createClientScreen;
	CreateAccountScreen createAccountScreen;

	DeleteClientScreen deleteClientScreen;
	DeleteAccountScreen deleteAccountScreen;

	RetreatMoneyScreen retreatMoneyScreen;
	DepositMoneyScreen depositMoneyScreen;

	
	private ArrayList<Person> clientsList;
	private ArrayList<Account> accountsList;


	private TableScreen tableScreen;

	public MainScreen(AppInterfaceButtonEvents buttonEventsHandler) {
		clientsList = new ArrayList<Person>();
		accountsList = new ArrayList<Account>();
		this.buttonEventsHandler = buttonEventsHandler;
		this.prepareGUI();
		this.placeButtons();
	}

	public void prepareGUI() {
		mainFrame = new JFrame("BANK");
		mainFrame.setSize(1200, 1200);
		mainFrame.setLayout(new GridLayout(1, 1));
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				buttonEventsHandler.saveEntries();
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
		createClientScreen = new CreateClientScreen(buttonEventsHandler);
		createAccountScreen = new CreateAccountScreen(buttonEventsHandler);

		deleteClientScreen = new DeleteClientScreen(buttonEventsHandler);
		deleteAccountScreen = new DeleteAccountScreen(buttonEventsHandler);
		
		retreatMoneyScreen = new RetreatMoneyScreen(buttonEventsHandler);
		depositMoneyScreen = new DepositMoneyScreen(buttonEventsHandler);

		
		insertClient = new JButton("Add client");
		deleteClient = new JButton("Delete client");
		showClients = new JButton("View clients");
		tableScreen = new TableScreen(buttonEventsHandler);
		

		insertAccount = new JButton("Add account");
		deleteAccount = new JButton("Delete account");
		showAccounts = new JButton("View accounts");

		retreatMoney = new JButton("Retreat money");
		addMoney = new JButton("Deposit money");
		
		controlPanel.add(insertClient);
		controlPanel.add(deleteClient);
		controlPanel.add(showClients);
		controlPanel.add(insertAccount);
		controlPanel.add(deleteAccount);
		controlPanel.add(showAccounts);
		controlPanel.add(retreatMoney);
		controlPanel.add(addMoney);

		insertClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableButtons(false);
				createClientScreen.prepareComponents();
			}
		});
		deleteClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableButtons(false);
				deleteClientScreen.prepareComponents();

			}
		});
		showClients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableButtons(false);
				tableScreen.prepareComponents(clientsList);
				tableScreen.mainFrame.setVisible(true);
			}
		});
		insertAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableButtons(false);
				createAccountScreen.prepareComponents();

			}
		});
		deleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableButtons(false);
				deleteAccountScreen.prepareComponents();
			}
		});
		showAccounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableButtons(false);
				tableScreen.prepareComponents(accountsList);
				tableScreen.mainFrame.setVisible(true);
			}
		});

		retreatMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableButtons(false);
				retreatMoneyScreen.prepareComponents();
			}
		});
		
		addMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableButtons(false);
				depositMoneyScreen.prepareComponents();
			}
		});
		mainFrame.setVisible(true);
	}


	public void enableButtons(boolean enable) {
		insertClient.setEnabled(enable);
		deleteClient.setEnabled(enable);
		showClients.setEnabled(enable);

		insertAccount.setEnabled(enable);
		deleteAccount.setEnabled(enable);
		showAccounts.setEnabled(enable);

		retreatMoney.setEnabled(enable);
	}
	
	public void showMessage(String message){
		JOptionPane.showMessageDialog(mainFrame, message);
	}

	public void setClientsList(ArrayList<Person> clientsList){
		this.clientsList = clientsList;
	}
	
	public void setAccountsList(ArrayList<Account> accountsList){
		this.accountsList = accountsList;
	}
	
}
