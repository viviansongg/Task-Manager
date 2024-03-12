package mainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class launcher {

	public static void main(String[] args) {
		String databaseURL = "jdbc:ucanaccess://TaskManager.accdb";
		
		try {
			Connection conn = DriverManager.getConnection(databaseURL);
			
			//addRow(conn);
			//addRow2(conn);
			//queryContacts(conn);
			//boolean t = isExist(conn, 3); //check if entry exist
			//createTable(conn);
			dropTable(conn, "Task");
			
			conn.close();
			
		} catch (SQLException e ) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void createTable(Connection conn) {
		
		try {
			String sql = "CREATE TABLE Task(Task_Id AUTOINCREMENT PRIMARY KEY, Title VARCHAR(255), Description VARCHAR(255))";
			Statement s = conn.createStatement();
						
			int count = s.executeUpdate(sql);
			
			System.out.println("Table created.");
		    			
			s.close();	
		} catch (SQLException e ) {
			e.printStackTrace();
		}
		
	}
	
	public static void dropTable(Connection conn, String table) {
		
		try {
			String sql = "DROP TABLE Task";
			Statement s = conn.createStatement();
						
			int count = s.executeUpdate(sql);
			
			System.out.println("Table dropped.");
		    			
			s.close();	
		} catch (SQLException e ) {
			e.printStackTrace();
		}
		
	}
	
	public static void addRow(Connection conn) {
		try {
			String sql = "INSERT INTO Contacts(Full_Name, Email, Phone) VALUES(" +
					"'Sean Song', 'sean.song@hotmail.com', '12345678')"; 
			Statement s = conn.createStatement();
								
			int rows = s.executeUpdate(sql);
			
			if(rows > 0) {
				System.out.println("A new row is added.");
			}
			s.close();	
		} catch (SQLException e ) {
			e.printStackTrace();
		}
		
	}
	
	public static void addRow2(Connection conn) {
		
		try {
			String sql = "INSERT INTO Contacts(Full_Name, Email, Phone) VALUES(?, ?, ?)";
			PreparedStatement s = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			s.setString(1, "Bill Gates");
			s.setString(2, "billg@microsoft.com");
			s.setString(3, "123456789");
						
			int rows = s.executeUpdate();
			
			if(rows > 0) {
				
				try (ResultSet generatedKeys = s.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	System.out.println("New Contact_ID = " + generatedKeys.getLong(1));
		            }
		            else {
		                throw new SQLException("Creating contact failed.");
		            }
		        }
			}
						
			s.close();	
		} catch (SQLException e ) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean isExist(Connection conn, int Id) {
		if(Id <= 0)
			return false;
		
		boolean result = false;
		
		try {
			String sql = "SELECT * FROM Contacts where Contact_Id = " + Id;
			Statement s = conn.createStatement();
						
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()) {
				result = true;
			}
			
			s.close();	
			
		} catch (SQLException e ) {
			e.printStackTrace();
		}
	
		return result;
	}
	
	public static void queryContacts(Connection conn) {
		
		try {
			String sql = "SELECT * FROM Contacts";
			Statement s = conn.createStatement();
						
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("Contact_ID");
				String fullName = rs.getString("Full_Name");
				String email = rs.getString("Email");
				String phone = rs.getString("Phone");
					
				System.out.println(id + ", " + fullName + ", " + email + ", " + phone);
			}
			
			s.close();	
			
		} catch (SQLException e ) {
			e.printStackTrace();
		}
	
	}
	
}
