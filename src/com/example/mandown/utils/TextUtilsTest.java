package com.example.mandown.utils;

import org.junit.Test;

import junit.framework.TestCase;

public class TextUtilsTest extends TestCase {
	
	@Test
	public void assertNullIsReturnedForNullEntry(){
		assertTrue(null == TextUtils.getFileNameWithoutExtension(null));
	}
	
	@Test
	public void testSeperatingNameFromExtension(){
		String filename = "Filename.txt";
		//assertEquals("Filename", TextUtils.getFileNameWithoutExtension(filename));
	}
}
