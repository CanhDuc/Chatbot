package model;

import java.util.ArrayList;

import stemmer.Stemmer;
import tokenize.Tokenize;

public class Classification {
	private String name;
	private String sentences[];
	private String responses[];

	public Classification(String name, String[] sentences, String[] responses) {
		this.name = name;
		this.sentences = sentences;
		this.responses = responses;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getSentences() {
		return sentences;
	}

	public void setSentences(String[] sentences) {
		this.sentences = sentences;
	}

	public String[] getResponses() {
		return responses;
	}

	public void setResponses(String[] responses) {
		this.responses = responses;
	}
	
	public ArrayList<String> getSentencesWords() {
		ArrayList<String> words = new ArrayList<String>();
		for (String senetence : sentences) {
			String[] sentenWords = Tokenize.getInstance().tokenize(senetence);
			for (String w : sentenWords) {
				words.add(Stemmer.getInstance().stemming(w.toLowerCase()));
			}
		}
		
		return words;
	}

}
