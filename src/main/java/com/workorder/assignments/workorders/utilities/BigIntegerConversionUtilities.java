package com.workorder.assignments.workorders.utilities;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Class to convert and check the project usage of Big Integer.
 *   Class:        BigIntegerConversionUtilities
 *   Created By:   brownless
 *   For Project:  workorder
 */

public class BigIntegerConversionUtilities {

  /** Regular Expression Pattern for matching a valid work order id. */
  private static final String POSITIVE_BIG_INTEGER_PATTERN = "[1-9]\\d{0,17}[0-7]?";

  /** Create a regular expression matching object. */
  private static Pattern pattern = Pattern.compile(POSITIVE_BIG_INTEGER_PATTERN);

  /** Exception string for an invalid value. */
  private static final String INVALID_STRING_FORMAT_ERROR =
      "The string %s is not a valid work order id value.";

  /** Hide the public default constructor */
  private BigIntegerConversionUtilities() {
    // hide
  }

  /**Convert a String representing a work order id to a BigInteger.
   * @param id the string value to be converted
   * @return the BigInteger representation of the id
   * @throws IllegalArgumentException if the string cannot be converted.
   */
  public static BigInteger getConverttoValidWorkOrderId(String id) {

    if (isStringValidId(id)) {
      return new BigInteger(id);
    } else {
      throw new IllegalArgumentException(String.format(INVALID_STRING_FORMAT_ERROR, id));
    }
  }

  /**
   * Checks if a string is a valid representation of a work order identity.
   * The check is performed by using a regular expression.
   * @param id the string representation of the id
   * @return true if the string can be converted to a valid identity; otherwise false.
   */
  public static final boolean isStringValidId(String id) {

    // check the input for validity
    if ((id == null) || (id.isEmpty())) {
      return false;
    }
    // create a matcher for the input
    Matcher matcher = pattern.matcher(id);

    // if there is no match return false. If there is ensure there are no extraneous characters.
    if (matcher.find()) {
      return (matcher.group(0).length() == id.length());
    }
    return false;
  }
}
