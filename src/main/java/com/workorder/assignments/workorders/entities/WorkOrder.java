package com.workorder.assignments.workorders.entities;

import com.workorder.assignments.workorders.utilities.BigIntegerConversionUtilities;
import com.workorder.assignments.workorders.utilities.DateConverterUtilities;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigInteger;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.Objects;

/**
 * Class:        WorkOrder
 * This class defines a WorkOrder that has an id, Classification and a
 * queue entry date. there are currently 4 types of work order. These
 * classifications are implemented at
 *
 * @see WorkOrderClassification#determineClassificationFromId
 * <p>
 * Created By:   brownless
 * For Project:  workorder
 */
@ApiModel(description = "Class representing a a work order managed by the application.")
public class WorkOrder implements Comparable<WorkOrder> {

  @ApiModelProperty(notes = "The identity for the work order, between 1 and BigInteger maximum.")
  private final BigInteger id;
  @ApiModelProperty(notes = "The data at which the work order was submitted to the queue. At creating time must be less than or equal to the current time")
  private final ZonedDateTime queueEntryDate;
  @ApiModelProperty(notes = "The classification of the work order. This determines the work order queue priority.")
  private final WorkOrderClassification workOrderClassification;

  /**
   * Constructs the workOrder from the string inputs.
   *
   * @param id        - string representation of the identity.
   * @param entryDate - string representation of the entry date, Format must be ISO8601.
   *                  Also date cannot be in the future.
   */
  public WorkOrder(String id, String entryDate) {
    this.id = BigIntegerConversionUtilities.getConverttoValidWorkOrderId(id);
    workOrderClassification = WorkOrderClassification.determineClassificationFromId(this.id);
    queueEntryDate = DateConverterUtilities.getDateTimefromIso8601StringFormat(entryDate);
    if (queueEntryDate.compareTo(ZonedDateTime.now()) > 0) {
      throw new IllegalArgumentException(
          String.format("Date in the future is not allowed."));
    }


  }

  /**
   * gets the work order id @return identity of the work order.
   */
  public BigInteger getId() {
    return id;
  }

  /**
   * Gets the classification of the work order  @return classification of the work order.
   */
  public WorkOrderClassification getWorkOrderClassification() {
    return workOrderClassification;
  }

  /**
   * gets the time in queue in seconds @return time spend in queue at this time.
   */
  public long getTimeInQueue() {
    ZonedDateTime now = ZonedDateTime.now();
    Duration duration = Duration.between(queueEntryDate, now);
    return duration.getSeconds();
  }

  /**
   * Gets the time in queue in seconds from reference date
   *
   * @param referenceTime string representation of the reference time
   * @return the time in queue since the reference time
   */
  public long getTimeInQueue(String referenceTime) {
    ZonedDateTime now = DateConverterUtilities.getDateTimefromIso8601StringFormat(referenceTime);
    Duration duration = Duration.between(queueEntryDate, now);
    return duration.getSeconds();
  }

  /**
   * Determine the rank according to the specification. Decision to make the management
   * override value the maximum Double value to put it at the top of the list.
   *
   * @return rank according to the specification
   */
  public double getRank() {

    double result;
    double secondsInQueue = this.getTimeInQueue();

    switch (workOrderClassification) {
      case MANAGEMENT_OVERRIDE:
        // default to the maximum rank sorting can then happen according to time in queue.
        result = Double.MAX_VALUE;
        break;
      case VIP:
        result = Math.max(4.0, (2 * secondsInQueue) * Math.log(secondsInQueue));
        break;
      case PRIORITY:
        result = Math.max(3.0, secondsInQueue * Math.log(secondsInQueue));
        break;
      default: // NORMAL Priority
        result = secondsInQueue;
    }
    return result;
  }

  @Override
  /** This CompareTo implementation simply compares the work order identities */ public int compareTo(WorkOrder o) {
    return (this.id.compareTo(o.id));
  }

  @Override
  /** To be compliant the equals method also uses only the id value */

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorkOrder workOrder = (WorkOrder) o;
    return Objects.equals(id, workOrder.id);
  }

  @Override
  /** To be compliant the hashCode method also uses only the id value */ public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "WorkOrder{" + "id=" + id + ", " + "queueEntryDate=" + queueEntryDate + ", workOrderClassification=" + workOrderClassification + '}';
  }

  /**
   * This inner class is used to define different sorting methods for
   * the class. Currently there is only 1 RANK but to add other flavours
   * simply mimic the RANK object
   */
  public static class Comparators {

    /**
     * Hide the public constructor class is static only.
     */
    private Comparators() {
    }

    /**
     * Comparator for the WorkOrder object that sorts Rank in descending order which
     * is opposite to the normal order. One Caveat if the ranks of the objects are equal then
     * the workOrders in the queue the longest take precedence.
     */
    public static final Comparator<WorkOrder> RANK = new Comparator<WorkOrder>() {
      @Override
      public int compare(WorkOrder o1, WorkOrder o2) {
        // Get the rank for each object
        Double o1Rank = o1.getRank();
        Double o2Rank = o2.getRank();
        // if the ranks are equal then sort by time in queue (oldest first)
        if (o1Rank.equals(o2Rank)) {
          return (int) (o2.getTimeInQueue() - o1.getTimeInQueue());
        } else {
          // Otherwise just return on the Rank of the object.
          if (o1.getRank() > o2.getRank()) {
            return -1;
          } else {
            return 1;
          }
        }
      }
    };
  }
}
