package de.mkrnr.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Locale;

import de.mkrnr.utils.IOHelper;

/**
 * @author Martin Koerner
 * 
 *         derived from
 *         http://101companies.org/index.php/101implementation:javaLexer
 * 
 */
public class AcquisNormalizer extends Normalizer {

	private BufferedReader reader;
	private BufferedWriter writer;
	private String line;

	public AcquisNormalizer(String input, String output, Locale locale) {
		super(locale);
		System.out.println(input);
		System.out.println(output);
		this.reader = IOHelper.openReadFile(input);
		this.writer = IOHelper.openWriteFile(output, 32 * 1024 * 1024);

	}

	public void normalize() {
		try {
			while ((this.line = this.reader.readLine()) != null) {
				this.line = this.normalizeString(this.line);
				if (!this.line.isEmpty()) {
					this.writer.write(this.line);
					this.writer.flush();
				}
				this.writer.flush();
			}
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
