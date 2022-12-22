import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;

@Command(
		name = "Name Finder",
		description = "It prints names from a given url."
)


public class NameFinder implements Runnable{
	@Parameters(description = "Url to be searched")
	public String url;

	public static void main(String[] args) {
		new CommandLine(new NameFinder()).execute(args);
	}


	@Override
	public void run() {
		try{
		Document doc = Jsoup.connect(url).get();

		// Extract the text from the HTML
		String text = doc.body().text();

		// Load the Name Finder model
		InputStream modelIn = new FileInputStream("src/main/resources/en-ner-person.bin");
		TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
		NameFinderME nameFinder = new NameFinderME(model);

		// Find the names in the text
		String[] tokens = text.split("\\s+");
		Span[] names = nameFinder.find(tokens);
		nameFinder.clearAdaptiveData();

		// Converting the name spans to Strings
		String [] Names = Span.spansToStrings(names,tokens);
			for (String Name:Names) {
				System.out.println(Name);
			}
		} catch (IOException e) {
			System.out.println("An error occurred, please try with another URL.");
		}


	}


}
