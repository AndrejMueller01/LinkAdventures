/*
 * LinkAdventures assignment from Willhaben.at
 * 16.01.2018
 */

package tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
* FileParser.java
*  
* Performs file operations
*  
* @author Andrej Mueller
*/

public class FileParser {

	/** 
	  * Reads the file
	  *
	  * @param path, path to the file
	  * @param filename, name of the file (with file extension)
	  * @return a string of the file
	  */
	public String readFile(String path, String filename) {
		String currentFileString = "";
		try {
			currentFileString = new String(Files.readAllBytes(Paths.get(path + filename)));
		} catch (IOException e) {
			System.err.println("Error reading file!");
			e.printStackTrace();
		}
		return currentFileString;
	}

	/** 
	  * Extracts the URLs from a file
	  *
	  * @param path, path to the file
	  * @param filename, name of the file (with file extension)
	  * @return a list of URLs in string form
	  */
	public List<String> extractURLsFromFile(String path, String filename) {

		String fileString = readFile(path, filename);
		String[] words = fileString.split(" ");
		List<String> links = findLinksInArray(words);

		return links;
	}

	// helper methods
	
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
		// regex for the specification: letters, numbers and any of the following characters: `+&@#/%?=~\-_|!:,.;`
		String regex = "([a-zA-Z]?[0-9]?\\+?&?@?#?\\/?%?\\??=?~?\\\\?-?_?\\|?!?:?,?\\.?;?)+";
		
		// shorten the link as long as the link (from tail) is invalid due to the regex specification.
		while (!link.matches(regex)) {
			link = link.substring(0, link.length() - 1);
		}
		return link.length();
	}
}
