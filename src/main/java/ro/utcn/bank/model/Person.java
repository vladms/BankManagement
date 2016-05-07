package ro.utcn.bank.model;

import java.util.Observable;
import java.util.Observer;

public class Person implements Observer, java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int age;
	private int personId;

	public Person(int personId, String name, int age){
		this.personId = personId;
		this.name = name;
		this.age = age;
	}
	@Override 
	public boolean equals(Object obj){
		if (!(obj instanceof Person)){
			return false;
		}
		Person objectPerson = (Person) obj;
		if (objectPerson.getClientId() == this.getClientId()){
			return true;
		}
		return false;
	}
	
	@Override 
	public int hashCode(){		
		return personId;
	}
	
	@Override 
	public String toString(){
		return "Person: " + this.personId + " name: " + this.name + " age: " + this.age + "\n";
	}
	
	public void update(Observable o, Object arg) {
		Account account = (Account)o;
		Double sum = (Double)arg;
		if (sum > 0){
			System.out.println("[Observer] Client: " + this.name + " account: " + account.getAccId() + " deposited money: " + sum + "\n");
		} else {
			System.out.println("[Observer] Client: " + this.name + " account: " + account.getAccId() + " retreated money: " + sum + "\n");
		}
	}
	
	public int getClientId() {
		return personId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
