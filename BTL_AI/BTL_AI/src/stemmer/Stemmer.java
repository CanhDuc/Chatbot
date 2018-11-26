package stemmer;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;

public class Stemmer {
	private static Stemmer stemmer;

	private Stemmer() {
	}

	public static synchronized Stemmer getInstance() {
		if (stemmer == null)
			stemmer = new Stemmer();
		return stemmer;
	}
	
	public String stemming(String word) {
		if (word == null || word.length() == 0) {
			return word;
		}
		
		String lowwerWord = word.toLowerCase();
		SnowballStemmer snowballStemmer = new englishStemmer();
        snowballStemmer.setCurrent(lowwerWord);
        snowballStemmer.stem();
        String result = snowballStemmer.getCurrent(); 
        
        return result;
	}

}
