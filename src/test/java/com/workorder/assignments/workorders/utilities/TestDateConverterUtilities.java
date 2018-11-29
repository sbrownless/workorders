package com.workorder.assignments.workorders.utilities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Class:        TestDateConverterUtilities
 * Created By:   brownless
 * For Project:  workorder
 */

public class TestDateConverterUtilities {

  private static final String VALID_ISO_8601_DATE_FORMAT = "2016-02-28T15:36:22Z";
  private static final String VALID_ISO_8601_DATE_FORMAT_NO_TZ = "2016-02-28T15:36:22";
  private static final String VALID_ISO_8601_DATE_FORMAT_WITH_INVALID_DATE = "2017-02-29T15:36:22Z";
  private static final String INVALID_ISO_8601_DATE_FORMAT = "2016-0B-28T15:36:22Z";
  private static final String INVALID_ISO_8601_DATE_WITH_GOOD_FORMAT = "2016-13-28T15:36:22Z";

  private static final long VALID_TEST_DATE_IN_MILLIS = 1522021543000L;
  private static final ZonedDateTime VALID_TEST_DATE = ZonedDateTime.ofInstant (Instant.ofEpochMilli(VALID_TEST_DATE_IN_MILLIS), ZoneId.of("Z"));
  private static final String VALID_TEST_DATE_STRING = "2018-03-25T23:45:43Z";
  private static final String VALID_TEST_DATE__IN_FUTURE_STRING = "2020-03-25T23:45:43Z";


/*===============================================================================================
  Test the low level test for the date string format
  ================================================================================================*/

  @Test
  @DisplayName("Test valid ISO date string format")
  void testValidISODateStringTest () {
    assertTrue(DateConverterUtilities.isDateStringInIso8601Format(VALID_ISO_8601_DATE_FORMAT));
  }

  @Test
  @DisplayName("Test valid ISO date string format but with a invalid date value. Should pass as only format tested.")
  void testInValidISODateStringTest () {
    assertTrue(DateConverterUtilities.isDateStringInIso8601Format(VALID_ISO_8601_DATE_FORMAT_WITH_INVALID_DATE));
  }

  @Test
  @DisplayName("Test null ISO date string.")
  void testNullISODateStringTest () {
    assertFalse(DateConverterUtilities.isDateStringInIso8601Format(null));
  }

  @Test
  @DisplayName("Test empty ISO date string.")
  void testEmptyISODateStringForValidity () {
    assertFalse(DateConverterUtilities.isDateStringInIso8601Format(""));
  }

  @Test
  @DisplayName("Test invalid ISO date string.")
  void testInvalidISODateStringForValidity () {
    assertFalse(DateConverterUtilities.isDateStringInIso8601Format(INVALID_ISO_8601_DATE_FORMAT));
  }

 /*===============================================================================================
  Test the functionality of getting the date in ISO 8061 string format
  ================================================================================================*/

  @Test
  @DisplayName("Test conversion from a Date object to an ISO 8601 representation")
  void testGetDateInIsoStringFormat () {
    assertEquals (VALID_TEST_DATE_STRING , DateConverterUtilities.getDateInIso8601Format(VALID_TEST_DATE));
  }

  @Test
  @DisplayName("Test failed conversion from a null Date object")
  void testGetDateInIsoStringFormatwithnullValue () {
    assertThrows(IllegalArgumentException.class, ()-> DateConverterUtilities.getDateInIso8601Format(null));
  }

 /*===============================================================================================
  Test the functionality of getting a Date object from a string format
  ================================================================================================*/

  @Test
  @DisplayName("Test conversion from a String to a valid date object")
  void testGetDateFromIso8601StringFormat () {
    assertEquals (VALID_TEST_DATE.toLocalDateTime().toEpochSecond(ZoneOffset.UTC), DateConverterUtilities.getDateTimefromIso8601StringFormat(VALID_TEST_DATE_STRING).toLocalDateTime().toEpochSecond(ZoneOffset.UTC));
  }

  @Test
  @DisplayName("Test conversion from a String with no TZ to a valid date object")
  void testGetDateFromIso8601StringFormatNoTz () {
    assertThrows(IllegalArgumentException.class, ()-> DateConverterUtilities.getDateTimefromIso8601StringFormat(VALID_ISO_8601_DATE_FORMAT_NO_TZ));
  }

  @Test
  @DisplayName("Test conversion from an invalid String to a date object")
  void testGetDateFromInvalidIso8601StringFormat () {
    assertThrows(IllegalArgumentException.class, ()-> DateConverterUtilities.getDateTimefromIso8601StringFormat(INVALID_ISO_8601_DATE_FORMAT));
  }

  @Test
  @DisplayName("Test conversion from a null String to a date object")
  void testGetDateFromNullIso8601StringFormat () {
    assertThrows(IllegalArgumentException.class, ()-> DateConverterUtilities.getDateTimefromIso8601StringFormat(null));
  }

  @Test
  @DisplayName("Test conversion from an valid String but invalid date representation to a date object")
  void testGetDateFromInvalidDateInlIso8601StringFormat () {
    assertThrows(IllegalArgumentException.class, ()-> DateConverterUtilities.getDateTimefromIso8601StringFormat(INVALID_ISO_8601_DATE_WITH_GOOD_FORMAT));
  }

  @Test
  @DisplayName("Test conversion from an valid String but invalid non leap year representation to a date object")
  void testGetDateFromInvalidNonLeapYearDateInlIso8601StringFormat () {
    assertThrows(IllegalArgumentException.class, ()-> DateConverterUtilities.getDateTimefromIso8601StringFormat(VALID_ISO_8601_DATE_FORMAT_WITH_INVALID_DATE));
  }

  @Test
  @DisplayName("Test conversion from a valid String but in the future to a date object, this is not allowed")
  void testGetDateFromDateInTheFutureInlIso8601StringFormat () {
    assertThrows(IllegalArgumentException.class, ()-> DateConverterUtilities.getDateTimefromIso8601StringFormat(VALID_TEST_DATE__IN_FUTURE_STRING));  }
}
