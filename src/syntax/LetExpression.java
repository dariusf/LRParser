package syntax;

import token.Symbol;
import token.Token;;

public class LetExpression implements Token, Expression {

    public String id;
    public Expression binding;
    public Expression in;

    public LetExpression(Token id, Token b, Token in) {
    	this.id = ((Identifier) id).value;
    	this.binding = (Expression) b;
    	this.in = (Expression) in;
    }
    
    @Override
    public String toString() {
    	return "let " + id + " = " + binding.toString() + " in\n  " + in.toString();
    }

	@Override
	public Symbol toSymbol() {
		return Symbol.LetExpr;
	}
	
	@Override
	public Expression eval(Environment e) {
		Environment ex = new Environment(e);
		ex.add(id, binding.eval(e));
		return in.eval(ex);
	}

	@Override
	public String toJSON() {
		return "{\"name\": \"let\", \"children\": [{\"name\": \"binding\", \"children\": [{\"name\": \""+id+"\"}, " + binding.toJSON() + "]}, " + in.toJSON() + "]}";
	}

}
