package com.mls.users.builder;

import java.util.*;

/**
 * Suggestion Builder class
 * </p>
 * Task definition:
 * Method "buildSuggestionsFromTokenFromString" derives a list of suggestions from the given token stream. The given list of tokens reflect
 * a sorted list of tokens of a text. Each token reflects either a single word or a punctuation mark like :.?
 * A suggestion is either a single word or a combination of following words (delimited by a single space)
 * and does not include any stopWord or a single character. Combined word suggestions
 * can maximal include MAX_COMBINED_TOKENS of following words. Should iterate over tokens and create all possible combinations of tokens considering
 * As a example, let's consider data mentioned above,
 * so tokens are "The", "beautiful", "girl", "from", "the", "farmers", "market", ".", "I", "like", "chewing", "gum", ".",
 * and stop words are "is", "can" and "the",
 * so result will be the following set (one entry per row):
 * "beautiful",
 * "beautiful girl",
 * "beautiful girl from",
 * "girl",
 * "girl from",
 * "from",
 * "farmers",
 * "farmers market",
 * "market",
 * "like",
 * "like chewing",
 * "like chewing gum",
 * "chewing",
 * "chewing gum",
 * "gum"
 * </p>
 *
 * @author Anastasya Chizhikova
 * @since 17.05.2018
 */

public class SuggestionBuilder {


//	public static void main(String[] args) {
//		ArrayList<String> tokens = new ArrayList<>();
//		tokens.addAll(Arrays.asList(TOKENS));
//		Set<String> stops = new HashSet<>();
//		stops.addAll(Arrays.asList(STOP_WORDS));
//		Set<Suggestion> set = buildSuggestionsFromTokenFromString(tokens.iterator(), stops);
//		set.forEach(n -> System.out.println(n.text));
//	}

	/**
	 * Class implements Comparable interface to be used inside a TreeSet
	 */
	private static final class Suggestion implements Comparable {
		private final String text;
		public Suggestion(String text) {
			this.text = text;
		}
		public String toString() {
			return text;
		}
		/**
		 * We overrides this method to manage sorting order inside TheeSet filled with Suggestion items
		 * @param o object to compare with
		 * @return result of comparison
		 */
		@Override
		public int compareTo(Object o) {
			return 1;
		}
	}

	/** The maximum amount of words which can be combined to a suggestion */
	private final static int MAX_COMBINED_TOKENS = 3;

	/** Tokens to create suggestions */
	private static final String[] TOKENS  =  {"The", "beautiful", "girl", "from", "the", "farmers", "market", ".", "I", "like", "chewing", "gum", "." };

	/** Array of stopping words */
	private static final String[] STOP_WORDS = {"is", "can", "the"};


	/**
	 * Method that builds ordered suggestions set
	 * @param tokens iterator of tokens to create suggestions
	 * @param stopWords set of stopping words
	 * @return ordered (as defined in the description) set of suggestions
	 */
	private static Set<Suggestion> buildSuggestionsFromTokenFromString(Iterator<String> tokens, Set<String> stopWords) {

		TreeSet<Suggestion> suggestionSet = new TreeSet<>();
		StringBuilder sb = new StringBuilder();

		tokens.forEachRemaining( n -> {
			if (!isStopping(n, stopWords) && sb.toString().split(" ").length<MAX_COMBINED_TOKENS){
				if (sb.length()>0)
					sb.append(" ");
				sb.append(n);
				suggestionSet.add(new Suggestion(sb.toString()));
			}
			else {
				while(sb.length()>0){
					sb.delete(0, sb.indexOf(" ")>0?sb.indexOf(" ")+1 : sb.length());
					if (sb.length()>0)
						suggestionSet.add(new Suggestion(sb.toString()));
				}
			}
		});

		return suggestionSet;
	}


	/**
	 * Check if we stumbled on a stop word or symbol
	 * @param token - token to check
	 * @param stopWords - set of special stopping words
	 * @return true if token is stopping or false otherwise
	 */
	private static boolean isStopping(String token, Set<String> stopWords){
		for (String stopWord: stopWords){
			if (stopWord.equalsIgnoreCase(token))
				return true;
		}
		return token.length()==1;
	}



}

