package syntax;

import java.util.ArrayList;
import java.util.Vector;


import token.Symbol;
import token.Token;
import token.TokenList;

public class Application implements Expression, Token {
	public String functionName;
	public ArrayList<Expression> operands = new ArrayList<>();

	public Application(Token functionName, Token operands) {
		this.functionName = ((Identifier) functionName).value;
		if (operands != null) {
			ArrayList<Token> temp = ((TokenList) operands).tokens;
			for (Token t : temp) {
				this.operands.add((Expression) t);
			}
		}
	}

	@Override
	public Symbol toSymbol() {
		return Symbol.Application;
	}
	
	@Override
	public String toString() {
		StringBuilder arguments = new StringBuilder();
		if (operands.size() > 0) {
			for (Expression e : operands) {
				arguments.append(e);
				arguments.append(", ");
			}
			arguments.deleteCharAt(arguments.length()-1);
			arguments.deleteCharAt(arguments.length()-1);
		}
		return functionName + "(" + arguments.toString() + ")";
	}

	@Override
	public Expression eval(Environment e) {
		Environment ex = new Environment(e);
		FunctionExpression body = (FunctionExpression) e.lookup(functionName);
		ex.extendWith(body.closure);
		Vector<String> identifiers = body.identifiers;
		for (int i=0; i<identifiers.size(); ++i) {
			ex.add(identifiers.get(i), operands.get(i).eval(e));
		}
		return body.body.eval(ex);
	}

	@Override
	public String toJSON() {
		StringBuilder operands = new StringBuilder();
		for (Expression operand : this.operands) {
			operands.append(operand.toJSON());
			operands.append(", ");
		}
		operands.deleteCharAt(operands.length()-1);
		operands.deleteCharAt(operands.length()-1);
		return "{\"name\": \"" + functionName+ "()\", children: [" + operands.toString() + "]}";
	}

}
