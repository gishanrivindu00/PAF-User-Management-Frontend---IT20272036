package com;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {
	
	private Connection connect() {
		
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");// this sample 1
	
			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/users?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root", "celetron123");
			
			//For testing
			System.out.print("Successfully connected");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	//Read function
	public String readUsers()
	{ 
		 String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
				return "Error while connecting to the database for reading."; 
			 } 
	
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>Name</th>" +"<th>Email</th><th>Address</th>"+ "<th>Update</th><th>Remove</th></tr>"; 
			 String query = "select * from users"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 	
				 // Retrieve from database using column names
				 String userID = Integer.toString(rs.getInt("userID")); 
				 String name = rs.getString("name"); 
				 String email = rs.getString("email");  
				 String address = rs.getString("address"); 
				 
				 // Add a row into the html table
				 output += "<tr><td>"+ name + "</td>";
				 output += "<td>" + email + "</td>"; 
				 output += "<td>" + address + "</td>";
				 
				 // Buttons
				 output += 
				   "<td><input name='btnUpdate' type='button' value='Update' " + "class='btnUpdate btn btn-secondary' data-userid='" + userID + "'></td>"
				 + "<td><input name='btnRemove' type='button' value='Remove'class='btnRemove btn btn-danger' data-userid='" + userID + "'>"+"</td>"
				 + "</form></td></tr>";			 
			 } 
			con.close(); 
			// Complete the html table
			output += "</table>"; 
			 } 
		catch (Exception e) 
		 { 
			 output = "Error while reading users."; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	}	
	//Insert function
	public String insertUser(String name, String email, String address)
	{ 
		String output = "";  
		try
		 { 
			Connection con = connect(); 
			 if (con == null) 
			 { 
				 return "Error while connecting to the database"; 
			 } 
			 // create a prepared statement
			 String query = " insert into users (`userID`,`name`,`email`,`address`)"+" values (?, ?, ?, ?)"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setString(2, name); 
			 preparedStmt.setString(3, email); 
			 preparedStmt.setString(4, address); 
			 
			 //execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 
			 String newUsers = readUsers();
			 output = "{\"status\":\"success\", \"data\": \"" + newUsers + "\"}";
		 } 
		catch (Exception e) 
		 { 
			output = "{\"status\":\"error\", \"data\": \"Error while inserting user.\"}";
			 System.err.println(e.getMessage()); 
		 } 
		return output; 
	}	
	//Delete function
	public String deleteUser(String userID)
	{
		String output = "";
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for deleting.";
			}
		
			// create a prepared statement
			String query = "delete from users where userID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(userID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newUsers = readUsers();
			output = "{\"status\":\"success\", \"data\": \"" + newUsers + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while deleting user.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	//Update function
	public String updateUser(String userID, String name, String email, String address)
	{
		String output = "";
		try 
		{
			Connection con = connect();
	
			if (con == null) 
			{
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE items SET name=?,email=?,address=?" + "WHERE userID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
					preparedStmt.setString(1, name);
					preparedStmt.setString(2, email);
					preparedStmt.setString(3, address);
					preparedStmt.setInt(5, Integer.parseInt(userID));

			// execute the statement
					preparedStmt.execute();
					con.close();

					String newUsers = readUsers();
					output = "{\"status\":\"success\", \"data\": \"" + newUsers + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while updating user.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
