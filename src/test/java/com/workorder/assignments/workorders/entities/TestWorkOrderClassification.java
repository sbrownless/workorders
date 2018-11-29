package com.workorder.assignments.workorders.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Class:        TestWorkOrderClassification
 * Created By:   brownless
 * For Project:  workorder
 */

class TestWorkOrderClassification {

  private static final BigInteger PRIORITY_ID = BigInteger.valueOf(3);
  private static final BigInteger VIP_ID = BigInteger.valueOf(5);
  private static final BigInteger MANAGEMENT_OVERRIDE_ID = BigInteger.valueOf(15);
  private static final BigInteger NORMAL_ID = BigInteger.valueOf(17);
  private static final BigInteger OUT_OF_RANGE_ID_ZERO = BigInteger.valueOf(0);
  private static final BigInteger OUT_OF_RANGE_ID_NEGATIVE = BigInteger.valueOf(-5);
  private static final BigInteger MAXIMUM_ID = BigInteger.valueOf(9223372036854775807L);
  private static final BigInteger MAXIMUM_PRIORITY_ID = BigInteger.valueOf(9223372036854775806L);
  private static final BigInteger MAXIMUM_VIP_ID = BigInteger.valueOf(9223372036854775805L);
  private static final BigInteger MAXIMUM_MANAGEMENT_OVERRIDE_ID = BigInteger.valueOf(9223372036854775800L);



  /** Test the priority id value */
  @Test
  @DisplayName("Priority classification assignment results from a priority work order id value")
  void testPriorityValue () {
    assertEquals (WorkOrderClassification.PRIORITY, WorkOrderClassification.determineClassificationFromId(PRIORITY_ID));
  }

  /** Test the VIP id value */
  @Test
  @DisplayName("VIP classification assignment results from a VIP work order id value")
  void testVipValue () {
    assertEquals(WorkOrderClassification.VIP, WorkOrderClassification.determineClassificationFromId(VIP_ID));
  }

  /** Test the Management Override id value */
  @Test
  @DisplayName("Management override classification assignment results from a management override work order id value")
  void testManagementOverrideValue () {
    assertEquals (WorkOrderClassification.MANAGEMENT_OVERRIDE, WorkOrderClassification.determineClassificationFromId(MANAGEMENT_OVERRIDE_ID));
  }

  /** Test the Normal id value */
  @Test
  @DisplayName("Normal classification assignment results from a normal work order id value")
  void testNormalValue () {
    assertEquals (WorkOrderClassification.NORMAL, WorkOrderClassification.determineClassificationFromId(NORMAL_ID));
  }

  /** Test the Maximum id value */
  @Test
  @DisplayName("Normal classification assignment results from maximum work order id value")
  void testMaxValue () {
    assertEquals (WorkOrderClassification.NORMAL, WorkOrderClassification.determineClassificationFromId(MAXIMUM_ID));
  }

  /** Test the Maximum priority id value */
  @Test
  @DisplayName("Priority classification assignment results from maximum priority work order id value")
  void testMaxPriorityValue () {
    assertEquals (WorkOrderClassification.PRIORITY, WorkOrderClassification.determineClassificationFromId(MAXIMUM_PRIORITY_ID));
  }

  /** Test the Maximum priority id value */
  @Test
  @DisplayName("VIP classification assignment results from maximum VIP work order id value")
  void testMaxVipValue () {
    assertEquals (WorkOrderClassification.VIP, WorkOrderClassification.determineClassificationFromId(MAXIMUM_VIP_ID));
  }

  @Test
  @DisplayName("Management override classification assignment results from maximum Management override work order id value")
  void testMaxManagementOverrideValue () {
    assertEquals (WorkOrderClassification.MANAGEMENT_OVERRIDE, WorkOrderClassification.determineClassificationFromId(MAXIMUM_MANAGEMENT_OVERRIDE_ID));
  }

  @Test
  @DisplayName("IllegalArgumentException results from a zero value work order id value")
  void testValueOutOfRangeIdZero () {
    assertThrows(IllegalArgumentException.class, ()-> WorkOrderClassification.determineClassificationFromId(OUT_OF_RANGE_ID_ZERO));
  }

  @Test
  @DisplayName("IllegalArgumentException results from a negative value work order id value")
  void testValueOutOfRangeIdNegative () {
    assertThrows(IllegalArgumentException.class, ()-> WorkOrderClassification.determineClassificationFromId(OUT_OF_RANGE_ID_NEGATIVE));
  }

  @Test
  @DisplayName("IllegalArgumentException results from a null work order id value")
  void testValueNull () {
    assertThrows(IllegalArgumentException.class, ()-> WorkOrderClassification.determineClassificationFromId(null));
  }

}
