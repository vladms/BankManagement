package ro.utcn.bank.model;

import java.util.Observable;

public abstract class Account extends Observable implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int accId;
	protected double money;
	protected String accountType;

	public Account(int accId, double money, String accountType) {
		this.accId = accId;
		this.money = money;
		this.accountType = accountType;
	}

	public abstract void addMoney(double sum);

	@Override
	public boolean equals(Object obj) {
		if ((obj instanceof Account) && (((Account) obj).getAccId() == this.accId)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int sum = 0;
		int aux = this.accId;
		while (aux > 0) {
			sum += aux % 10;
			aux = aux / 10;
		}
		return sum;
	}

	@Override
	public String toString() {
		return "Account: " + accId + " money: " + money + " accountType: " + accountType + "\n";
	}

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}
}
