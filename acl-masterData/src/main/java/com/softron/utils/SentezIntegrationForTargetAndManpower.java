package com.softron.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.Date;

import com.softron.schema.admin.entity.masterdata.OperationBreakDown;
import com.softron.schema.admin.entity.masterdata.TargetAndManpower;

public class SentezIntegrationForTargetAndManpower {

	static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	public static void updateData(Connection connection, String sql) throws SQLException {
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
			}
		}
	}

	public static Connection getDatabaseConnection(String DB_URL, String USER, String PASS) {
		Connection connection = null;
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void closeDatabaseConnection(Connection connection) {
		try {
			if (connection != null)
				connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void erpLineTargetCreation(String jdbcUrl, String username, String password,TargetAndManpower targetAndManpower) {

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		
		Connection con = null;
		Statement stmt = null;
 
		try {
			//System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();

//			String startDate=targetAndManpower.getDate();
//			Date date=Date.valueOf(startDate);//converting string into sql date  
			 //System.out.println(date); 

//			String tsql = "SET IDENTITY_INSERT Sentez.dbo.Erp_LineTarget ON;\r\n" + 
//					"INSERT INTO Sentez.dbo.Erp_LineTarget(RecId,TargetDate,ProductionLine,OperatorNumber,HelperNumber,LineTarget)\r\n" + 
//				    "VALUES ("+targetAndManpower.getId()+",'"+targetAndManpower.getDate()+"','"+targetAndManpower.getSection().getName()+"',"+targetAndManpower.getNumberOfOperator()+","+targetAndManpower.getNumberOfHelper()+","+targetAndManpower.getProductiontarget()+");\r\n"+ 
//				    "SET IDENTITY_INSERT Sentez.dbo.Erp_LineTarget OFF;";
			String tsql = "SET IDENTITY_INSERT Sentez.dbo.Erp_LineTarget ON;\r\n" + 
					"INSERT INTO Sentez.dbo.Erp_LineTarget(RecId,TargetDate,ProductionLine,OperatorNumber,HelperNumber,LineTarget)\r\n" + 
				    "VALUES ("+targetAndManpower.getId()+",'"+targetAndManpower.getDate()+"',"+targetAndManpower.getNumberOfOperator()+","+targetAndManpower.getNumberOfHelper()+","+targetAndManpower.getProductiontarget()+");\r\n"+ 
				    "SET IDENTITY_INSERT Sentez.dbo.Erp_LineTarget OFF;";
			
			System.out.println(tsql);

			stmt.executeUpdate(tsql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}

	}
	
	public static void erpLineTargetUpdate(String jdbcUrl, String username, String password,TargetAndManpower targetAndManpower) {

		String connectionUrl = jdbcUrl + "user=" + username + ";password=" + password + ";";
		
		Connection con = null;
		Statement stmt = null;
 
		try {
			//System.out.println("@@@@start@@@@");
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(connectionUrl);
			stmt = con.createStatement();



//			String tsql = "UPDATE Sentez.dbo.Erp_LineTarget\r\n" + 
//					"SET TargetDate ='"+targetAndManpower.getDate()+"',ProductionLine ='"+targetAndManpower.getSection().getName()+"',OperatorNumber="+targetAndManpower.getNumberOfOperator()+",HelperNumber="+targetAndManpower.getNumberOfHelper()+",LineTarget="+targetAndManpower.getProductiontarget()+"\r\n"+ 
//					"WHERE RecId="+targetAndManpower.getId()+";";
			String tsql = "UPDATE Sentez.dbo.Erp_LineTarget\r\n" + 
					"SET TargetDate ='"+targetAndManpower.getDate()+"',OperatorNumber="+targetAndManpower.getNumberOfOperator()+",HelperNumber="+targetAndManpower.getNumberOfHelper()+",LineTarget="+targetAndManpower.getProductiontarget()+"\r\n"+ 
					"WHERE RecId="+targetAndManpower.getId()+";";

			stmt.executeUpdate(tsql);

			

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("@@@Exception@@@");
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}

	}
	
	
	

	
}
