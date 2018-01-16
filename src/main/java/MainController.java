
import tools.FileHandler;
import tools.LinkHandler;

public class MainController {
	
	public static String defaultPath = System.getProperty("user.home") + "\\LAfiles\\";
	
	public static void main(String[] args) {
			FileHandler fileHandler = new FileHandler();
			LinkHandler linkHandler = new LinkHandler();

			fileHandler.extractURLsFromFile(defaultPath, "test01.txt");			
	}

}
