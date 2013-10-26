package de.mkrnr.parser;

import static de.mkrnr.parser.Token.BCC;
import static de.mkrnr.parser.Token.CC;
import static de.mkrnr.parser.Token.CONTENTTRANSFERENCODING;
import static de.mkrnr.parser.Token.CONTENTTYPE;
import static de.mkrnr.parser.Token.DATE;
import static de.mkrnr.parser.Token.FROM;
import static de.mkrnr.parser.Token.HEADER;
import static de.mkrnr.parser.Token.MESSAGEID;
import static de.mkrnr.parser.Token.MIMEVERSION;
import static de.mkrnr.parser.Token.RE;
import static de.mkrnr.parser.Token.STRING;
import static de.mkrnr.parser.Token.SUBJECT;
import static de.mkrnr.parser.Token.TO;
import static de.mkrnr.parser.Token.XBCC;
import static de.mkrnr.parser.Token.XCC;
import static de.mkrnr.parser.Token.XFILENAME;
import static de.mkrnr.parser.Token.XFOLDER;
import static de.mkrnr.parser.Token.XFROM;
import static de.mkrnr.parser.Token.XORIGIN;
import static de.mkrnr.parser.Token.XTO;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.mkrnr.utils.IOHelper;

/**
 * @author Martin Koerner
 * 
 *         derived from
 *         http://101companies.org/index.php/101implementation:javaLexer
 * 
 */
public class EnronTokenizer extends Tokenizer {

	// Keywords to token mapping
	private static Map<String, Token> keywords;

	public EnronTokenizer(File f) {
		this.reader = IOHelper.openReadFile(f.getAbsolutePath());

		keywords = new HashMap<String, Token>();

		keywords.put("Message-ID", MESSAGEID);
		keywords.put("Date", DATE);
		keywords.put("Re", RE);
		keywords.put("From", FROM);
		keywords.put("To", TO);
		keywords.put("cc", CC);
		keywords.put("bcc", BCC);
		keywords.put("Cc", CC);
		keywords.put("Bcc", BCC);
		keywords.put("Subject", SUBJECT);
		keywords.put("Mime-Version", MIMEVERSION);
		keywords.put("Content-Type", CONTENTTYPE);
		keywords.put("Content-Transfer-Encoding", CONTENTTRANSFERENCODING);
		keywords.put("X-From", XFROM);
		keywords.put("X-To", XTO);
		keywords.put("X-cc", XCC);
		keywords.put("X-bcc", XBCC);
		keywords.put("X-Folder", XFOLDER);
		keywords.put("X-Origin", XORIGIN);
		keywords.put("X-FileName", XFILENAME);
	}

	// Recognize a token
	@Override
	public boolean lex() {
		if (super.lex()) {
			return true;
		}
		// Recognize String
		if (Character.isLetterOrDigit(this.lookahead)) {
			do {
				this.read();
			} while (!Character.isWhitespace(this.lookahead)
					&& Character.isLetterOrDigit(this.lookahead)
					|| this.lookahead == '-');
			// - and : to recognize header
			if (this.lookahead == ':') {
				if (keywords.containsKey(this.getLexeme())) {
					this.token = HEADER;
					return true;
				}
			}
			this.token = STRING;
			return true;
		}
		super.lexGeneral();
		return true;
	}
}
