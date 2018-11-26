package controller;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import helper.JSONHelper;
import model.Classification;
import stemmer.Stemmer;
import tokenize.Tokenize;;

public class ClassificationController {
	private static ClassificationController stemmer;

	private Map<String, Integer> corpusWord;
	private ArrayList<Classification> classifiArr;

	private ClassificationController() {
		corpusWord = new HashMap<String, Integer>();
		classifiArr = getAllClassFromFile();
	}

	public static synchronized ClassificationController getInstance() {
		if (stemmer == null) {
			stemmer = new ClassificationController();
		}

		return stemmer;
	}

	private ArrayList<Classification> getAllClassFromFile() {
		JSONParser parser = new JSONParser();
		ArrayList<Classification> classificationArr = new ArrayList<Classification>();
		try {
			Object obj = parser.parse(new FileReader("resource/intent/intent.xml"));
			JSONArray intenArray = (JSONArray) obj;

			for (int i = 0; i < intenArray.size(); i++) {
				Object intentObj = intenArray.get(i);
				JSONObject intentJSONObj = (JSONObject) intentObj;
				String className = (String) intentJSONObj.get("class");
				JSONArray sentencesJSON = (JSONArray) intentJSONObj.get("sentences");
				String sentences[] = JSONHelper.getStringArray(sentencesJSON);
				JSONArray responseJSON = (JSONArray) intentJSONObj.get("responses");
				String responses[] = JSONHelper.getStringArray(responseJSON);
				Classification classifi = new Classification(className, sentences, responses);
				classificationArr.add(classifi);

				// add to corpus words
				for (String sen : sentences) {
					String[] words = Tokenize.getInstance().tokenize(sen);
					for (String w : words) {
						String stemmingWord = Stemmer.getInstance().stemming(w.toLowerCase());
						List<String> avoidWords = Arrays.asList("?", "'s");
						if (!avoidWords.contains(stemmingWord)) {
							if (!corpusWord.containsKey(stemmingWord)) {
								corpusWord.put(stemmingWord, 1);
							} else {
								corpusWord.put(stemmingWord, corpusWord.get(stemmingWord) + 1);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return classificationArr;
	}

	public String getResponseFrom(String sentence) {
		double maxScore = 0;
		Classification c = null;

		for (Classification classifi : classifiArr) {
			double score = cacualatorClassScore(sentence, classifi);
			System.out.println(classifi.getName() + "~>" + score);
			if (maxScore < score)
			{
				maxScore = score;
				c = classifi;
			}
		}
		
		System.out.println(c.getName());

		if (c != null) {
			String[] responses = c.getResponses();
			if (responses != null) {
				Random r = new Random();
				int rIndex = r.nextInt(responses.length);
				return responses[rIndex];
			}

		}

		return "I don't know";
	}

	private double cacualatorClassScore(String sentence, Classification classifi) {
		if (sentence == null) {
			return 0;
		}
		if (classifi == null) {
			System.out.println("Lớp này ko tồn tại");
			return 0;
		}

		double score = 0;
		for (String w : Tokenize.getInstance().tokenize(sentence)) {
			String stemmerW = Stemmer.getInstance().stemming(w);
			List<String> avoidWords = Arrays.asList("?", "'s");
			if (!avoidWords.contains(stemmerW)) {
				if (classifi.getSentencesWords().contains(stemmerW)) {
					score += 1.0 / this.corpusWord.get(stemmerW);
				}
			}
		}

		return score;
	}

}
