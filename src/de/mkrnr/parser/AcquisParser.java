package de.mkrnr.parser;

import static de.mkrnr.parser.Token.BODY;
import static de.mkrnr.parser.Token.CLOSEDBODY;
import static de.mkrnr.parser.Token.CLOSEDROUNDBRACKET;
import static de.mkrnr.parser.Token.CLOSEDTUV;
import static de.mkrnr.parser.Token.COLON;
import static de.mkrnr.parser.Token.COMMA;
import static de.mkrnr.parser.Token.EXCLAMATIONMARK;
import static de.mkrnr.parser.Token.FULLSTOP;
import static de.mkrnr.parser.Token.HYPHEN;
import static de.mkrnr.parser.Token.QUESTIONMARK;
import static de.mkrnr.parser.Token.QUOTATIONMARK;
import static de.mkrnr.parser.Token.ROUNDBRACKET;
import static de.mkrnr.parser.Token.SEMICOLON;
import static de.mkrnr.parser.Token.STRING;
import static de.mkrnr.parser.Token.TUV;
import static de.mkrnr.parser.Token.WS;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import de.mkrnr.utils.Config;
import de.mkrnr.utils.IOHelper;

/**
 * @author Martin Koerner
 * 
 *         derived from
 *         http://101companies.org/index.php/101implementation:javaLexer
 * 
 */
public class AcquisParser {
	private AcquisTokenizer tokenizer;
	private String lexeme = new String();
	boolean lastLineWasAHeader;
	boolean isString;
	private Token current;
	// private Token previous;
	private Writer writer;
	private ArrayList<File> fileList;
	private String acquisLanguage;

	public AcquisParser(ArrayList<File> fileList, String output,
			String acquisLanguage) {
		this.acquisLanguage = acquisLanguage;
		this.fileList = fileList;
		this.writer = IOHelper.openWriteFile(output,
				Config.get().memoryLimitForWritingFiles);
	}

	public void parse() {
		for (File f : this.fileList) {
			this.tokenizer = new AcquisTokenizer(f, this.acquisLanguage);
			// try {
			// this.writer.write(f.toString()+"\n");
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			this.reset();
			while (this.tokenizer.hasNext()) {
				this.read();
				if (this.current == BODY) {
					while (this.tokenizer.hasNext()
							&& this.current != CLOSEDBODY) {
						// inside a textblock
						this.read();
						if (this.current == TUV) {
							while (this.tokenizer.hasNext()
									&& this.current != CLOSEDTUV) {
								this.read();
								if (this.current == STRING) {
									// the following if-statement removes words
									// written in all caps
									// if (!this.lexeme.matches(".+[A-Z].*")) {
									this.write(this.lexeme);
									// }
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
									this.write(": ");
								}
								if (this.current == QUOTATIONMARK) {
									this.write("'");
								}
								if (this.current == HYPHEN) {
									this.write("-");
								}
								if (this.current == WS) {
									this.write(" ");
								}
								if (this.current == QUESTIONMARK) {
									this.write(this.lexeme);
								}
								if (this.current == EXCLAMATIONMARK) {
									this.write(this.lexeme);
								}
								if (this.current == ROUNDBRACKET) {
									while (this.tokenizer.hasNext()
											&& this.current != CLOSEDROUNDBRACKET
											&& this.current != CLOSEDTUV) {
										this.read();
									}
								}
							}
							this.write(" ");
						}
					}
				}
			}
			this.write("\n");// new line after file
			this.tokenizer.close();
		}
		try {
			this.writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void read() {
		if (this.tokenizer.hasNext()) {
			// this.previous = this.current;
			this.tokenizer.lex();
			this.current = this.tokenizer.next();
			this.lexeme = this.tokenizer.getLexeme();
		} else {
			throw new IllegalStateException();
		}
	}

	public void reset() {
		this.lexeme = "";
		this.current = null;
		// this.previous = null;
	}

	public void write(String s) {
		try {
			this.writer.write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
