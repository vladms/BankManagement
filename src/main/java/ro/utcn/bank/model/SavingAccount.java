package ro.utcn.bank.model;

public class SavingAccount extends Account{

	public SavingAccount(int accId, double money) {
		super(accId, money, "Saving Account");
	}
	
	public void addMoney(double sum){
		if (sum > 0){
			money += sum + 1/100 * sum;
		} else {
			money += sum + 2/100 * sum;
		}
		setChanged();
		notifyObservers(sum);
	}
}
