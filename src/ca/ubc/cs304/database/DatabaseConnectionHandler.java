package ca.ubc.cs304.database;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;

import ca.ubc.cs304.model.*;
import ca.ubc.cs304.model.Driver;
import ca.ubc.cs304.util.PrintablePreparedStatement;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	// Use this version of the ORACLE_URL if you are running the code off of the server
	// private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
	// Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";

	private Connection connection = null;

	public DatabaseConnectionHandler() {
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

	/* INSERT INTO driver VALUES ('00003333', 'Steve Sloss', '1223345', '886655220', 'stevesloss@gmail.com', '2360095656'); */
	public void insertDriver(Driver driver){
		try {
			String query = "INSERT INTO driver VALUES (?,?,?,?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, driver.getDriverID());
			ps.setString(2, driver.getName());
			ps.setString(3, driver.getLicenseNumber());
			ps.setString(4, driver.getSin());
			if (driver.getEmail().equals("null")){
				ps.setNull(5, Types.CHAR);
			} else {
				ps.setString(5, driver.getEmail());
			}
			if (driver.getPhoneNumber().equals("null")) {
				ps.setNull(6, Types.CHAR);
			} else {
				ps.setString(6, driver.getPhoneNumber());
			}

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	/* Ex: DELETE FROM driver
			WHERE Name = 'Angus Tian';
	 */
	public void deleteDriver(String driverName) {
		try {
			String query = "DELETE FROM driver WHERE Name = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, driverName);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Driver " + driverName + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	/* Ex: UPDATE driver
			SET PhoneNumber = '6048381100'
			WHERE DriverID = '00001235';
	 */
	public void updateDriver(String phoneNum, String id) {
		try {
			String query = "UPDATE driver SET PhoneNumber = ? WHERE DriverID = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, phoneNum);
			ps.setString(2, id);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Driver " + id + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertOrder(Order order){
		try {
			String query = "INSERT INTO orders VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, order.getOrderID());
			ps.setString(2, order.getStatus());
			ps.setFloat(3, order.getOrderPrice());
			if (order.getDeliveryDate().equals("null")){
				ps.setNull(4, Types.CHAR);
			} else {
				ps.setTimestamp(4, order.getDeliveryDate());
			}
			if (order.getPickUpDate().equals("null")){
				ps.setNull(5, Types.CHAR);
			} else {
				ps.setTimestamp(5, order.getPickUpDate());
			}
			if (order.getCreationTime().equals("null")){
				ps.setNull(6, Types.CHAR);
			} else {
				ps.setTimestamp(6, order.getCreationTime());
			}
			if (order.getCompletionTime().equals("null")){
				ps.setNull(7, Types.CHAR);
			} else {
				ps.setTimestamp(7, order.getCompletionTime());
			}
			if (order.getPaymentMethod().equals("null")){
				ps.setNull(8, Types.CHAR);
			} else {
				ps.setString(8, order.getPaymentMethod());
			}
			if (order.getPickUpAddress().equals("null")){
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

	public void insertMerchant(Merchant merchant){
		try {
			String query = "INSERT INTO merchant VALUES (?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, merchant.getMerchantID());
			ps.setString(2, merchant.getCompanyName());
			if (merchant.getSignUpDate().equals("null")){
				ps.setNull(3, Types.DATE);
			} else{
				ps.setDate(3, merchant.getSignUpDate());
			}

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertMerchantInfo(MerchantInfo mercInfo){
		try {
			String query = "INSERT INTO merchantinfo VALUES (?,?,?,?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, mercInfo.getCompanyName());
			ps.setString(2, mercInfo.getType());
			ps.setString(3, mercInfo.getOpeningHour());
			ps.setString(4, mercInfo.getClosingHour());
			if (mercInfo.getEmail().equals("null")){
				ps.setNull(5, Types.CHAR);
			} else{
				ps.setString(5, mercInfo.getEmail());
			}
			if (mercInfo.getPhoneNumber().equals("null")){
				ps.setNull(6, Types.CHAR);
			} else{
				ps.setString(6, mercInfo.getPhoneNumber());
			}

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertMerchantHours(MerchantHours mercHour){
		try {
			String query = "INSERT INTO merchanthours VALUES (?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, mercHour.getOpeningHour());
			ps.setString(2, mercHour.getClosingHour());
			ps.setString(3, mercHour.getOpeningDuration());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertCustomer(Customer customer){
		try {
			String query = "INSERT INTO customer VALUES (?,?,?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, customer.getCustomerID());
			ps.setString(2, customer.getName());
			ps.setString(3, customer.getPhoneNumber());
			if (customer.getSignUpDate().equals("null")){
				ps.setNull(4, Types.DATE);
			} else{
				ps.setDate(4, customer.getSignUpDate());
			}
			if (customer.getNote().equals("null")){
				ps.setNull(5, Types.CHAR);
			} else {
				ps.setString(4, customer.getNote());
			}

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertTask(Task_Perform task_perform){
		try {
			String query = "INSERT INTO task_perform VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, task_perform.getTaskID());
			ps.setString(2, task_perform.getStatus());
			ps.setString(3, task_perform.getDestination());
			if (task_perform.getCompleteAfter().equals("null")){
				ps.setNull(4, Types.CHAR);
			} else {
				ps.setTimestamp(4, task_perform.getCompleteAfter());
			}
			if (task_perform.getCompleteBefore().equals("null")){
				ps.setNull(5, Types.CHAR);
			} else {
				ps.setTimestamp(5, task_perform.getCompleteBefore());
			}
			if (task_perform.getTaskNote().equals("null")){
				ps.setNull(6, Types.CHAR);
			} else {
				ps.setString(6, task_perform.getTaskNote());
			}
			if (task_perform.getDistanceTravelled() == -1){
				ps.setNull(7, Types.FLOAT);
			} else {
				ps.setFloat(7, task_perform.getDistanceTravelled());
			}
			if (task_perform.getCompleteTime().equals("null")){
				ps.setNull(8, Types.CHAR);
			} else {
				ps.setTimestamp(8, task_perform.getCompleteTime());
			}
			if (task_perform.getArriveTime().equals("null")){
				ps.setNull(9, Types.CHAR);
			} else {
				ps.setTimestamp(9, task_perform.getArriveTime());
			}
			if (task_perform.getDepartTime().equals("null")){
				ps.setNull(10, Types.CHAR);
			} else {
				ps.setTimestamp(10, task_perform.getDepartTime());
			}
			if (task_perform.getStartTime().equals("null")){
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

	public void insertTransshipTask(TransshipTask_Perform transship){
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
			if (transship.getStartTime().equals("null")){
				ps.setNull(4, Types.CHAR);
			} else {
				ps.setTimestamp(4, transship.getStartTime());
			}
			if (transship.getDepartTime().equals("null")){
				ps.setNull(5, Types.CHAR);
			} else {
				ps.setTimestamp(5, transship.getDepartTime());
			}
			if (transship.getArriveTime().equals("null")){
				ps.setNull(6, Types.CHAR);
			} else {
				ps.setTimestamp(6, transship.getArriveTime());
			}
			if (transship.getCompleteTime().equals("null")){
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

	public void insertPickUpTask(PickUpTask_Perform pickup){
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
			if (pickup.getStartTime().equals("null")){
				ps.setNull(4, Types.CHAR);
			} else {
				ps.setTimestamp(4, pickup.getStartTime());
			}
			if (pickup.getDepartTime().equals("null")){
				ps.setNull(5, Types.CHAR);
			} else {
				ps.setTimestamp(5, pickup.getDepartTime());
			}
			if (pickup.getArriveTime().equals("null")){
				ps.setNull(6, Types.CHAR);
			} else {
				ps.setTimestamp(6, pickup.getArriveTime());
			}
			if (pickup.getCompleteTime().equals("null")){
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

	public void insertDeliveryTask(DeliveryTask_Perform delivery){
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
			if (delivery.getStartTime().equals("null")){
				ps.setNull(4, Types.CHAR);
			} else {
				ps.setTimestamp(4, delivery.getStartTime());
			}
			if (delivery.getDepartTime().equals("null")){
				ps.setNull(5, Types.CHAR);
			} else {
				ps.setTimestamp(5, delivery.getDepartTime());
			}
			if (delivery.getArriveTime().equals("null")){
				ps.setNull(6, Types.CHAR);
			} else {
				ps.setTimestamp(6, delivery.getArriveTime());
			}
			if (delivery.getCompleteTime().equals("null")){
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

	public void insertLocation(Location location){
		try {
			String query = "INSERT INTO location VALUES (?,?,?,?,?,?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, location.getLocationID());
			ps.setString(2, location.getCity());
			ps.setString(3, location.getProvince());
			if (location.getStreetName().equals("null")){
				ps.setNull(4, Types.CHAR);
			} else {
				ps.setString(4, location.getStreetName());
			}
			if (location.getStreetNumber().equals("null")){
				ps.setNull(5, Types.CHAR);
			} else {
				ps.setString(5, location.getStreetNumber());
			}
			if (location.getUnitNumber().equals("null")){
				ps.setNull(6, Types.CHAR);
			} else {
				ps.setString(6, location.getUnitNumber());
			}
			ps.setString(7, location.getPostalCode());
			if (location.getNote().equals("null")){
				ps.setNull(8, Types.CHAR);
			} else {
				ps.setString(8, location.getNote());
			}

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertCustomerLocation(CustomerLocation custLoc){
		try {
			String query = "INSERT INTO customerlocation VALUES (?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, custLoc.getLocationID());
			if (custLoc.getBuzzCode().equals("null")){
				ps.setNull(2, Types.CHAR);
			} else{
				ps.setString(2, custLoc.getBuzzCode());
			}
			if (custLoc.getPropertyType().equals("null")){
				ps.setNull(5, Types.CHAR);
			} else {
				ps.setString(4, custLoc.getPropertyType());
			}

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertStorageLocation(StorageLocation strLoc){
		try {
			String query = "INSERT INTO storagelocation VALUES (?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, strLoc.getLocationID());
			if (strLoc.getCapacity().equals("null")){
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

	public void insertMerchantLocation(MerchantLocation_Owns mercLoc){
		try {
			String query = "INSERT INTO merchantlocation_owns VALUES (?,?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, mercLoc.getLocationID());
			ps.setString(2, mercLoc.getType());
			if (mercLoc.getLandLineNumber().equals("null")){
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

	public void insertBranch(BranchModel model) {
		try {
			String query = "INSERT INTO branch VALUES (?,?,?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(1, model.getId());
			ps.setString(2, model.getName());
			ps.setString(3, model.getAddress());
			ps.setString(4, model.getCity());
			if (model.getPhoneNumber() == 0) {
				ps.setNull(5, java.sql.Types.INTEGER);
			} else {
				ps.setInt(5, model.getPhoneNumber());
			}

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void deleteBranch(int branchId) {
		try {
			String query = "DELETE FROM branch WHERE branch_id = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(1, branchId);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Branch " + branchId + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public BranchModel[] getBranchInfo() {
		ArrayList<BranchModel> result = new ArrayList<BranchModel>();

		try {
			String query = "SELECT * FROM branch";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				BranchModel model = new BranchModel(rs.getString("branch_addr"),
						rs.getString("branch_city"),
						rs.getInt("branch_id"),
						rs.getString("branch_name"),
						rs.getInt("branch_phone"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new BranchModel[result.size()]);
	}

	public void updateBranch(int id, String name) {
		try {
			String query = "UPDATE branch SET branch_name = ? WHERE branch_id = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, name);
			ps.setInt(2, id);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Branch " + id + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
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

	public void databaseBranchSetup() {
		dropTableIfExists("branch");

		try {
			String query = "CREATE TABLE branch (branch_id integer PRIMARY KEY, branch_name varchar2(20) not null, branch_addr varchar2(50), branch_city varchar2(20) not null, branch_phone integer)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		BranchModel branch1 = new BranchModel("123 Charming Ave", "Vancouver", 1, "First Branch", 1234567);
		insertBranch(branch1);

		BranchModel branch2 = new BranchModel("123 Coco Ave", "Vancouver", 2, "Second Branch", 1234568);
		insertBranch(branch2);
	}

	public void databaseDriverSetup(){
		dropTableIfExists("driver");

		try {
			String query = "CREATE TABLE driver (DriverID CHAR(30), Name CHAR(30) NOT NULL, LicenseNumber CHAR(30) NOT NULL, SIN CHAR(30) NOT NULL, Email CHAR(50), PhoneNumber CHAR(30), PRIMARY KEY (DriverID))";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		Driver driver1 = new Driver("00009301", "Angus Tian", "2138602", "729386023", "atian32@gmail.com", "7789235892");
		insertDriver(driver1);

		Driver driver2 = new Driver("00005511", "Nord Forstrong", "4275823", "682658306", "nforstrong@gmail.com", "6042386923");
		insertDriver(driver2);

		Driver driver3 = new Driver("00001235", "Liam Martin", "3649260", "639547027", "liammartin3@gmail.com", "2369993504");
		insertDriver(driver3);

		Driver driver4 = new Driver("00002293", "Karen Wilson", "2645732", "148759365", "kwilson14@gmail.com", "6047393649");
		insertDriver(driver4);

		Driver driver5 = new Driver("00002323", "Sandra Cote", "7296471", "247839237", "sandraco@gmail.com", "2064638658");
		insertDriver(driver5);
	}

	private void dropTableIfExists(String tableName) {
		try {
			String query = "select table_name from user_tables";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if(rs.getString(1).toLowerCase().equals(tableName)) {
					ps.execute("DROP TABLE "+tableName);
					break;
				}
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
}
