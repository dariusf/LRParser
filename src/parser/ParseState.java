package parser;

import token.Symbol;

public class ParseState {
	int state;
	Symbol symbol;
	
	public ParseState(int state, Symbol token) {
		this.state = state;
		this.symbol = token;
	}
}
