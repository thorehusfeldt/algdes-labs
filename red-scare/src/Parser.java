import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {
	public List<Node> doParse(String input_path){
		List<Node> nodes = new ArrayList<>();
		try {
			List<String> lines = Files.readAllLines(Paths.get(input_path),
					Charset.defaultCharset());
		} catch (IOException ex) {
			// fuck
			ex.printStackTrace();
		}
		return nodes;
	}
}
