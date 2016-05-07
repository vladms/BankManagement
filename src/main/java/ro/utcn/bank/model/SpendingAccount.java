package ro.utcn.bank.model;

public class SpendingAccount extends Account{

	public SpendingAccount(int accId, double money) {
		super(accId, money, "Spending Account");
		
	}
	
	public void addMoney(double sum){
		money += sum;
		setChanged();
		notifyObservers(sum);
	}
}
