package com.workorder.assignments.workorders.repository;

/**
 * Class:        TestWorkOrderRepository
 * Created By:   brownless
 * Date:         29/11/2018  11:46
 * For Project:  workorders
 */

import com.workorder.assignments.workorders.entities.TestWorkOrderData;
import com.workorder.assignments.workorders.utilities.BigIntegerConversionUtilities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.workorder.assignments.workorders.entities.WorkOrder;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class:        TestWorkOrderRepository
 * Created By:   brownless
 * For Project:  workorder
 */

public class TestWorkOrderRepository extends TestWorkOrderData {

  static final WorkOrderRepository overallRepository = new WorkOrderRepositoryImpl();


  @Test
  @DisplayName("Enter a 2 of items into the repo and check for consistency.")
  void testAddingNewWorkOrders () {

    final WorkOrderRepository repository = new WorkOrderRepositoryImpl();
    repository.addWorkOrder(PRIORITY_ID, ENTRY_TIME);
    repository.addWorkOrder(VIP_ID, ENTRY_TIME);

    // Make sure that they are added and are sorted into the correct order.
    List<BigInteger> list = repository.getSortedListOfWorkOrderIds();
    assertTrue(list.size() == 2);
    assertEquals(VIP_ID, list.get(0).toString());
    assertEquals(PRIORITY_ID, list.get(1).toString());
  }

  @Test
  @DisplayName("Enter a several of items into the repo and ensure their correct behaviour when retrieved.")
  void testGetNextWorkOrder () {

    // all of the following work on the same entry time tpo test the basic rank sorting for categories
    final WorkOrderRepository repository = new WorkOrderRepositoryImpl();
    repository.addWorkOrder(PRIORITY_ID, ENTRY_TIME);
    repository.addWorkOrder(VIP_ID, ENTRY_TIME);
    repository.addWorkOrder(MANAGEMENT_ID, ENTRY_TIME);
    repository.addWorkOrder(NORMAL_ID, ENTRY_TIME);

    List<BigInteger> list = repository.getSortedListOfWorkOrderIds();
    assertTrue(list.size() == 4);
    assertEquals(MANAGEMENT_ID, list.get(0).toString());
    assertEquals(VIP_ID, list.get(1).toString());
    assertEquals(PRIORITY_ID, list.get(2).toString());
    assertEquals(NORMAL_ID, list.get(3).toString());

    //Get the top work order and test the repo
    WorkOrder nextWorkOrder = repository.getNextWorkOrder();
    assertEquals(MANAGEMENT_ID, nextWorkOrder.getId().toString());

    // get the list again and test its contents
    list = repository.getSortedListOfWorkOrderIds();
    assertTrue(list.size() == 3);
    assertEquals(VIP_ID, list.get(0).toString());
    assertEquals(PRIORITY_ID, list.get(1).toString());
    assertEquals(NORMAL_ID, list.get(2).toString());

    //Get the top work order and test the repo
    nextWorkOrder = repository.getNextWorkOrder();
    assertEquals(VIP_ID, nextWorkOrder.getId().toString());
    // get the list again and test its contents
    list = repository.getSortedListOfWorkOrderIds();
    assertTrue(list.size() == 2);
    assertEquals(PRIORITY_ID, list.get(0).toString());
    assertEquals(NORMAL_ID, list.get(1).toString());

    //Get the top work order and test the repo
    nextWorkOrder = repository.getNextWorkOrder();
    assertEquals(PRIORITY_ID, nextWorkOrder.getId().toString());
    // get the list again and test its contents
    list = repository.getSortedListOfWorkOrderIds();
    assertTrue(list.size() == 1);
    assertEquals(NORMAL_ID, list.get(0).toString());

    //Get the top work order and test the repo
    nextWorkOrder = repository.getNextWorkOrder();
    assertEquals(NORMAL_ID, nextWorkOrder.getId().toString());
    // get the list again and test its contents
    list = repository.getSortedListOfWorkOrderIds();
    assertTrue(list.size() == 0);
  }

