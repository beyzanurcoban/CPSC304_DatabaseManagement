package ca.ubc.cs304.model;

import java.sql.Timestamp;

public class PickUpTask_Perform {
    private final String taskID;
    private final String merchantName;
    private final Float distanceTravelled;
    private final java.sql.Timestamp completeTime;
    private final java.sql.Timestamp arriveTime;
    private final java.sql.Timestamp departTime;
    private final java.sql.Timestamp startTime;
    private final String driverID;

    public PickUpTask_Perform(String taskID, String merchantName, float distanceTravelled, java.sql.Timestamp completeTime, java.sql.Timestamp arriveTime, java.sql.Timestamp departTime,
                              java.sql.Timestamp startTime, String driverID){
        this.taskID = taskID;
        this.merchantName = merchantName;
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

    public String getMerchantName() {
        return merchantName;
    }

    public Float getDistanceTravelled() {
        return distanceTravelled;
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
