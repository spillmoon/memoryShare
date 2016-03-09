package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
	public static Connection getConnection(){
		
		Connection conn=null;
		
		try{
			String url="jdbc:oracle:thin:@localhost:1521:orcl";
			String id="scott";
			String pass="tiger";
			conn=DriverManager.getConnection(url, id, pass);
		}catch(SQLException e){
			System.out.println("Connection error: "+e);
		}
		
		return conn;
	}
}