  @Test
  @DisplayName("Enter a several of items into the repo and ensure sorting of management work orders.")
  void testManagementCategoryWorkOrderSorting () {
    // all of the following work on the same entry time tpo test the basic rank sorting for categories
    final WorkOrderRepository repository = new WorkOrderRepositoryImpl();
    repository.addWorkOrder(PRIORITY_ID, ENTRY_TIME);
    repository.addWorkOrder(MANAGEMENT_ID, ENTRY_TIME);
    repository.addWorkOrder(VIP_ID, ENTRY_TIME);
    repository.addWorkOrder(OLDER_MANAGEMENT_ID, OLDER_ENTRY_TIME);
    repository.addWorkOrder(NORMAL_ID, ENTRY_TIME);
    repository.addWorkOrder(ANCIENT_MANAGEMENT_ID, ANCIENT__ENTRY_TIME);

    // get the list again and test its contents are consistent
    List<BigInteger>list = repository.getSortedListOfWorkOrderIds();
    assertTrue(list.size() == 6);
    assertEquals(ANCIENT_MANAGEMENT_ID, list.get(0).toString());
    assertEquals(OLDER_MANAGEMENT_ID, list.get(1).toString());
    assertEquals(MANAGEMENT_ID, list.get(2).toString());
    assertEquals(VIP_ID, list.get(3).toString());
    assertEquals(PRIORITY_ID, list.get(4).toString());
    assertEquals(NORMAL_ID, list.get(5).toString());
  }

  @Test
  @DisplayName("Enter a duplicate work order id and ensure it fails.")
  void testAddingDuplicateWorkOrderId () {
    final WorkOrderRepository repository = new WorkOrderRepositoryImpl();
    repository.addWorkOrder(PRIORITY_ID, ENTRY_TIME);
    repository.addWorkOrder(MANAGEMENT_ID, ENTRY_TIME);
    repository.addWorkOrder(VIP_ID, ENTRY_TIME);
    assertThrows(IllegalArgumentException.class, ()-> repository.addWorkOrder(PRIORITY_ID, ENTRY_TIME));
  }

  @Test
  @DisplayName("Delete work order item from the queue.")
  void testWorkOrderItemDeletion () {
    final WorkOrderRepository repository = new WorkOrderRepositoryImpl();
    repository.addWorkOrder(PRIORITY_ID, ENTRY_TIME);
    repository.addWorkOrder(MANAGEMENT_ID, ENTRY_TIME);
    repository.addWorkOrder(VIP_ID, ENTRY_TIME);

    WorkOrder workOrder = repository.deleteWorkOrder(BigIntegerConversionUtilities.getConverttoValidWorkOrderId(MANAGEMENT_ID));
    List<BigInteger>list = repository.getSortedListOfWorkOrderIds();
    assertTrue(list.size() == 2);
    assertEquals(VIP_ID, list.get(0).toString());
    assertEquals(PRIORITY_ID, list.get(1).toString());
  }

  @Test
  @DisplayName("Get the work order queue position.")
  void testGetWorkOrderQueuePositiion () {

    final WorkOrderRepository repository = new WorkOrderRepositoryImpl();
    repository.addWorkOrder(PRIORITY_ID, ENTRY_TIME);
    repository.addWorkOrder(MANAGEMENT_ID, ENTRY_TIME);
    repository.addWorkOrder(VIP_ID, ENTRY_TIME);

    assertEquals(1, repository.getWorkOrderQueuePosition(BigIntegerConversionUtilities.getConverttoValidWorkOrderId(VIP_ID)));
    assertEquals(0, repository.getWorkOrderQueuePosition(BigIntegerConversionUtilities.getConverttoValidWorkOrderId(MANAGEMENT_ID)));
    assertEquals(2, repository.getWorkOrderQueuePosition(BigIntegerConversionUtilities.getConverttoValidWorkOrderId(PRIORITY_ID)));
  }

