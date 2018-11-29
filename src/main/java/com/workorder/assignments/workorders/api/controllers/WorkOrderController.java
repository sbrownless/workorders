package com.workorder.assignments.workorders.api.controllers;

import com.workorder.assignments.workorders.entities.WorkOrder;
import com.workorder.assignments.workorders.repository.WorkOrderRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class:        WorkOrderController
 * Created By:   brownless
 * For Project:  workorders
 */

@RestController
@RequestMapping(path = "/workorder", produces = {"application/json", "text/xml"})
@Api(description = "Sets the endpoints for Creating, Retrieving and Deleting work orders.")
public class WorkOrderController {

  private WorkOrderRepository workOrderRepository;

  @Autowired
  public WorkOrderController(WorkOrderRepository workOrderRepository) {
    this.workOrderRepository = workOrderRepository;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation("Creates a new work order in the queue.")
//  public WorkOrder addNewWorkOrder(@ApiParam(value="Identity of the work order. must be positive and not already exist in the queue", required=true) String id,
//                                   @ApiParam(value="The work order queue entry date. must be in ISO 8601 format. i.e. '2018-11-25T23:45:42Z'", required=true)String entryDate) {
  public WorkOrder addNewWorkOrder(String id, String entryDate) {
    return workOrderRepository.addWorkOrder(id, entryDate);
  }

  @PatchMapping
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("Retrieves the work order identity at the top of the queue and removes it from the queue.")
  public BigInteger getTopId() {
    WorkOrder workOrder = workOrderRepository.getNextWorkOrder();
    return workOrder != null ? workOrder.getId() : BigInteger.valueOf(-1L);
  }

  @GetMapping(path = "sortedlist")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("Retrieves an ordered list of work order identities.")
  public List<BigInteger> getWorkOrderSortedList() {
    return workOrderRepository.getSortedListOfWorkOrderIds();
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("Delets the given work order from the queue if it exists.")
  public WorkOrder deleteWorkOrder(@PathVariable("id") BigInteger id) {
    WorkOrder deletedWorkOrder = workOrderRepository.deleteWorkOrder(id);
    if (deletedWorkOrder == null) {
      throw new NoSuchElementException(String.format("the work id %s does not exist in the queue.", id.toString()));
    } else {
      return workOrderRepository.deleteWorkOrder(id);
    }
  }

  @GetMapping(path = "queue_position/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("gets the queue position of the given work order id")
  public int getWorkOrderQueuePosition(@PathVariable("id") BigInteger id) {
    return workOrderRepository.getWorkOrderQueuePosition(id);
  }

  @GetMapping(path = "meanwaittime/{referenceDate}")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("gets the mean wait time in the queue for the reference time. Time must be supplied in ISO 8601 format. i.e. 2018-11-25T23:45:42Z'")
  public double getQueuMeanWaitTimeFromReferenceTime(@PathVariable("referenceDate") String referenceDate) {
    return workOrderRepository.getQueueMeanWaitTime(referenceDate);
  }
}
