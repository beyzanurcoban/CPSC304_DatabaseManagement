package ca.ubc.cs304.model;

import java.sql.Timestamp;

/**
 * The intent for this class is to update/store information about a single task_perform
 */
public class Task_Perform {
    private final String taskID;
    private final String status;
    private final String destination;
    private final java.sql.Timestamp completeAfter;
    private final java.sql.Timestamp completeBefore;
    private final String taskNote;
    private final Float distanceTravelled;
    private final java.sql.Timestamp completeTime;
    private final java.sql.Timestamp arriveTime;
    private final java.sql.Timestamp departTime;
    private final java.sql.Timestamp startTime;
    private final String driverID;

    public Task_Perform(String taskID, String status, String destination, Timestamp completeAfter, Timestamp completeBefore, String taskNote,
                        Float distanceTravelled, Timestamp completeTime, Timestamp arriveTime, Timestamp departTime,
                        Timestamp startTime, String driverID){
        this.taskID = taskID;
        this.status = status;
        this.destination = destination;
        this.completeAfter = completeAfter;
        this.completeBefore = completeBefore;
        this.taskNote = taskNote;
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

    public String getStatus() {
        return status;
    }

    public String getDestination() {
        return destination;
    }

    public Timestamp getCompleteAfter() {
        return completeAfter;
    }

    public Timestamp getCompleteBefore() {
        return completeBefore;
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
        if (driverID == null) {
            return "";
        }
        return driverID;
    }

    public String getTaskNote() {
        if (taskNote == null) {
            return "";
        }
        return taskNote;
    }
}
