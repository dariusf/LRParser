package parser;
import java.util.ArrayList;

import token.Symbol;



public class Production {
	
	/**
	 * A production is a rule, containing an LHS symbol and a set of RHS symbols
	 */
	
	Symbol LHS;
	ArrayList<Symbol> RHS;
	
	public Production(Symbol LHS, ArrayList<Symbol> RHS) {
		this.LHS = LHS;
		this.RHS = RHS;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Symbol s : RHS) {
			sb.append(s);
			sb.append(" ");
		}
		sb.deleteCharAt(sb.length()-1);
		return LHS.toString() + " -> " + sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((LHS == null) ? 0 : LHS.hashCode());
		result = prime * result + ((RHS == null) ? 0 : RHS.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Production other = (Production) obj;
		if (LHS != other.LHS)
			return false;
		if (RHS == null) {
			if (other.RHS != null)
				return false;
		} else if (!RHS.equals(other.RHS))
			return false;
		return true;
	}

	
}
