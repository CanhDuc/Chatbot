package tokenize;

import opennlp.tools.tokenize.SimpleTokenizer;

public class Tokenize {
	private static Tokenize stemmer;

	private Tokenize() {
	}

	public static synchronized Tokenize getInstance() {
		if (stemmer == null)
			stemmer = new Tokenize();
		return stemmer;
	}
	
	public String[] tokenize(String sentences) {
		SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
		return tokenizer.tokenize(sentences);
	}

}
