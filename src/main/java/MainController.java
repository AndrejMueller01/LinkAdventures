
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import tools.DefaultLinkExtractor;
import tools.LinkExtractor;
import tools.LinkProcessor;
import tools.LinkValidatorTest;
import tools.DefaultLinkProcessorThread;
import tools.DefaultLinkValidator;

public class MainController {

	public static String defaultPath = System.getProperty("user.home") + "\\LAfiles\\";

	public static void main(String[] args) {
		LinkExtractor<String> le = new DefaultLinkExtractor();
		DefaultLinkValidator lv = new DefaultLinkValidator();
		List<String> validLinks = evalLinks(le.extract(defaultPath, "test01.txt"), lv);
		for(int i = 0; i<validLinks.size();i++) {
			System.out.println(validLinks.get(i));
		}
		for (int i = 0; i < validLinks.size(); i++) {
			new DefaultLinkProcessorThread(validLinks.get(i)).run();
			
		}

	}

	public static List<String> evalLinks(List<String> links, Predicate<String> predicate) {
		List<String> validLinks = new ArrayList<>();
		for (String link : links) {

			if (predicate.test(link)) {
				validLinks.add(link);
			}
		}
		return validLinks;
	}
}
