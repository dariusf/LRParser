package token;


public class Keyword implements Token {
	String keyword;
	public Keyword(String keyword) {
		this.keyword = keyword;
	}
	@Override
	public Symbol toSymbol() {
		switch (keyword) {
		case "let":
			return Symbol.LET;
		case "in":
			return Symbol.IN;
		case "if":
			return Symbol.IF;
		case "then":
			return Symbol.THEN;
		case "else":
			return Symbol.ELSE;
		case "fun":
			return Symbol.FUNCTION;
		case "->":
			return Symbol.ARROW;
		case "(":
			return Symbol.LBRACKET;
		case ")":
			return Symbol.RBRACKET;
		case "[":
			return Symbol.LSQBR;
		case "]":
			return Symbol.RSQBR;
		case ",":
			return Symbol.COMMA;
		}
		assert false;
		return null;
	}
	@Override
	public String toString() {
		return "Keyword [keyword=" + keyword + "]";
	}
}