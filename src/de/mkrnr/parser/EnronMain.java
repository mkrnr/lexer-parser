package de.mkrnr.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import de.mkrnr.utils.Config;
import de.mkrnr.utils.IOHelper;

public class EnronMain {
	private static ArrayList<File> fileList;

	/**
	 * @author Martin Koerner
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		File outputDirectory = new File(Config.get().outputDirectory
				+ "enron/en/");
		outputDirectory.mkdirs();
		EnronMain.run(Config.get().enronInputDirectory,
				outputDirectory.getAbsolutePath() + "/parsed.txt",
				outputDirectory.getAbsolutePath() + "/normalized.txt");
	}

	public static void run(String enronInputPath, String parsedOutputPath,
			String normalizedOutputPath) throws IOException {
		long startTime = System.currentTimeMillis();
		IOHelper.log("getting file list");
		fileList = IOHelper.getDirectory(new File(enronInputPath));

		EnronParser parser = new EnronParser(fileList, parsedOutputPath);
		IOHelper.log("start parsing: " + enronInputPath);
		parser.parse();
		IOHelper.log("parsing done");
		IOHelper.log("start cleanup");
		EnronNormalizer wn = new EnronNormalizer(parsedOutputPath,
				normalizedOutputPath, Locale.ENGLISH);
		wn.normalize();
		IOHelper.log("cleanup done");
		IOHelper.log("generate indicator file");
		long endTime = System.currentTimeMillis();
		long time = (endTime - startTime) / 1000;
		IOHelper.strongLog("done normalizing: " + enronInputPath + ", time: "
				+ time + " seconds");
	}

}
