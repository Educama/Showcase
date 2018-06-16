package org.educama.shipment.process;

/**
 * This class contains all model constants of the case diagram ShipmentCase.cmmn
 * .
 */
public final class ShipmentCaseConstants {

    private ShipmentCaseConstants() {
    }

    public static final String SHIPMENTCASEKEY = "ShipmentCase";

    public static final String SHIPMENT_CASE_PLAN_MODEL = "shipmentCasePlanModel";

    // Human Tasks
    public static final String PLAN_ITEM_HUMAN_TASK_CREATE_INVOICE = "PlanItem_HumanTask_CreateInvoice";
    public static final String PLAN_ITEM_HUMAN_TASK_CHANGE_SHIPMENT_ORDER = "PlanItem_HumanTask_ChangeShipmentOrder";
    public static final String PLAN_ITEM_HUMAN_TASK_COMPLETE_SHIPMENT_ORDER = "PlanItem_HumanTask_CompleteShipmentOrder";
    public static final String PLAN_ITEM_HUMAN_TASK_ORGANIZE_FLIGHT = "PlanItem_HumanTask_OrganizeFlight";

    // Stages
    public static final String PLAN_ITEM_STAGE_PROCESS_SHIPMENT_ORDER = "PlanItem_Stage_ProcessShipmentOrder";

    // Milestones
    public static final String PLAN_ITEM_MILESTONE_SHIPMENT_ORDER_COMPLETED = "PlanItem_Milestone_ShipmentOrderCompleted";
    public static final String PLAN_ITEM_MILESTONE_FLIGHT_DEPARTED = "PlanItem_Milestone_FlightDeparted";

    // Variables
    public static final String VARIABLE_LAST_EVALUATE_DATE = "lastEvaluateDate";
}
