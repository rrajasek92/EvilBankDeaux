package EvilBank;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class InitializeDatabase {
	public static void initDB(){

				 String url = "jdbc:oracle:thin:system/password@localhost"; 
			      
			        //properties for creating connection to Oracle database
			        Properties props = new Properties();
			        props.setProperty("user", "TestUserDB");
			        props.setProperty("password", "password");
			      
			        //creating connection to Oracle database using JDBC
			        Connection conn = null;
					try {
						conn = DriverManager.getConnection(url,props);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					PreparedStatement preStatement = null;
				//	PreparedStatement preStatement1 = null;

			String sql ="delete from Account";
		//	String sql1="delete from Transaction";
			        //creating PreparedStatement object to execute query
			        
					try {
						preStatement = conn.prepareStatement(sql);
					//	preStatement1 = conn.prepareStatement(sql1);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    
			        try {
			       // 	System.out.println(sql+sql1);
						ResultSet result = preStatement.executeQuery();
					//	ResultSet result1 = preStatement1.executeQuery();
						System.out.println();
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        
			        System.out.println("Initialized DB");      
			

		
	}
}
