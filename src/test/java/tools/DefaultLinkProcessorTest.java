/*
 * LinkAdventures assignment from Willhaben.at
 * Graz - 18.01.2018
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
 * DefaultLinkProcessorTest.java
 * 
 * test for the class DefaultLinkProcessorThread.java
 * 
 * @author Andrej Mueller
 */
@RunWith(Parameterized.class)
public class DefaultLinkProcessorTest {
	private DefaultLinkProcessorThread lp;

	// parameters for the test run
	@Parameter
	public String linkString;
	@Parameter(1)
	public String hashString;

	// parameter values for the test runs
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "https://tools.ietf.org/rfc/rfc3986.txt", "RbwRYKKAw0uSMwmukf8oOg==" },
				{ "https://www.ietf.org/rfc/rfc1149.txt", "5zAjHAcCDH/HsNXfEoVeMA==" } });
	}

	@Before
	public void initialize() {
		lp = new DefaultLinkProcessorThread(linkString, null);
	}

	@Test
	public void testProcess() {
		assertEquals(lp.process(linkString), hashString);
	}
}
