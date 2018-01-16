/*
 * LinkAdventures assignment from Willhaben.at
 * 16.01.2018
 */
package tools;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
* FileHandlerTest.java
*  
* test for the class FileHandler.java
*  
* @author Andrej Mueller
*/

@RunWith(Parameterized.class)
public class FileHandlerTest {

	private FileHandler fh;
	private String absolutePath;
	private String filnameWExt;
	private Path pathWFileName;
	private List<String> linesIn;

	//parameters for the test run
	@Parameter
	public String fileString;
	@Parameter(1)
	public String linkString;

	// parameter values for the test runs
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "The first line \n The second line \n some link: http://www.test.tt))", "http://www.test.tt" },
				{ "The first line \n The second line \n some link: http://www.test.tt)) \n https://holalala",
						"http://www.test.tt \n https://holalala" },
				{ "The first {http://www./te+&@#/%?=~\\-_|!:,.;line \n The second line \n some link: http://www.test.tt)) \n https://holalala",
				"http://www./te+&@#/%?=~\\-_|!:,.;line \n http://www.test.tt \n https://holalala" }});
	}

	// initialization of the class and preparation for the tests
	@Before
	public void initialize() {
		fh = new FileHandler();
		absolutePath = new File("").getAbsolutePath();
		filnameWExt = "t01.txt";
		pathWFileName = Paths.get(absolutePath + filnameWExt);
		linesIn = Arrays.asList(fileString.split("\n"));

		try {
			Files.write(pathWFileName, linesIn, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// test for the readFile() method
	@Test
	public void testReadFile() {

		String out = fh.readFile(absolutePath, filnameWExt);
		String[] linesOut = out.split("\n");

		for (int i = 0; i < linesIn.size(); i++) {
			assertEquals(linesIn.get(i).trim(), linesOut[i].trim());
		}
	}

	// test of the ectractURLsFromFile method
	@Test
	public void testExtractURLsFromFile() {

		String[] linksInArr = linkString.split("\n");	
		List<String> linksIn = new ArrayList<>();
		
		for(int i = 0; i< linksInArr.length; i++) 
		{
			linksIn.add(linksInArr[i].trim());
		}
		
		List<String> linksOut = fh.extractURLsFromFile(absolutePath, filnameWExt);
		assertEquals(linksIn, linksOut);
	}
}