package ca.ubc.cs304.model;

import java.sql.Timestamp;

public class DeliveryTask_Perform {
    private final String taskID;
    private final String recipientName;
    private final Float distanceTravelled;
    private final java.sql.Timestamp completeTime;
    private final java.sql.Timestamp arriveTime;
    private final java.sql.Timestamp departTime;
    private final java.sql.Timestamp startTime;
    private final String driverID;

    public DeliveryTask_Perform(String taskID, String recipientName, float distanceTravelled, java.sql.Timestamp completeTime, java.sql.Timestamp arriveTime, java.sql.Timestamp departTime,
                                java.sql.Timestamp startTime, String driverID){
        this.taskID = taskID;
        this.recipientName = recipientName;
        this.distanceTravelled = distanceTravelled;
        this.completeTime = completeTime;
        this.arriveTime = arriveTime;
        this.departTime = departTime;
        this.startTime = startTime;
        this.driverID = driverID;
    }

    public String getTaskID() {
        return taskID;
    }

    public Float getDistanceTravelled() {
        return distanceTravelled;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public Timestamp getCompleteTime() {
        return completeTime;
    }

    public Timestamp getArriveTime() {
        return arriveTime;
    }

    public Timestamp getDepartTime() {
        return departTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public String getDriverID() {
        return driverID;
    }
}
