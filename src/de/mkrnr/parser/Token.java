package de.mkrnr.parser;

public enum Token {
	// general token
	OBJECT, STRING, OTHER, WS, LINESEPARATOR, URI, EOF,
	//
	FULLSTOP, COMMA, SEMICOLON, COLON, EQUALITYSIGN, ASTERISK, HYPHEN, DASH, UNDERSCORE, EXCLAMATIONMARK, QUESTIONMARK, QUOTATIONMARK, VERTICALBAR, SLASH,
	//
	ROUNDBRACKET, CLOSEDROUNDBRACKET, CURLYBRACKET, CLOSEDCURLYBRACKET, SQUAREDBRACKET, CLOSEDSQUAREDBRACKET, AND, AT, LESSTHAN, GREATERTHAN,

	// structure token
	TITLE, CLOSEDTITLE,
	//
	TEXT, CLOSEDTEXT,

	// Acquis token
	TUV, CLOSEDTUV,
	//
	SEG, CLOSEDSEG,
	//
	BODY, CLOSEDBODY,

	// Enron token
	HEADER, MESSAGEID, DATE, RE, FROM, TO, CC, BCC, SUBJECT, MIMEVERSION, CONTENTTYPE, CONTENTTRANSFERENCODING, XFROM, XTO, XCC, XBCC, XFOLDER, XORIGIN, XFILENAME,

	// Reuters token
	P, CLOSEDP,

	// Wikipedia token
	EHH, // EHH = !--
	HH, // HH = --
	//
	ELEMENT, CLOSEDELEMENT,
	//
	LINK, LABELEDLINK,
	//
	PAGE, REF, BR,
	//
	CLOSEDPAGE, CLOSEDREF,
	//
	DISAMBIGUATION, TOC,
	//
	AUDIO // {Audio|...|...}
}
