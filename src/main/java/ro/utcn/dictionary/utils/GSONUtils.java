package ro.utcn.dictionary.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.omg.CORBA.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ro.utcn.dictionary.model.AbstractDictionary;
import ro.utcn.dictionary.model.Dictionary;
import ro.utcn.dictionary.model.DictionaryMaker;
import ro.utcn.dictionary.model.NullDictionary;

public class GSONUtils {
	public static void saveDictionaryToFile(DictionaryMaker dictionaryMaker, String type) throws IOException {
		Gson gson = new GsonBuilder().create();
		System.out.println("Save: " + dictionaryMaker.getWordsFromDictionary().size());
		try {
			Writer writer = new FileWriter("Dictionary.json");
			gson.toJson(dictionaryMaker.getDictionaryObject(), writer);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static AbstractDictionary loadDictionaryFromFile(String type) {
		Gson gson = new GsonBuilder().create();
		Dictionary dictionary = new Dictionary();

		try {
			Reader myReader = new FileReader("Dictionary.json");
			dictionary = gson.fromJson(myReader, Dictionary.class);
			myReader.close();

		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (dictionary.getWordsFromDictionary().size() == 0) {
			return new NullDictionary();
		} else {
			
		}
		
		return dictionary;
	}
}
