package de.mkrnr.parser;

import static de.mkrnr.parser.Token.COLON;
import static de.mkrnr.parser.Token.COMMA;
import static de.mkrnr.parser.Token.EXCLAMATIONMARK;
import static de.mkrnr.parser.Token.FULLSTOP;
import static de.mkrnr.parser.Token.HYPHEN;
import static de.mkrnr.parser.Token.LINESEPARATOR;
import static de.mkrnr.parser.Token.QUESTIONMARK;
import static de.mkrnr.parser.Token.QUOTATIONMARK;
import static de.mkrnr.parser.Token.SEMICOLON;
import static de.mkrnr.parser.Token.STRING;
import static de.mkrnr.parser.Token.WS;

import java.io.IOException;
import java.io.Writer;

import de.mkrnr.utils.IOHelper;

/**
 * Given a NGramRecognizer, this parser only prints declared parts of the ngram.
 * <p>
 * derived from http://101companies.org/index.php/101implementation:javaLexer
 * 
 * @author Martin Koerner
 */
public class GoogleParser {
	private GoogleRecognizer recognizer;
	private String lexeme = new String();
	boolean lastLineWasAHeader;
	boolean isString;
	private Token current;
	private Writer writer;

	public GoogleParser(GoogleRecognizer recognizer,
			String parsedGoogleOutputPath) {
		this.recognizer = recognizer;
		this.writer = IOHelper.openWriteFile(parsedGoogleOutputPath,
				32 * 1024 * 1024);
	}

	public void parse() throws IOException {
		this.reset();
		while (this.recognizer.hasNext()) {
			this.read();
			if (this.current == STRING) {
				this.write(this.lexeme);
			}
			if (this.current == FULLSTOP) {
				this.write(this.lexeme);
			}
			if (this.current == COMMA) {
				this.write(this.lexeme);
			}
			if (this.current == SEMICOLON) {
				this.write(this.lexeme);
			}
			if (this.current == COLON) {
				this.write(this.lexeme);
			}
			if (this.current == HYPHEN) {
				this.write("-");
			}
			if (this.current == QUESTIONMARK) {
				this.write(this.lexeme);
			}
			if (this.current == EXCLAMATIONMARK) {
				this.write(this.lexeme);
			}
			if (this.current == QUOTATIONMARK) {
				this.write("'");
			}
			if (this.current == WS) {
				this.write(" ");
			}
			if (this.current == LINESEPARATOR) {
				this.write("\n");
			}
		}
		this.recognizer.close();
		this.writer.close();
	}

	public void read() throws IOException {
		if (this.recognizer.hasNext()) {
			// this.previous = this.current;
			this.current = this.recognizer.next();
			this.lexeme = this.recognizer.getLexeme();
		} else {
			throw new IllegalStateException();
		}
	}

	public void reset() {
		this.lexeme = "";
		this.current = null;
	}

	public void skip() {
		if (this.recognizer.hasNext()) {
			this.current = this.recognizer.next();
		} else {
			throw new IllegalStateException();
		}
	}

	public void write(String s) {
		try {
			this.writer.write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
