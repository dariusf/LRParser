package parser;

public interface ParseTableAction {
}

class Shift implements ParseTableAction {
	public int state;
	public Shift(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Shift [state=" + state + "]";
	}
}

class Reduce implements ParseTableAction {
	public int rule;
	public Reduce(int rule) {
		this.rule = rule;
	}
	@Override
	public String toString() {
		return "Reduce [rule=" + rule + "]";
	}
}

class Transition implements ParseTableAction {
	public int state;
	public Transition(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Transition [state=" + state + "]";
	}
}

class Accept implements ParseTableAction {
	public Accept() {
	}
	@Override
	public String toString() {
		return "Accept []";
	}
}
