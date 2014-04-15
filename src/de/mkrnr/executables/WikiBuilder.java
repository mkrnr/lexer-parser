package de.mkrnr.executables;

import java.io.File;
import java.io.IOException;

import de.mkrnr.parser.WikipediaMain;
import de.mkrnr.utils.Config;
import de.mkrnr.utils.IOHelper;

public class WikiBuilder {

	/**
	 * executes the following steps:
	 * <p>
	 * 1) parse and normalize wikipedia data
	 * <p>
	 * 2) split into training.txt, testing.txt, and learning.txt
	 * <p>
	 * 3) build index.txt
	 * <p>
	 * 4) build ngrams
	 * <p>
	 * 5) build typoedges
	 * 
	 * @author Martin Koerner
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		IOHelper.strongLog("start: WikiBuilder");
		File dir = new File(Config.get().wikiInputDirectory);
		String outputDirectory = Config.get().outputDirectory + "wiki/";
		String parsedFileName = "parsed.txt";
		String normalizedFileName = "normalized.txt";
		new File(outputDirectory).mkdirs();
		for (File f : dir.listFiles()) {
			String wikiTyp = f.getName().split("-")[0];
			wikiTyp = wikiTyp.replace("wiki", "");
			IOHelper.log("Processing wiki language: " + wikiTyp);
			String outputPath = outputDirectory + wikiTyp + "/";
			IOHelper.strongLog("start building: " + outputPath);
			new File(outputPath).mkdirs();

			try {
				WikipediaMain.run(f.getAbsolutePath(), outputPath
						+ parsedFileName, outputPath + normalizedFileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			IOHelper.strongLog("done building: " + outputPath);
		}
		IOHelper.strongLog("done: WikiBuilder");
	}
}
