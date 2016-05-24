package ro.utcn.dictionary.model;

public interface DictionaryInterface {
	/**
	 * @precondition word != null, word != "", synonim != null, synonim != "",
	 *               isWellFormed()
	 * @postcondition numberOfWordsPost = numberOfWordsPre + 1
	 * @param word
	 * @param synonim
	 */
	public void addSynonimForWord(String word, String synonim);

	/**
	 * @precondition word != null, word != "", isWellFormed()
	 * @postcondition numberOfWordsPost = numberOfWordsPre - 1, isWellFormed()
	 * @param word
	 */
	public void deleteWord(String word);
}
