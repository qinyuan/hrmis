package qinyuan.lib.web;

import static org.junit.Assert.*;
import static qinyuan.lib.web.MyBasicAction.*;
import org.junit.Test;

public class MyBasicActionTest {

	@Test
	public void testEmpty() {
		assertTrue(empty(""));
		assertTrue(empty(null));
		assertFalse(empty(" "));
	}

	@Test
	public void testIsNull() {
		assertTrue(isNull(null));
		assertFalse(isNull(""));
	}

	@Test
	public void testIsNumeric() {
		assertTrue(isNumeric("12313"));
		assertTrue(isNumeric("0123.310"));
		assertFalse(isNumeric("aaa"));
		assertFalse(isNumeric("1.3.4"));
	}

	@Test
	public void testIsDate() {
		assertTrue(isDate("1989-01-02"));
		assertTrue(isDate("2012-2-29"));
		assertFalse(isDate("2011-2-29"));
		assertFalse(isDate("aaaa-aa-aa"));
	}

	@Test
	public void testParseDoubleString() {
		assertEquals(parseDouble("132"), 132, 1e-8);
	}

	@Test
	public void testParseDoubleStringArray() {
		String[] strs = { "11", "2.2", "3.5" };
		double[] nums = parseDouble(strs);
		for (int i = 0; i < nums.length; i++) {
			assertEquals(Double.parseDouble(strs[i]), nums[i], 1e-8);
		}
	}

	@Test
	public void testParseFloatString() {
		assertEquals(13.2, parseFloat("13.2"), 1e-6);
	}

	@Test
	public void testParseFloatStringArray() {
		String[] strs = { "11", "2.21", "0.53" };
		float[] nums = parseFloat(strs);
		for (int i = 0; i < nums.length; i++) {
			assertEquals(nums[i], Float.parseFloat(strs[i]), 1e-8);
		}
	}

	@Test
	public void testParseIntString() {
		assertEquals(10, parseInt("10"));
	}

	@Test
	public void testParseIntStringArray() {
		String[] strs = { "1", "2", "3" };
		int[] nums = parseInt(strs);
		for (int i = 0; i < nums.length; i++) {
			assertEquals(nums[i], Integer.parseInt(strs[i]));
		}
	}

}
