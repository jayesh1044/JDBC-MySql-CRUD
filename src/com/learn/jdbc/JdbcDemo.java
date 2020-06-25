package com.learn.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JdbcDemo {

	private String dburl = "jdbc:mysql://localhost:3306/test";
	private String dbuname = "root";
	private String dbpasword = "jayesh";
	private String dbdriver = "com.mysql.jdbc.Driver";
	Connection con;
	

	public void loadDriver(String dbdriver)
	{
		try {
			getClass().forName(dbdriver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Connection getConnection()
	{
		
		Connection con = null;
		try {
			con = DriverManager.getConnection(dburl,dbuname,dbpasword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
		
	}
	
	public String insert(Member member) 
	{
		
		System.out.println("Connected");
		String sql = "insert into test.member values (?,?,?,?)";
		String result = "Data Entered successfully";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, member.getUname());
			ps.setString(2,member.getPassword());
			ps.setString(3, member.getEmail());
			ps.setString(4, member.getPhone());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "Data not entered";
		}
		return result;
	}
	
	public void display()
	{

		try {
			Statement stmt = con.createStatement();
		      String sql;
		      sql = "SELECT * FROM member";
		      ResultSet rs = stmt.executeQuery(sql);
		      System.out.println("UserName\tPassword\tEmail\tPhone");
		      while(rs.next()){
		         //Retrieve by column name
		         String uname  = rs.getString("uname");
		         String password = rs.getString("password");
		         String email = rs.getString("email");
		         String phone = rs.getString("phone");

		         
		         System.out.print(uname);
		         System.out.print("\t" + password);
		         System.out.print("\t" + email);
		         System.out.println("\t" + phone);
		         
		      }

		} catch (SQLException e) {
			// TODO: handle exception
		}
				
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JdbcDemo obj = new JdbcDemo();
		obj.loadDriver(obj.dbdriver);
		obj.con = obj.getConnection();
		Scanner sc = new Scanner(System.in);
		String ch = "y";
		
		while(ch.equals("y"))
		{
			System.out.println("Welcome to Java-MySql CRUD Application");
			System.out.println("1. Insert");
			System.out.println("2. Display");
			
			System.out.println("Enter your choice");
			
			int choice = sc.nextInt();
			
			if(choice == 1)
			{
				System.out.println("Enter User Name: ");
				String uname = sc.next();
				
				System.out.println("Enter Password: ");
				String password = sc.next();
				
				System.out.println("Enter Email: ");
				String email = sc.next();
				
				System.out.println("Enter Phone: ");
				String phone = sc.next();
				
				Member member = new Member(uname, password, email, phone);
				
				
				String result = obj.insert(member);
				
				System.out.println(result);
				
			}
			else if(choice == 2)
			{
				obj.display();
			}
			else
			{
				continue;
			}
			System.out.println("Do you want to continue?(y/n)");
			ch = sc.next();
		}
		
		
	}

}
