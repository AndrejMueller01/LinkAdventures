/*
 * LinkAdventures assignment from Willhaben.at
 * Graz - 16.01.2018
 */

package tools;

import java.util.ArrayList;
import java.util.List;

/**
 * DefaultLinkExtractor.java
 * 
 * Performs link extraction from a given file.
 * Implementation of the LinkExtractor Interface
 * 
 * @author Andrej Mueller
 */

public class DefaultLinkExtractor implements LinkExtractor<String> {

	/**
	 * Extracts the URLs from an array
	 *
	 * @param filePath,
	 *            path to the file
	 * @param fileNameWExt,
	 *            name of the file (with file extension)
	 * @return a string list of URLs
	 */

	@Override
	public List<String> extract(String filePath) {
		String fileString = readFile(filePath);
		String[] words = fileString.split(" ");
		return findLinksInArray(words);
	}

	// ------------------------- helper methods-------------------------------------

	private List<String> findLinksInArray(String[] input) {
		int URLStartIndex = 0;
		int URLEndIndex = 0;
		boolean containsURL = false;
		List<String> links = new ArrayList<>();
		for (int i = 0; i < input.length; i++) {
			containsURL = false;
			// find links in the current list entry
			if (input[i].contains("http://")) {
				containsURL = true;
				URLStartIndex = input[i].indexOf("http://");
			}
			if (input[i].contains("https://")) {
				containsURL = true;
				URLStartIndex = input[i].indexOf("https://");
			}
			if (containsURL) {
				input[i] = input[i].substring(URLStartIndex, input[i].length());
				URLEndIndex = findURLEndIndex(input[i].trim());
				links.add(input[i].substring(0, URLEndIndex));
			}
		}
		return links;

	}

	private int findURLEndIndex(String link) {
		// regex for the specification: letters, numbers and any of the following
		// characters: `+&@#/%?=~\-_|!:,.;`
		String regex = "([a-zA-Z]?[0-9]?\\+?&?@?#?\\/?%?\\??=?~?\\\\?-?_?\\|?!?:?,?\\.?;?)+";

		// shorten the link as long as the link (from tail) is invalid due to the regex
		// specification.
		while (!link.matches(regex)) {
			link = link.substring(0, link.length() - 1);
		}
		return link.length();
	}

}
