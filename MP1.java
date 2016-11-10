package dornsife_extract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;

public class MP1 {
	Random generator;
	String userName;
	String inputFileName;
	String delimiters = "[\\,\\;\\.\\?\\!\\-\\:\\@\\_\\(\\)\\{\\}\\_\\*\\/\\t/[/]\\s]";
	String[] stopWordsArray = { "i", "me", "my", "myself", "we", "our", "ours",
			"ourselves", "you", "your", "yours", "yourself", "yourselves",
			"he", "him", "his", "himself", "she", "her", "hers", "herself",
			"it", "its", "itself", "they", "them", "their", "theirs",
			"themselves", "what", "which", "who", "whom", "this", "that",
			"these", "those", "am", "is", "are", "was", "were", "be", "been",
			"being", "have", "has", "had", "having", "do", "does", "did",
			"doing", "a", "an", "the", "and", "but", "if", "or", "because",
			"as", "until", "while", "of", "at", "by", "for", "with", "about",
			"against", "between", "into", "through", "during", "before",
			"after", "above", "below", "to", "from", "up", "down", "in", "out",
			"on", "off", "over", "under", "again", "further", "then", "once",
			"here", "there", "when", "where", "why", "how", "all", "any",
			"both", "each", "few", "more", "most", "other", "some", "such",
			"no", "nor", "not", "only", "own", "same", "so", "than", "too",
			"very", "s", "t", "can", "will", "just", "don", "should", "now", "" };
	Set<String> stopWordSet = new HashSet<String>(
			Arrays.asList(this.stopWordsArray));

	void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA");
		messageDigest.update(seed.toLowerCase().trim().getBytes());
		byte[] seedMD5 = messageDigest.digest();

		long longSeed = 0;
		for (int i = 0; i < seedMD5.length; i++) {
			longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
		}

		this.generator = new Random(longSeed);
	}

	Integer[] getIndexes() throws NoSuchAlgorithmException {
		Integer n = 10000;
		Integer number_of_lines = 50000;
		Integer[] ret = new Integer[n];
		this.initialRandomGenerator(this.userName);
		for (int i = 0; i < n; i++) {
			ret[i] = generator.nextInt(number_of_lines);
		}
		return ret;
	}

	public MP1(String userName, String inputFileName) {
		this.userName = userName;
		this.inputFileName = inputFileName;
	}

	public Map process(String 	inputFileName) throws Exception {
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		String[] ret = new String[20];
		BufferedReader br = null;
		PrintWriter writer = null;
		try {

			br = new BufferedReader(new FileReader(inputFileName));

			String line;
			while ((line = br.readLine()) != null) {
				// System.out.println(line);

				String[] words = line.split(delimiters);
				if (null != words) {
					List<String> wordList = Arrays.asList(words);
					for (String word : wordList) {
						word = word.toLowerCase();

						if (!stopWordSet.contains(word)) {
							if (map.containsKey(word)) {
								int x = map.get(word);
								map.put(word, x + 1);

							} else {
								map.put(word, 1);

							}
						}
					}

				}
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
		// TODO
		/*
		 * ValueComparator bvc = new ValueComparator(); List<Entry<String,
		 * Integer>> list = new ArrayList<Entry<String, Integer>>(
		 * map.entrySet());
		 * 
		 * Collections.sort(list, bvc);
		 */

		return map;
	}

	public static Map sortByValue(Map unsortMap) {
		List list = new LinkedList(unsortMap.entrySet());

		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				// TODO Auto-generated method stub
				Map.Entry om1 = (Entry) o1;
				Map.Entry om2 = (Entry) o2;
				if (om1.getValue() == om2.getValue()) {
					return om1.getKey().toString()
							.compareTo(om2.getKey().toString());
				}

				else {
					return 0;

				}
			}
		});
		Map sortedMap = new LinkedHashMap();
		int i = 0;
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
			i++;
			if (i >= 4) {
				break;
			}
		}
		return sortedMap;
	}

	public static void main(String[] args) throws Exception {

		/*
		 * String userName = "himica"; String inputFileName =
		 * "C:\\ALL_WORK_SPACE\\Cloud\\Mp1\\src\\input.txt";
		 */
		
			String userName = "test";
			String inputFileName = "C:\\ALL_WORK_SPACE\\Cloud\\dornsife_extract\\big.txt";
			MP1 mp = new MP1(userName, inputFileName);
			Map<String, Integer> topItems = mp.process(inputFileName);
			int i = 0;
			PrintWriter writer = new PrintWriter("big_new.txt", "UTF-8");

			for (Entry<String, Integer> item : topItems.entrySet()) {
				writer.println(item.getKey());
			}
			writer.close();

		
	}
}

class ValueComparator implements Comparator<Entry<String, Integer>> {
	Map<String, Integer> base;

	public ValueComparator(Map<String, Integer> base) {
		this.base = base;
	}

	public ValueComparator() {
	}

	// Note: this comparator imposes orderings that are inconsistent with
	// equals.
	public int compare(Entry<String, Integer> om1, Entry<String, Integer> om2) {
		// TODO Auto-generated method stub

		/*
		 * if (om1.getValue() == om2.getValue()) { return
		 * om1.getKey().compareTo(om2.getKey()); }
		 * 
		 * else {
		 */
		return om2.getValue() - om1.getValue();

		// }
	}
}