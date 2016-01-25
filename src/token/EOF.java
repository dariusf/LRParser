package token;


public class EOF implements Token {
	public EOF() {
	}
	@Override
	public Symbol toSymbol() {
		return Symbol.EOF;
	}
	@Override
	public String toString() {
		return "EOF []";
	}
}