package com.workorder.assignments.workorders.utilities;


import java.time.DateTimeException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * Class:        DateConverterUtilities
 *                Static class to manage the date validation from string.
 *                For this project only ISO_8601 wll be handled for UTC only.
 *                Dates in the form yyyy-MM-ddTHH:mm:ssZ will be used. This is a formatter
 *                that is predefined in the DateTimeFormatter class.
 * Created By:   brownless
 */

public class DateConverterUtilities {

  /** Regex to match the date string. e.g. 2017-12-31T17:42:57Z*/
  private static final String ISO_8601_REGEX =
      "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z$";

  /** Pattern for matching the ISO 8601 date format. */
  private static final Pattern ISO8601_PATTERN = Pattern.compile(ISO_8601_REGEX);

  /** Error string for parse error exception. */
  private static final String PARSE_ERROR_STRING =
      "Error parsing the supplied string '%s' to a valid date. Ensure it is in ISO 8601 format.";

  /** Error string for conversion exception. */
  private static final String CANNOT_CONVERT_ERROR_STRING =
      "Cannot convert to a from the supplied value '%s' to a valid date string.";

  /** Error message if an attempt is made to have the queue entry date in the future. */
  private static final String DATE_IN_FUTURE_ERROR_STRING =
      "The date string '%s' is in the future. This is not allowed";


  /** This is a helper static class so hide the default constructor. */
  private DateConverterUtilities() {
    // hide default constructor
  }

  /**
   * Gets the given date in ISO 8601 form.
   *
   * @param date the date to be represented in ISO 8601
   * @return the supplied date represented as an ISO 8601 formatted string.
   * @throws IllegalArgumentException a vaild Date object must be passed.
   *
   */
  public static String getDateInIso8601Format(final ZonedDateTime date) {

    if (date == null) {
      throw new IllegalArgumentException(String.format(CANNOT_CONVERT_ERROR_STRING, date));
    }
    return date.format(DateTimeFormatter.ISO_INSTANT);
  }

  /**
   * Converts a ISO8601 date form string to a Date object.
   *
   * @param iso8601Representation the string representation of the date, assumed to be in UTC.
   * @return Date representation of the string if it can be parsed
   *
   */
  public static final ZonedDateTime getDateTimefromIso8601StringFormat(
      final String iso8601Representation) {

    if (isDateStringInIso8601Format(iso8601Representation)) {
      try {
        ZonedDateTime zonedDateTime =
            ZonedDateTime.parse(iso8601Representation, DateTimeFormatter.ISO_DATE_TIME);

        return ZonedDateTime.parse(iso8601Representation, DateTimeFormatter.ISO_DATE_TIME);
      } catch (DateTimeException dte) {
        throw new IllegalArgumentException(
            String.format(PARSE_ERROR_STRING, iso8601Representation), dte);
      }
    } else {
      throw new IllegalArgumentException(String.format(PARSE_ERROR_STRING, iso8601Representation));
    }
  }

  /**
   * Determines whether a string passed as a date is in the required ISO 8601 format.
   *
   * @param dateString string to test
   * @return true if it is in the expected format; otherwise false.
   *
   */
  public static boolean isDateStringInIso8601Format(final String dateString) {

    if ((dateString != null) && (!dateString.isEmpty())) {
      return ISO8601_PATTERN.matcher(dateString).matches();
    }
    return false;
  }
}
