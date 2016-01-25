package syntax;

import token.Symbol;
import token.Token;

public class IfExpression implements Token, Expression {

    public Expression condition;
    public Expression thenExpression;
    public Expression elseExpression;

    public IfExpression(Token c, Token t, Token e) {
        condition = (Expression) c;
        thenExpression = (Expression) t;
        elseExpression = (Expression) e;
    }
    
	@Override
	public Symbol toSymbol() {
		return Symbol.IfExpr;
	}
	
    @Override
    public String toString() {
    	return "if " + condition.toString() + " then " + thenExpression.toString() + " else " + elseExpression.toString();
    }

	@Override
	public Expression eval(Environment e) {
		BooleanConstant condition = (BooleanConstant) this.condition.eval(e);
		if (condition.value) {
			return thenExpression.eval(e);
		}
		else {
			return elseExpression.eval(e);
		}
	}

	@Override
	public String toJSON() {
		return "{\"name\": \"conditional\", \"children\": [" + condition.toJSON() + ", " + thenExpression.toJSON() + ", " + elseExpression.toJSON() + "]}";
	}

}
