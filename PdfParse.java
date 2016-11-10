package dornsife_extract;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class PdfParse {

	public static String process(File file) throws IOException, TikaException,
			SAXException {

		BodyContentHandler handler = new BodyContentHandler();

		Metadata metadata = new Metadata();
		FileInputStream inputstream = new FileInputStream(file);
		ParseContext pcontext = new ParseContext();

		// parsing the document using PDF parser
		PDFParser pdfparser = new PDFParser();
		pdfparser.parse(inputstream, handler, metadata, pcontext);

		// getting the content of the document
		String output = handler.toString().trim();
		output = getOnlyStrings(output);
		String[] metadataNames = metadata.names();
		for (String name : metadataNames) {
			output = output + getOnlyStrings(metadata.get(name));
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
