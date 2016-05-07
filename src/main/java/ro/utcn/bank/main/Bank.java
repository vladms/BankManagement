package ro.utcn.bank.main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import ro.utcn.bank.gui.AppInterfaceButtonEvents;
import ro.utcn.bank.gui.MainScreen;
import ro.utcn.bank.model.Account;
import ro.utcn.bank.model.IBank;
import ro.utcn.bank.model.Person;
import ro.utcn.bank.model.SavingAccount;
import ro.utcn.bank.model.SpendingAccount;

import java.util.Set;

public class Bank implements IBank, AppInterfaceButtonEvents {

	private Map<Person, Set<Account>> entries;

	MainScreen mainScreen;
	private static Bank sBank;
	
	private int numberOfClients;
	private int numberOfAccounts;

	public static void main(String[] args) {
		sBank = Bank.getInstance();
	}

	public static Bank getInstance() {
		if (sBank == null) {
			sBank = new Bank();
		}
		return sBank;
	}

	public Bank() {
		loadEntries();

		mainScreen = new MainScreen(this);
		mainScreen.setAccountsList(getAccountsFromEntries());
		mainScreen.setClientsList(getClientsFromEntries());
		numberOfClients = this.getClientsFromEntries().size();
		numberOfAccounts = this.getAccountsFromEntries().size();
	}

