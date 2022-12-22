import org.junit.*;
import org.junit.Assert;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.containsString;

public class NameFinderTest {
	// setting up streams to get console output
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	@Test
	public void TestExamplePage() {
		String url = "https://opennlp.apache.org/books-tutorials-and-talks.html";
		String[] args = new String []{url};

		NameFinder.main(args);
		Assert.assertThat(outContent.toString(), containsString("Ingersoll"));

	}

	@Test
	public void TestError(){
		String url = "https://doesn'texist.com";
		String[] args = new String []{url};

		NameFinder.main(args);
		Assert.assertThat(outContent.toString(), containsString("An error occurred, please try with another URL."));
	}

	@Test
	public void TestEmptyArgs(){
		String[] args = new String []{};

		NameFinder.main(args);

		Assert.assertEquals("",outContent.toString());
	}
}