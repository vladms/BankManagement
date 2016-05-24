package ro.utcn.dictionary.main;

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

import ro.utcn.dictionary.model.AbstractDictionary;
import ro.utcn.dictionary.model.Dictionary;
import ro.utcn.dictionary.model.DictionaryMaker;
import ro.utcn.dictionary.utils.GSONUtils;
import ro.utcn.dictionary.gui.AppInterfaceButtonEvents;
import ro.utcn.dictionary.gui.MainScreen;
import ro.utcn.dictionary.gui.TableScreen;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictionaryMain implements AppInterfaceButtonEvents {

	MainScreen mainScreen;
	private static DictionaryMain sBank;

	private DictionaryMaker dictionaryMaker;

	private TableScreen tableScreen;

	public static void main(String[] args) {
		sBank = DictionaryMain.getInstance();
	}

	public static DictionaryMain getInstance() {
		if (sBank == null) {
			sBank = new DictionaryMain();
		}
		return sBank;
	}

	public DictionaryMain() {
		mainScreen = new MainScreen(this);
		tableScreen = new TableScreen(this);
		dictionaryMaker = new DictionaryMaker();
		AbstractDictionary abstractDictionary = GSONUtils.loadDictionaryFromFile("");
		if (abstractDictionary.isNil()){
			mainScreen.showMessage("Dictionary is Empty!");
		} else {
			dictionaryMaker.setDictionaryObject((Dictionary)abstractDictionary);
		}
		dictionaryMaker.setNumberOfWords(dictionaryMaker.getWordsFromDictionary().size());
	}

	public void saveDictionary() {
		try {
			GSONUtils.saveDictionaryToFile(dictionaryMaker, "json");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void newSynonimInserted(String word, String synonim) {
		dictionaryMaker.addSynonimForWord(word, synonim);
		mainScreen.enableButtons(true);
	}

	public void wordDeleted(String word) {
		if (dictionaryMaker.getDictionary().containsKey(word)) {
			dictionaryMaker.deleteWord(word);
		} else {
			mainScreen.showMessage(word + " is not in the dicitonary!");
		}
		mainScreen.enableButtons(true);
	}

	public void showSynonims(String word) {
		Set<String> synonims = dictionaryMaker.getSynonimsForWord(word);
		ArrayList<String> synonimsArray = new ArrayList<String>();
		if (synonims != null) {
			Iterator<String> iterator = synonims.iterator();

			while (iterator.hasNext()) {
				synonimsArray.add((String) iterator.next());
			}
			tableScreen.prepareComponents(synonimsArray);
			tableScreen.mainFrame.setVisible(true);
		} else {
			mainScreen.showMessage(word + " is not in the dicitonary!");
			mainScreen.enableButtons(true);
		}
	}

	public void checkForWellFormedDictionary() {

	}

	public void canceled() {
		mainScreen.enableButtons(true);
	}

	private boolean isWellFormed() {
		return true;
	}

	public void searchForWord(String query) {
		ArrayList<String> result = new ArrayList<String>();
		String regex = "";
		if(query.contains("?")){
			String[] parts = query.split("\\?");
			int numberOfParts = parts.length;
			if(numberOfParts>0){
				regex = regex.concat(parts[0]);
				for(int i=1; i<numberOfParts; i++){
					regex = regex.concat("[a-zA-Z]");
					regex = regex.concat(parts[i]);
				}
				
				if(query.endsWith("?")){
					regex = regex.concat("[a-zA-Z]");
				}
				
				ArrayList<String> entries = dictionaryMaker.getWordsFromDictionary();

				for(String s : entries){
					if(s.matches(regex)){
						result.add(s);
					}
				}
			} else {
				if(query.endsWith("?")){
					regex = regex.concat("[a-zA-Z]");
				}
				
				ArrayList<String> entries = dictionaryMaker.getWordsFromDictionary();

				for(String s : entries){
					if(s.matches(regex)){
						result.add(s);
					}
				}
				
			}
		} else if(query.contains("*")){
			String[] parts = query.split("\\*");
			int numberOfParts = parts.length;
			if(numberOfParts > 0){
				regex = regex.concat(parts[0]);
				for(int i=1; i<numberOfParts; i++){
					regex = regex.concat(".*");
					regex = regex.concat(parts[i]);
				}
				
				if(query.endsWith("*")){
					regex = regex.concat(".*");
				}
				
				ArrayList<String> entries = dictionaryMaker.getWordsFromDictionary();

				for(String s : entries){
					if(s.matches(regex)){
						
						result.add(s);
					}
				}
			} else {
				if(query.endsWith("*")){
					regex = regex.concat(".*");
				}
				ArrayList<String> entries = dictionaryMaker.getWordsFromDictionary();

				for(String s : entries){
					if(s.matches(regex)){
						
						result.add(s);
					}
				}
			}
		} else {
			ArrayList<String> entries = dictionaryMaker.getWordsFromDictionary();
			for(String s : entries){ 
				if(s.equalsIgnoreCase(query)){
					result.add(s);
				}
			}
		}
		tableScreen.prepareComponents(result);
		tableScreen.mainFrame.setVisible(true);		
	}
}
