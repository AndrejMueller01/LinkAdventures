/*
 * LinkAdventures assignment from Willhaben.at
 * Graz - 16.01.2018
 */

package tools;

import java.util.function.Predicate;

/**
 * DefaultLinkValidator.java
 * 
 * Validates an URL as specified in the specifications of the willhaben task
 * Implements the pre definied Predicate Interface
 * 
 * @author Andrej Mueller
 */
public class DefaultLinkValidator implements Predicate<String>{
	
	@Override
	public boolean test(String link) {
		String regex = "http(s)?:\\/\\/[a-zA-Z0-9_-]+\\.[a-zA-Z0-9_.-]+[a-zA-Z0-9_\\/()-.%]*";

		if(link.toString().matches(regex))
			return true;
		return false;	
	}
}
