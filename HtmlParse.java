package dornsife_extract;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class HtmlParse {

	public static String process(File file) throws Exception {

		// detecting the file type
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		FileInputStream inputstream = new FileInputStream(file);
		ParseContext pcontext = new ParseContext();

		// Html parser
		HtmlParser htmlparser = new HtmlParser();
		htmlparser.parse(inputstream, handler, metadata, pcontext);
		/*
		 * System.out.println("Contents of the document:" +
		 * handler.toString().trim());
		 */
		
		
		String output = handler.toString().trim();
		output = getOnlyStrings(output);

		String[] metadataNames = metadata.names();

		for (String name : metadataNames) {
			output.concat(getOnlyStrings(metadata.get(name)));
/*			System.out.println(name + ":   " + metadata.get(name));
*/		}
		MP1 mp = new MP1("", "");
		Map<String, Integer> topItems = mp.process(output);

		return output;
	}

	public static String getOnlyStrings(String s) {

		Pattern pattern = Pattern.compile("[^a-z A-Z]");
		Matcher matcher = pattern.matcher(s);
		Pattern pattern2 = Pattern.compile("[\\t\\n\\r]+");
		Matcher L = pattern.matcher(s);
		s = L.replaceAll(" ");
		String number = matcher.replaceAll("");
		if (number.length() < 2) {
			number = "";
		}
		return number;
	}
}