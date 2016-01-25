package syntax;

import token.Symbol;
import token.Token;

public class Identifier implements Token, Expression {
	public String value;
	
	public Identifier(String id) {
		this.value = id;
	}
	
	@Override
	public Symbol toSymbol() {
		return Symbol.ID;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public Expression eval(Environment e) {
		return e.lookup(value);
	}

	@Override
	public String toJSON() {
		return "{\"name\": \"" + toString() + "\"}";
	}
}
