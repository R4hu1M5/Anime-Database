package project.animedb;

import java.util.*;
import java.sql.*;

public class DBHelper {
	
	//STEP 2a: Set JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/animeDB";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "IncorrectPassword";

    static Scanner in = new Scanner(System.in);

    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet rs;
    
    public void initialize()
    {
    	try
    	{
            //STEP 2b: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...\n");
            stmt = conn.createStatement();
        }
    	catch(SQLException se)
    	{
            //Handle errors for JDBC
            se.printStackTrace();
        }
    	catch(Exception e)
    	{
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }
    
    public void terminate()
    {
    	try
    	{
            if(stmt!=null)
            {
            	stmt.close();
            }
        }
    	catch(SQLException se2)
    	{
    		// nothing we can do
        }
        try
        {
            if(conn!=null)
            {
            	System.out.println("\nClosing Connection to Database....");
            	conn.close();
            }
        }
        catch(SQLException se)
        {
            se.printStackTrace();
        }//end finally try
    }
    
    public ResultSet select(String sql)
    {
    	ResultSet rs = null;
    	try
    	{
    		rs = stmt.executeQuery(sql);
    	}
    	catch(SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    	return rs;
    }
    
    public void insert(String sql)
    {
    	try
    	{
    		stmt.executeUpdate(sql);
    	}
    	catch(SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }
    
    public void delete(String sql)
    {
    	try
    	{
    		stmt.executeUpdate(sql);
    	}
    	catch(SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }
}
