package test;

import static org.junit.Assert.*;
import org.junit.Test;
import syntax.*;

public class Tests {

	public static final int TEST_CASE_COUNT = 14;
	
	@Test
	public void test() {
		assertEquals(Toyscript.eval("1").toString(), new NumberConstant(1).toString());
		assertEquals(Toyscript.eval("1.5").toString(), new NumberConstant(1.5).toString());
		
		for (int i=0; i<TEST_CASE_COUNT; ++i) {
			String input = Utility.readFile("test\\test"+i+".in");
			String output = Utility.readFile("test\\test"+i+".out");
			if (output != null) {
				assertEquals(Toyscript.eval(input).toString().trim(), output.trim());
			}
			else {
				System.out.println("No test case for test"+i+" yet");
				Expression result = Toyscript.eval(input);
				if (result == null) {
					System.out.println("Problem running test " +i);
				}
				else {
					String testResult = result.toString().trim();
					System.out.println("Example output created for test " +i+": " +testResult);
					Utility.writeFile("test\\test"+i+".out", testResult);
				}
			}
		}
	}
}
