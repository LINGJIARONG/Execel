package ArcSoLu.Excel;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Original version of this file was part of InterClient 2.01 examples
//
//Copyright InterBase Software Corporation, 1998.
//Written by com.inprise.interbase.interclient.r&d.PaulOstler :-)
//
//Code was modified by Roman Rokytskyy to show that Firebird JCA-JDBC driver
//does not introduce additional complexity in normal driver usage scenario.
//
//A small application to demonstrate basic, but not necessarily simple, JDBC features.
//
//Note: you will need to hardwire the path to your copy of employee.gdb
//    as well as supply a user/password in the code below at the
//    beginning of method main().

public class DriverExample
{	
	static 	PreparedStatement insertRow=null;
	static java.sql.Driver d = null;
	static java.sql.Connection c = null;
	static java.sql.ResultSet rs = null;
	static java.sql.Statement s = null;
	public static int data_count =0;
	static Log log=new Log();
	private static ArrayList<String> n_SAV=new ArrayList<>();
	private static int random_cr=900;


	// Make a connection to an employee.gdb on your local machine,
	// and demonstrate basic JDBC features.
	// Notice that main() uses its own local variables rather than
	// static class variables, so it need not be synchronized.
	public static void connect ()throws Exception
	{
		// Modify the following hardwired settings for your environment.
		// Note: localhost is a TCP/IP keyword which resolves to your local machine's IP address.
		//       If localhost is not recognized, try using your local machine's name or
		//       the loopback IP address 127.0.0.1 in place of localhost.
		// String databaseURL = "jdbc:firebirdsql:localhost/3050:c:/database/employee.gdb";
		//String databaseURL = "jdbc:firebirdsql:native:localhost/3050:c:/database/employee.gdb";
		//String databaseURL = "jdbc:firebirdsql:local:c:/database/employee.gdb";
		//String databaseURL = "jdbc:firebirdsql:embedded:c:/database/employee.fdb?lc_ctype=WIN1251";
		String databaseURL = "jdbc:firebirdsql:localhost/3050:/Users/ano/sav.fdb";
		String user = "SYSDBA";
		String password = "masterkey";
		String driverName = "org.firebirdsql.jdbc.FBDriver";

		try {


			int registrationAlternative = 1;
			switch (registrationAlternative) {

			case 1:

				try {
					Class.forName ("org.firebirdsql.jdbc.FBDriver");
				}
				catch (java.lang.ClassNotFoundException e) {
					// A call to Class.forName() forces us to consider this exception :-)...
					log.add("Firebird JCA-JDBC driver not found in class path");
					log.add(e.getMessage ());
					return;
				}
				break;

			case 2:

				try {
					java.sql.DriverManager.registerDriver (
							(java.sql.Driver) Class.forName ("org.firebirdsql.jdbc.FBDriver").newInstance ()
							);
				}
				catch (java.lang.ClassNotFoundException e) {
					// A call to Class.forName() forces us to consider this exception :-)...
					log.add("Driver not found in class path");
					log.add(e.getMessage ());
					return;
				}
				catch (java.lang.IllegalAccessException e) {
					// A call to newInstance() forces us to consider this exception :-)...
					log.add("Unable to access driver constructor, this shouldn't happen!");
					log.add(e.getMessage ());
					return;
				}
				catch (java.lang.InstantiationException e) {
					// A call to newInstance() forces us to consider this exception :-)...
					// Attempt to instantiate an interface or abstract class.
					log.add("Unable to create an instance of driver class, this shouldn't happen!");
					log.add(e.getMessage ());
					return;
				}
				catch (java.sql.SQLException e) {
					// A call to registerDriver() forces us to consider this exception :-)...
					log.add("Driver manager failed to register driver");
					showSQLException (e);
					return;
				}
				break;

			case 3:

				java.util.Properties sysProps = System.getProperties ();
				StringBuffer drivers = new StringBuffer ("org.firebirdsql.jdbc.FBDriver");
				String oldDrivers = sysProps.getProperty ("jdbc.drivers");
				if (oldDrivers != null)
					drivers.append (":" + oldDrivers);
				sysProps.put ("jdbc.drivers", drivers.toString ());
				System.setProperties (sysProps);
				break;

			case 4:

				d = new org.firebirdsql.jdbc.FBDriver ();
			}

			int connectionAlternative = 1;
			switch (connectionAlternative) {

			case 1:

				try {
					c = java.sql.DriverManager.getConnection (databaseURL, user, password);
					log.add("Connection established.");
				}
				catch (java.sql.SQLException e) {
					e.printStackTrace();
					log.add("Unable to establish a connection through the driver manager.");
					showSQLException (e);
					return;
				}
				break;

			case 2:
				// If you're working with a particular driver d, which may or may not be registered,
				// you can get a connection directly from it, bypassing the driver manager...
				try {
					java.util.Properties connectionProperties = new java.util.Properties ();
					connectionProperties.put ("user", user);
					connectionProperties.put ("password", password);
					connectionProperties.put ("lc_ctype", "WIN1251");
					c = d.connect (databaseURL, connectionProperties);
					log.add("Connection established.");
				}
				catch (java.sql.SQLException e) {
					e.printStackTrace();
					log.add("Unable to establish a connection through the driver.");
					showSQLException (e);
					return;
				}
				break;
			}
		}finally {
			log.add("Closing database resources and rolling back any changes we made to the database.");

		}
	}

	private static void showSQLException (java.sql.SQLException e)
	{

		java.sql.SQLException next = e;
		if(next.getErrorCode()==335544665) {
			return ;
		}
		while (next != null) {
			log.add(next.getMessage ());
			log.add(next.getStackTrace().toString());
			log.add("Error Code: " + next.getErrorCode ());
			log.add("SQL State: " + next.getSQLState ());
			next = next.getNextException ();
		}
	}

	public static void prepare() throws SQLException {
		s = c.createStatement();
		String updateString = "insert into SAVDATA values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		insertRow = c.prepareStatement(updateString);
	}

	public static void insertLine(ArrayList<String> line) throws SQLException {
	
		insertRow.clearParameters();
		
		try {
			for(int i=0;i<line.size()-1;i++) {
				String cell=line.get(i);	
				//cell=cell.replace("'", "&acute");
			
				if(i==1&&cell.trim()=="") {
					random_cr+=2;
					cell="def"+random_cr;
				}
				if(i==1) {
					if(!n_SAV.contains(cell))
						n_SAV.add(cell);
					else {
						random_cr+=2;
						cell="def"+random_cr;
						n_SAV.add(cell);					
					}
				}
				insertRow.setString(i+1,cell);
			}
			data_count++;

			insertRow.setString(line.size(),line.get(line.size()-1));
			insertRow.addBatch();

		}catch (java.sql.SQLException e) {
			log.add(line.get(1)+"date:"+line.get(0)+insertRow.getParameterMetaData().toString());
			//e.printStackTrace();
			showSQLException (e);
		}
	}
	public static void execute() throws SQLException {
		try{
			insertRow.executeBatch();			
		}catch(SQLException ex) {          
			showSQLException(ex);
		}finally {
			insertRow.close();
		}
	}

	public static void  close() throws SQLException {
		c.close();
		
	
	}


}




