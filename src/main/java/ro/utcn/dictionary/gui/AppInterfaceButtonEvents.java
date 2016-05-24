package ro.utcn.dictionary.gui;

import java.util.ArrayList;


public interface AppInterfaceButtonEvents {
	public void newSynonimInserted(String word, String synonim);
	public void wordDeleted(String word);
	public void showSynonims(String word);
	public void checkForWellFormedDictionary();
	public void searchForWord(String word);
	
	public void canceled();

	public void saveDictionary();


}
