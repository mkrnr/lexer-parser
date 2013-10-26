package de.mkrnr.parser;

import static de.mkrnr.parser.Token.BR;
import static de.mkrnr.parser.Token.CLOSEDELEMENT;
import static de.mkrnr.parser.Token.CLOSEDPAGE;
import static de.mkrnr.parser.Token.CLOSEDREF;
import static de.mkrnr.parser.Token.CLOSEDTEXT;
import static de.mkrnr.parser.Token.CLOSEDTITLE;
import static de.mkrnr.parser.Token.EHH;
import static de.mkrnr.parser.Token.ELEMENT;
import static de.mkrnr.parser.Token.GREATERTHAN;
import static de.mkrnr.parser.Token.LESSTHAN;
import static de.mkrnr.parser.Token.LINESEPARATOR;
import static de.mkrnr.parser.Token.OTHER;
import static de.mkrnr.parser.Token.PAGE;
import static de.mkrnr.parser.Token.REF;
import static de.mkrnr.parser.Token.SLASH;
import static de.mkrnr.parser.Token.STRING;
import static de.mkrnr.parser.Token.TEXT;
import static de.mkrnr.parser.Token.TITLE;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Martin Koerner
 * 
 *         derived from
 *         http://101companies.org/index.php/101implementation:javaLexer
 * 
 */
public class WikipediaRecognizer implements Iterator<Token> {

	private WikipediaTokenizer tokenizer;
	private String lexeme;
	private Token current;
	private Token previous;
	private Token label;

	// Keywords to token mapping
	private static Map<String, Token> keywords;

	static {
		keywords = new HashMap<String, Token>();
		keywords.put("!--", EHH);
		keywords.put("page", PAGE);
		keywords.put("title", TITLE);
		keywords.put("text", TEXT);
		keywords.put("ref", REF);
		keywords.put("br", BR);
		keywords.put("/page", CLOSEDPAGE);
		keywords.put("/title", CLOSEDTITLE);
		keywords.put("/text", CLOSEDTEXT);
		keywords.put("/ref", CLOSEDREF);
	}

	public WikipediaRecognizer(WikipediaTokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	public String getLexeme() {
		return this.lexeme;
	}

	public WikipediaTokenizer getTokenizer() {
		return this.tokenizer;
	}

	public void reset() {
		this.lexeme = "";
		this.current = null;
		this.previous = null;
		this.label = null;
	}

	public void read() {
		if (this.tokenizer.hasNext()) {
			this.tokenizer.lex();
			this.lexeme += this.tokenizer.getLexeme();
			this.previous = this.current;
			this.current = this.tokenizer.next();

		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	public boolean hasNext() {
		return this.tokenizer.hasNext();
	}

	@Override
	public Token next() {
		this.reset();
		this.read();
		// Recognize <...>
		if (this.current == LESSTHAN) {
			this.read();
			if (this.current == EHH) {
				return EHH;
			}

			if (this.current == STRING) {
				this.label = ELEMENT;
				if (keywords.containsKey(this.lexeme.substring(1))) {
					this.label = keywords.get(this.lexeme.substring(1));
				}
				if (this.lexeme.startsWith("ref")) {
					this.label = REF;
				}
			}
			if (this.current == SLASH) {
				this.label = CLOSEDELEMENT;
				this.read();
				if (keywords.containsKey(this.lexeme.substring(1))) {
					this.label = keywords.get(this.lexeme.substring(1));
				}
			}
			while (this.current != GREATERTHAN && this.current != LINESEPARATOR) {
				this.read();
			}
			if (this.current == GREATERTHAN) {
				if (this.previous == SLASH) {
					return OTHER;
				}
				return this.label;
			}

		}

		return this.current;
	}

	public void close() {
		this.tokenizer.close();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
