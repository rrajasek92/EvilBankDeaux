package EvilBank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Transfer {
	
	private static float bal1, bal2,amount;
	
	public static void transferDB(String acct1, String acct2, Float amount){
		//URL of Oracle database server
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
		PreparedStatement preStatement1 = null;
		String sql ="Update Account set balance=balance+"+amount+" where account_no = "+acct1;
		String sql1 ="Update Account set balance=balance-"+amount+" where account_no = "+acct2;

        //creating PreparedStatement object to execute query
        
		try {
			preStatement = conn.prepareStatement(sql);
			preStatement1 = conn.prepareStatement(sql1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
        try {
			ResultSet result = preStatement.executeQuery();
			ResultSet result1 = preStatement1.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Amount transferred.");
}


	public static float getBal1() {
		return bal1;
	}

	public static void setBal1(float bal1) {
		Transfer.bal1 = bal1;
	}

	public static float getBal2() {
		return bal2;
	}

	public static void setBal2(float bal2) {
		Transfer.bal2 = bal2;
	}

	public static float getAmount() {
		return amount;
	}

	public static void setAmount(float amount) {
		Transfer.amount = amount;
	}
	
	public static void acctTransfer(String acc1,String acc2, Float bal1, Float bal2, Float amount){
		Account accbal1=new Account(acc1);
		Account accbal2=new Account(acc2);
		if (amount>bal2){
			System.out.println("Insufficient funds!");
		}
		else{
			
			accbal1.setAcctBalance(bal1+=amount);
			accbal2.setAcctBalance(bal2-=amount);
			transferDB(acc1,acc2,amount);
		}
		
		
	}
}