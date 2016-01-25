package parser;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

import token.Symbol;



public class ItemSet {

	// private
	HashSet<Item> items;
	
	public ItemSet() {
	}
	
	public Item getFullyAdvancedItem() {
		// TODO is it guaranteed that there's only one of these?
		for (Item i : items) {
			if (!i.hasMoreSymbols()) {
				return i;
			}
		}
		return null;
	}
	
	public ItemSet nextItemSet(Symbol transitionSymbol, ArrayList<Production> productions) {
		ItemSet result = new ItemSet();
		
		result.items = new HashSet<>();
		for (Item i : items) {
			if (!i.hasMoreSymbols()) continue;
			if (i.nextSymbol() == transitionSymbol) {
				result.items.add(i.shiftRight());
			}
		}
		result.closure(result.items, productions);
		
		return result;
	}
	
	public HashSet<Symbol> possibleTransitions() {
		assert items != null;
		
		HashSet<Symbol> result = new HashSet<>();
		
		for (Item i : items) {
			if (!i.hasMoreSymbols()) continue;
			result.add(i.nextSymbol());
		}
		
		return result;
	}
	
	public ArrayList<Item> closure(HashSet<Item> items, ArrayList<Production> productions) {
		Stack<Item> considered = new Stack<>();
		considered.addAll(items);

		HashSet<Item> result = new HashSet<>();

		while (considered.size() > 0) {
			Item current = considered.pop();
			result.add(current);

			ArrayList<Item> closedCandidates = new ArrayList<>();
			for (Production p : productions) {
				if (!current.hasMoreSymbols()) continue;
				if (p.LHS == current.nextSymbol()) {
					closedCandidates.add(new Item(p));
				}
			}
			for (Item i : closedCandidates) {
				if (!result.contains(i)) {
					considered.add(i);
				}
			}
		}

		this.items = result;
		return new ArrayList<>(result);
	}

	@Override
	public String toString() {
		return "ItemSet [items=" + items.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
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
		ItemSet other = (ItemSet) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}
}