  @Test
  @DisplayName("Test proper sorting of really old priority work ID")
  void testProperSortingOfReallyOldWorkId () {

    final WorkOrderRepository repository = new WorkOrderRepositoryImpl();
    repository.addWorkOrder(MANAGEMENT_ID, ENTRY_TIME);
    repository.addWorkOrder(OLDER_MANAGEMENT_ID, OLDER_ENTRY_TIME);
    repository.addWorkOrder(ANCIENT_MANAGEMENT_ID, ANCIENT__ENTRY_TIME);
    repository.addWorkOrder(VIP_ID, ENTRY_TIME);
    repository.addWorkOrder(PRIORITY_ID, ENTRY_TIME);
    repository.addWorkOrder(OLD_PRIORITY_ID, ANCIENT__ENTRY_TIME);
    repository.addWorkOrder(NORMAL_ID, ENTRY_TIME);

    // in this test the ancient priority id should appear before the vip id but after management
    List<BigInteger>list = repository.getSortedListOfWorkOrderIds();
    assertTrue(list.size() == 7);
    assertEquals(ANCIENT_MANAGEMENT_ID, list.get(0).toString());
    assertEquals(OLDER_MANAGEMENT_ID, list.get(1).toString());
    assertEquals(MANAGEMENT_ID, list.get(2).toString());
    assertEquals(OLD_PRIORITY_ID, list.get(3).toString());
    assertEquals(VIP_ID, list.get(4).toString());
    assertEquals(PRIORITY_ID, list.get(5).toString());
    assertEquals(NORMAL_ID, list.get(6).toString());
  }

  @Test
  @DisplayName("Test proper sorting of really old priority and Normal work ID")
  void testProperSortingOfReallyOldPriorityAndNormalWorkId () {

    final WorkOrderRepository repository = new WorkOrderRepositoryImpl();
    repository.addWorkOrder(MANAGEMENT_ID, ENTRY_TIME);
    repository.addWorkOrder(OLDER_MANAGEMENT_ID, OLDER_ENTRY_TIME);
    repository.addWorkOrder(ANCIENT_MANAGEMENT_ID, ANCIENT__ENTRY_TIME);
    repository.addWorkOrder(VIP_ID, ENTRY_TIME);
    repository.addWorkOrder(OLD_NORMAL_ID, ANCIENT__ENTRY_TIME);
    repository.addWorkOrder(PRIORITY_ID, ENTRY_TIME);
    repository.addWorkOrder(OLD_PRIORITY_ID, ANCIENT__ENTRY_TIME);
    repository.addWorkOrder(NORMAL_ID, ENTRY_TIME);

    // in this test the ancient priority id should appear before the vip id and the old normal id but after management
    List<BigInteger>list = repository.getSortedListOfWorkOrderIds();
    assertTrue(list.size() == 8);
    assertEquals(ANCIENT_MANAGEMENT_ID, list.get(0).toString());
    assertEquals(OLDER_MANAGEMENT_ID, list.get(1).toString());
    assertEquals(MANAGEMENT_ID, list.get(2).toString());
    assertEquals(OLD_PRIORITY_ID, list.get(3).toString());
    assertEquals(OLD_NORMAL_ID, list.get(4).toString());
    assertEquals(VIP_ID, list.get(5).toString());
    assertEquals(PRIORITY_ID, list.get(6).toString());
    assertEquals(NORMAL_ID, list.get(7).toString());
  }

  @Test
  void testMeanFunction () {
    // all of the following work on the same entry time tpo test the basic rank sorting for categories
    final WorkOrderRepository repository = new WorkOrderRepositoryImpl();
    repository.addWorkOrder(PRIORITY_ID, ENTRY_TIME);
    repository.addWorkOrder(VIP_ID, ENTRY_TIME);
    repository.addWorkOrder(MANAGEMENT_ID, ENTRY_TIME);
    repository.addWorkOrder(NORMAL_ID, ENTRY_TIME);

    assertEquals(60.0, repository.getQueueMeanWaitTime("2018-11-25T23:46:43Z"));

    // put a stupid reference id in here in the past for all entries
    assertEquals(0.0, repository.getQueueMeanWaitTime("2018-11-25T23:43:43Z"));



  }
}
