package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.AdminDBConnectionHandler;
import ca.ubc.cs304.delegates.AdminPanelDelegate;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.model.*;
import ca.ubc.cs304.ui.AdminPanel;
import ca.ubc.cs304.ui.LoginWindow;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

public class AdminController implements LoginWindowDelegate, AdminPanelDelegate {
    private static final String STATUS_PENDING = "Pending";

    private static final int NEW_ENTRY = 0;
    private static final int UPDATE_ENTRY = 1;
    private static final int DELETE_ENTRY = 2;

    private AdminDBConnectionHandler dbHandler = null;
    private LoginWindow loginWindow = null;

    public AdminController() {
        dbHandler = new AdminDBConnectionHandler();
    }

    private void start() {
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);


//        AdminPanel adminPanel = new AdminPanel();
//        adminPanel.showFrame(this);
    }

    /**
     * LoginWindowDelegate Implementation
     *
     * connects to Oracle database with supplied username and password
     */
    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window and start the admin panel
            loginWindow.dispose();

            AdminPanel adminPanel = new AdminPanel();
            adminPanel.showFrame(this);

        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }
    }


    @Override
    public void insertIntoTables(String panelName, Component[] components) {
        
        // Out put the information in JTextField into an String arrayList
        ArrayList<String> inputs = new ArrayList<>();
        for (Component component: components) {
            if (component instanceof JTextField) {
                inputs.add(((JTextField) component).getText());
            }
        }

        switch (panelName){
            case "Order":
                ChangeOrderTable(inputs, NEW_ENTRY);
                break;
            case "Task":
                ChangeTaskTable(inputs, NEW_ENTRY);
                break;
            case "Driver":
                ChangeDriverTable(inputs, NEW_ENTRY);
                break;
            case "Merchant":
                ChangeMerchantTable(inputs, NEW_ENTRY);
                break;
            case "Customer":
                ChangeCustomerTable(inputs, NEW_ENTRY);
                break;
            default:
                break;
        }
    }

    @Override
    public void updateEntryInTables(String panelName, Component[] components) {
        ArrayList<String> inputs = new ArrayList<>();
        for (Component component: components) {
            if (component instanceof JTextField) {
                inputs.add(((JTextField) component).getText());
            }
        }

        switch (panelName){
            case "Order":
                ChangeOrderTable(inputs, UPDATE_ENTRY);
                break;
            case "Task":
                ChangeTaskTable(inputs, UPDATE_ENTRY);
                break;
            case "Driver":
                ChangeDriverTable(inputs, UPDATE_ENTRY);
                break;
            case "Merchant":
                ChangeMerchantTable(inputs, UPDATE_ENTRY);
                break;
            case "Customer":
                ChangeCustomerTable(inputs, UPDATE_ENTRY);
                break;
            case "Location":
                ChangeLocationTable(inputs, UPDATE_ENTRY);
                break;
            case "MerchantLocation":
                ChangeMerchantLocationTable(inputs, UPDATE_ENTRY);
                break;
            default:
                break;
        }
    }

    @Override
    public void deleteEntryFromTables(String panelName, Component[] components) {
        ArrayList<String> inputs = new ArrayList<>();
        for (Component component: components) {
            if (component instanceof JTextField) {
                inputs.add(((JTextField) component).getText());
            }
        }

        switch (panelName){
            case "Order":
                ChangeOrderTable(inputs, DELETE_ENTRY);
                break;
            case "Task":
                ChangeTaskTable(inputs, DELETE_ENTRY);
                break;
            case "Driver":
                ChangeDriverTable(inputs, DELETE_ENTRY);
                break;
            case "Merchant":
                ChangeMerchantTable(inputs, DELETE_ENTRY);
                break;
            case "Customer":
                ChangeCustomerTable(inputs, DELETE_ENTRY);
                break;
            case "Location":
                ChangeLocationTable(inputs, DELETE_ENTRY);
                break;
            case "MerchantLocation":
                ChangeMerchantLocationTable(inputs, DELETE_ENTRY);
                break;
            default:
                break;
        }
    }

    /**
     * Automatically cascades delete all table entries
     * @param panelName     tab panel name for the GUI
     */
    @Override
    public void deleteAllFromTable(String panelName) {
        switch (panelName){
            case "Order":
                dbHandler.deleteAll("Orders");
                break;
            case "Task":
                dbHandler.deleteAll("Task_Perform");
                break;
            case "Driver":
                dbHandler.deleteAll("Driver");
                break;
            case "Merchant":
                dbHandler.deleteAll("Merchant");
                break;
            case "Customer":
                dbHandler.deleteAll("Customer");
                break;
            case "Location":
                dbHandler.deleteAll("Location");
                break;
            case "MerchantLocation":
                dbHandler.deleteAll("MerchantLocation_Owns");
                break;
            default:
                System.out.println(getClass().getName() + "UNKNOWN deleteAll Table");
                break;
        }
    }

    private void ChangeCustomerTable(ArrayList<String> inputs, int mode) {
        if (mode == NEW_ENTRY) {
            String customerId, locationId;
            customerId = randomId();
            locationId = randomId();

            String name = inputs.get(0);
            String phone = inputs.get(1);
            String city = inputs.get(2);
            String province = inputs.get(3);
            String country = inputs.get(4);
            String streetName = inputs.get(5);
            String streetNumber = inputs.get(6);
            String unitNumber = inputs.get(7);
            String buzzCode = inputs.get(8);
            String postalCode = inputs.get(9);
            Date signUpDate = new Date(System.currentTimeMillis());
            String note = inputs.get(10);
            String propertyType = inputs.get(11);

            Location location = new Location(locationId, city, province, streetName, streetNumber, unitNumber, postalCode, "");
            CustomerLocation customerLocation = new CustomerLocation(locationId, buzzCode, propertyType);
            City cityEntity = new City(city, province, "", "", country);
            Customer customer = new Customer(customerId, name, phone, locationId, signUpDate, note);

            dbHandler.insertCustomer(customer, customerLocation, location, cityEntity);
        } else if (mode == UPDATE_ENTRY) {

        }
    }

    private void ChangeMerchantTable(ArrayList<String> inputs, int mode) {
        if (mode == NEW_ENTRY) {
            String merchantId = randomId();
            String companyName = inputs.get(0);
            String phone = inputs.get(1);
            String email = inputs.get(2);
            String openingHour = inputs.get(3);
            String closingHour = inputs.get(4);
            String city = inputs.get(5);
            String province = inputs.get(6);
            String country = inputs.get(7);
            String streetName = inputs.get(8);
            String streetNumber = inputs.get(9);
            String unitNumber = inputs.get(10);
            String postalCode = inputs.get(11);
            String locationType = inputs.get(12);
            String merchantType = inputs.get(13);

            String locationId = randomId();

            Date signUpDate = new Date(System.currentTimeMillis());

            MerchantWithInfo merchant = new MerchantWithInfo(merchantId, companyName, signUpDate,
                    merchantType, openingHour, closingHour, email, phone);

            City cityEntity = new City(city, province, "", "", country);

            Location location = new Location(randomId(), city, province, streetName, streetNumber, unitNumber, postalCode, "");

            MerchantLocation_Owns merchantLocation = new MerchantLocation_Owns(locationId, locationType, "", merchantId);

            dbHandler.insertMerchant(merchant, cityEntity, location, merchantLocation);
        }
    }

    private void ChangeDriverTable(ArrayList<String> inputs, int mode) {
        if (mode == NEW_ENTRY) {
            String driverId = randomId();
            String name = inputs.get(0);
            String phone = inputs.get(1);
            String email = inputs.get(2);
            String licenseNumber = inputs.get(3);
            String SIN = inputs.get(4);
            Driver driver = new Driver(driverId, name, licenseNumber, SIN, email, phone);

            dbHandler.insertDriver(driver);
        } else if (mode == UPDATE_ENTRY) {
            String driverID = inputs.get(0);
            String name = inputs.get(1);
            String phone = inputs.get(2);
            String email = inputs.get(3);
            String licenseNumber = inputs.get(4);
            String SIN = inputs.get(5);
            Driver driver = new Driver(driverID, name, licenseNumber, SIN, email, phone);
            dbHandler.updateDriver(driver);
        } else {
            String driverID = inputs.get(0);
            dbHandler.deleteDriver(driverID);
        }
    }

    private void ChangeTaskTable(ArrayList<String> inputs, int mode) {
        if (mode == NEW_ENTRY) {
            String taskId = randomId();
            String taskType = inputs.get(0);
            String destination = inputs.get(1);
            String completeBeforeStr = inputs.get(2);
            Timestamp completeBefore = null;
            if (!completeBeforeStr.isEmpty()) {
                completeBefore = Timestamp.valueOf(completeBeforeStr);
            }
            String completeAfterStr = inputs.get(3);
            Timestamp completeAfter = null;
            if (!completeAfterStr.isEmpty()) {
                completeAfter = Timestamp.valueOf(completeAfterStr);
            }
            String contactName = inputs.get(4);
            String contactPhone = inputs.get(5);
            String taskNote = inputs.get(6);

            Task_Perform task_perform = new Task_Perform(taskId, STATUS_PENDING, destination, completeAfter, completeBefore,
                    taskNote, (float) 0, null, null, null, null, null);

            dbHandler.insertTask(task_perform, taskType, contactName, contactPhone);
        }
    }

    private void ChangeOrderTable(ArrayList<String> inputs, int mode) {
        if (mode == NEW_ENTRY) {
            String orderId = randomId();
            String deliveryDateStr = inputs.get(0);
            Timestamp deliveryDate = Timestamp.valueOf(deliveryDateStr);
            String deliveryAddress = inputs.get(1);
            String pickUpDateStr = inputs.get(2);
            Timestamp pickUpDate = Timestamp.valueOf(pickUpDateStr);
            String pickUpAddress = inputs.get(3);
            String priceStr = inputs.get(4);
            float price = Float.parseFloat(priceStr);
            String paymentMethod = inputs.get(5);
            String CustomerId = inputs.get(6);
            String MerchantId = inputs.get(7);

            Timestamp creationDate = new Timestamp(System.currentTimeMillis());

            Order order = new Order(orderId, STATUS_PENDING, price, deliveryDate, pickUpDate, creationDate,
                    null, paymentMethod, pickUpAddress, deliveryAddress, CustomerId, MerchantId);
            dbHandler.insertOrder(order);
        }

        // TODO: pop-up window for error msg
    }

    private void ChangeLocationTable(ArrayList<String> inputs, int mode) {
        if (mode == NEW_ENTRY) {
            String orderId = randomId();
            String deliveryDateStr = inputs.get(0);
            Timestamp deliveryDate = Timestamp.valueOf(deliveryDateStr);
            String deliveryAddress = inputs.get(1);
            String pickUpDateStr = inputs.get(2);
            Timestamp pickUpDate = Timestamp.valueOf(pickUpDateStr);
            String pickUpAddress = inputs.get(3);
            String priceStr = inputs.get(4);
            float price = Float.parseFloat(priceStr);
            String paymentMethod = inputs.get(5);
            String CustomerId = inputs.get(6);
            String MerchantId = inputs.get(7);

            Timestamp creationDate = new Timestamp(System.currentTimeMillis());

            Order order = new Order(orderId, STATUS_PENDING, price, deliveryDate, pickUpDate, creationDate,
                    null, paymentMethod, pickUpAddress, deliveryAddress, CustomerId, MerchantId);
            dbHandler.insertOrder(order);
        }
    }

    private void ChangeMerchantLocationTable(ArrayList<String> inputs, int mode) {
        if (mode == NEW_ENTRY) {
            String orderId = randomId();
            String deliveryDateStr = inputs.get(0);
            Timestamp deliveryDate = Timestamp.valueOf(deliveryDateStr);
            String deliveryAddress = inputs.get(1);
            String pickUpDateStr = inputs.get(2);
            Timestamp pickUpDate = Timestamp.valueOf(pickUpDateStr);
            String pickUpAddress = inputs.get(3);
            String priceStr = inputs.get(4);
            float price = Float.parseFloat(priceStr);
            String paymentMethod = inputs.get(5);
            String CustomerId = inputs.get(6);
            String MerchantId = inputs.get(7);

            Timestamp creationDate = new Timestamp(System.currentTimeMillis());

            Order order = new Order(orderId, STATUS_PENDING, price, deliveryDate, pickUpDate, creationDate,
                    null, paymentMethod, pickUpAddress, deliveryAddress, CustomerId, MerchantId);
            dbHandler.insertOrder(order);
        }
    }

    @Override
    public ArrayList<String> projection(ArrayList<String> checked){
        ArrayList<String> arr = dbHandler.projectionTaskPerform(checked);
        return arr;
    }

    @Override
    public ArrayList<String> selection(String opt){
        ArrayList<String> arr = dbHandler.selectRestaurantNames(opt);
        return arr;
    }

    @Override
    public ArrayList<String> aggregation(){
        ArrayList<String> arr = dbHandler.aggregationItemAndMerchant();
        return arr;
    }

    @Override
    public ArrayList<String> nestedAggregation(){
        ArrayList<String> arr = dbHandler.nestedAggregationAverage();
        return arr;
    }

    @Override
    public ArrayList<String> join(){
        ArrayList<String> arr = dbHandler.joinItemAndMerchant();
        return arr;
    }

    @Override
    public ArrayList<String> division() {
        ArrayList<String> arr = dbHandler.divisionMerchantLocation();
        return arr;
    }

    @Override
    public ArrayList<Object[]> fillTables(String panelName) {
        switch (panelName){
            case "Order":
                return fillOrderTable();
            case "Task":
                return fillTaskTable();
            case "Driver":
                return fillDriverTable();
            case "Merchant":
                return fillMerchantTable();
            case "Customer":
                return fillCustomerTable();
            case "Location":
                return fillLocationTable();
            case "MerchantLocation":
                return fillMerchantLocationTable();
            default:
                return new ArrayList<>();
        }
    }


    private ArrayList<Object[]> fillCustomerTable() {
        Customer[] customers = dbHandler.getCustomerInfo();

        ArrayList<Object[]> rows = new ArrayList<>();

        for(Customer customer : customers) {
            Object[] row = new Object[5];
            row[0] = customer.getCustomerID().trim();
            row[1] = customer.getName().trim();
            row[2] = customer.getPhoneNumber().trim();
            row[3] = customer.getSignUpDate();
            row[4] = customer.getNote().trim();
            rows.add(row);
        }
        return rows;
    }

    private ArrayList<Object[]> fillMerchantTable() {
        MerchantWithInfo[] merchants = dbHandler.getMerchantInfo();

        ArrayList<Object[]> rows = new ArrayList<>();

        for(MerchantWithInfo merchant : merchants) {
            Object[] row = new Object[8];
            row[0] = merchant.getMerchantID().trim();
            row[1] = merchant.getCompanyName().trim();
            row[2] = merchant.getMerchantType().trim();
            row[3] = merchant.getPhoneNumber().trim();
            row[4] = merchant.getEmailAddress().trim();
            row[5] = merchant.getSignUpDate();
            row[6] = merchant.getOpeningHour().trim();
            row[7] = merchant.getClosingHour().trim();
            rows.add(row);
        }
        return rows;
    }

    private ArrayList<Object[]> fillDriverTable() {
        Driver[] drivers = dbHandler.getDriverInfo();

        ArrayList<Object[]> rows = new ArrayList<>();

        for(Driver driver : drivers) {
            Object[] row = new Object[6];
            row[0] = driver.getDriverID().trim();
            row[1] = driver.getName().trim();
            row[2] = driver.getPhoneNumber().trim();
            row[3] = driver.getEmail().trim();
            row[4] = driver.getLicenseNumber().trim();
            row[5] = driver.getSin().trim();
            rows.add(row);
        }
        return rows;
    }

    private ArrayList<Object[]> fillTaskTable() {
        Task_Perform[] tasks = dbHandler.getTaskInfo();

        ArrayList<Object[]> rows = new ArrayList<>();

        for (Task_Perform task : tasks) {
            Object[] row = new Object[6];
            row[0] = task.getTaskID().trim();
            row[1] = task.getDestination().trim();
            if (task.getCompleteBefore() != null) {
                row[2] = task.getCompleteBefore().toString().substring(0, 16);
            }
            if (task.getCompleteAfter() != null) {
                row[3] = task.getCompleteAfter().toString().substring(0, 16);
            }
            row[4] = task.getTaskNote().trim();
            row[5] = task.getStatus().trim();
            rows.add(row);
        }

        return rows;
    }

    private ArrayList<Object[]> fillOrderTable() {
        Order[] orders = dbHandler.getOrderInfo();

        ArrayList<Object[]> rows = new ArrayList<>();

        for (Order order : orders) {
            Object[] row = new Object[9];
            row[0] = order.getOrderID().trim();
            row[1] = order.getPickUpAddress().trim();
            row[2] = order.getPickUpDate().toString().substring(0,10);
            row[3] = order.getDeliveryAddress().trim();
            row[4] = order.getDeliveryDate().toString().substring(0, 10);
            row[5] = order.getPaymentMethod().trim();
            row[6] = order.getOrderPrice();
            row[7] = order.getStatus().trim();
            row[8] = order.getCompletionTime();
            rows.add(row);
        }

        return rows;
    }

    private ArrayList<Object[]> fillLocationTable() {
        Location[] locations = dbHandler.getLocationInfo();

        ArrayList<Object[]> rows = new ArrayList<>();

        for(Location location : locations) {
            Object[] row = new Object[7];
            row[0] = location.getLocationID().trim();
            row[1] = location.getCity().trim();
            row[2] = location.getProvince().trim();
            row[3] = location.getStreetName().trim();
            if (location.getUnitNumber() != null) {
                row[4] = location.getUnitNumber().trim();
            }
            row[5] = location.getPostalCode().trim();
            if (location.getNote() != null) {
                row[6] = location.getNote().trim();
            }
            rows.add(row);
        }
        return rows;
    }

    private ArrayList<Object[]> fillMerchantLocationTable() {
        MerchantLocation_Owns[] mls = dbHandler.getMerchantLocationInfo();

        ArrayList<Object[]> rows = new ArrayList<>();

        for(MerchantLocation_Owns ml : mls) {
            Object[] row = new Object[4];
            row[0] = ml.getLocationID().trim();
            row[1] = ml.getType().trim();
            if (ml.getLandLineNumber() != null) {
                row[2] = ml.getLandLineNumber().trim();
            }
            row[3] = ml.getMerchantID().trim();
            rows.add(row);
        }

        return rows;
    }

    private String randomId() {
        return UUID.randomUUID().toString().replace("-","").substring(0, 30);
    }

    /**
     * AdminPanelDelegate Implementation
     *
     * The TerminalTransaction instance tells us that it is done with what it's
     * doing so we are cleaning up the connection since it's no longer needed.
     */
    public void programExited() {
        if (dbHandler != null) {
            dbHandler.close();
            dbHandler = null;
        }

        System.out.println("dbHandler dealt with");

        System.exit(0);
    }


    public static void main(String[] args) {
        AdminController adminController = new AdminController();
        adminController.start();
    }

}
