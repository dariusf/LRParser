package syntax;
import token.Symbol;
import token.Token;
import token.TokenList;

import java.util.ArrayList;
import java.util.Vector;



public class FunctionExpression implements Expression, Token {
	Vector<String> identifiers = new Vector<>();
	Expression body;
	Environment closure;
	
	public FunctionExpression(Token parameters, Token body) {
		assert parameters instanceof TokenList;
		ArrayList<Token> temp = ((TokenList) parameters).tokens;
		for (Token t : temp) {
			this.identifiers.add(((Identifier) t).value);
		}
		this.body = (Expression) body;
	}
	
	// copy constructor
	private FunctionExpression(FunctionExpression fe, Environment closure) {
		this.closure = closure;
		this.identifiers = fe.identifiers;
		this.body = fe.body;
	}
	
	public String stringifyArguments() {
		StringBuilder arguments = new StringBuilder();
		for (String id : identifiers) {
			arguments.append(id);
			arguments.append(", ");
		}
		arguments.deleteCharAt(arguments.length()-1);
		arguments.deleteCharAt(arguments.length()-1);
		return arguments.toString();
	}
	
	@Override
	public String toString() {
		return "fun (" + stringifyArguments() + ") -> " + body.toString();
	}

	@Override
	public Expression eval(Environment e) {
		return new FunctionExpression(this, new Environment(e));
	}
	
	@Override
	public Symbol toSymbol() {
		return Symbol.FunctionExpr;
	}

	@Override
	public String toJSON() {
		String argumentStructure = "{\"name\": \"" + stringifyArguments() + "\"}";
		String body = this.body.toJSON();
		return "{\"name\": \"function\", \"children\": [" + argumentStructure + ", " + body + "]}"; 
	}
}
