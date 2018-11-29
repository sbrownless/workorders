package com.workorder.assignments.workorders.repository;

import com.workorder.assignments.workorders.entities.WorkOrder;

import java.math.BigInteger;
import java.util.List;

/** Describes the access and management to submitted work orders.
 * Class:        WorkOrderRepository
 * Created By:   brownless
 * For Project:  workorder
 */
public interface WorkOrderRepository {

  /**
   * Create a new work item to be added to the queue.
   * @param id for the work item mst be between 1 and 9223372036854775807.
   * @param queueEntryTime entry date time must be in ISO 8601 format i.e. YYYY-MM-DDTHH:mm:SSZ.
   *                       also the time cannot be in the future.
   * @return the created work order object
   * @throws IllegalArgumentException if any of the input parameters are faulty as described.
   *                                  or nn attempt to insert a duplicate key occurs.
   */
  WorkOrder addWorkOrder(String id, String queueEntryTime);

  /**
   * Gets and dequeue the work order at the top of the list. After this operation
   * the top priority value is removed from the queue.
   * @return the work order at the top of the list. null for an empty list.
   */
  WorkOrder getNextWorkOrder();

  /**
   * Gets a list of Id's in the queue.
   * @return a list of work order identities in rank order. Or an empty list.
   */
  List<BigInteger> getSortedListOfWorkOrderIds();

  /**
   * Removes a given work order from the queue if it exists.
   * @param workOrderId the id of the WorkOrder to remove
   * @return the workOrder that was removed or null if it did not exist.
   */
  WorkOrder deleteWorkOrder(BigInteger workOrderId);

  /**
   * Gets the actual position of a work order in the queue by it's identity.
   * @param workOrderId  to look up in the queue.
   * @return the queue position of the work order or -1 if the item does not exist.
   */
  int getWorkOrderQueuePosition(BigInteger workOrderId);

  /**
   * Calculates the mean wait time in the queue from the given time.
   * This function only works on items in the queue that are older than
   * the reference time. Ignores others. If all values are newer than the reference time
   * then 0 will be returned.
   * @param referenceDate the date to use as a reference point.
   * @return mean wait time of the queue entries that are older than the supplied reference point
   */
  double getQueueMeanWaitTime(String referenceDate);

}
