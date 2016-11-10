package dornsife_extract;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class MicrosoftFiles {

	public static String process(File file) throws IOException, TikaException,
			SAXException {

		// detecting the file type
		BodyContentHandler handler = new BodyContentHandler(2000);
		Metadata metadata = new Metadata();
		FileInputStream inputstream = new FileInputStream(file);
		ParseContext pcontext = new ParseContext();

		// OOXml parser
		OOXMLParser msofficeparser = new OOXMLParser();
		msofficeparser.parse(inputstream, handler, metadata, pcontext);
	/*	System.out.println("Contents of the document:" + handler.toString());
		System.out.println("Metadata of the document:");*/
		String[] metadataNames = metadata.names();
		String output = handler.toString().trim();
		output = getOnlyStrings(output);
		for (String name : metadataNames) {
			 output = 	output+getOnlyStrings(metadata.get(name));
		}
		return output;
	}

	public static String getOnlyStrings(String s) {

		Pattern pattern = Pattern.compile("[^a-z A-Z]");
		Matcher matcher = pattern.matcher(s);
		Pattern pattern2 = Pattern.compile("[\\t\\n\\r]+");
		Matcher L = pattern.matcher(s);
		s = L.replaceAll(" ");
		String number = matcher.replaceAll(" ");
		if (number.length() < 2) {
			number = "";
		}
		return number;
	}
}
