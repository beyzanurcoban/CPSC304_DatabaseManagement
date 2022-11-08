package ca.ubc.cs304.database;

import ca.ubc.cs304.model.*;
import ca.ubc.cs304.model.Driver;
import ca.ubc.cs304.util.PrintablePreparedStatement;

import java.sql.*;
import java.util.ArrayList;

public class AdminDBConnectionHandler {
    // Use this version of the ORACLE_URL if you are running the code off of the server
    //	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
    // Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private static final String TASK_TYPE_PICKUP = "PickUp";
    private static final String TASK_TYPE_DELIVERY = "Delivery";
    private static final String TASK_TYPE_TRANSSHIP = "Transship";

    private Connection connection = null;

    public AdminDBConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void insertTask(Task_Perform task_perform, String type, String contactName, String contactPhone) {
        insertTask(task_perform);
        switch (type) {
            case TASK_TYPE_DELIVERY:
                DeliveryTask_PerformRecipient recipient = new DeliveryTask_PerformRecipient(contactName, contactPhone);
                DeliveryTask_Perform deliveryTask_perform = new DeliveryTask_Perform(
                        task_perform.getTaskID(),
                        contactName,
                        task_perform.getDistanceTravelled(),
                        task_perform.getCompleteTime(),
                        task_perform.getArriveTime(),
                        task_perform.getDepartTime(),
                        task_perform.getStartTime(),
                        task_perform.getDriverID()
                );
                insertDeliveryContact(recipient);
                insertDeliveryTask(deliveryTask_perform);
                break;
            case TASK_TYPE_PICKUP:
                PickUpTask_PerformMerchant merchant = new PickUpTask_PerformMerchant(contactName, contactPhone);
                PickUpTask_Perform pickUpTask_perform = new PickUpTask_Perform(
                        task_perform.getTaskID(),
                        contactName,
                        task_perform.getDistanceTravelled(),
                        task_perform.getCompleteTime(),
                        task_perform.getArriveTime(),
                        task_perform.getDepartTime(),
                        task_perform.getStartTime(),
                        task_perform.getDriverID()
                );
                insertPickUpContact(merchant);
                insertPickUpTask(pickUpTask_perform);
                break;
            case TASK_TYPE_TRANSSHIP:
                TransshipTask_PerformContact contact = new TransshipTask_PerformContact(contactName, contactPhone);
                TransshipTask_Perform transshipTask_perform = new TransshipTask_Perform(
                        task_perform.getTaskID(),
                        contactName,
                        task_perform.getDistanceTravelled(),
                        task_perform.getCompleteTime(),
                        task_perform.getArriveTime(),
                        task_perform.getDepartTime(),
                        task_perform.getStartTime(),
                        task_perform.getDriverID()
                );
                insertTransshipContact(contact);
                insertTransshipTask(transshipTask_perform);
                break;
            default:
                System.out.println("ERROR: Unknown task type!");
                rollbackConnection();
                break;
        }
    }

