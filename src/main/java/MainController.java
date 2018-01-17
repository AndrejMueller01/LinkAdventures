
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

public class MainController {

	public static String defaultPath = System.getProperty("user.home") + "\\LAfiles\\";

	public static void main(String[] args) {
		Options options = new Options();

        Option apikey = new Option("i", "import", true, "imports the given file. ex.: filepath/filename.txt ");
        apikey.setRequired(true);
        options.addOption(apikey);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;
        String pathAndFilenameWExt = null;

        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("i")) {
            	pathAndFilenameWExt = cmd.getOptionValue("i");
            }
        } catch (org.apache.commons.cli.ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
            return;
}
		LinkExtractor<String> le = new DefaultLinkExtractor();
		DefaultLinkValidator lv = new DefaultLinkValidator();
		System.out.println(pathAndFilenameWExt);
		List<String> validLinks = evalLinks(le.extract(pathAndFilenameWExt), lv);
		
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
