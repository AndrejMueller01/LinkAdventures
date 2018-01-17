/*
 * LinkAdventures assignment from Willhaben.at
 * 16.01.2018
 */
package tools;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * LinkValidatorTest.java
 * 
 * test for the class LinkValidator.java
 * 
 * @author Andrej Mueller
 */

@RunWith(Parameterized.class)
public class LinkValidatorTest {

	private DefaultLinkValidator lv;

	// parameters for the test run
	@Parameter
	public String linkString;
	@Parameter(1)
	public boolean isValid;

	// parameter values for the test runs
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "https://www.test.at", true }, { "http://www.test.at#", false },
				{ "http://foo.com/blah_blah_(wikipedia)", true }, { "https://me:secret@test.com/wh-assessment-101/invalid.txt", false },
				{ "https://test.com/wh/fragment#invalid", false }, { "https://test.com/wh-101/query?invalid=true", false },
				{ "https://test.com:8080/wh-101", false }});
	}

	@Before
	public void initialize() {
		lv = new DefaultLinkValidator();
	}

	@Test
	public void testTest() {
		assertEquals(lv.test(linkString), isValid);
	}
}