    private void insertTask(Task_Perform task_perform){
        try {
            String query = "INSERT INTO task_perform VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, task_perform.getTaskID());
            ps.setString(2, task_perform.getStatus());
            ps.setString(3, task_perform.getDestination());
            if (task_perform.getCompleteAfter() == null){
                ps.setNull(4, Types.CHAR);
            } else {
                ps.setTimestamp(4, task_perform.getCompleteAfter());
            }
            if (task_perform.getCompleteBefore() == null){
                ps.setNull(5, Types.CHAR);
            } else {
                ps.setTimestamp(5, task_perform.getCompleteBefore());
            }
            if (task_perform.getTaskNote() == null){
                ps.setNull(6, Types.CHAR);
            } else {
                ps.setString(6, task_perform.getTaskNote());
            }
            if (task_perform.getDistanceTravelled() == -1){
                ps.setNull(7, Types.FLOAT);
            } else {
                ps.setFloat(7, task_perform.getDistanceTravelled());
            }
            if (task_perform.getCompleteTime() == null){
                ps.setNull(8, Types.CHAR);
            } else {
                ps.setTimestamp(8, task_perform.getCompleteTime());
            }
            if (task_perform.getArriveTime() == null){
                ps.setNull(9, Types.CHAR);
            } else {
                ps.setTimestamp(9, task_perform.getArriveTime());
            }
            if (task_perform.getDepartTime() == null){
                ps.setNull(10, Types.CHAR);
            } else {
                ps.setTimestamp(10, task_perform.getDepartTime());
            }
            if (task_perform.getStartTime() == null){
                ps.setNull(11, Types.CHAR);
            } else {
                ps.setTimestamp(11, task_perform.getStartTime());
            }
            ps.setString(12, task_perform.getDriverID());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    private void insertTransshipTask(TransshipTask_Perform transship){
        try {
            String query = "INSERT INTO transshiptask_perform VALUES (?,?,?,?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, transship.getTaskID());
            ps.setString(2, transship.getContactName());
            if (transship.getDistanceTravelled() == -1){
                ps.setNull(3, Types.FLOAT);
            } else {
                ps.setFloat(3, transship.getDistanceTravelled());
            }
            if (transship.getStartTime() == null){
                ps.setNull(4, Types.CHAR);
            } else {
                ps.setTimestamp(4, transship.getStartTime());
            }
            if (transship.getDepartTime() == null){
                ps.setNull(5, Types.CHAR);
            } else {
                ps.setTimestamp(5, transship.getDepartTime());
            }
            if (transship.getArriveTime() == null){
                ps.setNull(6, Types.CHAR);
            } else {
                ps.setTimestamp(6, transship.getArriveTime());
            }
            if (transship.getCompleteTime() == null){
                ps.setNull(7, Types.CHAR);
            } else {
                ps.setTimestamp(7, transship.getCompleteTime());
            }
            ps.setString(8, transship.getDriverID());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    private void insertPickUpTask(PickUpTask_Perform pickup){
        try {
            String query = "INSERT INTO pickuptask_perform VALUES (?,?,?,?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, pickup.getTaskID());
            ps.setString(2, pickup.getMerchantName());
            if (pickup.getDistanceTravelled() == -1){
                ps.setNull(3, Types.FLOAT);
            } else {
                ps.setFloat(3, pickup.getDistanceTravelled());
            }
            if (pickup.getStartTime() == null){
                ps.setNull(4, Types.CHAR);
            } else {
                ps.setTimestamp(4, pickup.getStartTime());
            }
            if (pickup.getDepartTime() == null){
                ps.setNull(5, Types.CHAR);
            } else {
                ps.setTimestamp(5, pickup.getDepartTime());
            }
            if (pickup.getArriveTime() == null){
                ps.setNull(6, Types.CHAR);
            } else {
                ps.setTimestamp(6, pickup.getArriveTime());
            }
            if (pickup.getCompleteTime() == null){
                ps.setNull(7, Types.CHAR);
            } else {
                ps.setTimestamp(7, pickup.getCompleteTime());
            }
            ps.setString(8, pickup.getDriverID());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    private void insertDeliveryTask(DeliveryTask_Perform delivery){
        try {
            String query = "INSERT INTO deliverytask_perform VALUES (?,?,?,?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, delivery.getTaskID());
            ps.setString(2, delivery.getRecipientName());
            if (delivery.getDistanceTravelled() == -1){
                ps.setNull(3, Types.FLOAT);
            } else {
                ps.setFloat(3, delivery.getDistanceTravelled());
            }
            if (delivery.getStartTime() == null){
                ps.setNull(4, Types.CHAR);
            } else {
                ps.setTimestamp(4, delivery.getStartTime());
            }
            if (delivery.getDepartTime() == null){
                ps.setNull(5, Types.CHAR);
            } else {
                ps.setTimestamp(5, delivery.getDepartTime());
            }
            if (delivery.getArriveTime() == null){
                ps.setNull(6, Types.CHAR);
            } else {
                ps.setTimestamp(6, delivery.getArriveTime());
            }
            if (delivery.getCompleteTime() == null){
                ps.setNull(7, Types.CHAR);
            } else {
                ps.setTimestamp(7, delivery.getCompleteTime());
            }
            ps.setString(8, delivery.getDriverID());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    private void insertTransshipContact(TransshipTask_PerformContact transshipTask_performContact) {
        try {
            String query = "INSERT INTO TRANSSHIPTASK_PERFORMCONTACT VALUES (?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, transshipTask_performContact.getContactName());
            ps.setString(2, transshipTask_performContact.getContactPhone());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    private void insertDeliveryContact(DeliveryTask_PerformRecipient deliveryTask_performRecipient) {
        try {
            String query = "INSERT INTO DELIVERYTASK_PERFORMRECIPIENT VALUES (?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, deliveryTask_performRecipient.getRecipientName());
            ps.setString(2, deliveryTask_performRecipient.getRecipientPhone());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    private void insertPickUpContact(PickUpTask_PerformMerchant pickUpTask_performMerchant) {
        try {
            String query = "INSERT INTO PICKUPTASK_PERFORMMERCHANT VALUES (?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, pickUpTask_performMerchant.getMerchantName());
            ps.setString(2, pickUpTask_performMerchant.getMerchantPhone());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertMerchant(MerchantWithInfo merchantWithInfo, City city, Location location, MerchantLocation_Owns merchantLocation) {
        try {
            Merchant merchant = new Merchant(
                    merchantWithInfo.getMerchantID(),
                    merchantWithInfo.getCompanyName(),
                    merchantWithInfo.getSignUpDate()
            );
            MerchantInfo merchantInfo = new MerchantInfo(
                    merchantWithInfo.getCompanyName(),
                    merchantWithInfo.getEmailAddress(),
                    merchantWithInfo.getPhoneNumber(),
                    merchantWithInfo.getOpeningHour(),
                    merchantWithInfo.getClosingHour(),
                    merchantWithInfo.getMerchantType()
            );
            MerchantHours merchantHours = new MerchantHours(
                    merchantWithInfo.getOpeningHour(),
                    merchantWithInfo.getClosingHour(),
                    "placeHolder" // TODO: calculate the duration, this will need input validation
            );
            insertMerchantHours(merchantHours);
            insertMerchantInfo(merchantInfo);
            insertMerchant(merchant);

            insertCity(city);
            insertLocation(location);
            insertMerchantLocation(merchantLocation);

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    private void insertMerchant(Merchant merchant) throws SQLException {
        String query = "INSERT INTO merchant VALUES (?,?,?)";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
        ps.setString(1, merchant.getMerchantID());
        ps.setString(2, merchant.getCompanyName());
        if (merchant.getSignUpDate() == null){
            ps.setNull(3, Types.DATE);
        } else{
            ps.setDate(3, merchant.getSignUpDate());
        }

        ps.executeUpdate();
        connection.commit();

        ps.close();
    }

    public void insertMerchantInfo(MerchantInfo mercInfo) throws SQLException {
        String query = "INSERT INTO merchantinfo VALUES (?,?,?,?,?,?)";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
        ps.setString(1, mercInfo.getCompanyName());
        ps.setString(2, mercInfo.getType());
        ps.setString(3, mercInfo.getOpeningHour());
        ps.setString(4, mercInfo.getClosingHour());
        if (mercInfo.getEmail() == null){
            ps.setNull(5, Types.CHAR);
        } else{
            ps.setString(5, mercInfo.getEmail());
        }
        if (mercInfo.getPhoneNumber() == null){
            ps.setNull(6, Types.CHAR);
        } else{
            ps.setString(6, mercInfo.getPhoneNumber());
        }

        ps.executeUpdate();
        connection.commit();

        ps.close();
    }

    public void insertMerchantHours(MerchantHours merchantHours) throws SQLException {
        String query = "INSERT INTO merchanthours VALUES (?,?,?)";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
        ps.setString(1, merchantHours.getOpeningHour());
        ps.setString(2, merchantHours.getClosingHour());
        ps.setString(3, merchantHours.getOpeningDuration());

        ps.executeUpdate();
        connection.commit();

        ps.close();
    }

    public void insertOrder(Order order){
        try {
            String query = "INSERT INTO orders VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, order.getOrderID());
            ps.setString(2, order.getStatus());
            ps.setFloat(3, order.getOrderPrice());
            if (order.getDeliveryDate() == null){
                ps.setNull(4, Types.CHAR);
            } else {
                ps.setTimestamp(4, order.getDeliveryDate());
            }
            if (order.getPickUpDate() == null){
                ps.setNull(5, Types.CHAR);
            } else {
                ps.setTimestamp(5, order.getPickUpDate());
            }
            if (order.getCreationTime() == null){
                ps.setNull(6, Types.CHAR);
            } else {
                ps.setTimestamp(6, order.getCreationTime());
            }
            if (order.getCompletionTime() == null){
                ps.setNull(7, Types.CHAR);
            } else {
                ps.setTimestamp(7, order.getCompletionTime());
            }
            if (order.getPaymentMethod() == null){
                ps.setNull(8, Types.CHAR);
            } else {
                ps.setString(8, order.getPaymentMethod());
            }
            if (order.getPickUpAddress() == null){
                ps.setNull(9, Types.CHAR);
            } else {
                ps.setString(9, order.getPickUpAddress());
            }
            ps.setString(10, order.getDeliveryAddress());
            ps.setString(11, order.getCustomerID());
            ps.setString(12, order.getMerchantID());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }


    public void insertDriver(Driver driver){
        try {
            String query = "INSERT INTO driver VALUES (?,?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, driver.getDriverID());
            ps.setString(2, driver.getName());
            ps.setString(3, driver.getLicenseNumber());
            ps.setString(4, driver.getSin());
            ps.setString(5, driver.getEmail());
            ps.setString(6, driver.getPhoneNumber());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertCustomer(Customer customer, CustomerLocation customerLocation, Location location, City cityEntity) {
        try {
            insertCity(cityEntity);
            insertLocation(location);
            insertCustomerLocation(customerLocation);
            insertCustomer(customer);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    private void insertCity(City city) throws SQLException {
        String query = "INSERT INTO CITY VALUES (?,?,?,?,?)";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
        ps.setString(1, city.getCity());
        if (city.getProvince() == null) {
            ps.setNull(2, Types.CHAR);
        }
        else {
            ps.setString(2, city.getProvince());
        }
        if (city.getLongitude() == null){
            ps.setNull(3, Types.CHAR);
        } else{
            ps.setString(3, city.getLongitude());
        }
        if (city.getLatitude() == null){
            ps.setNull(4, Types.CHAR);
        } else{
            ps.setString(4, city.getLatitude());
        }
        ps.setString(5, city.getCountry());

        ps.executeUpdate();
        connection.commit();

        ps.close();
    }

    private void insertCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO CUSTOMER VALUES (?,?,?,?,?,?)";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
        ps.setString(1, customer.getCustomerID());
        ps.setString(2, customer.getName());
        ps.setString(3, customer.getPhoneNumber());
        ps.setString(4, customer.getLocationId());
        if (customer.getSignUpDate() == null){
            ps.setNull(5, Types.DATE);
        } else{
            ps.setDate(5, customer.getSignUpDate());
        }
        if (customer.getNote() == null){
            ps.setNull(6, Types.CHAR);
        } else {
            ps.setString(6, customer.getNote());
        }

        ps.executeUpdate();
        connection.commit();

        ps.close();
    }

    private void insertLocation(Location location) throws SQLException {
        String query = "INSERT INTO location VALUES (?,?,?,?,?,?,?,?)";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
        ps.setString(1, location.getLocationID());
        ps.setString(2, location.getCity());
        ps.setString(3, location.getProvince());
        if (location.getStreetName() == null){
            ps.setNull(4, Types.CHAR);
        } else {
            ps.setString(4, location.getStreetName());
        }
        if (location.getStreetNumber() == null){
            ps.setNull(5, Types.CHAR);
        } else {
            ps.setString(5, location.getStreetNumber());
        }
        if (location.getUnitNumber() == null){
            ps.setNull(6, Types.CHAR);
        } else {
            ps.setString(6, location.getUnitNumber());
        }
        ps.setString(7, location.getPostalCode());
        if (location.getNote() == null){
            ps.setNull(8, Types.CHAR);
        } else {
            ps.setString(8, location.getNote());
        }

        ps.executeUpdate();
        connection.commit();

        ps.close();
    }

    private void insertCustomerLocation(CustomerLocation customerLocation) throws SQLException {
        String query = "INSERT INTO customerlocation VALUES (?,?,?)";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
        ps.setString(1, customerLocation.getLocationID());
        if (customerLocation.getBuzzCode() == null){
            ps.setNull(2, Types.CHAR);
        } else{
            ps.setString(2, customerLocation.getBuzzCode());
        }
        if (customerLocation.getPropertyType() == null){
            ps.setNull(3, Types.CHAR);
        } else {
            ps.setString(3, customerLocation.getPropertyType());
        }

        ps.executeUpdate();
        connection.commit();

        ps.close();
    }

    private void insertStorageLocation(StorageLocation strLoc){
        try {
            String query = "INSERT INTO storagelocation VALUES (?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, strLoc.getLocationID());
            if (strLoc.getCapacity() == null){
                ps.setNull(2, Types.CHAR);
            } else{
                ps.setString(2, strLoc.getCapacity());
            }

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    private void insertMerchantLocation(MerchantLocation_Owns mercLoc){
        try {
            String query = "INSERT INTO merchantlocation_owns VALUES (?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, mercLoc.getLocationID());
            ps.setString(2, mercLoc.getType());
            if (mercLoc.getLandLineNumber() == null){
                ps.setNull(3, Types.CHAR);
            } else{
                ps.setString(3, mercLoc.getLandLineNumber());
            }
            ps.setString(4, mercLoc.getMerchantID());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    /* Ex: SELECT CompleteBefore
			FROM task_perform;
	 */
    public ArrayList<String> projectionTaskPerform(ArrayList<String> checked){
        ArrayList<String> result = new ArrayList<>();

        try {
            // "SELECT TaskID, Status, Destination, DriverID FROM task_perform";
            String query = "SELECT ";
            for (int counter = 0; counter < checked.size()-1; counter++) {
                query += checked.get(counter) + ", ";
            }
            query += checked.get(checked.size()-1);
            query += " FROM task_perform";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);

            ResultSet rs = ps.executeQuery();

            String taskid = null;
            String stat = null;
            String dest = null;
            String note = null;
            Float dist = null;
            String driverid = null;

            while(rs.next()) {
                if(checked.contains("TaskID")){
                    taskid = rs.getString("TaskID");
                    result.add(taskid);
                }

                if(checked.contains("Status")){
                    stat = rs.getString("Status");
                    result.add(stat);
                }

                if(checked.contains("Destination")){
                    dest = rs.getString("Destination");
                    result.add(dest);
                }

                if(checked.contains("TaskNote")) {
                    note = rs.getString("TaskNote");
                    result.add(note);
                }

                if(checked.contains("DistanceTravelled")){
                    dist = rs.getFloat("DistanceTravelled");
                    result.add(dist.toString());
                }

                if(checked.contains("DriverID")) {
                    driverid = rs.getString("DriverID");
                    result.add(driverid);
                }

                Task_Perform model = new Task_Perform(taskid,
                        stat,
                        dest,
                        null,
                        null,
                        note,
                        dist,
                        null,
                        null,
                        null,
                        null,
                        driverid);
            }

            rs.close();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return result;
    }

    /**
     * Selection Query
     * Selects restaurants from merchantInfo
     * @return ArrayList of Columns
     */
    public ArrayList<String> selectRestaurantNames(String opt) {
        ArrayList<String> result = new ArrayList<>();

        try {
            String query = "SELECT * FROM merchantinfo WHERE MerchantType = '" + opt + "'";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                MerchantInfo model = new MerchantInfo(rs.getString("CompanyName"),
                        rs.getString("MerchantType"),
                        rs.getString("EmailAddress"),
                        rs.getString("PhoneNumber"),
                        rs.getString("OpeningHour"),
                        rs.getString("ClosingHour"));
                result.add(model.getCompanyName());
                result.add(model.getType());
                result.add(model.getEmail());
                result.add(model.getPhoneNumber());
                result.add(model.getOpeningHour());
                result.add(model.getClosingHour());
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result;
    }

    /* For each item that is expensive than 5 dollars, find the merchant who has the earliest sign up date and that item:
		SELECT M.MERCHANTID, M.COMPANYNAME, M.SIGNUPDATE
        FROM merchant M
        WHERE M.SignUpDate = TO_DATE((SELECT MIN(M2.SignUpDate) FROM merchant M2, ITEM I WHERE M2.MERCHANTID=I.MERCHANTID AND I.PRICE > 5));
	 */
    public ArrayList<String> aggregationItemAndMerchant(){
        ArrayList<String> result = new ArrayList<>();

        try {
            String query = "SELECT M.MERCHANTID, M.COMPANYNAME, M.SIGNUPDATE FROM merchant M WHERE M.SignUpDate = TO_DATE((SELECT MIN(M2.SignUpDate) FROM merchant M2, ITEM I WHERE M2.MERCHANTID=I.MERCHANTID AND I.PRICE > 5))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Merchant model = new Merchant(rs.getString("MerchantID"),
                        rs.getString("CompanyName"),
                        rs.getDate("SignUpDate"));
                result.add(model.getMerchantID());
                result.add(model.getCompanyName());
                result.add(model.getSignUpDate().toString());
            }

            rs.close();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return result;
    }

    /**
     * Nested Aggregation Query
     * @return ArrayList of Columns
     */
    public ArrayList<String> nestedAggregationAverage(){
        ArrayList<String> result = new ArrayList<>();

        try {
            String query = "SELECT AVG(Price) AS averageItemPrice, m.companyName FROM Item i INNER JOIN Merchant m ON i.MerchantID = m.MerchantID GROUP BY companyName ORDER BY averageItemPrice DESC";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                result.add(rs.getString("averageItemPrice"));
                result.add(rs.getString("companyName"));
            }

            rs.close();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return result;
    }

    /**
     * Join Query
     * @return ArrayList of Columns
     */
    public ArrayList<String> joinItemAndMerchant(){
        ArrayList<String> result = new ArrayList<>();

        try {
            String query = "SELECT i.ItemID, i.Name, i.Price, m.companyName FROM Item i INNER JOIN Merchant m ON i.MerchantID = m.MerchantID";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                result.add(rs.getString("ItemID"));
                result.add(rs.getString("Name"));
                result.add(rs.getString("Price"));
                result.add(rs.getString("companyName"));
            }

            rs.close();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return result;
    }

    /**
     * Division query
     * Selects all merchantNames who has an office in every location
     */
    public ArrayList<String> divisionMerchantLocation(){
        ArrayList<String> result = new ArrayList<>();

        try {
            String query = "SELECT m.MerchantID, m.CompanyName, m.SignUpDate " +
                    "FROM merchant m " +
                    "WHERE NOT EXISTS " +
                    "(SELECT l.LocationID " +
                    "FROM location l " +
                    "WHERE NOT EXISTS (SELECT ml.LocationID FROM merchantlocation_owns ml " +
                    "WHERE ml.LocationID = l.LocationID " +
                    "AND m.MerchantID = ml.MerchantID))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                result.add(rs.getString("MerchantID"));
                result.add(rs.getString("CompanyName"));
                result.add(rs.getString("SignUpDate"));
            }

            rs.close();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return result;
    }

    /**
     * Update driver
     */
    public void updateDriver(Driver driver) {
        try {
            String id = driver.getDriverID();
            String name = driver.getName();
            String license = driver.getLicenseNumber();
            String sin = driver.getSin();
            String email = driver.getEmail();
            String pnum = driver.getPhoneNumber();
            String query = "UPDATE driver SET Name = '" + name + "', LicenseNumber = '" + license +
                    "', SIN = '" + sin + "', Email = '" + email + "', PhoneNumber = '" + pnum + "' WHERE DriverID = '" + id + "'";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    /**
     * Delete driver
     */
    public void deleteDriver(String driverID) {
        try {
            String query = "DELETE FROM driver WHERE DriverID = '" + driverID + "'";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public Order[] getOrderInfo() {
        ArrayList<Order> result = new ArrayList<>();

        try {
            String query = "SELECT * FROM ORDERS";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Order model = new Order(rs.getString("OrderId"),
                        rs.getString("Status"),
                        rs.getFloat("OrderPrice"),
                        rs.getTimestamp("DeliveryDate"),
                        rs.getTimestamp("PickUpDate"),
                        rs.getTimestamp("CreationTime"),
                        rs.getTimestamp("CompletionTime"),
                        rs.getString("PaymentMethod"),
                        rs.getString("PickUpAddress"),
                        rs.getString("DeliveryAddress"),
                        rs.getString("CustomerID"),
                        rs.getString("MerchantID"));
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Order[result.size()]);
    }

    public Task_Perform[] getTaskInfo() {
        ArrayList<Task_Perform> result = new ArrayList<>();

        try {
            String query = "SELECT * FROM TASK_PERFORM";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Task_Perform model = new Task_Perform(
                        rs.getString("TaskID"),
                        rs.getString("Status"),
                        rs.getString("Destination"),
                        rs.getTimestamp("CompleteAfter"),
                        rs.getTimestamp("CompleteBefore"),
                        rs.getString("TaskNote"),
                        rs.getFloat("DistanceTravelled"),
                        rs.getTimestamp("CompleteTime"),
                        rs.getTimestamp("ArriveTime"),
                        rs.getTimestamp("DepartTime"),
                        rs.getTimestamp("StartTime"),
                        rs.getString("DriverID")
                );
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Task_Perform[result.size()]);
    }

    public Driver[] getDriverInfo() {
        ArrayList<Driver> result = new ArrayList<>();

        try {
            String query = "SELECT * FROM DRIVER";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Driver model = new Driver(
                        rs.getString("DriverID"),
                        rs.getString("Name"),
                        rs.getString("LicenseNumber"),
                        rs.getString("SIN"),
                        rs.getString("Email"),
                        rs.getString("PhoneNumber")
                );
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Driver[result.size()]);
    }

    public void deleteAll(String tableName) {
        try {
            String query = "DELETE FROM " + tableName;
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public MerchantWithInfo[] getMerchantInfo() {
        ArrayList<MerchantWithInfo> result = new ArrayList<>();

        try {
            String query = "SELECT MERCHANTID, M.COMPANYNAME, SIGNUPDATE, MERCHANTTYPE, OPENINGHOUR, CLOSINGHOUR, EMAILADDRESS, PHONENUMBER\n" +
                           "FROM MERCHANT M\n" +
                           "JOIN MERCHANTINFO MI ON M.COMPANYNAME = MI.COMPANYNAME";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                MerchantWithInfo model = new MerchantWithInfo(
                        rs.getString("merchantID"),
                        rs.getString("companyName"),
                        rs.getDate("signUpDate"),
                        rs.getString("merchantType"),
                        rs.getString("openingHour"),
                        rs.getString("closingHour"),
                        rs.getString("emailAddress"),
                        rs.getString("phoneNumber")
                );
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new MerchantWithInfo[result.size()]);
    }


    public Customer[] getCustomerInfo() {
        ArrayList<Customer> result = new ArrayList<>();

        try {
            String query = "SELECT * FROM CUSTOMER";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Customer model = new Customer(
                        rs.getString("customerID"),
                        rs.getString("name"),
                        rs.getString("phoneNumber"),
                        rs.getString("locationId"),
                        rs.getDate("signUpDate"),
                        rs.getString("Note")
                );
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Customer[result.size()]);
    }

    public Location[] getLocationInfo() {
        ArrayList<Location> result = new ArrayList<>();

        try {
            String query = "SELECT * FROM Location";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Location model = new Location(
                        rs.getString("LocationID"),
                        rs.getString("City"),
                        rs.getString("Province"),
                        rs.getString("StreetName"),
                        rs.getString("StreetNumber"),
                        rs.getString("UnitNumber"),
                        rs.getString("PostalCode"),
                        rs.getString("Note")
                );
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Location[result.size()]);
    }

    public MerchantLocation_Owns[] getMerchantLocationInfo() {
        ArrayList<MerchantLocation_Owns> result = new ArrayList<>();

        try {
            String query = "SELECT * FROM MERCHANTLOCATION_OWNS";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                MerchantLocation_Owns model = new MerchantLocation_Owns(
                        rs.getString("LocationID"),
                        rs.getString("Type"),
                        rs.getString("LandlineNumber"),
                        rs.getString("MerchantID")
                );
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new MerchantLocation_Owns[result.size()]);
    }
}
