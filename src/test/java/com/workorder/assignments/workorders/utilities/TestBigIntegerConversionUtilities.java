package com.workorder.assignments.workorders.utilities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Class:        TestBigIntegerConversionUtilities
 * Created By:   brownless
 * For Project:  workorder
 */

public class TestBigIntegerConversionUtilities {

  private final String NEGATIVE_VALUE = "-1";
  private final String SMALLEST_VALUE = "1";
  private final String ZERO_VALUE = "0000";
  private final String VALID_VALUE = "128765";
  private final String MAXIMUM_VALUE = "9223372036854775807";
  private final String MAXIMUM_VALUE_PLUS_ONE = "9223372036854775808";
  private final String TOO_MANY_CHARACTERS = "922337203685477583421";
  private final String NON_NUMERIC_CHARACTER = "1287E65";
  private final String ALL_NON_NUMERIC_CHARACTERS = "ETuvH";

  /* ============================================================================
      TEST isStringValidId method
   ==============================================================================*/
  @Test
  @DisplayName("Test Negative value for id")
  void testNegativeIdValue () {
    assertFalse(BigIntegerConversionUtilities.isStringValidId(NEGATIVE_VALUE));
  }

  @Test
  @DisplayName("Test zero value for id")
  void testZeroIdValue () {
    assertFalse(BigIntegerConversionUtilities.isStringValidId(ZERO_VALUE));
  }

  @Test
  @DisplayName("Test valid value for id")
  void testValidIdValue () {
    assertTrue(BigIntegerConversionUtilities.isStringValidId(VALID_VALUE));
  }

  @Test
  @DisplayName("Test maximum valid value for id")
  void testMaximumValidIdValue () {
    assertTrue(BigIntegerConversionUtilities.isStringValidId(MAXIMUM_VALUE));
  }

  @Test
  @DisplayName("Test smallest valid value for id")
  void testMinimumValidIdValue () {
    assertTrue(BigIntegerConversionUtilities.isStringValidId(SMALLEST_VALUE));
  }

  @Test
  @DisplayName("Test maximum valid value plus one for id")
  void testMaximumValidIdValuePlusOne () {
    assertFalse(BigIntegerConversionUtilities.isStringValidId(MAXIMUM_VALUE_PLUS_ONE));
  }

  @Test
  @DisplayName("Test too many characters for id")
  void testTooManyCharactersIdValuePlusOne () {
    assertFalse(BigIntegerConversionUtilities.isStringValidId(TOO_MANY_CHARACTERS));
  }

  @Test
  @DisplayName("Test non numeric character in id")
  void testNonNumbericIdValuePlusOne () {
    assertFalse(BigIntegerConversionUtilities.isStringValidId(NON_NUMERIC_CHARACTER));
  }

  @Test
  @DisplayName("Test all non numeric characters for id")
  void testAllNonNumericIdValuePlusOne () {
    assertFalse(BigIntegerConversionUtilities.isStringValidId(ALL_NON_NUMERIC_CHARACTERS));
  }

  @Test
  @DisplayName("Test null value plus one for id")
  void testNullIdValuePlusOne () {
    assertFalse(BigIntegerConversionUtilities.isStringValidId(null));
  }

  @Test
  @DisplayName("Test empty value plus one for id")
  void testEmptyIdValuePlusOne () {
    assertFalse(BigIntegerConversionUtilities.isStringValidId(""));
  }

   /* ============================================================================
      TEST isStringValidId method
   ==============================================================================*/

  @Test
  @DisplayName("Test negative work order id fails")
  void testNegativeValue () {
    assertThrows(IllegalArgumentException.class, ()-> BigIntegerConversionUtilities.getConverttoValidWorkOrderId(NEGATIVE_VALUE));
  }

  @Test
  @DisplayName("Test minimum valid work order id is produced")
  void testMinimumValidValue () {
    assertEquals (1L, BigIntegerConversionUtilities.getConverttoValidWorkOrderId(SMALLEST_VALUE).longValue());
  }

  @Test
  @DisplayName("Test zero work order id fails")
  void testZeroValue () {
    assertThrows(IllegalArgumentException.class, ()-> BigIntegerConversionUtilities.getConverttoValidWorkOrderId(ZERO_VALUE));
  }

  @Test
  @DisplayName("Test valid work order id is produced")
  void testValidValue () {
    assertEquals (128765L, BigIntegerConversionUtilities.getConverttoValidWorkOrderId(VALID_VALUE).longValue());
  }

  @Test
  @DisplayName("Test maximum valid work order id is produced")
  void testMaximumValidValue () {
    assertEquals (9223372036854775807L, BigIntegerConversionUtilities.getConverttoValidWorkOrderId(MAXIMUM_VALUE).longValue());
  }

  @Test
  @DisplayName("Test maximum value plus one id fails")
  void testMaximumValuePlusOne () {
    assertThrows(IllegalArgumentException.class, ()-> BigIntegerConversionUtilities.getConverttoValidWorkOrderId(MAXIMUM_VALUE_PLUS_ONE));
  }

  @Test
  @DisplayName("Test too long id fails")
  void testTooManyCharactersInId () {
    assertThrows(IllegalArgumentException.class, ()-> BigIntegerConversionUtilities.getConverttoValidWorkOrderId(TOO_MANY_CHARACTERS));
  }

  @Test
  @DisplayName("Test id with non numeric character fails")
  void testNonNumericCharactersInId () {
    assertThrows(IllegalArgumentException.class, ()-> BigIntegerConversionUtilities.getConverttoValidWorkOrderId(NON_NUMERIC_CHARACTER));
  }

  @Test
  @DisplayName("Test id with all non numeric characters fails")
  void testAllNonNumericCharactersInId () {
    assertThrows(IllegalArgumentException.class, ()-> BigIntegerConversionUtilities.getConverttoValidWorkOrderId(ALL_NON_NUMERIC_CHARACTERS));
  }
}
