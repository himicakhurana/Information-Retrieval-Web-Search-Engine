package dornsife_extract;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

public class Fetchfiles {
	public static void main(String argsp[]) throws FileNotFoundException,
			UnsupportedEncodingException {

		File f = new File(
				"C:\\ALL_WORK_SPACE\\IR\\Crawler_Maven\\crawler4j\\dornsifedata");
		PrintWriter writer = new PrintWriter("big.txt", "UTF-8");
		File f2 = new File(
				"C:\\ALL_WORK_SPACE\\Cloud\\dornsife_extract\\big.txt");
		File[] files = f.listFiles();
		int i = 0;
		for (File file : files) {
			try {
				long size = f2.length();
				int count = StringUtils.countMatches(file.getName(), "%2F");
			

					HtmlParse html = new HtmlParse();
					PdfParse pdf = new PdfParse();
					MicrosoftFiles micDoc = new MicrosoftFiles();

					if (file.getName().endsWith("html")
							|| file.getName().endsWith("cfm")) {
						String p = html.process(file);
						writer.print(p);
					}
					if (file.getName().endsWith("pdf")) {
						String p = pdf.process(file);
						writer.print(p);
					}
					if (file.getName().endsWith("doc")
							|| file.getName().endsWith("docx")) {
						String p = micDoc.process(file);
						writer.print(p);
				

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		writer.close();
	}
}
