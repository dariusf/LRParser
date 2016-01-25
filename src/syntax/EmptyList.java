package syntax;

import token.Symbol;
import token.Token;

public class EmptyList implements Token, Expression {
	@Override
	public String toString() {
		return "[]";
	}
	
	@Override
	public Expression eval(Environment e) {
		return this;
	}
	
	@Override
	public String toJSON() {
		return "{\"name\": \"" + toString() + "\"}";
	}


	@Override
	public Symbol toSymbol() {
		return Symbol.EMPTYLIST;
	}
}
