
/*
 * LinkAdventures assignment from Willhaben.at
 * Graz - 16.01.2018
 */

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import tools.DefaultLinkExtractor;
import tools.DefaultLinkProcessorThread;
import tools.DefaultLinkValidator;
import tools.LinkExtractor;

/**
 * MainController.java
 * 
 * contains the main method, handles cli
 * 
 * @author Andrej Mueller
 */
public class MainController {

	public static String defaultPath = System.getProperty("user.home") + "\\LAfiles\\";

	public static void main(String[] args) {
		Options options = new Options();
		System.out.println("######## Welcome to LinkAdventures: A willhaben assessment ########");
		System.out.println(
				"This program extracts links from a file, validates them and hashes the content of the HTTP response");

		Option importFile = new Option("i", "import", true,
				"imports the given file. F.e.: C:\\filepath\\filename.txt ");
		importFile.setRequired(true);
		options.addOption(importFile);

		Option hash = new Option("h", "hash", true,
				"hash value you want to search in the HTTP responses. Considers max. 2 arguments F.e.: R-hbwRYKKAw0uSMwmukf8oOg==");
		hash.setRequired(false);
		hash.setOptionalArg(true);
		hash.setArgs(2);
		options.addOption(hash);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;
		String pathAndFilenameWExt = null;
		String[] optionalHashes = null;

		try {
			cmd = parser.parse(options, args);
			if (cmd.hasOption("i")) {
				pathAndFilenameWExt = cmd.getOptionValue("i");
			}
			if (cmd.hasOption("h")) {
				optionalHashes = cmd.getOptionValues("h");
			}
		} catch (org.apache.commons.cli.ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("utility-name", options);

			System.exit(1);
			return;
		}
		LinkExtractor<String> le = new DefaultLinkExtractor();
		DefaultLinkValidator lv = new DefaultLinkValidator();
		// System.out.println(pathAndFilenameWExt);
		List<String> validLinks = evalLinks(le.extract(pathAndFilenameWExt), lv);

		for (int i = 0; i < validLinks.size(); i++) {
			new DefaultLinkProcessorThread(validLinks.get(i), optionalHashes).run();

		}
	}

	/**
	 * Extracts the URLs from an array
	 *
	 * @param List<String>
	 *            links, the links to evaluate
	 * @param Predicate<String>
	 *            predicate, the link validator
	 * @return a string list of valid URLs
	 */
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
