package ro.utcn.bank.test;

import org.junit.Before;
import org.junit.Test;

import ro.utcn.bank.main.Bank;
import ro.utcn.bank.model.Account;
import ro.utcn.bank.model.Person;
import ro.utcn.bank.model.SavingAccount;
import ro.utcn.bank.model.SpendingAccount;

public class TestBank {
	private Account account1, account2;
	private Person person1, person2, person3;
	private static Bank sBank;
	
	@Before
	public void setUp() {

		
		
	}
	
	@Test
	public void testConnectionAccountPerson() {
		sBank = Bank.getInstance();
		person1 = new Person(1, "Vlad Bonta", 20);
		person2 = new Person(2, "Michael Bortan", 43);
		person3 = new Person(3, "Mihaela Barita", 101);
		account1 = new SavingAccount(1, 0);
		account2 = new SpendingAccount(2, 0);
		
		sBank.addAccForPerson(person1, account1);
		sBank.addAccForPerson(person1, account2);
		sBank.addAccForPerson(person2, account2);

		sBank.depositMoney(111, 1, person1);
		sBank.addPersonIntoBank(person3);
		sBank.printAllAccounts();
	}

}
