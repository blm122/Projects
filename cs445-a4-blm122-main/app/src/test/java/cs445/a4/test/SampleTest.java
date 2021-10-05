package cs445.a4.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs445.a4.HashingLookup;
import cs445.a4.LookupInterface;

/**
 * A class that provides sample test of the HashingLookup data structure
 * @author William C. Garrison III
 */

public class SampleTest {
    // Initialize any global reference variables/final values
    LookupInterface<String, String> strToStr;
    LookupInterface<Integer, String> intToStr;
    static final String[] strings = {
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "ten",
            "eleven",
            "twelve",
            "thirteen",
            "fourteen",
            "fifteen",
            "sixteen",
            "seventeen",
            "eighteen",
            "nineteen",
            "twenty",
    };

    /**
     * This method is called after each test. Used to reset all
     * global variables after tests
     */
    @AfterEach
    public void teardown() {
        strToStr = null;
        intToStr = null;
    }

    @Test
    @DisplayName("HashingLookup() test")
    void testDefaultConstructor() {
        strToStr = new HashingLookup<String, String>();
        intToStr = new HashingLookup<Integer, String>();
        assertTrue(strToStr.isEmpty(), "New lookup not empty");
        assertTrue(intToStr.isEmpty(), "New lookup not empty");
        assertEquals(0, strToStr.getSize(), "New lookup not size 0");
        assertEquals(0, intToStr.getSize(), "New lookup not size 0");
    }

    @Test
    @DisplayName("HashingLookup(int) test")
    void testCapacityConstructor() {
        strToStr = new HashingLookup<String, String>(10);
        intToStr = new HashingLookup<Integer, String>(1000);
        assertTrue(strToStr.isEmpty(), "New lookup not empty");
        assertTrue(intToStr.isEmpty(), "New lookup not empty");
        assertEquals(0, strToStr.getSize(), "New lookup not size 0");
        assertEquals(0, intToStr.getSize(), "New lookup not size 0");
    }

    @Test
    @DisplayName("getSize() test")
    void testGetSize() {
        strToStr = new HashingLookup<String, String>();
        intToStr = new HashingLookup<Integer, String>(10);
        strToStr.add("test", "test2");
        intToStr.add(10, "the number 10");
        intToStr.add(7, "another number, 7");
        assertEquals(1, strToStr.getSize(), "Lookup not size 1 after adding 1 pair");
        assertEquals(2, intToStr.getSize(), "Lookup not size 2 after adding 2 pairs");
    }

    @Test
    @DisplayName("add(I, C) test")
    void testAdd() {
        strToStr = new HashingLookup<String, String>();
        intToStr = new HashingLookup<Integer, String>(10);
        for (int i = 0; i < strings.length; i++) {
            assertTrue(strToStr.add(strings[i], strings[(i+1)%strings.length]),
                    "Add did not return true when expected");
            assertTrue(intToStr.add(i, strings[i]), "Add did not return true when expected");
        }
        for (int i = strings.length - 1; i >= 0; i--) {
            assertFalse(strToStr.add(strings[i], ""), "Add did not return false when expected");
            assertFalse(intToStr.add(i, ""), "Add did not return false when expected");
        }
        assertEquals(20, strToStr.getSize(), "Lookup not size 20 after adding 20 pairs");
        assertEquals(20, intToStr.getSize(), "Lookup not size 20 after adding 20 pairs");
        assertThrows(NullPointerException.class, () -> {
            strToStr.add(null, "");
        }, "Lookup did not throw NullPointerException when identifier is null");
        assertThrows(NullPointerException.class, () -> {
            intToStr.add(null, "");
        }, "Lookup did not throw NullPointerException when identifier is null");
    }

    @Test
    @DisplayName("getAllIdentifiers() test")
    void testGetAllIdentifiers() {
        strToStr = new HashingLookup<String, String>(10);
        intToStr = new HashingLookup<Integer, String>();
        assertEquals(0, strToStr.getAllIdentifiers().length,
                "New lookup does not return a 0-size array.");
        for (int i = 0; i < strings.length; i++) {
            strToStr.add(strings[i], strings[(i+1)%strings.length]);
            intToStr.add(i, strings[i]);
        }
        Object[] strIds = strToStr.getAllIdentifiers();
        Object[] intIds = intToStr.getAllIdentifiers();
        assertEquals(20, strIds.length, "Lookup did not return a 20-size array after 20 adds.");
        assertEquals(20, intIds.length, "Lookup did not return a 20-size array after 20 adds.");
        for (int i = strings.length - 1; i >= 0; i--) {
            boolean sFound = false;
            boolean iFound = false;
            for (int j = 0; j < strIds.length; j++) {
                if (strIds[j].equals(strings[i])) sFound = true;
                if (intIds[j].equals(i)) iFound = true;
            }
            assertTrue(sFound, "Expected identifier not found in array.");
            assertTrue(iFound, "Expected identifier not found in array.");
        }
    }

}

