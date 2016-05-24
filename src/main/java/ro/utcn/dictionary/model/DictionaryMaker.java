package ro.utcn.dictionary.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DictionaryMaker {
	private Dictionary dictionary;

	public DictionaryMaker() {
		dictionary = new Dictionary();
	}

	public ArrayList<String> getWordsFromDictionary() {
		return dictionary.getWordsFromDictionary();
	}

	public ArrayList<String> getSynonimsFromDictionary() {
		return dictionary.getSynonimsFromDictionary();
	}

	public void addSynonimForWord(String word, String synonim) {
		dictionary.addSynonimForWord(word, synonim);
	}

	public void deleteWord(String word) {
		dictionary.deleteWord(word);
	}
	
	public void setNumberOfWords(int numberOfWords){
		dictionary.setNumberOfWords(numberOfWords);
	}
	
	public Map<String, Set<String>> getDictionary() {
		return dictionary.getDictionary();
	}
	
	public Set<String> getSynonimsForWord(String word){
		return dictionary.getSynonimsForWord(word);
	}
	
	public void setDictionaryObject(Dictionary dictionary){
		this.dictionary = dictionary;
	}
	
	public Dictionary getDictionaryObject(){
		return dictionary;
	}
}
