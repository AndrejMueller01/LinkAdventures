/*
 * LinkAdventures assignment from Willhaben.at
 * Graz - 16.01.2018
 */

package tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * LinkExtractor.java
 * 
 * Interface for Link Extraction
 * 
 * @author Andrej Mueller
 */
public interface LinkExtractor<T> {

	public List<T> extract(String filePath, String fileNameWExt);

	/**
	 * default implementation of reading a file
	 *
	 * @param path,
	 *            path to the file
	 * @param filename,
	 *            name of the file (with file extension)
	 * @return a string of the file
	 */
	default String readFile(String path, String filename) {
		String currentFileString = "";
		try {
			currentFileString = new String(Files.readAllBytes(Paths.get(path + filename)));
		} catch (IOException e) {
			System.err.println("Error reading file!");
			e.printStackTrace();
		}
		return currentFileString;
	}
}
