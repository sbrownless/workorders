package com.workorder.assignments.workorders.entities;

import com.workorder.assignments.workorders.entities.WorkOrder;

/** Class to hold common test data.
 * Class:        TestWorkOrderData
 * Created By:   brownless
 * For Project:  workorder
 */
public abstract class TestWorkOrderData {

  public static final String MANAGEMENT_ID = "15";
  public static final String OLDER_MANAGEMENT_ID = "60";
  public static final String ANCIENT_MANAGEMENT_ID = "120";

  public static final String VIP_ID = "5";
  public static final String PRIORITY_ID = "3";
  public static final String NORMAL_ID = "7";
  public static final String OLD_NORMAL_ID = "43";

  public static final String OLD_PRIORITY_ID = "6";

  public static final String ENTRY_TIME = "2018-11-25T23:45:43Z";
  public static final String OLDER_ENTRY_TIME = "2018-11-25T23:45:42Z";
  public static final String ANCIENT__ENTRY_TIME = "2016-11-25T23:45:42Z";

  public static final WorkOrder MANAGEMENT_WORK_ORDER = new WorkOrder(MANAGEMENT_ID, ENTRY_TIME);
  public static final WorkOrder OLDER_MANAGEMENT_WORK_ORDER = new WorkOrder(OLDER_MANAGEMENT_ID, OLDER_ENTRY_TIME);
  public static final WorkOrder ANCIENT_MANAGEMENT_WORK_ORDER = new WorkOrder(ANCIENT_MANAGEMENT_ID, ANCIENT__ENTRY_TIME);
  public static final WorkOrder VIP_WORK_ORDER = new WorkOrder(VIP_ID, ENTRY_TIME);
  public static final WorkOrder PRIORITY_WORK_ORDER = new WorkOrder(PRIORITY_ID, ENTRY_TIME);
  public static final WorkOrder OLDER_PRIORITY_WORK_ORDER = new WorkOrder(OLD_PRIORITY_ID, ANCIENT__ENTRY_TIME);
  public static final WorkOrder NORMAL_WORK_ORDER = new WorkOrder(NORMAL_ID, ENTRY_TIME);
  public static final WorkOrder OLDER_WORK_ORDER = new WorkOrder(OLD_NORMAL_ID, ANCIENT__ENTRY_TIME);

}
