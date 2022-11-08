package ca.ubc.cs304.delegates;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * This interface uses the delegation design pattern where instead of having
 * the LoginWindow class try to do everything, it will only focus on
 * handling the UI. The actual logic/operation will be delegated to the controller
 * class (in this case Bank).
 *
 * LoginWindow calls the methods that we have listed below but
 * Bank is the actual class that will implement the methods.
 */
public interface AdminPanelDelegate {

    public ArrayList<Object[]> fillTables(String panelName);
    public void insertIntoTables(String panelName, Component[] components);
    public void updateEntryInTables(String panelName, Component[] components);
    public void deleteEntryFromTables(String panelName, Component[] components);
    public void deleteAllFromTable(String panelName);

    /**
     * Query functions
     */
    public ArrayList<String> projection(ArrayList<String> checked);
    public ArrayList<String> selection(String opt);
    public ArrayList<String> aggregation();
    public ArrayList<String> nestedAggregation();
    public ArrayList<String> join();
    public ArrayList<String> division();

    public void programExited();
}
