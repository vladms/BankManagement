package ro.utcn.dictionary.model;

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

import ro.utcn.dictionary.gui.AppInterfaceButtonEvents;
import ro.utcn.dictionary.gui.MainScreen;
import ro.utcn.dictionary.utils.GSONUtils;

import java.util.Set;

public class Dictionary extends AbstractDictionary implements DictionaryInterface{
	private Map<String, Set<String>> dictionary;
	private int numberOfWords;

	public Dictionary() {
		dictionary = new HashMap<String, Set<String>>();
		numberOfWords = 0; 
	}

	public ArrayList<String> getWordsFromDictionary() {
		ArrayList<String> wordsList = new ArrayList<String>();
		Iterator<?> it = dictionary.entrySet().iterator();
		String word = null;
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			word = (String) pair.getKey();
			wordsList.add(word);
		}
		return wordsList;
	}

	public ArrayList<String> getSynonimsFromDictionary() {
		Iterator<?> it = dictionary.entrySet().iterator();
		ArrayList<String> synonymsList = new ArrayList<String>();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			synonymsList = (ArrayList<String>) pair.getValue();
			for (String synonim : synonymsList) {
				if (!synonymsList.contains(synonim)) {
					synonymsList.add(synonim);
				}
			}
		}
		return synonymsList;
	}

	public void addSynonimForWord(String word, String synonim){
		assert(word != null && synonim != null);
		assert(!word.equals("") && !synonim.equals(""));
		assert(isWellFormed());
		
		int numberOfWordsPre = numberOfWords;
		if (dictionary.containsKey(word)){
			dictionary.get(word).add(synonim);
		} else {
			HashSet<String> hashSetSynonims = new HashSet<String>();
			hashSetSynonims.add(synonim);
			dictionary.put(word, hashSetSynonims);
			numberOfWords++;
		}
		
		int numberOfWordsPost = numberOfWords;
		assert(numberOfWordsPost == numberOfWordsPre + 1);
		assert(isWellFormed());
	}
	
	public void deleteWord(String word){
		assert(word != null && !word.equals(""));
		assert(isWellFormed());
		int numberOfWordsPre = numberOfWords;

		dictionary.remove(word);
		numberOfWords--;
		
		int numberOfWordsPost = numberOfWords;
		assert(numberOfWordsPost == numberOfWordsPre - 1);
		assert(isWellFormed());
	}

	public Map<String, Set<String>> getDictionary() {
		return dictionary;
	}
	
	public Set<String> getSynonimsForWord(String word){
		return dictionary.get(word);
	}
	
	public boolean isWellFormed(){
		if (numberOfWords == getWordsFromDictionary().size()){
			return true;
		}
		return false;
	}
	
	public void setNumberOfWords(int numberOfWords){
		this.numberOfWords = numberOfWords;
	}

	@Override
	public boolean isNil() {
		return false;
	}
	
}
