package edu.kit.ipd.pp.prolog.vipr.model.parser;

import java.util.HashMap;

import edu.kit.ipd.pp.prolog.vipr.model.parser.Token.TokenType;

/**
 * Diese Klasse lext ein String-Programm in einzelne Token. Die Tokens werden
 * einzeln nach Anfrage des Parsers gelext.
 *
 */
public class PrologLexer {

    /**
     * Ein String aus Zeichen, die Teile eines Operators sein könnten.
     */
    private static final String SYMBOLS = ":-=<>\\";

    /**
     * Bildet einen String bestehend aus Zeichen von symbols auf den korrekten
     * Operator ab. Operatoren, die nur aus einem Zeichen bestehen, werden direkt
     * gelext und kommen hier nicht rein.
     */
    private static final HashMap<String, TokenType> SYMBOL_STRING_TO_TOKEN = new HashMap<>();

    static {
        SYMBOL_STRING_TO_TOKEN.put(":-", TokenType.COLON_MINUS);
        SYMBOL_STRING_TO_TOKEN.put("=", TokenType.EQ);
        SYMBOL_STRING_TO_TOKEN.put("<", TokenType.LESS);
        SYMBOL_STRING_TO_TOKEN.put("=<", TokenType.EQ_LESS);
        SYMBOL_STRING_TO_TOKEN.put(">", TokenType.GREATER);
        SYMBOL_STRING_TO_TOKEN.put(">=", TokenType.GREATER_EQ);
        SYMBOL_STRING_TO_TOKEN.put("=:=", TokenType.EQ_COLON_EQ);
        SYMBOL_STRING_TO_TOKEN.put("=\\=", TokenType.EQ_BS_EQ);
    }

    /**
     * Das Programm als String.
     */
    private final String program;

    /**
     * Die aktuelle Position im Programm.
     */
    private int pos = 0;

    /**
     * Die aktuelle Zeile im Programm.
     */
    private int line = 1;

    /**
     * Die aktuelle Spalte im Programm.
     */
    private int col = 1;

    /**
     * Konstruktor des Lexers.
     * 
     * @param program
     *            Das Programm, das zum Lexen übergeben wird.
     */
    public PrologLexer(String program) {
        this.program = program;
    }

    private void advance() {
        if (program.charAt(pos) == '\n') {
            line += 1;
            col = 1;
        } else {
            col += 1;
        }
        pos += 1;
    }

    private void lexComment() {
        while (pos < program.length() && program.charAt(pos) != '\n') {
            pos += 1;
            col += 1;
        }
        if (pos < program.length()) {
            advance();
        }
    }

    /**
     * Lext das nächste Token und gibt es zurück.
     * 
     * @return Das nächste Token.
     * @throws ParseException
     *             Wenn ein Parserfehler auftritt.
     */
    public Token nextToken() throws ParseException {
        while (pos < program.length() && (Character.isWhitespace(program.charAt(pos)) || program.charAt(pos) == '%')) {
            // ignoriere Whitespace und Kommentare
            if (program.charAt(pos) == '%') {
                lexComment();
            } else {
                advance();
            }
        }
        if (pos >= program.length()) {
            // Programm hat geendet, gib EOF zurück
            return new Token(TokenType.EOF, "", line, col, pos);
        }
        Token t;
        char c = program.charAt(pos);
        switch (c) {
        case ':':
        case '=':
        case '<':
        case '>': {
            // diese Operatoren können aus mehreren Symbolen bestehen
            int colStart = col;
            int posStart = pos;
            StringBuilder sb = new StringBuilder();
            do {
                sb.append(program.charAt(pos));
                advance();
            } while (pos < program.length() && SYMBOLS.indexOf(program.charAt(pos)) > -1);
            TokenType type = SYMBOL_STRING_TO_TOKEN.get(sb.toString());
            if (type == null) {
                throw new ParseException("Error in line: " + line + "; column: " + colStart
                        + System.getProperty("line.separator") + "Operator '" + sb.toString() + "' not recognized!");
            }
            return new Token(type, sb.toString(), line, colStart, posStart);
        }
        // Tokens mit einem Schriftzeichen
        case '.':
            t = new Token(TokenType.DOT, ".", line, col, pos);
            advance();
            return t;
        case ',':
            t = new Token(TokenType.COMMA, ",", line, col, pos);
            advance();
            return t;
        case '(':
            t = new Token(TokenType.LP, "(", line, col, pos);
            advance();
            return t;
        case ')':
            t = new Token(TokenType.RP, ")", line, col, pos);
            advance();
            return t;
        case '[':
            t = new Token(TokenType.LB, "[", line, col, pos);
            advance();
            return t;
        case ']':
            t = new Token(TokenType.RB, "]", line, col, pos);
            advance();
            return t;
        case '|':
            t = new Token(TokenType.BAR, "|", line, col, pos);
            advance();
            return t;
        case '+':
            t = new Token(TokenType.PLUS, "+", line, col, pos);
            advance();
            return t;
        case '-':
            t = new Token(TokenType.MINUS, "-", line, col, pos);
            advance();
            return t;
        case '*':
            t = new Token(TokenType.STAR, "*", line, col, pos);
            advance();
            return t;
        case '!':
            t = new Token(TokenType.EXCLAMATION, "!", line, col, pos);
            advance();
            return t;
        default:
            int colStart = col;
            int posStart = pos;
            if (Character.isLetter(c)) {
                // Variable, identifier oder "is"
                StringBuilder sb = new StringBuilder();
                do {
                    sb.append(program.charAt(pos));
                    advance();
                } while (pos < program.length() && Character.isLetterOrDigit(program.charAt(pos)));
                String s = sb.toString();
                TokenType type;
                if ("is".equals(s)) {
                    type = TokenType.IS;
                } else if (Character.isUpperCase(c)) {
                    type = TokenType.VARIABLE;
                } else {
                    type = TokenType.IDENTIFIER;
                }
                return new Token(type, s, line, colStart, posStart);
            } else if (Character.isDigit(c)) {
                // Zahl
                StringBuilder sb = new StringBuilder();
                do {
                    sb.append(program.charAt(pos));
                    advance();
                } while (pos < program.length() && Character.isDigit(program.charAt(pos)));
                return new Token(TokenType.NUMBER, sb.toString(), line, colStart, posStart);
            } else {
                throw new ParseException("Error in line: " + line + "; column: " + colStart
                        + System.getProperty("line.separator") + "Illegal character '" + program.charAt(pos) + "'");
            }
        }
    }
}