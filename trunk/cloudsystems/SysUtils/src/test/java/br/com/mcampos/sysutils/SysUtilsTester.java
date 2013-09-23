package br.com.mcampos.sysutils;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import junit.framework.TestCase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SysUtilsTester extends TestCase {

	public SysUtilsTester() {
		super("Teste do SysUtils");
	}

	public SysUtilsTester(String name) {
		super(name);
	}

	@BeforeClass
	public static void testSetup() {
		System.out.println ( "Setting up SysUtilsTester" );
	}

	@AfterClass
	public static void testCleanup() {
		System.out.println ( "Cleaning up SysUtilsTester" );
	}

	@Test
	public void testTrim() {

		String stringToTest = " *This is a Test* ";
		stringToTest = SysUtils.trim(stringToTest);
		assertEquals("Expecting: *This is a Test*", "*This is a Test*", stringToTest);

		stringToTest = null;
		stringToTest = SysUtils.trim(stringToTest);
		assertEquals("Expecting: null", null, stringToTest);

		stringToTest = "*This is a Test*";
		stringToTest = SysUtils.trim(stringToTest);
		assertEquals("Expecting: *This is a Test*", "*This is a Test*", stringToTest);

		stringToTest = "";
		stringToTest = SysUtils.trim(stringToTest);
		assertEquals("Expecting null", null, stringToTest);

		stringToTest = "          ";
		stringToTest = SysUtils.trim(stringToTest);
		assertEquals("Expecting empty but not null", "", stringToTest);

	}

	@Test
	public void testIsEmptyString() {
		String stringToTest = " *This is a Test* ";
		assertFalse(SysUtils.isEmpty(stringToTest));

		stringToTest = " ";
		assertFalse(SysUtils.isEmpty(stringToTest));

		stringToTest = "";
		assertTrue(SysUtils.isEmpty(stringToTest));

		stringToTest = null;
		assertTrue(SysUtils.isEmpty(stringToTest));
	}

	@Test
	public void testIsEmptyAfterTrim() {
		String s = " *This is a Test* ";
		assertFalse ( SysUtils.isEmptyAfterTrim(s));
		s = null;
		assertTrue ( SysUtils.isEmptyAfterTrim(s));
		s = "*This is a Test*";
		assertFalse ( SysUtils.isEmptyAfterTrim(s));
		s = "";
		assertTrue ( SysUtils.isEmptyAfterTrim(s));
		s = "          ";
		assertTrue ( SysUtils.isEmptyAfterTrim(s));
	}

	@Test
	public void testIsZero() {
		assertTrue ( SysUtils.isZero(0) );
		assertTrue ( SysUtils.isZero(null) );
		assertFalse ( SysUtils.isZero(1) );
		assertFalse ( SysUtils.isZero(-1) );
	}

	@Test
	public void testIsNull() {
		assertTrue ( SysUtils.isNull(null) );
		assertFalse ( SysUtils.isNull(1) );
		assertFalse ( SysUtils.isNull("") );
	}

	@Test
	public void testNowTimestamp() {
		assertNotNull(SysUtils.nowTimestamp());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testIsEmptyCollectionOfQ() {
		Collection<Object> c = null;
		assertTrue ( SysUtils.isEmpty(c) );
		c = Collections.EMPTY_LIST;
		assertTrue ( SysUtils.isEmpty(c) );
		c = Collections.emptyList();
		assertTrue ( SysUtils.isEmpty(c) );
		c = new ArrayList<Object>();
		assertTrue ( SysUtils.isEmpty(c) );
		c.add(SysUtils.nowTimestamp());
		assertFalse ( SysUtils.isEmpty(c) );
	}

	@Test
	public void testFormatDateForSQLSearch() {
	}

	@Test
	public void testFormatDateDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormatDateDateString() {
		fail("Not yet implemented");
	}

	@Test
	public void testToUpperCase() {
		fail("Not yet implemented");
	}

	@Test
	public void testToLowerCase() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnaccent() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHexString() {
		fail("Not yet implemented");
	}

	@Test
	public void testItrim() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDeclaredFields() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearch() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDeclaredField() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDeclaredMethod() {
		fail("Not yet implemented");
	}

	@Test
	public void testInvokeMethod() {
		fail("Not yet implemented");
	}

	@Test
	public void testNewInstanceClassOfT() {
		fail("Not yet implemented");
	}

	@Test
	public void testNewInstanceClassOfTClassOfQArrayObjectArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetFieldValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testAdd() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemove() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetExtension() {
		fail("Not yet implemented");
	}

	@Test
	public void testReadByteFromStream() {
		fail("Not yet implemented");
	}

	@Test
	public void testParseDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testLocale_BR() {
		fail("Not yet implemented");
	}

	@Test
	public void testParseNumber() {
		fail("Not yet implemented");
	}

	@Test
	public void testParseBigDecimal() {
		fail("Not yet implemented");
	}

	@Test
	public void testParseInteger() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormatStringStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormatStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormatCEP() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormatPhone() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormatCPF() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormatCNPJ() {
		fail("Not yet implemented");
	}

}
