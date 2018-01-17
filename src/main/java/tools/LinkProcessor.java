/*
 * LinkAdventures assignment from Willhaben.at
 * Graz - 16.01.2018
 */
package tools;
/**
* LinkProcessor.java
*  
* Interface for Link processing
*  
* @author Andrej Mueller
*/
public interface LinkProcessor<T> {
	public boolean process(T link);
}
