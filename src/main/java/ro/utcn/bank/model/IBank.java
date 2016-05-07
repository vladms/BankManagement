package ro.utcn.bank.model;

public interface IBank {
	/**
	 * @preconditions person != null account != null
	 * @postconditions preSizeAccount +1 == postSizeAccount
	 * @param person
	 * @param assocAccount
	 */
	public void addAccForPerson(Person person, Account assocAccount);
	
	/**
	 * @preconditions sum >= 0 valid accId person != null
	 * @postconditions postAccIdSum == preAccIdSum + sum 
	 * @param sum
	 * @param accId
	 * @param person
	 */
	public void depositMoney(double sum, int accId, Person person);
	
	/**
	 * @preconditions person != null 
	 * @postconditions preSizeEntries +1 == postSizeEntries
	 * @param person
	 */
	public void addPersonIntoBank(Person person);

}
