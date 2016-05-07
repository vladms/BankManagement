package ro.utcn.bank.gui;

import java.util.ArrayList;


public interface AppInterfaceButtonEvents {
	public void newClientCreated(String cnp, String name, int age);
	public void newAccountCreated(int clientId, int accountId, String accountType);
	
	public void clientDeleted(int clientId);	
	public void accountDeleted(int accountId, int clientId);

	public void retreatMoney(int accountId, int clientId, double sum);
	public void depositMoney(int accountId, int clientId, double sum);

	public void canceled();
	
	public void saveEntries();


}
