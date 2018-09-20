package edu.kit.ipd.pp.prolog.vipr.model.parser;

/**
 * Logisch zusammengehörige Einheit im Quellcode.
 */
public class Token {

    /**
     * Die verschiedenen Arten eines Tokens.
     */
    enum TokenType {

    /**
     * !
     */
    EXCLAMATION,

    /**
     * [a-z][a-zA-Z0-9]*
     */
    IDENTIFIER,

    /**
     * [A-Z][a-zA-Z0-9]*
     */
    VARIABLE,

    /**
     * [0-9]+
     */
    NUMBER,

    /**
     * (
     */
    LP,

    /**
     * )
     */
    RP,

    /**
     * is
     */
    IS,

    /**
     * =
     */
    EQ,

    /**
     * ,
     */
    COMMA,

    /**
     * .
     */
    DOT,

    /**
     * :-
     */
    COLON_MINUS,

    /**
     * [
     */
    LB,

    /**
     * ]
     */
    RB,

    /**
     * |
     */
    BAR,

    /**
     * +
     */
    PLUS,

    /**
     * -
     */
    MINUS,

    /**
     * *
     */
    STAR,

    /**
     * <
     */
    LESS,

    /**
     * =<
     */
    EQ_LESS,

    /**
     * >
     */
    GREATER,

    /**
     * >=
     */
    GREATER_EQ,

    /**
     * =:=
     */
    EQ_COLON_EQ,

    /**
     * =\=
     */
    EQ_BS_EQ,

    /**
     * Pseudo-Token, falls das Ende des Programms erreicht ist.
     */
    EOF
    }

    /**
     * Die Art dieses Tokens.
     */
    private final TokenType type;

    /**
     * Der Text dieses Tokens im Quellcode.
     */
    private final String text;

    /**
     * Die Zeile, in der sich dieses Token befindet.
     */
    private final int line;

    /**
     * Die Spalte, in der dieses Token beginnt.
     */
    private final int col;

    /**
     * Die Position, an der dieses Token beginnt.
     */
    private int pos;

    /**
     * Erstellt ein Token mir gegebener Art, Text, Zeile und Spalte.
     * 
     * @param type
     *            Die Art des Tokens.
     * @param text
     *            Der Text des Tokens im Quellcode.
     * @param line
     *            Die Zeile, in der sich das Token befindet.
     * @param col
     *            Die Spalte, in der das Token beginnt.
     * @param pos
     *            Die Position, an der das Token beginnt.
     */
    public Token(TokenType type, String text, int line, int col, int pos) {
        this.type = type;
        this.text = text;
        this.line = line;
        this.col = col;
        this.pos = pos;
    }

    /**
     * Gibt die Art dieses Tokens zurück.
     * 
     * @return Die Art dieses Tokens.
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Gibt den Text dieses Tokens im Quellcode zurück.
     * 
     * @return Der Text dieses Tokens im Quellcode.
     */
    public String getText() {
        return text;
    }

    /**
     * Gibt die Zeile zurück, in der sich dieses Token befindet.
     * 
     * @return Die Zeile dieses Tokens.
     */
    public int getLine() {
        return line;
    }

    /**
     * Gibt die Spalte zurück, in der dieses Token beginnt.
     * 
     * @return Die Spalte, in der dieses Token beginnt.
     */
    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return type + "(\"" + text + "\")";
    }

    /**
     * Gibt die Position zurück, an der dieses Token beginnt.
     * 
     * @return Die Position, an der dieses Token beginnt.
     */
    public int getPos() {
        return pos;
    }
}
