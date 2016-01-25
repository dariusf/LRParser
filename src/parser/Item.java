package parser;
import java.util.ArrayList;

import token.Symbol;




public class Item extends Production {
	
	/**
	 * An item is a production extended with a marker
	 */

	int marker = 0;

	public Item(Symbol LHS, ArrayList<Symbol> RHS, int marker) {
		super(LHS, RHS);
		this.marker = marker;
	}

	public Item(Production p) {
		this(p.LHS, p.RHS, 0);
	}

	public Item shiftRight() {
		return new Item(LHS, RHS, Math.min(marker + 1, RHS.size()));
	}

	public boolean hasMoreSymbols() {
		return this.marker < RHS.size();
	}

	public Symbol nextSymbol() {
		return this.RHS.get(marker);
	}
	
	public boolean correspondsToProduction(Production p) {
		return p.LHS == this.LHS && p.RHS.equals(this.RHS);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + marker;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (marker != other.marker)
			return false;
		return true;
	}

	public String toString() {
		ArrayList<Symbol> rhs = new ArrayList<>(this.RHS);
		rhs.add(this.marker, Symbol.MARKER);

		StringBuilder sb = new StringBuilder();
		for (Symbol s : rhs) {
			sb.append(s);
			sb.append(" ");
		}
		sb.deleteCharAt(sb.length() - 1);
		return LHS.toString() + " -> " + sb.toString();
	}

}
