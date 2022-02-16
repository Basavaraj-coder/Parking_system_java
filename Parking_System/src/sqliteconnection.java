import java.sql.*;

import javax.swing.*;

public class sqliteconnection {
	Connection con=null;
	public static Connection dbconnector()
	{
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con=DriverManager.getConnection("jdbc:sqlite:D:\\eclipse works\\Parking_System\\src\\parkingSystem.sqlite");
			//JOptionPane.showMessageDialog(null, "Connection Successfull");
			return con;
		}catch(Exception ae)
		{
			JOptionPane.showMessageDialog(null, ae);
			return null;
		}
	}
}
