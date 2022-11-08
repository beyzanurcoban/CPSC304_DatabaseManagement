package ca.ubc.cs304.model;
/**
 * The intent for this class is to update/store information about a single transfer
 */
public class Transfers {
    private final String taskID;
    private final String itemID;

    public Transfers(String taskID, String itemID){
        this.taskID = taskID;
        this.itemID = itemID;
    }

    public String getTaskID() {
        return taskID;
    }

    public String getItemID() {
        return itemID;
    }
}