	public void printAllAccounts() {
		Iterator<?> it = entries.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Person person = (Person) pair.getKey();
			System.out.println("Person: " + person.toString());
			Set<Account> accounts = (Set<Account>) pair.getValue();
			for (Account account : accounts) {
				System.out.println("                               Account: " + account.toString());
			}

		}
	}

	public void addAccForPerson(Person person, Account assocAccount) {
		assert person != null : "Person should not be null";
		assert assocAccount != null : "Account should not be null";
		if (!isWellFormed()) {
			mainScreen.showMessage("Bank has been corupted!");
			return;
		}
		int preSizeAccount, postSizeAccount;
		assocAccount.addObserver(person);
		if (entries.containsKey(person)) {
			preSizeAccount = entries.get(person).size();
			entries.get(person).add(assocAccount);
			postSizeAccount = entries.get(person).size();
		} else {
			preSizeAccount = 0;
			HashSet<Account> hashSetAccounts = new HashSet<Account>();
			hashSetAccounts.add(assocAccount);
			entries.put(person, hashSetAccounts);
			postSizeAccount = entries.get(person).size();
		}
		numberOfAccounts = this.getAccountsFromEntries().size();
		if (!isWellFormed()) {
			mainScreen.showMessage("Bank has been corupted!");
			return;
		}
		assert preSizeAccount + 1 == postSizeAccount : "preSize and postSize of account are not good";
	}

	public void addPersonIntoBank(Person person) {
		assert person != null : "Person should not be null";
		if (!isWellFormed()) {
			mainScreen.showMessage("Bank has been corupted1!");
			return;
		}
		int preSizeEntries, postSizeEntries;
		if (entries.containsKey(person)) {
			preSizeEntries = entries.size();
			postSizeEntries = entries.size();
		} else {
			preSizeEntries = entries.size();
			HashSet<Account> hashSetAccounts = new HashSet<Account>();
			entries.put(person, hashSetAccounts);
			postSizeEntries = entries.size();
			numberOfClients++;
		}
		if (!isWellFormed()) {
			mainScreen.showMessage("Bank has been corupted2!");
			return;
		}
		assert preSizeEntries + 1 == postSizeEntries : "preSize and postSize of entries are not good";
	}

	public void depositMoney(double sum, int accId, Person person) {
		assert person != null : "Person should not be null";
		if (!isWellFormed()) {
			mainScreen.showMessage("Bank has been corupted!");
			return;
		}
		double preAccIdSum = 0;
		double postAccIdSum = 0;

		if (entries.containsKey(person)) {
			HashSet<Account> hashSetAccounts = new HashSet<Account>();
			hashSetAccounts = (HashSet<Account>) entries.get(person);
			for (Account account : hashSetAccounts) {
				if (account.getAccId() == accId) {
					preAccIdSum = account.getMoney();
					account.addMoney(sum);
					postAccIdSum = account.getMoney();

				}
			}
		}
		if (!isWellFormed()) {
			mainScreen.showMessage("Bank has been corupted!");
			return;
		}
		if (sum > 0) {
			mainScreen.showMessage("Money added into account!");
		} else {
			mainScreen.showMessage("Money retreated from account!");
		}
		assert preAccIdSum + sum == postAccIdSum : "preAccIdSum and postAccIdSum are not good";
	}

	public void canceled() {
		mainScreen.enableButtons(true);
	}

	public void newClientCreated(String cnp, String name, int age) {

		int cnp_int = Integer.parseInt(cnp);

		Person person = new Person(cnp_int, name, age);

		boolean found = false;
		Iterator<?> it = entries.entrySet().iterator();

		while (it.hasNext() && !found) {
			Map.Entry pair = (Map.Entry) it.next();
			Person person2 = (Person) pair.getKey();
			if (person2.getClientId() == person.getClientId()) {
				found = true;
			}
		}
		if (found) {
			mainScreen.showMessage("This client id already esists!");
		} else {
			mainScreen.showMessage("Client created!");

			this.addPersonIntoBank(person);
		}
		mainScreen.setClientsList(this.getClientsFromEntries());
		mainScreen.enableButtons(true);
	}

	public void newAccountCreated(int clientId, int accountId, String accountType) {
		boolean clientFound = false;
		boolean accountFoundOnUser = false;
		boolean accountAlreadyCreated = false;

		Iterator<?> it = entries.entrySet().iterator();
		Person person = null;
		while (it.hasNext() && !clientFound) {
			Map.Entry pair = (Map.Entry) it.next();
			person = (Person) pair.getKey();
			if (person.getClientId() == clientId) {
				clientFound = true;
				Set<Account> clientAccounts = (Set<Account>) pair.getValue();
				for (Account account : clientAccounts) {
					if (account.getAccId() == accountId) {
						accountFoundOnUser = true;
					}
				}
			}
		}
		Account foundedAccount = null;
		if (clientFound) {
			it = entries.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				Set<Account> clientAccounts = (Set<Account>) pair.getValue();
				for (Account account : clientAccounts) {
					if (account.getAccId() == accountId) {
						accountAlreadyCreated = true;
						foundedAccount = account;
					}

				}
			}
		}
		if (accountFoundOnUser) {
			mainScreen.showMessage("This account id already exists on the user list!");
		} else if (!clientFound) {
			mainScreen.showMessage("This client id do not exists!");
		} else {
			if (accountAlreadyCreated) {
				this.addAccForPerson(person, foundedAccount);
				mainScreen.showMessage(
						"An account with this id already exists. The user has been made an associate to this account!");
			} else {
				Account account;
				if (accountType.equals("SpendingAccount")) {
					account = new SpendingAccount(accountId, 0);
					mainScreen.showMessage("Spending account created!");
				} else {
					account = new SavingAccount(accountId, 0);
					mainScreen.showMessage("Saving account created!");
				}
				this.addAccForPerson(person, account);
				mainScreen.setAccountsList(this.getAccountsFromEntries());
			}
		}
		mainScreen.enableButtons(true);
	}

	public void clientDeleted(int clientId) {
		boolean found = false;
		Iterator<?> it = entries.entrySet().iterator();
		Person person = null;
		while (it.hasNext() && !found) {
			Map.Entry pair = (Map.Entry) it.next();
			person = (Person) pair.getKey();
			if (person.getClientId() == clientId) {
				found = true;
				entries.remove(person);
			}
		}
		if (!found) {
			mainScreen.showMessage("This client id is not valid!");
		} else {
			numberOfClients--;
			mainScreen.showMessage("Client  deleted!");
			mainScreen.setClientsList(this.getClientsFromEntries());

		}
		mainScreen.enableButtons(true);
	}

	public void accountDeleted(int accountId, int clientId) {
		boolean accountFound = false;
		boolean clientFound = false;

		Iterator<?> it = entries.entrySet().iterator();
		Person person = null;
		while (it.hasNext() && !accountFound) {
			Map.Entry pair = (Map.Entry) it.next();
			person = (Person) pair.getKey();
			if (person.getClientId() == clientId) {
				clientFound = true;
				Set<Account> clientAccounts = (Set<Account>) pair.getValue();
				for (Account account : clientAccounts) {
					if (account.getAccId() == accountId) {
						accountFound = true;
						entries.get(person).remove(account);
					}
				}
			}
		}
		if (!clientFound) {
			mainScreen.showMessage("This client id is not valid!");
		} else if (!accountFound) {
			mainScreen.showMessage("This account id is not valid!");
		} else {
			mainScreen.showMessage("Account associated with the client deleted!");
			mainScreen.setAccountsList(this.getAccountsFromEntries());
		}
		mainScreen.enableButtons(true);
	}

	public void depositMoney(int accountId, int clientId, double sum) {
		boolean accountFound = false;
		boolean clientFound = false;

		Iterator<?> it = entries.entrySet().iterator();
		Person person = null;
		while (it.hasNext() && !accountFound) {
			Map.Entry pair = (Map.Entry) it.next();
			person = (Person) pair.getKey();
			if (person.getClientId() == clientId) {
				clientFound = true;
				Set<Account> clientAccounts = (Set<Account>) pair.getValue();
				for (Account account : clientAccounts) {
					if (account.getAccId() == accountId) {
						accountFound = true;
					}
				}
			}
		}
		if (!clientFound) {
			mainScreen.showMessage("This client id is not valid!");
		} else if (!accountFound) {
			mainScreen.showMessage("This account id is not valid!");
		} else {
			depositMoney(sum, accountId, person);
			mainScreen.setAccountsList(this.getAccountsFromEntries());
		}
		mainScreen.enableButtons(true);
	}

	public void retreatMoney(int accountId, int clientId, double sum) {
		boolean accountFound = false;
		boolean clientFound = false;

		Iterator<?> it = entries.entrySet().iterator();
		Person person = null;
		while (it.hasNext() && !accountFound) {
			Map.Entry pair = (Map.Entry) it.next();
			person = (Person) pair.getKey();
			if (person.getClientId() == clientId) {
				clientFound = true;
				Set<Account> clientAccounts = (Set<Account>) pair.getValue();
				for (Account account : clientAccounts) {
					if (account.getAccId() == accountId) {
						accountFound = true;
					}
				}
			}
		}
		if (!clientFound) {
			mainScreen.showMessage("This client id is not valid!");
		} else if (!accountFound) {
			mainScreen.showMessage("This account id is not valid!");
		} else {
			depositMoney(-sum, accountId, person);
			mainScreen.setAccountsList(this.getAccountsFromEntries());
		}
		mainScreen.enableButtons(true);
	}

	private ArrayList<Person> getClientsFromEntries() {
		ArrayList<Person> clientsList = new ArrayList<Person>();
		Iterator<?> it = entries.entrySet().iterator();
		Person person = null;
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			person = (Person) pair.getKey();
			clientsList.add(person);
		}
		return clientsList;
	}

	private ArrayList<Account> getAccountsFromEntries() {
		ArrayList<Account> accountsList = new ArrayList<Account>();
		Iterator<?> it = entries.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Set<Account> clientAccounts = (Set<Account>) pair.getValue();
			for (Account account : clientAccounts) {
				if (!accountsList.contains(account)) {
					accountsList.add(account);
				}
			}
		}
		return accountsList;
	}

	public void saveEntries() {
		try {
			FileOutputStream fileOut = new FileOutputStream("/tmp/entries.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(entries);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in /tmp/entries.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
		this.printAllAccounts();
	}

	public void loadEntries() {
		entries = new HashMap<Person, Set<Account>>();
		try {
			FileInputStream fileIn = new FileInputStream("/tmp/entries.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			entries = ((Map<Person, Set<Account>>)in.readObject());
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Employee class not found");
			c.printStackTrace();
			return;
		}
		this.printAllAccounts();
	}

	private boolean isWellFormed(){
		boolean result = true;
		if (numberOfClients != this.getClientsFromEntries().size()){
			result = false;
		}
		if (numberOfAccounts != this.getAccountsFromEntries().size()){
			result = false;
		}
		return result;
	}
}
