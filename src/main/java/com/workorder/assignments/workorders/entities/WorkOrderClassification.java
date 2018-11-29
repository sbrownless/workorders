package com.workorder.assignments.workorders.entities;

import java.math.BigInteger;

/**
 * Class:       WorkOrderClassification.
 *              This is an enum which denotes the classification of a work order request.
 *              There are currently 4 classifications that are determined from the id
 *              of the work order request.
 *                NORMAL - id does not meet any of the following criteria
 *                PRIORITY - id is divisable by 3 and not by 5
 *                VIP - id is divisable by 5 and not by 3
 *                MANAGEMENT_OVERRIDE - id is divisable by 3 and also by 5
 * Created By:  brownless
 */

public enum  WorkOrderClassification {

  NORMAL,
  PRIORITY,
  VIP,
  MANAGEMENT_OVERRIDE;

  private static final BigInteger PRIORITY_DIVISOR = BigInteger.valueOf(3L);
  private static final BigInteger VIP_DIVISOR = BigInteger.valueOf(5L);

  /**
   * Determines a work order request classification from the id of the work order.
   * @param id the id field of the work order, must be between 1 and BigInteger maximum.
   * @return the value to be assigned to the work order request classification.
   * @throws IllegalArgumentException null value and values less than 1 are not allowed.
   */
  public static WorkOrderClassification determineClassificationFromId(BigInteger id) {

    // Test the input is legal. Throw exception if not
    if (id == null) {
      throw new IllegalArgumentException("Passed value must not be null");
    }
    if (id.compareTo(BigInteger.ONE) < 0) {
      throw new IllegalArgumentException("Passed value must be greater then 0");
    }

    // set the default classification
    WorkOrderClassification workOrderClassification = WorkOrderClassification.NORMAL;

    // determine the VIP divisor result for the ID as we need to test it twice
    boolean vipTest = id.mod(VIP_DIVISOR).equals(BigInteger.ZERO);

    // Perform the matching logic starting with is this a priority candidate
    if (id.mod(PRIORITY_DIVISOR).equals(BigInteger.ZERO)) {
      if (vipTest) {
        workOrderClassification = WorkOrderClassification.MANAGEMENT_OVERRIDE;
      } else {
        workOrderClassification = WorkOrderClassification.PRIORITY;
      }
    } else if (vipTest) {
      workOrderClassification = VIP;
    }
    return workOrderClassification;
  }
}
