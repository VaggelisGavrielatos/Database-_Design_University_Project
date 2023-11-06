import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MysqlConnect {
	public static void main(String[] args) throws SQLException {
		System.out.println("MySQL Connect Example");
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "ergasiavash";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "";
		Scanner sc = new Scanner(System.in);
		String ans;
		ResultSet rs = null;
		ResultSet rs1 = null;
		int CodeBin = 0;
		int ManagerID = 0;
		int NumComb = 0;
		int CodeBatch = 0;
		int ID = 0;
		int ADNum = 0;
		int ADTK = 0;
		int quantity  =0;
		String CodeComb = null;
		String CodeOrder = null;
		String exit = "no";
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		int i = 0;
		java.util.List<String> list = new ArrayList<String>();
		try {
				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(url + dbName, userName, password);
				System.out.println("Connected to database");
			} catch (Exception e) {
				e.printStackTrace();
			}

		Statement stmt1 = conn.createStatement();
		rs = stmt1.executeQuery("SELECT * FROM manager");
		if (!(rs.next())) {
			// auto create the first manager - employee
			try {
				Statement st = conn.createStatement();
				int val = st.executeUpdate("INSERT INTO manager VALUES(123123)");
			} catch (SQLException s) {
				System.out.println("Error!");
			}
			try {
				Statement st = conn.createStatement();
				int val = st.executeUpdate("INSERT INTO employee VALUES(123123 , 'George' , 'Iwannou' , 123123)");
			} catch (SQLException s) {
				System.out.println("Error!");
			}
			try {
				Statement st = conn.createStatement();
				int val = st.executeUpdate("INSERT INTO name VALUES('Bill' , 123123)");
			} catch (SQLException s) {
				System.out.println("Error!");
			}
			try {
				Statement st = conn.createStatement();
				int val = st.executeUpdate("INSERT INTO address VALUES('Ag. Grigoriou' , 15 , 'Athens' , 15487 , 123123)");
			} catch (SQLException s) {
				System.out.println("Error!");
			}
			try {
				BigInteger phonenum1 = BigInteger.valueOf(2106487253);
				Statement st = conn.createStatement();
				int val = st.executeUpdate("INSERT INTO phone VALUES( '"+phonenum1+"' , 123123)");
			} catch (SQLException s) {
				System.out.println("Error!");
			}
		}


		while(exit.equals("No") || exit.equals("NO") || exit.equals("no")) {

			System.out.println("What do you want to do?");
			System.out.println("Type \"Insert\" , \"Update\" , \"Delete\" or \"Show\" : ");
			ans = sc.nextLine();

			while (!"Insert".equals(ans) && !"Update".equals(ans) && !"Delete".equals(ans) && !"Show".equals(ans)) {
				System.out.println("Wrong entry please type only  \"Insert\" , \"Update\" , \"Delete\" or \"Show\" :");
				ans = sc.nextLine();
			}

			if (ans.equals("Insert")) {
				System.out.println("What do you want to Insert");
				System.out.println("Type \"Warehouse\" , \"Bin\" , \"Parts\" , \"Combination\" , \"Batch\" , \"Orders\" , \"Employee\" , \"Manager\":");
				ans = sc.nextLine();
				while (!"Warehouse".equals(ans) && !"Bin".equals(ans) && !"Parts".equals(ans) && !"Combination".equals(ans) && !"Batch".equals(ans) && !"Orders".equals(ans) && !"Employee".equals(ans) && !"Manager".equals(ans)) {
					System.out.println("Wrong entry please type only  \"Warehouse\" , \"Bin\" , \"Parts\" , \"Combination\" , \"Batch\" , \"Orders\" , \"Employee\" , \"Manager\" :");
					ans = sc.next();
				}

				if (ans.equals("Warehouse")){
					System.out.println("Give the 4 character Signature Code of the Warehouse :");
					String CodeWh = sc.nextLine();
					while (CodeWh.length() != 4) {
						System.out.println("Signature Code of the Warehouse must be only 4 characters :");
						System.out.println("Give the 4 character Signature Code of the Warehouse :");
						CodeWh = sc.nextLine();
					}
					Statement stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT * FROM warehouse WHERE CodeWh='"+ CodeWh+"'");
					if (rs.next()) {
						System.out.println("This Code is already Registered for another Warehouse.");
					}
					else {
						System.out.println("Enter the ID of the Manager that is responsible for this Warehouse : ");
						ManagerID = sc.nextInt();
						sc.nextLine();
						while (String.valueOf(ManagerID).length() != 6) {
							System.out.println("Manager ID must be only 6 digits :");
							System.out.println("Give the 6 digit Integer ID of the Manager :");
							ManagerID = sc.nextInt();
							sc.nextLine();
						}
						rs = stmt.executeQuery("SELECT * FROM manager WHERE ManagerID='"+ManagerID+"'");
						if (rs.next()) {
							try {
								Statement st = conn.createStatement();
								int val = st.executeUpdate("INSERT INTO warehouse VALUES('" + CodeWh + "', '"+ManagerID+"')");
							} catch (SQLException s) {
								System.out.println("Error!");
							}
						}
						else {
							System.out.println("There is no registered Manager with this ID.");
						}
					}
				}
				if (ans.equals("Bin")){
					System.out.println("Give the Signature Code of the Bin :");
					CodeBin = sc.nextInt();
					sc.nextLine();
					Statement stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT * FROM bin WHERE codebin='"+CodeBin+"'");
					if (rs.next()) {
						System.out.println("This code is already registered for another bin.");
					}
					else {
						System.out.println("Give the 4 character Signature Code of the Warehouse in which you want to insert the bin:");
						String CodeWh = sc.nextLine();
						rs = stmt.executeQuery("SELECT * FROM warehouse WHERE CodeWh='"+ CodeWh+"'");
						if (rs.next())
						{
							System.out.println("Give the Capacity of the Bin :");
							int Capacity = sc.nextInt();
							try {
								Statement st = conn.createStatement();
								int val = st.executeUpdate("INSERT INTO bin VALUES('" + CodeBin + "', '" + Capacity + "', '" + CodeWh + "')");
							} catch (SQLException s) {
								System.out.println("Error!");
							}
						}
						else
						{
							System.out.println("Warehouse Code does not exist.");
						}
					}
				}
				if (ans.equals("Manager")){
					Statement stmt = conn.createStatement();
					System.out.println("Give the 6 digit Integer ID of the Employee that is promoted to Manager :");
					ID = sc.nextInt();
					sc.nextLine();
					while (String.valueOf(ID).length() != 6)
					{
						System.out.println("Employee ID must be only 6 digits :");
						System.out.println("Give the 6 digit Integer ID of the Employee that is promoted to Manager :");
						ID = sc.nextInt();
						sc.nextLine();
					}
					rs = stmt.executeQuery("SELECT * FROM employee WHERE ID='"+ID+"'");
					if (rs.next()) {
						try {
							Statement st = conn.createStatement();
							int val = st.executeUpdate("INSERT INTO manager VALUES('"+ID+"')");
						} catch (SQLException s) {
							System.out.println("Error!");
						}
						String query = "UPDATE employee SET manager_managerid = ? WHERE employee.id = ?";
						PreparedStatement preparedStmt = conn.prepareStatement(query);
						preparedStmt.setInt (1, ID);
						preparedStmt.setString (2, rs.getString("id"));
						preparedStmt.executeUpdate();
					}
					else {
						System.out.println("There is no Employee with this ID");
					}
				}
				if (ans.equals("Batch")){
					System.out.println("Give the Code of the Bin in which you want to insert the Batch:");
					int CodeBin1 = sc.nextInt();
					sc.nextLine();

					Statement stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT * FROM bin WHERE CodeBin='"+CodeBin1+"'");

					if (rs.next()) {
						System.out.println("Give the Signature Code of the Batch :");
						CodeBatch = sc.nextInt();
						sc.nextLine();
						rs = stmt.executeQuery("SELECT * FROM batch WHERE CodeBatch='"+CodeBatch+"'");

						if (rs.next()) {
							System.out.println("Batch Code already exist.");
						}
						else
						{
							System.out.println("Give the Number of Parts that are Contained in the Batch :");
							int PartsNum = sc.nextInt();
							sc.nextLine();

							rs = stmt.executeQuery("SELECT * FROM bin WHERE codebin='"+CodeBin1+"'");
							rs.next();
							if (PartsNum > rs.getInt("capacity"))
							{
								System.out.println("The Number of Parts contained in this Batch is larger than the capacity of this Bin");
							}
							else
							{
								int sum =0;
								rs = stmt.executeQuery("SELECT * FROM batch WHERE bin_codebin='"+CodeBin1+"'");
								while (rs.next()) {
									sum = sum + rs.getInt("partsnum");
								}
								sum = sum + PartsNum;
								rs = stmt.executeQuery("SELECT * FROM bin WHERE codebin='"+CodeBin1+"'");
								rs.next();
								if (sum > rs.getInt("capacity"))
								{
									System.out.println("This Bin is full Choose another Bin to enter the Batch.");
								}
								else
								{
									System.out.println("Give the ID of the Manager that Checked the Batch :");
									int ManagerID1 = sc.nextInt();
									sc.nextLine();
									while (String.valueOf(ManagerID1).length() != 6) {
										System.out.println("Manager ID must be only 6 digits :");
										System.out.println("Give the 6 digit Integer ID of the Manager :");
										ManagerID1 = sc.nextInt();
										sc.nextLine();
									}
									rs = stmt.executeQuery("SELECT * FROM manager WHERE ManagerID='" + ManagerID1 + "'");
									if (rs.next()) {
										try {
											Statement st = conn.createStatement();
											int val = st.executeUpdate("INSERT INTO batch VALUES('" + CodeBatch + "' , '" + date + "' , '" + PartsNum + "' , '" + date + "' , '" + CodeBin1 + "' , '" + ManagerID1 + "')");
										} catch (SQLException s) {
											System.out.println("Error!");
										}
									} else {
										System.out.println("Manager ID does not exist.");
									}
								}
							}
						}
					}
					else
					{
						System.out.println("Bin Code does not exist.");
					}
				}
				if (ans.equals("Combination")){
					System.out.println("Give the Numeric Code of the Combination :");
					NumComb = sc.nextInt();
					sc.nextLine();
					Statement stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT * FROM combination WHERE CodeComb='"+CodeComb+"'");
					if (rs.next())
					{
						System.out.println("There is already a Combination registered with this number.");
					}
					else
					{
						System.out.println("Give the 5 character Combined Product Code :");
						CodeComb = sc.nextLine();
						while (CodeComb.length() != 5) {
							System.out.println("Combined Product Code must be only 5 characters :");
							System.out.println("Give the 5 character Combined Product Code :");
							CodeComb = sc.nextLine();
						}
						stmt = conn.createStatement();
						rs = stmt.executeQuery("SELECT * FROM combination WHERE CodeComb='"+CodeComb+"'");
						if (rs.next())
						{
							System.out.println("There is already a Combined Product registered with this code.");
						}
						else {
							try {
								Statement st = conn.createStatement();
								int val = st.executeUpdate("INSERT INTO combination VALUES('"+CodeComb+"' , '" + NumComb + "')");
							} catch (SQLException s) {
								System.out.println("Error!");
							}
						}
					}
				}
				if (ans.equals("Parts")){
					System.out.println("Enter the 5 character Signature Code of the Part:");
					String CodePart = sc.nextLine();
					while (CodePart.length() != 5) {
						System.out.println("Product Code must be only 5 characters :");
						System.out.println("Give the 5 character Signature Code of the Part:");
						CodePart = sc.nextLine();
					}
					Statement stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT * FROM part WHERE CodePart='"+CodePart+"'");
					if (rs.next())
					{
						System.out.println("The Part Code already exists.");
					}
					else{
						System.out.println("Enter the Combined Part Code in which this Part takes place:");
						CodeComb = sc.nextLine();

						rs = stmt.executeQuery("SELECT * FROM combination WHERE CodeComb='"+CodeComb+"'");
						if(rs.next())
						{
							System.out.println("Enter the Batch Code in which this Part is contained:");
							CodeBatch = sc.nextInt();
							sc.nextLine();
							rs = stmt.executeQuery("SELECT * FROM batch WHERE CodeBatch='"+CodeBatch+"'");
							if(rs.next())
							{
								try {
									Statement st = conn.createStatement();
									int val = st.executeUpdate("INSERT INTO part VALUES('" + CodePart + "' , '" + CodeBatch + "' , '" + CodeComb + "')");
								} catch (SQLException s) {
									System.out.println("Error!");
								}
							}
							else
							{
								System.out.println("There is no Batch registered with this Code.");
							}
						}
						else
						{
							System.out.println("There is no Combination registered with this Code.");
						}
					}
				}
				if (ans.equals("Employee")){
					System.out.println("Enter this Employee's 6 digit Integer ID : ");
					ID = sc.nextInt();
					sc.nextLine();
					while (String.valueOf(ID).length() != 6)
					{
						System.out.println("Employee ID must be only 6 digits :");
						System.out.println("Give the 6 digit Integer ID of the Manager :");
						ID = sc.nextInt();
						sc.nextLine();
					}

					Statement stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT * FROM employee WHERE ID='"+ID+"'");
					if (rs.next()){
						System.out.println("There is already an Employee registered with this ID.");
					}
					else {
						System.out.println("Enter this Employee's Supervisor 6 digit Integer ID : ");
						ManagerID = sc.nextInt();
						sc.nextLine();
						while (String.valueOf(ManagerID).length() != 6) {
							System.out.println("Manager ID must be only 6 digits :");
							System.out.println("Give the 6 digit Integer ID of the Manager :");
							ManagerID = sc.nextInt();
							sc.nextLine();
						}
						rs = stmt.executeQuery("SELECT * FROM manager WHERE ManagerID='" + ManagerID + "'");
						if (rs.next()) {
							System.out.println("Give the Employee's Father Name:");
							String fathername = sc.nextLine();
							while (fathername.length() > 10) {
								System.out.println("Father Name must up to 10 digits");
								System.out.println("Give the Employee's Father Name:");
								fathername = sc.nextLine();
							}
							System.out.println("Give the Employee's Last Name:");
							String lastname = sc.nextLine();
							while (lastname.length() > 10) {
								System.out.println("Last Name must up to 10 digits");
								System.out.println("Give the Employee's Last Name:");
								lastname = sc.nextLine();
							}
							try {
								Statement st = conn.createStatement();
								int val = st.executeUpdate("INSERT INTO employee VALUES('" + ID + "' , '" + fathername + "' , '" + lastname + "' , '" + ManagerID + "')");
							} catch (SQLException s) {
								System.out.println("Error!");
							}
							System.out.println("How many Phone numbers this Employee has :");
							i = sc.nextInt();
							sc.nextLine();
							if (i == 0) {
								System.out.println("How many Names this Employee has :");
								i = sc.nextInt();
								sc.nextLine();
								while (i < 1) {
									System.out.println("Employee's must at least have 1 name :");
									System.out.println("How many Names this Employee has :");
									i = sc.nextInt();
									sc.nextLine();
								}
								for (int j = 1; j <= i; j++) {
									System.out.println("Give the Employee's Name:");
									String name = sc.nextLine();
									while (name.length() > 10) {
										System.out.println("Last Name must up to 10 digits");
										System.out.println("Give the Employee's Last Name:");
										name = sc.nextLine();
									}
									rs = stmt.executeQuery("SELECT * FROM name WHERE name='" + name + "'");
									if (rs.next()) {
										name = name.concat(".");
										try {
											Statement st = conn.createStatement();
											int val = st.executeUpdate("INSERT INTO name VALUES('" + name + "' , '" + ID + "')");
										} catch (SQLException s) {
											System.out.println("Error!");
										}
									}
									else {
										try {
											Statement st = conn.createStatement();
											int val = st.executeUpdate("INSERT INTO name VALUES('" + name + "' , '" + ID + "')");
										} catch (SQLException s) {
											System.out.println("Error!");
										}
									}
								}
								System.out.println("How many Addresses this Employee has :");
								i = sc.nextInt();
								sc.nextLine();
								while (i < 1) {
									System.out.println("Employee's must at least have 1 Address :");
									System.out.println("How many Addresses this Employee has :");
									i = sc.nextInt();
									sc.nextLine();
								}
								for (int j = 1; j <= i; j++) {
									System.out.println("Give the Employee's Street Name :");
									String ADName = sc.nextLine();
									while (ADName.length() > 14) {
										System.out.println("Street name must have up to 14 characters");
										System.out.println("Give the Employee's Street Name :");
										ADName = sc.nextLine();
									}
									System.out.println("Give the Employee's Street Number :");
									ADNum = sc.nextInt();
									sc.nextLine();
									System.out.println("Give the Employee's Town Name :");
									String ADTown = sc.nextLine();
									while (ADTown.length() > 14) {
										System.out.println("Street name must have up to 14 characters");
										System.out.println("Give the Employee's Street Name :");
										ADTown = sc.nextLine();
									}
									System.out.println("Give the Employee's Postal Code :");
									ADTK = sc.nextInt();
									sc.nextLine();
									try {
										Statement st = conn.createStatement();
										int val = st.executeUpdate("INSERT INTO address VALUES('" + ADName + "' , '" + ADNum + "' , '" + ADTown + "' , '" + ADTK + "' , '" + ID + "')");
									} catch (SQLException s) {
										System.out.println("Error!");
									}
								}
							}
							else {
								for (int j = 0; j < i; j++) {
									System.out.println("Give the phone number:");
									BigInteger phone = sc.nextBigInteger();
									sc.nextLine();
									while (String.valueOf(phone).length() != 10) {
										System.out.println("phone number must have 10 digits");
										System.out.println("Give the phone number:");
										phone = sc.nextBigInteger();
									}
									try {
										Statement st = conn.createStatement();
										int val = st.executeUpdate("INSERT INTO phone VALUES('" + phone + "' , '" + ID + "' )");
									} catch (SQLException s) {
										System.out.println("Error!");
									}
								}
								System.out.println("How many Names this Employee has :");
								i = sc.nextInt();
								sc.nextLine();
								while (i < 1) {
									System.out.println("Employee's must at least have 1 name :");
									System.out.println("How many Names this Employee has :");
									i = sc.nextInt();
									sc.nextLine();
								}
								for (int j = 1; j <= i; j++) {
									System.out.println("Give the Employee's Name:");
									String name = sc.nextLine();
									while (name.length() > 10) {
										System.out.println("Last Name must up to 10 digits");
										System.out.println("Give the Employee's Last Name:");
										name = sc.nextLine();
									}
									rs = stmt.executeQuery("SELECT * FROM name WHERE name='" + name + "'");
									if (rs.next()) {
										name = name.concat(".");
										try {
											Statement st = conn.createStatement();
											int val = st.executeUpdate("INSERT INTO name VALUES('" + name + "' , '" + ID + "')");
										} catch (SQLException s) {
											System.out.println("Error!");
										}
									}
									else {
										try {
											Statement st = conn.createStatement();
											int val = st.executeUpdate("INSERT INTO name VALUES('" + name + "' , '" + ID + "')");
										} catch (SQLException s) {
											System.out.println("Error!");
										}
									}
								}
								System.out.println("How many Addresses this Employee has :");
								i = sc.nextInt();
								sc.nextLine();
								while (i < 1) {
									System.out.println("Employee's must at least have 1 Address :");
									System.out.println("How many Addresses this Employee has :");
									i = sc.nextInt();
									sc.nextLine();
								}
								for (int j = 1; j <= i; j++) {
									System.out.println("Give the Employee's Street Name :");
									String ADName = sc.nextLine();
									while (ADName.length() > 14) {
										System.out.println("Street name must have up to 14 characters");
										System.out.println("Give the Employee's Street Name :");
										ADName = sc.nextLine();
									}
									System.out.println("Give the Employee's Street Number :");
									ADNum = sc.nextInt();
									sc.nextLine();
									System.out.println("Give the Employee's Town Name :");
									String ADTown = sc.nextLine();
									while (ADTown.length() > 14) {
										System.out.println("Street name must have up to 14 characters");
										System.out.println("Give the Employee's Street Name :");
										ADTown = sc.nextLine();
									}
									System.out.println("Give the Employee's Postal Code :");
									ADTK = sc.nextInt();
									sc.nextLine();
									try {
										Statement st = conn.createStatement();
										int val = st.executeUpdate("INSERT INTO address VALUES('" + ADName + "' , '" + ADNum + "' , '" + ADTown + "' , '" + ADTK + "' , '" + ID + "')");
									} catch (SQLException s) {
										System.out.println("Error!");
									}
								}
							}
						} else {
							System.out.println("There is no Manager with this ID.");
						}
					}
				}
				if (ans.equals("Orders")) {
					System.out.println("Give the 6 digit Integer ID of the Manager that is making the new Order :");
					ID = sc.nextInt();
					sc.nextLine();
					while (String.valueOf(ID).length() != 6) {
						System.out.println("Manager ID must be only 6 digits ");
						System.out.println("Give the 6 digit Integer ID of the Manager who is making the new Order :");
						ID = sc.nextInt();
						sc.nextLine();
					}
					Statement stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT * FROM manager WHERE ManagerID='" + ID + "'");
					if (rs.next()) {
						System.out.println("Give the Signature Code of the new Order :");
						CodeOrder = sc.nextLine();
						rs = stmt.executeQuery("SELECT * FROM old_orders WHERE orders_codeorder ='" + CodeOrder + "'");
						if (rs.next()){
							System.out.println("There is already an Old_Order registered with this Code.");
						}
						else {
							System.out.println("Give the Quantity of the Parts that are Ordered :");
							quantity = sc.nextInt();
							sc.nextLine();
							try {
								Statement st = conn.createStatement();
								int val = st.executeUpdate("INSERT INTO orders VALUES('" + CodeOrder + "' , '" + date + "' , '" + quantity + "' ,'" + ID + "')");
							} catch (SQLException s) {
								System.out.println("Error!");
							}
						}

					} else {
						System.out.println("There is no registered Manager with this ID");
					}
				}
			}
			if (ans.equals("Update")) {
				System.out.println("What do you want to Update");
				System.out.println("Type \"Warehouse\" , \"Bin\" , \"Parts\" , \"Combination\" , \"Batch\" , \"Orders\" , \"Employee\" :");
				ans = sc.nextLine();
				while (!"Warehouse".equals(ans) && !"Bin".equals(ans) && !"Parts".equals(ans) && !"Combination".equals(ans) && !"Batch".equals(ans) && !"Orders".equals(ans) && !"Employee".equals(ans)) {
					System.out.println("Wrong entry please type only  \"Warehouse\" , \"Bin\" , \"Parts\" , \"Combination\" , \"Batch\" , \"Orders\" , \"Employee\" , \"Manager\" :");
					ans = sc.next();
				}

				if (ans.equals("Warehouse")) {
					System.out.println("Give the 4 character Code of the Warehouse you want to Update");
					String CodeWh = sc.nextLine();
					while (CodeWh.length() != 4) {
						System.out.println("Signature Code of the Warehouse must be only 4 characters :");
						System.out.println("Give the 4 character Signature Code of the Warehouse :");
						CodeWh = sc.nextLine();
					}
					Statement stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT * FROM warehouse WHERE CodeWh='"+CodeWh+"'");
					if (rs.next())
					{
						System.out.println("What do you want to change \"Code\" or \"Manager\"");
						ans = sc.nextLine();
						while (!"Code".equals(ans) && !"Manager".equals(ans)) {
							System.out.println("Wrong entry please type only  \"Code\" , \"Manager\" :");
							ans = sc.next();
						}
						if (ans.equals("Code")) {
							System.out.println("Give the 4 character Signature Code of the Warehouse :");
							String CodeWh1 = sc.nextLine();
							while (CodeWh1.length() != 4) {
								System.out.println("Signature Code of the Warehouse must be only 4 characters :");
								System.out.println("Give the 4 character Signature Code of the Warehouse :");
								CodeWh1 = sc.nextLine();
							}
							stmt = conn.createStatement();
							rs = stmt.executeQuery("SELECT * FROM warehouse WHERE CodeWh='"+CodeWh1+"'");
							if (rs.next())
							{
								System.out.println("The new Warehouse Code already exists.");
							}
							else
							{
								stmt.execute("SET FOREIGN_KEY_CHECKS=0");
								stmt.close();
								String query = "UPDATE warehouse SET codewh = ? WHERE codewh = ?";
								PreparedStatement preparedStmt = conn.prepareStatement(query);
								preparedStmt.setString(1, CodeWh1);
								preparedStmt.setString(2, CodeWh);
								preparedStmt.executeUpdate();
								stmt = conn.createStatement();
								stmt.execute("SET FOREIGN_KEY_CHECKS=1");
								stmt.close();

								rs = stmt.executeQuery("SELECT * FROM bin WHERE warehouse_codewh='"+CodeWh+"'");
								while (rs.next()) {
									stmt.execute("SET FOREIGN_KEY_CHECKS=0");
									stmt.close();
									query = "UPDATE bin SET warehouse_codewh = ? WHERE warehouse_codewh = ?";
									preparedStmt = conn.prepareStatement(query);
									preparedStmt.setString(1, CodeWh1);
									preparedStmt.setString(2, CodeWh);
									preparedStmt.executeUpdate();
									stmt = conn.createStatement();
									stmt.execute("SET FOREIGN_KEY_CHECKS=1");
									stmt.close();
								}
							}

						}
						if (ans.equals("Manager")) {
							System.out.println("Enter the ID of the Manager that is responsible for this Warehouse : ");
							ManagerID = sc.nextInt();
							sc.nextLine();
							while (String.valueOf(ManagerID).length() != 6) {
								System.out.println("Manager ID must be only 6 digits :");
								System.out.println("Give the 6 digit Integer ID of the Manager :");
								ManagerID = sc.nextInt();
								sc.nextLine();
							}
							stmt = conn.createStatement();
							rs = stmt.executeQuery("SELECT * FROM manager WHERE ManagerID='"+ManagerID+"'");
							if (rs.next()) {
								stmt.execute("SET FOREIGN_KEY_CHECKS=0");
								stmt.close();
								String query = "UPDATE warehouse SET manager_managerid = ? WHERE codewh = ?";
								PreparedStatement preparedStmt = conn.prepareStatement(query);
								preparedStmt.setString(2, CodeWh);
								preparedStmt.setInt(1, ManagerID);
								preparedStmt.executeUpdate();
								stmt = conn.createStatement();
								stmt.execute("SET FOREIGN_KEY_CHECKS=1");
								stmt.close();
							}
							else {
								System.out.println("There is no Manager with this ID.");
							}
						}
					}
					else {
						System.out.println("There is no Warehouse registered with this code.");
					}
				}
				if (ans.equals("Bin")) {
					System.out.println("Give the Code of the Bin you want to Update :");
					CodeBin = sc.nextInt();
					sc.nextLine();
					Statement stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT * FROM bin WHERE CodeBin='"+CodeBin+"'");

					if (rs.next()) {
						System.out.println("What do you want to change \"Code\" , \"Capacity\" or \"Warehouse\"");
						ans = sc.nextLine();
						while (!"Code".equals(ans) && !"Capacity".equals(ans) && !"Warehouse".equals(ans)) {
							System.out.println("Wrong entry please type only \"Code\" , \"Capacity\" or \"Warehouse\" :");
							ans = sc.next();
						}
						if (ans.equals("Code")) {
							System.out.println("Give the new Signature Code of the Bin :");
							int CodeBin1 = sc.nextInt();
							sc.nextLine();

							rs = stmt.executeQuery("SELECT * FROM bin WHERE CodeBin='"+ CodeBin1+"'");
							if (rs.next())
							{
								System.out.println("This code already exists");
							}
							else
							{
								stmt.execute("SET FOREIGN_KEY_CHECKS=0");
								stmt.close();
								String query = "UPDATE bin SET codebin = ? WHERE codebin = ?";
								PreparedStatement preparedStmt = conn.prepareStatement(query);
								preparedStmt.setInt(1, CodeBin1);
								preparedStmt.setInt(2, CodeBin);
								preparedStmt.executeUpdate();
								stmt = conn.createStatement();
								stmt.execute("SET FOREIGN_KEY_CHECKS=1");
								stmt.close();

								rs = stmt.executeQuery("SELECT * FROM batch WHERE bin_codebin='"+CodeBin+"'");
								while (rs.next()) {
									stmt.execute("SET FOREIGN_KEY_CHECKS=0");
									stmt.close();
									query = "UPDATE bin SET bin_codebin = ? WHERE bin_codebin = ?";
									preparedStmt = conn.prepareStatement(query);
									preparedStmt.setInt(1, CodeBin1);
									preparedStmt.setInt(2, CodeBin);
									preparedStmt.executeUpdate();
									stmt = conn.createStatement();
									stmt.execute("SET FOREIGN_KEY_CHECKS=1");
									stmt.close();
								}
							}
						}
						if (ans.equals("Capacity")) {
							System.out.println("Give the new Capacity of the Bin :");
							int Capacity = sc.nextInt();
							sc.nextLine();

							int sum =0;
							rs = stmt.executeQuery("SELECT * FROM batch WHERE bin_codebin='"+CodeBin+"'");
							while (rs.next()) {
								sum = sum + rs.getInt("partsnum");
							}
							if (sum > Capacity)
							{
								System.out.println("You can't set this capacity for the bin because its smaller than the amount of parts that are already contained in this bin.");
							}
							else {
								stmt.execute("SET FOREIGN_KEY_CHECKS=0");
								stmt.close();
								String query = "UPDATE bin SET capacity = ? WHERE codebin = ?";
								PreparedStatement preparedStmt = conn.prepareStatement(query);
								preparedStmt.setInt(1, Capacity);
								preparedStmt.setInt(2, CodeBin);
								preparedStmt.executeUpdate();
								stmt = conn.createStatement();
								stmt.execute("SET FOREIGN_KEY_CHECKS=1");
								stmt.close();
							}
						}
						if (ans.equals("Warehouse")) {
							System.out.println("Give the 4 character Signature Code of the Warehouse in which you want to insert the bin:");
							String CodeWh = sc.nextLine();
							stmt = conn.createStatement();
							rs = stmt.executeQuery("SELECT * FROM warehouse WHERE CodeWh='"+ CodeWh+"'");
							if (rs.next())
							{
								stmt.execute("SET FOREIGN_KEY_CHECKS=0");
								stmt.close();
								String query = "UPDATE bin SET warehouse_codewh = ? WHERE codebin = ?";
								PreparedStatement preparedStmt = conn.prepareStatement(query);
								preparedStmt.setString(1, CodeWh);
								preparedStmt.setInt(2, CodeBin);
								preparedStmt.executeUpdate();
								stmt = conn.createStatement();
								stmt.execute("SET FOREIGN_KEY_CHECKS=1");
								stmt.close();
							}
							else {
								System.out.println("There is no Warehouse with this Code.");
							}
						}
					}
				}
				if (ans.equals("Batch")) {
					System.out.println("Give the Code of the Batch you want to update:");
					CodeBatch = sc.nextInt();
					sc.nextLine();

					Statement stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT * FROM batch WHERE CodeBatch='"+CodeBatch+"'");
					int partsnum = rs.getInt("partsnum");
					if (rs.next()) {
							System.out.println("What do you want to change \"Code\" , \"Parts Number\" , \"Bin\" or \"Manager\" :");
						ans = sc.nextLine();
						while (!"Code".equals(ans) && !"Parts Number".equals(ans) && !"Bin".equals(ans) && !"Manager".equals(ans)) {
							System.out.println("Wrong entry please type only  \"Code\" , \"Parts Number\" , \"Bin\" or \"Manager\" :");
							ans = sc.next();
						}
						if (ans.equals("Code")) {
							System.out.println("Give the Signature Code of the Batch :");
							int CodeBatch1 = sc.nextInt();
							sc.nextLine();
							rs = stmt.executeQuery("SELECT * FROM batch WHERE CodeBatch='" + CodeBatch1 + "'");
							if (rs.next()) {
								System.out.println("Batch Code already exist.");
							} else {
								stmt.execute("SET FOREIGN_KEY_CHECKS=0");
								stmt.close();
								String query = "UPDATE batch SET codebatch = ? WHERE codebatch= ?";
								PreparedStatement preparedStmt = conn.prepareStatement(query);
								preparedStmt.setInt(1, CodeBatch1);
								preparedStmt.setInt(2, CodeBatch);
								preparedStmt.executeUpdate();
								stmt = conn.createStatement();
								stmt.execute("SET FOREIGN_KEY_CHECKS=1");
								stmt.close();

								rs = stmt.executeQuery("SELECT * FROM parts WHERE batch_codebatch='"+CodeBatch+"'");
								while (rs.next()) {
									stmt.execute("SET FOREIGN_KEY_CHECKS=0");
									stmt.close();
									query = "UPDATE parts SET batch_codebatch = ? WHERE batch_codebatch = ?";
									preparedStmt = conn.prepareStatement(query);
									preparedStmt.setInt(1, CodeBatch1);
									preparedStmt.setInt(2, CodeBatch);
									preparedStmt.executeUpdate();
									stmt = conn.createStatement();
									stmt.execute("SET FOREIGN_KEY_CHECKS=1");
									stmt.close();
								}
							}
						}
						if (ans.equals("Parts Number")) {
							System.out.println("Give the Number of Parts that are Contained in the Batch :");
							int PartsNum = sc.nextInt();
							sc.nextLine();

							int sum =0;
							rs = stmt.executeQuery("SELECT * FROM batch WHERE CodeBatch='"+CodeBatch+"'");
							ResultSet res = stmt.executeQuery("SELECT * FROM batch WHERE bin_codebin='" + rs.getInt("bin_codebin") + "'");

							while (res.next()) {
								sum = sum + rs.getInt("partsnum");
							}
							sum = sum + PartsNum;
							rs = stmt.executeQuery("SELECT * FROM bin WHERE codebin='"+rs.getInt("bin_codebin")+"'");
							rs.next();
							if (sum > rs.getInt("capacity"))
							{
								System.out.println("You can enter this amount of new Parts in this batch because the bin it is stored can't take it.");
							}
							else
							{
								stmt.execute("SET FOREIGN_KEY_CHECKS=0");
								stmt.close();
								String query = "UPDATE batch SET partsnum = ? WHERE codebatch= ?";
								PreparedStatement preparedStmt = conn.prepareStatement(query);
								preparedStmt.setInt(1, PartsNum);
								preparedStmt.setInt(2, CodeBatch);
								preparedStmt.executeUpdate();
								stmt = conn.createStatement();
								stmt.execute("SET FOREIGN_KEY_CHECKS=1");
								stmt.close();
							}
						}
						if (ans.equals("Bin")) {
							System.out.println("Give the Code of the Bin in which you want to insert the Batch:");
							int CodeBin1 = sc.nextInt();
							sc.nextLine();

							stmt = conn.createStatement();
							rs = stmt.executeQuery("SELECT * FROM bin WHERE CodeBin='" + CodeBin1 + "'");
							if (rs.next()) {
								int sum =0;
								rs = stmt.executeQuery("SELECT * FROM batch WHERE bin_codebin='"+CodeBin1+"'");
								while (rs.next()) {
									sum = sum + rs.getInt("partsnum");
								}
								sum = sum + partsnum;
								rs = stmt.executeQuery("SELECT * FROM bin WHERE codebin='"+CodeBin1+"'");
								rs.next();
								if (sum > rs.getInt("capacity"))
								{
									System.out.println("This Bin is full Choose another Bin to enter the Batch.");
								}
								else
								{
									stmt.execute("SET FOREIGN_KEY_CHECKS=0");
									stmt.close();
									String query = "UPDATE batch SET bin_codebin = ? WHERE codebatch= ?";
									PreparedStatement preparedStmt = conn.prepareStatement(query);
									preparedStmt.setInt(1, CodeBin1);
									preparedStmt.setInt(2, CodeBatch);
									preparedStmt.executeUpdate();
									stmt = conn.createStatement();
									stmt.execute("SET FOREIGN_KEY_CHECKS=1");
									stmt.close();
								}
							}
						}
						if (ans.equals("Manager")) {
							System.out.println("Enter the ID of the Manager that checked this Batch : ");
							ManagerID = sc.nextInt();
							sc.nextLine();
							while (String.valueOf(ManagerID).length() != 6) {
								System.out.println("Manager ID must be only 6 digits :");
								System.out.println("Give the 6 digit Integer ID of the Manager :");
								ManagerID = sc.nextInt();
								sc.nextLine();
							}
							stmt = conn.createStatement();
							rs = stmt.executeQuery("SELECT * FROM manager WHERE ManagerID='"+ManagerID+"'");
							if (rs.next()) {
								stmt.execute("SET FOREIGN_KEY_CHECKS=0");
								stmt.close();
								String query = "UPDATE batch SET manager_managerid = ? WHERE codebatch = ?";
								PreparedStatement preparedStmt = conn.prepareStatement(query);
								preparedStmt.setInt(1, ManagerID);
								preparedStmt.setInt(2, CodeBatch);
								preparedStmt.executeUpdate();
								stmt = conn.createStatement();
								stmt.execute("SET FOREIGN_KEY_CHECKS=1");
								stmt.close();
							}
							else {
								System.out.println("There is no Manager with this ID.");
							}
						}
					}
					else {
						System.out.println("There is no Batch registered with this code.");
					}
				}
				if (ans.equals("Combination")) {
					System.out.println("Give the 5 character Code of the Combined Product you want to Update:");
					CodeComb = sc.nextLine();
					while (CodeComb.length() != 5) {
						System.out.println("Combined Product Code must be only 5 characters :");
						System.out.println("Give the 5 character Combined Product Code :");
						CodeComb = sc.nextLine();
					}

					Statement stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT * FROM combination WHERE CodeComb='"+CodeComb+"'");
					if (rs.next()) {
						System.out.println("What do you want to change \"Code\" , \"Combination Number\" :");
						ans = sc.nextLine();
						while (!"Code".equals(ans) && !"Combination Number".equals(ans)) {
							System.out.println("Wrong entry please type only  \"Code\" , \"Combination Number\" :");
							ans = sc.next();
						}
						if (ans.equals("Code")) {
							System.out.println("Give the 5 character Combined Product Code :");
							String CodeComb1 = sc.nextLine();
							while (CodeComb.length() != 5) {
								System.out.println("Combined Product Code must be only 5 characters :");
								System.out.println("Give the 5 character Combined Product Code :");
								CodeComb1 = sc.nextLine();
							}
							stmt = conn.createStatement();
							rs = stmt.executeQuery("SELECT * FROM combination WHERE CodeComb='"+CodeComb1+"'");
							if (rs.next())
							{
								System.out.println("There is already a Combined Product registered with this code.");
							}
							else {
								rs = stmt.executeQuery("SELECT * FROM part");
								while (rs.next()) {
									if (rs.getString("codepart").equals(CodeComb1)) {
										System.out.println("There is a Part Registered with this Code.");
										i =1;
									}
								}
								if (i!=1) {
									stmt.execute("SET FOREIGN_KEY_CHECKS=0");
									stmt.close();
									String query = "UPDATE combination SET codecomb = ? WHERE codecomb = ?";
									PreparedStatement preparedStmt = conn.prepareStatement(query);
									preparedStmt.setString(1, CodeComb1);
									preparedStmt.setString(2, CodeComb);
									preparedStmt.executeUpdate();
									stmt = conn.createStatement();
									stmt.execute("SET FOREIGN_KEY_CHECKS=1");
									stmt.close();
								}

								rs = stmt.executeQuery("SELECT * FROM part WHERE combination_codecomb='"+CodeComb+"'");
								while (rs.next())
								{
									stmt.execute("SET FOREIGN_KEY_CHECKS=0");
									stmt.close();
									String query = "UPDATE part SET combination_codecomb = ? WHERE combination_codecomb = ?";
									PreparedStatement preparedStmt = conn.prepareStatement(query);
									preparedStmt.setString(1, CodeComb1);
									preparedStmt.setString(2, CodeComb);
									preparedStmt.executeUpdate();
									stmt = conn.createStatement();
									stmt.execute("SET FOREIGN_KEY_CHECKS=1");
									stmt.close();
								}
							}
						}
						if (ans.equals("Combination Number")){
							System.out.println("Give the Numeric Code of the Combination :");
							NumComb = sc.nextInt();
							sc.nextLine();
							stmt = conn.createStatement();
							rs = stmt.executeQuery("SELECT * FROM combination WHERE NumComb='"+NumComb+"'");
							if (rs.next())
							{
								System.out.println("There is already a Combination registered with this number.");
							}
							else
							{
								stmt.execute("SET FOREIGN_KEY_CHECKS=0");
								stmt.close();
								String query = "UPDATE combination SET numcomb = ? WHERE codecomb = ?";
								PreparedStatement preparedStmt = conn.prepareStatement(query);
								preparedStmt.setInt(1, NumComb);
								preparedStmt.setString(2, CodeComb);
								preparedStmt.executeUpdate();
								stmt = conn.createStatement();
								stmt.execute("SET FOREIGN_KEY_CHECKS=1");
								stmt.close();
							}
						}
					}
					else {
						System.out.println("There is no Combined Product with this code");
					}
				}
				if (ans.equals("Parts")) {
					System.out.println("Enter the Signature Code of the Part you want to Update:");
					String CodePart = sc.nextLine();

					Statement stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT * FROM part WHERE CodePart='"+CodePart+"'");
					if (rs.next())
					{
						System.out.println("What do you want to change \"Code\" , \"Batch\" , \"Combination\" :");
						ans = sc.nextLine();
						while (!"Code".equals(ans) && !"Batch".equals(ans) && !"Combination".equals(ans)) {
							System.out.println("Wrong entry please type only  \"Code\" , \"Batch\" , \"Combination\" :");
							ans = sc.next();
						}
						if (ans.equals("Code")) {
							System.out.println("Enter the Signature Code of the Part you want to Update:");
							String CodePart1 = sc.nextLine();

							while (CodePart1.length() != 5) {
								System.out.println("Part Code must be only 5 characters :");
								System.out.println("Enter the Signature Code of the Part you want to Update:");
								CodePart1 = sc.nextLine();
							}

							stmt = conn.createStatement();
							rs = stmt.executeQuery("SELECT * FROM part WHERE CodePart='"+CodePart1+"'");
							if (rs.next())
							{
								System.out.println("There is already a Part registered with this Code");
							}
							else {
								rs = stmt.executeQuery("SELECT * FROM combination");
								while (rs.next()) {
									if (rs.getString("codecomb").equals(CodePart1)) {
										System.out.println("There is a Combined Part Registered with this Code.");
										i = 1;
									}
								}
								if (i!=1) {
									stmt.execute("SET FOREIGN_KEY_CHECKS=0");
									stmt.close();
									String query = "UPDATE part SET codepart = ? WHERE codepart = ?";
									PreparedStatement preparedStmt = conn.prepareStatement(query);
									preparedStmt.setString(1, CodePart1);
									preparedStmt.setString(2, CodePart);
									preparedStmt.executeUpdate();
									stmt = conn.createStatement();
									stmt.execute("SET FOREIGN_KEY_CHECKS=1");
									stmt.close();
								}
							}
						}
						if (ans.equals("Batch")) {
							System.out.println("Give the Signature Code of the Batch :");
							CodeBatch = sc.nextInt();
							sc.nextLine();
							rs = stmt.executeQuery("SELECT * FROM batch WHERE CodeBatch='"+CodeBatch+"'");
							if (rs.next()) {
								stmt.execute("SET FOREIGN_KEY_CHECKS=0");
								stmt.close();
								String query = "UPDATE part SET batch_codebatch = ? WHERE codepart = ?";
								PreparedStatement preparedStmt = conn.prepareStatement(query);
								preparedStmt.setInt(1, CodeBatch);
								preparedStmt.setString(2, CodePart);
								preparedStmt.executeUpdate();
								stmt = conn.createStatement();
								stmt.execute("SET FOREIGN_KEY_CHECKS=1");
								stmt.close();
							}
							else
							{
								System.out.println("There is no Batch registered with this Code.");
							}
						}
						if (ans.equals("Combination")) {
							System.out.println("Enter the Combined Part Code in which this Part takes place:");
							CodeComb = sc.nextLine();

							while (CodeComb.length() != 5) {
								System.out.println("Combined Part Code must be only 5 characters :");
								System.out.println("Enter the Combined Part Code in which this Part takes place:");
								CodeComb = sc.nextLine();
							}

							rs = stmt.executeQuery("SELECT * FROM combination WHERE CodeComb='"+CodeComb+"'");
							if(rs.next())
							{
								stmt.execute("SET FOREIGN_KEY_CHECKS=0");
								stmt.close();
								String query = "UPDATE part SET combination_codecomb = ? WHERE codepart = ?";
								PreparedStatement preparedStmt = conn.prepareStatement(query);
								preparedStmt.setString(1, CodeComb);
								preparedStmt.setString(2, CodePart);
								preparedStmt.executeUpdate();
								stmt = conn.createStatement();
								stmt.execute("SET FOREIGN_KEY_CHECKS=1");
								stmt.close();
							}
							else {
								System.out.println("There is no Combined Product with this Code.");
							}
						}
					}
					else{
						System.out.println("There is no Part registered with this Code");
					}
				}
				if (ans.equals("Orders")) {
					System.out.println("Give the Code of the Order that you want to update (received parts) :");
					CodeOrder = sc.nextLine();

					Statement stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT * FROM orders WHERE CodeOrder ='" + CodeOrder + "'");
					if (rs.next()) {
						System.out.println("How many parts received from this order :");
						int numparts = sc.nextInt();
						sc.nextLine();

						int remain =  rs.getInt("quantity") - numparts;
						if (remain > 0) {
							stmt.execute("SET FOREIGN_KEY_CHECKS=0");
							String query = "UPDATE orders SET quantity = ? WHERE codeorder = ?";
							PreparedStatement preparedStmt = conn.prepareStatement(query);
							preparedStmt.setInt(1, remain);
							preparedStmt.setString(2, CodeOrder);
							preparedStmt.executeUpdate();
							stmt.execute("SET FOREIGN_KEY_CHECKS=1");
							stmt.close();
						}
						else {
							try {
								Statement st = conn.createStatement();
								int val = st.executeUpdate("INSERT INTO old_orders VALUES('" + CodeOrder + "')");
							} catch (SQLException s) {
								System.out.println("Error!");
							}
							stmt.execute("SET FOREIGN_KEY_CHECKS=0");
							stmt.close();
							PreparedStatement st = conn.prepareStatement("DELETE FROM orders WHERE CodeOrder = '" + CodeOrder +"'");
							st.executeUpdate();
							stmt = conn.createStatement();
							stmt.execute("SET FOREIGN_KEY_CHECKS=1");
							stmt.close();

						}
					}
					else {
						System.out.println("There is no Order registered under this code.");
					}
				}
				if (ans.equals("Employee")) {
					System.out.println("Enter 6 digit Integer ID of the Employee you want to Update: ");
					ID = sc.nextInt();
					sc.nextLine();
					while (String.valueOf(ID).length() != 6)
					{
						System.out.println("Manager ID must be only 6 digits :");
						System.out.println("Give the 6 digit Integer ID of the Manager :");
						ID = sc.nextInt();
						sc.nextLine();
					}
					Statement stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT * FROM employee WHERE ID='"+ID+"'");
					if (rs.next()){
						System.out.println("What do you want to change \"ID\" , \"Name\" , \"Last Name\" , \"Father Name\" , \"Phone\" , \"Address\", \"Manager\" :");
						ans = sc.nextLine();
						while (!"ID".equals(ans) && !"Name".equals(ans) && !"Last Name".equals(ans) && !"Father Name".equals(ans) && !"Phone".equals(ans) && !"Address".equals(ans) && !"Manager".equals(ans) ) {
							System.out.println("Wrong entry please type only \"ID\" , \"Name\" , \"Last Name\" , \"Father Name\" , \"Phone\" , \"Address\", \"Manager\" :");
							ans = sc.next();
						}

						if (ans.equals("ID")) {
							System.out.println("Enter this Employee's 6 digit Integer ID : ");
							int ID1 = sc.nextInt();
							sc.nextLine();
							while (String.valueOf(ID).length() != 6)
							{
								System.out.println("Manager ID must be only 6 digits :");
								System.out.println("Give the 6 digit Integer ID of the Manager :");
								ID1 = sc.nextInt();
								sc.nextLine();
							}
							stmt = conn.createStatement();
							rs = stmt.executeQuery("SELECT * FROM employee WHERE ID='"+ID1+"'");
							if (rs.next()){
								System.out.println("There is already an Employee registered with this ID.");
							}
							else {
								stmt.execute("SET FOREIGN_KEY_CHECKS=0");
								stmt.close();
								String query = "UPDATE employee SET id = ? WHERE id = ?";
								PreparedStatement preparedStmt = conn.prepareStatement(query);
								preparedStmt.setInt(1, ID1);
								preparedStmt.setInt(2, ID);
								preparedStmt.executeUpdate();
								stmt = conn.createStatement();
								stmt.execute("SET FOREIGN_KEY_CHECKS=1");
								stmt.close();
							}
						}
						if (ans.equals("Name")) { //
							stmt.execute("SET FOREIGN_KEY_CHECKS=0");
							stmt.close();
							PreparedStatement st = conn.prepareStatement("DELETE FROM name WHERE employee_id = '" + ID +"'");
							st.executeUpdate();
							stmt = conn.createStatement();
							stmt.execute("SET FOREIGN_KEY_CHECKS=1");
							stmt.close();

							System.out.println("How many Names this Employee has :");
							i = sc.nextInt();
							sc.nextLine();
							while (i < 1) {
								System.out.println("Employee's must at least have 1 name :");
								System.out.println("How many Names this Employee has :");
								i = sc.nextInt();
								sc.nextLine();
							}
							for (int j = 1; j <= i; j++) {
								System.out.println("Give the Employee's Name:");
								String name = sc.nextLine();
								while (name.length() > 10) {
									System.out.println("Last Name must up to 10 digits");
									System.out.println("Give the Employee's Last Name:");
									name = sc.nextLine();
								}
								rs = stmt.executeQuery("SELECT * FROM name WHERE name='" + name + "'");
								if (rs.next()) {
									name = name.concat(".");
									try {
										Statement st1 = conn.createStatement();
										int val = st1.executeUpdate("INSERT INTO name VALUES('" + name + "' , '" + ID + "')");
									} catch (SQLException s) {
										System.out.println("Error!");
									}
								}
								else {
									try {
										Statement st1 = conn.createStatement();
										int val = st1.executeUpdate("INSERT INTO name VALUES('" + name + "' , '" + ID + "')");
									} catch (SQLException s) {
										System.out.println("Error!");
									}
								}
							}
						}
						if (ans.equals("Last Name")) {
							System.out.println("Give the Employee's Last Name:");
							String lastname = sc.nextLine();
							while (lastname.length() > 10) {
								System.out.println("Last Name must up to 10 digits");
								System.out.println("Give the Employee's Last Name:");
								lastname = sc.nextLine();
							}
							stmt.execute("SET FOREIGN_KEY_CHECKS=0");
							stmt.close();
							String query = "UPDATE employee SET lastname = ? WHERE id = ?";
							PreparedStatement preparedStmt = conn.prepareStatement(query);
							preparedStmt.setString(1, lastname);
							preparedStmt.setInt(2, ID);
							preparedStmt.executeUpdate();
							stmt = conn.createStatement();
							stmt.execute("SET FOREIGN_KEY_CHECKS=1");
							stmt.close();
						}
						if (ans.equals("Father Name")) {
							System.out.println("Give the Employee's Father Name:");
							String fathername = sc.nextLine();
							while (fathername.length() > 10) {
								System.out.println("Father Name must up to 10 digits");
								System.out.println("Give the Employee's Father Name:");
								fathername = sc.nextLine();
							}
							stmt.execute("SET FOREIGN_KEY_CHECKS=0");
							stmt.close();
							String query = "UPDATE employee SET fathername = ? WHERE id = ?";
							PreparedStatement preparedStmt = conn.prepareStatement(query);
							preparedStmt.setString(1, fathername);
							preparedStmt.setInt(2, ID);
							preparedStmt.executeUpdate();
							stmt = conn.createStatement();
							stmt.execute("SET FOREIGN_KEY_CHECKS=1");
							stmt.close();
						}
						if (ans.equals("Manager")) {
							System.out.println("Enter this Employee's Supervisor 6 digit Integer ID : ");
							ManagerID = sc.nextInt();
							sc.nextLine();
							while (String.valueOf(ManagerID).length() != 6) {
								System.out.println("Manager ID must be only 6 digits :");
								System.out.println("Give the 6 digit Integer ID of the Manager :");
								ManagerID = sc.nextInt();
								sc.nextLine();
							}
							rs = stmt.executeQuery("SELECT * FROM manager WHERE ManagerID='" + ManagerID + "'");
							if (rs.next()) {
								stmt.execute("SET FOREIGN_KEY_CHECKS=0");
								stmt.close();
								String query = "UPDATE employee SET manager_managerid = ? WHERE id = ?";
								PreparedStatement preparedStmt = conn.prepareStatement(query);
								preparedStmt.setInt(1, ManagerID);
								preparedStmt.setInt(2, ID);
								preparedStmt.executeUpdate();
								stmt = conn.createStatement();
								stmt.execute("SET FOREIGN_KEY_CHECKS=1");
								stmt.close();
							}
							else {
								System.out.println("There is no Manager registered with this ID.");
							}
						}
						if (ans.equals("Phone")) {
							stmt.execute("SET FOREIGN_KEY_CHECKS=0");
							stmt.close();
							PreparedStatement st = conn.prepareStatement("DELETE FROM phone WHERE employee_id = '" + ID +"'");
							st.executeUpdate();
							stmt = conn.createStatement();
							stmt.execute("SET FOREIGN_KEY_CHECKS=1");
							stmt.close();

							System.out.println("How many Phone numbers this Employee has :");
							i = sc.nextInt();
							sc.nextLine();
							if ( i != 0) {
								for (int j = 0; j < i; j++) {
									System.out.println("Give the phone number:");
									BigInteger phone = sc.nextBigInteger();
									sc.nextLine();
									while (String.valueOf(phone).length() != 10) {
										System.out.println("phone number must have 10 digits");
										System.out.println("Give the phone number:");
										phone = sc.nextBigInteger();
									}
									try {
										Statement st1 = conn.createStatement();
										int val = st1.executeUpdate("INSERT INTO phone VALUES('" + phone + "' , '" + ID + "')");
									} catch (SQLException s) {
										System.out.println("Error!");
									}
								}
							}
						}
						if (ans.equals("Address")) {
							stmt.execute("SET FOREIGN_KEY_CHECKS=0");
							stmt.close();
							PreparedStatement st = conn.prepareStatement("DELETE FROM address WHERE employee_id = '" + ID +"'");
							st.executeUpdate();
							stmt = conn.createStatement();
							stmt.execute("SET FOREIGN_KEY_CHECKS=1");
							stmt.close();

							System.out.println("How many Addresses this Employee has :");
							i = sc.nextInt();
							sc.nextLine();
							while (i < 1) {
								System.out.println("Employee's must at least have 1 Address :");
								System.out.println("How many Addresses this Employee has :");
								i = sc.nextInt();
								sc.nextLine();
							}
							for (int j = 1; j <= i; j++) {
								System.out.println("Give the Employee's Street Name :");
								String ADName = sc.nextLine();
								while (ADName.length() > 14) {
									System.out.println("Street name must have up to 14 characters");
									System.out.println("Give the Employee's Street Name :");
									ADName = sc.nextLine();
								}
								System.out.println("Give the Employee's Street Number :");
								ADNum = sc.nextInt();
								sc.nextLine();
								System.out.println("Give the Employee's Town Name :");
								String ADTown = sc.nextLine();
								while (ADTown.length() > 14) {
									System.out.println("Street name must have up to 14 characters");
									System.out.println("Give the Employee's Street Name :");
									ADTown = sc.nextLine();
								}
								System.out.println("Give the Employee's Postal Code :");
								ADTK = sc.nextInt();
								sc.nextLine();
								try {
									Statement st1 = conn.createStatement();
									int val = st1.executeUpdate("INSERT INTO address VALUES('" + ADName + "' , '" + ADNum + "' , '" + ADTown + "' , '" + ADTK + "' , '" + ID + "')");
								} catch (SQLException s) {
									System.out.println("Error!");
								}
							}
						}
					}
					else {
						System.out.println("There is no Employee registered with this ID.");
					}
				}
			}
			if (ans.equals("Delete")) {
				int counter=0;
				Statement st = conn.createStatement();
				int flag=1;
				String ans2 =null;
				int flag2=0;
				do {
					System.out.println("Enter the Employee's ID whose number you want to delete.");
					ans2 = sc.nextLine();
					st = conn.createStatement();
					ResultSet ras = st.executeQuery("SELECT * FROM phone WHERE employee_id='"+ans2+"'");
					if (ras.next()){
						flag2=1;
					}
					else {
						System.out.println("There is no Employee registered with this ID.");
						flag2=0;
					}
				}while(flag2==0);



				String phone_id = "SELECT * FROM phone " + "where employee_id=" +ans2;
				st= (PreparedStatement) conn.prepareStatement(phone_id);
				ResultSet fs = st.executeQuery(phone_id);
				while (fs.next()) {
					counter++;
					if(counter>=2) {
						break;
					}
				}
				if(counter<2) {
					System.out.println("You cannot delete this employee's phone number. This employee has only one phone number. \n");
				}
				else {
					do {

						if(flag==0) {
							flag=1;
							System.out.println("Error: Phone number not found.");
						}
						System.out.println("Enter the phone number you want to delete:");
						ans = sc.nextLine();
						String dlt_id=null;
						long phonenum_dlt=0;

						String selectSQL = "SELECT * FROM phone WHERE employee_id = '" + ans2 +"' and Number = '" + ans +"'";
						st= (PreparedStatement) conn.prepareStatement(selectSQL);
						ResultSet ms = st.executeQuery(selectSQL);
						while (ms.next()) {
							dlt_id = ms.getString("employee_id");
							phonenum_dlt = ms.getLong("Number");
						}

						if(dlt_id==null ||  phonenum_dlt ==0) {
							flag=0;
						}

						if(flag!=0) {
							st.execute("SET FOREIGN_KEY_CHECKS=0");
							st.close();
							PreparedStatement vt = conn.prepareStatement("DELETE FROM phone WHERE Number = '" + ans +"'");
							vt.executeUpdate();
							st = conn.createStatement();
							st.execute("SET FOREIGN_KEY_CHECKS=1");
							st.close();
							flag=1;}
					}while(flag==0);
				}
			}
			if (ans.equals("Show")) {
				System.out.println("What do you want to Show");
				System.out.println("Type \"Warehouse\" , \"Bin\" , \"Parts\" , \"Combination\" , \"Orders\" , \"Employee\" , \"Manager\" :");
				ans = sc.nextLine();
				while (!"Warehouse".equals(ans) && !"Bin".equals(ans) && !"Parts".equals(ans) && !"Combination".equals(ans) && !"Batch".equals(ans) && !"Orders".equals(ans) && !"Employee".equals(ans) && !"Manager".equals(ans))
				{
					System.out.println("Wrong entry please type only  \"Warehouse\" , \"Bin\" , \"Parts\" , \"Combination\" , \"Orders\" , \"Employee\" , \"Manager\" :");
					ans = sc.next();
				}
				if (ans.equals("Warehouse")) {
					try{
						Statement st = conn.createStatement();
						//INSERT COMMENTS THE FIRST TRY WITH INSERT AND AFTER REMOVE THE COMMENTS OF DELETE QUERY.
						// st.execute("Delete  FROM publisher WHERE publisher_code='P888'");
						ResultSet res = st.executeQuery("SELECT codewh FROM warehouse");
						System.out.println("Warehouse_Code: " + "\t");
						while (res.next()) {
							String is = res.getString("codewh");
							System.out.println(is + "\t");
						}
					}catch(SQLException s){
						System.out.println("SQL code does not execute.");
					}
				}
				else if(ans.equals("Bin")) {
					try{
						Statement st = conn.createStatement();
						ResultSet res = st.executeQuery("SELECT * FROM bin");
						System.out.println("Code_Bin : " + "\t" +  "Capacity:" + "\t" + "Remaining capacity:");
						while (res.next()) {
							int cbin = res.getInt("codebin");
							int bincap=res.getInt("capacity");

							String batch1 = "SELECT * FROM batch " + "where bin_codebin=" +cbin;
							st= (PreparedStatement) conn.prepareStatement(batch1);
							ResultSet rss = st.executeQuery(batch1);
							int sum=0;
							while (rss.next()) {
								int batch = rss.getInt("partsnum");
								sum = sum + batch;
							}
							int remaining = bincap-sum;
							System.out.println(cbin + "\t" + bincap + "\t" + remaining +"\t" );
						}
					}catch(SQLException s){
						System.out.println("SQL code does not execute.");
					}
				}
				else if(ans.equals("Parts")) {
					try{
						Statement st = conn.createStatement();
						ResultSet res = st.executeQuery("SELECT part.codepart as partc , part.batch_codebatch as batchc, batch.bin_codebin as binc , bin.warehouse_codewh as whc FROM part , batch , bin where part.batch_codebatch=batch.codebatch and batch.bin_codebin=bin.codebin order by batch.bin_codebin , batch.codebatch");
						System.out.println("Code_Part: " + "\t" +  "Batch_codebatch:" + "\t" + "Bin_Code:" +  "\t" + "Warehouse_Code:" );
						while (res.next()) {
							String is = res.getString("partc");
							String y = res.getString("batchc");
							String z = res.getString("binc");
							String m = res.getString("whc");
							System.out.println(is + "\t\t" + y + "\t\t" + z + "\t\t"+ m + "\t\t");

						}
					}catch(SQLException s){
						System.out.println("SQL code does not execute.");
					}
				}
				else if(ans.equals("Combination")) {

					try{
						Statement st = conn.createStatement();
						//INSERT COMMENTS THE FIRST TRY WITH INSERT AND AFTER REMOVE THE COMMENTS OF DELETE QUERY.
						// st.execute("Delete  FROM publisher WHERE publisher_code='P888'");
						ResultSet res = st.executeQuery("SELECT * FROM part order by combination_codecomb ,codepart");
						System.out.println("Part Code: \t Combination_Code: " + "\t");
						while (res.next()) {
							String is = res.getString("codepart");
							String y = res.getString("combination_codecomb");

							System.out.println(is + "\t\t" + y);
						}
					}catch(SQLException s){
						System.out.println("SQL code does not execute.");
					}
				}
				else if(ans.equals("Employee")) {
					try{
						Statement st = conn.createStatement();
						ResultSet res = st.executeQuery("SELECT * FROM manager order by managerid");
						System.out.println("\t"+"ID : " + "\t" +  "Last_Name:" + "\t" + "First Name:");
						while (res.next()) {
							int id = res.getInt("managerid");


							String managers = "SELECT * FROM employee " + "where id=" +id;
							st= (PreparedStatement) conn.prepareStatement(managers);
							ResultSet rss = st.executeQuery(managers);
							while (rss.next()){
								String last = rss.getString("lastname");

								String managernames = "SELECT * FROM name " + "where employee_id=" +id;
								st= (PreparedStatement) conn.prepareStatement(managernames);
								ResultSet rsss = st.executeQuery(managernames);
								System.out.print("\n" + "MANAGER: "+ id + "\t" + last +" " );
								while (rsss.next()) {
									String managername = rsss.getString("name");
									System.out.print( managername+" " );

								}
								String employees = "SELECT * FROM employee " + "where manager_managerid=" +id;
								st= (PreparedStatement) conn.prepareStatement(employees);
								ResultSet remp = st.executeQuery(employees);

								while (remp.next()) {
									int emp_id=remp.getInt("id");
									String emp_last = remp.getString("lastname");

									if(id!=emp_id) {
										String employeenames = "SELECT * FROM name " + "where employee_id=" +emp_id;
										st= (PreparedStatement) conn.prepareStatement(employeenames);
										ResultSet rempn = st.executeQuery(employeenames);
										System.out.print("\n"+"\t" + emp_id + "\t" + emp_last + " ");
										while (rempn.next()) {
											String emp_name=rempn.getString("name");
											System.out.print(emp_name +" " );
										}
									}
								}
							}
						}
						System.out.println("\n");
					}catch(SQLException s){
						System.out.println("SQL code does not execute.");
					}
				}
				else if(ans.equals("Manager")) {
					try{
						Statement st = conn.createStatement();
						ResultSet res = st.executeQuery("SELECT *  FROM employee order by lastname");
						System.out.println("Manager_ID:" + "\t" + "Last_Name:" + "\t" + "First_Name:");
						while (res.next()) {

							int mnid = res.getInt("manager_managerid");
							int	id2=res.getInt("id");
							String last = res.getString("lastname");
							if(mnid==id2) {
								System.out.print("\n"+id2 + "\t" + last + "\t");


								String nam = "SELECT * FROM name " + "where employee_id=" +id2;
								st= (PreparedStatement) conn.prepareStatement(nam);
								ResultSet rss = st.executeQuery(nam);
								while (rss.next()) {
									String nn = rss.getString("name");
									System.out.print(" "+nn);
								}
							}
						}
						System.out.println("\n");
					}catch(SQLException s){
						System.out.println("SQL code does not execute.");
					}
				}
				else if(ans.equals("Orders")) {
					try{
						Statement st = conn.createStatement();
						ResultSet res = st.executeQuery("SELECT *  FROM employee order by lastname");
						System.out.println("Orders:" + "\t\t" + "Manager_id:" + "\t" + "Last_Name:" + "\t" + "First_Name:");
						while (res.next()) {

							int mnid = res.getInt("manager_managerid");
							int	id2=res.getInt("id");
							String last = res.getString("lastname");
							if(mnid==id2) {


								String nam = "SELECT * FROM name " + "where employee_id=" +id2;
								st= (PreparedStatement) conn.prepareStatement(nam);
								ResultSet rss = st.executeQuery(nam);
								list.clear();
								while (rss.next()) {
									String nn = rss.getString("name");
									list.add(nn);
								}

								int ordcount = 0;
								String ord = "SELECT * FROM orders " + "where manager_managerid=" +mnid;
								st= (PreparedStatement) conn.prepareStatement(ord);
								rs = st.executeQuery(ord);
								while (rs.next()) {
									String orders = rs.getString("codeorder");
									System.out.print(orders + " ");
									ordcount++;
								}
								if (ordcount!=0) {
									System.out.print(" "+id2 + "\t\t" + last +"\t") ;
									String result = String.join(",", list);
									System.out.println(result);	}

							}
						}
						System.out.println("\n");
					}catch(SQLException s){
						System.out.println("SQL code does not execute.");
					}
				}
			}
			System.out.println("Do you want program to end ? (Yes or No):");
			exit = sc.nextLine();
			while (!"Yes".equals(exit) && !"YES".equals(exit) && !"yes".equals(exit) && !"No".equals(exit) && !"NO".equals(exit) && !"no".equals(exit))
			{
				System.out.println("Error! Yes or No answers only");
				System.out.println("Do you want program to end ? (Yes or No):");
				exit = sc.nextLine();
			}
		}
			conn.close();
	}
}