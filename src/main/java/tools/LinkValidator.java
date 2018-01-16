/*
 * LinkAdventures assignment from Willhaben.at
 * 16.01.2018
 */

package tools;

import java.util.function.Predicate;
/**
* LinkValidator.java
*  
* Verifies an URL
*  
* @author Andrej Mueller
*/
public class LinkValidator implements Predicate<String>{
	@Override
	public boolean test(String link) {
		String regex = "http(s)?:\\/\\/[a-zA-Z0-9_-]+\\.[a-zA-Z0-9_.-]+[a-zA-Z0-9_\\/()-]*";

		if(link.toString().matches(regex))
			return true;
		return false;	
	}
}
