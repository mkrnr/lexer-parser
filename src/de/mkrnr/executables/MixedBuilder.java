package de.mkrnr.executables;

import java.io.File;
import java.io.IOException;

import de.mkrnr.parser.AcquisMain;
import de.mkrnr.utils.Config;

public class MixedBuilder {

	/**
	 * executes the following steps:
	 * <p>
	 * 1) parse and normalize enron
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
		String outputDirectory;
		String parsedFileName;
		String normalizedFileName;

		// build acquis
		outputDirectory = Config.get().outputDirectory + "brown/";
		parsedFileName = "parsed.txt";
		normalizedFileName = "normalized.txt";
		String[] languages = Config.get().acquisLanguages.split(",");
		new File(outputDirectory).mkdirs();

		for (String language : languages) {
			String outputPath = outputDirectory + language + "/";
			new File(outputPath).mkdirs();

			try {
				AcquisMain.run(Config.get().acquisInputDirectory, outputPath
						+ parsedFileName, outputPath + normalizedFileName,
						language);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// // build acquis
		// outputDirectory = Config.get().outputDirectory + "acquis/";
		// parsedFileName = "parsed.txt";
		// normalizedFileName = "normalized.txt";
		// String[] languages = Config.get().acquisLanguages.split(",");
		// new File(outputDirectory).mkdirs();
		//
		// for (String language : languages) {
		// String outputPath = outputDirectory + language + "/";
		// new File(outputPath).mkdirs();
		//
		// try {
		// AcquisMain.run(Config.get().acquisInputDirectory,
		// outputPath + parsedFileName, outputPath
		// + normalizedFileName, language);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }

		// // build enron
		// outputDirectory = Config.get().outputDirectory + "enron/en/";
		// parsedFileName = "parsed.txt";
		// normalizedFileName = "normalized.txt";
		// new File(outputDirectory).mkdirs();
		//
		// try {
		// EnronMain.run(Config.get().enronInputDirectory, outputDirectory
		// + parsedFileName, outputDirectory + normalizedFileName);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		//
		// // build reuters
		// outputDirectory = Config.get().outputDirectory + "reuters/en/";
		// parsedFileName = "parsed.txt";
		// normalizedFileName = "normalized.txt";
		// new File(outputDirectory).mkdirs();
		//
		// try {
		// ReutersMain.run(Config.get().reutersInputDirectory,
		// outputDirectory + parsedFileName, outputDirectory
		// + normalizedFileName);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}
}
