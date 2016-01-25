package syntax;
import java.util.HashMap;


public class Environment {
	private HashMap<String, Expression> table = new HashMap<>();
	
	public Environment() {
	}
	
	public Environment(Environment e) {
		for (String key : e.getTable().keySet()) {
			table.put(key, e.getTable().get(key));
		}
	}

	public void add(String id, Expression e) {
		if (table.containsKey(id)) {
			table.remove(id);
		}
		table.put(id, e);
	}
	
	public void extendWith(Environment e) {
		for (String key : e.getTable().keySet()) {
			add(key, e.lookup(key));
		}
	}

	public Expression lookup(String id) {
		if (table.containsKey(id)) {
			return table.get(id);
		}
		else {
			throw new IllegalArgumentException("Identifier " + id + " is unbound!");
		}
	}

	public HashMap<String, Expression> getTable() {
		return table;
	}

	@Override
	public String toString() {
		return "Environment [table=" + table.toString() + "]";
	}
}
