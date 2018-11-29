package com.workorder.assignments.workorders.repository;

import com.workorder.assignments.workorders.entities.WorkOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/** Work order repository implementation.
 * Class:        WorkOrderRepositoryImpl
 * Created By:   brownless
 * For Project:  workorder
 */
@Repository
public class WorkOrderRepositoryImpl implements WorkOrderRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkOrderRepositoryImpl.class);

  private static final String DUPLICATE_WORK_ORDER_ID_ERROR =
      "A work order for this id '%s' already exists, duplicates not allowed.";

  /** Repository to hold the work orders. */
  private AbstractMap<BigInteger,WorkOrder> workOrderRepository =
      new ConcurrentHashMap<BigInteger,WorkOrder>();

  /**
   * {@inheritDoc}
   */
  @Override
  public WorkOrder addWorkOrder(String id, String queueEntryTime) {
    LOGGER.info("ENTRY:addWorkOrder({}, {})", id, queueEntryTime);
    WorkOrder newWorkOrder = new WorkOrder(id, queueEntryTime);
    if (workOrderRepository.containsKey(newWorkOrder.getId())) {
      throw new IllegalArgumentException(String.format(DUPLICATE_WORK_ORDER_ID_ERROR, id));
    }
    workOrderRepository.put(newWorkOrder.getId(), newWorkOrder);
    LOGGER.info("RETURN:addWorkOrder{}", newWorkOrder);
    return newWorkOrder;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WorkOrder getNextWorkOrder() {
    LOGGER.info("ENTRY:getNextWorkOrder()");
    if (workOrderRepository.size() > 0) {
      List<WorkOrder> sortedWorkOrderList = getSortedWorkOrderList();
      WorkOrder topWorkOrder = sortedWorkOrderList.get(0);
      LOGGER.info("FOUND:getNextWorkOrder:{}", topWorkOrder);
      return getAndDeleteWorkOrderFromRepository(topWorkOrder.getId());
    }
    LOGGER.info("EMPTY LIST:getNextWorkOrder");
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<BigInteger> getSortedListOfWorkOrderIds() {
    LOGGER.info("ENTRY:getSortedListOfWorkOrderIds()");
    List<BigInteger> returnList = new ArrayList<>(workOrderRepository.size());
    List<WorkOrder> sortedWorkOrderList = getSortedWorkOrderList();
    sortedWorkOrderList.forEach(workOrder -> returnList.add(workOrder.getId()));
    LOGGER.info("RETURN:getSortedListOfWorkOrderIds:{}", returnList);
    return returnList;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WorkOrder deleteWorkOrder(BigInteger workOrderId) {
    LOGGER.info("ENTRY:deleteWorkOrder({})", workOrderId);
    WorkOrder workOrder = getAndDeleteWorkOrderFromRepository(workOrderId);
    LOGGER.info("RETURN:deleteWorkOrder:{}", workOrder);
    return workOrder;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getWorkOrderQueuePosition(BigInteger workOrderId) {
    LOGGER.info("ENTRY:getWorkOrderQueuePosition({})", workOrderId);
    int foundIndex = -1;
    if (workOrderRepository.containsKey(workOrderId)) {
      List<WorkOrder> sortedWorkOrderList = getSortedWorkOrderList();
      LOGGER.info("SORTED_LIST:getWorkOrderQueuePosition:{}", sortedWorkOrderList);
      for (int i = 0; ((i < sortedWorkOrderList.size()) && (foundIndex == -1)); i++) {
        if (workOrderId.compareTo(sortedWorkOrderList.get(i).getId()) == 0) {
          foundIndex = i;
        }
      }
      LOGGER.info("RETURN:getWorkOrderQueuePosition:{}", foundIndex);
    }
    return foundIndex;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getQueueMeanWaitTime(String referenceDate) {
    LOGGER.info("ENTRY:getQueueMeanWaitTime({})", referenceDate);
    List<WorkOrder> sortedList = getSortedWorkOrderList();
    double mean = 0.0;
    double sum = 0.0;
    int count = 0;
    if (sortedList.size() > 0) {
      for (WorkOrder workOrder : sortedList) {
        long timeInQueue = workOrder.getTimeInQueue(referenceDate);
        if (timeInQueue >= 0) { // only allow sensible values.
          ++count;
          sum += timeInQueue;
        }
      }
      if (count > 0) {
        mean = sum / count;
      }
      else {
        LOGGER.info("getQueueMeanWaitTime:NO_VALID_WORKORDER_DATESIN_THE_PAST_FROM_REFERENCE");
      }
    }

    LOGGER.info("RETURN:getQueueMeanWaitTime:{}", mean);
    return mean;
  }

  /**
   * Gets a sorted work order list from the repository.
   * @return a rank sorted list of work orders.
   */
  private List<WorkOrder> getSortedWorkOrderList() {
    List<WorkOrder> listToSort = new ArrayList<>(workOrderRepository.values());
    Collections.sort(listToSort, WorkOrder.Comparators.RANK);
    return listToSort;
  }

  /**
   * If a work order with the given id exists in the repository it is retrieved and deleted.
   * @param workOrderId id to retrieve and delete
   * @return found WorkOrder or null if not found.
   */
  private WorkOrder getAndDeleteWorkOrderFromRepository(BigInteger workOrderId) {
    WorkOrder foundValue = null;
    if (workOrderRepository.containsKey(workOrderId)) {
      foundValue = workOrderRepository.get(workOrderId);
      workOrderRepository.remove(workOrderId);
    }
    return foundValue;
  }
}
