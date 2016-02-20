package com.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlPreparedStatement {
	private static final String DB_NAME = "masterface";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	public static Connection getConnection(){
		 try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("error in driver");
				e.printStackTrace();
			}
			java.sql.Connection con = null;
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DB_NAME, USER, PASSWORD);
			}catch(SQLException e){
				System.out.println("database connection error");
				e.printStackTrace();
				return null;
			}
			return con;
	}
	public static ResultSet select(String statement){
		Connection connection = getConnection();
		try {
			PreparedStatement st = connection.prepareStatement(statement);
			ResultSet result = st.executeQuery();
			return result;
		} catch (SQLException e) {
			System.out.println("select error");
			e.printStackTrace();
			return null;
		}
	}
	public static boolean insert(String statement, String ...values){
		boolean result = true;
		Connection connection = getConnection();
		try {
			PreparedStatement st = connection.prepareStatement(statement);
			for (int i = 1; i <= values.length; i++) {
				st.setString(i, values[i-1]);
			}
			result = st.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("insert error");
			e.printStackTrace();
		}
		return false;
	}
	public static boolean delete(String statement){
		boolean result = true;
		Connection connection = getConnection();
		try {
			PreparedStatement st = connection.prepareStatement(statement);
			result = st.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("delete error");
			e.printStackTrace();
		}
		return false;
	}
	public static int increment(String statement, int value){
		int index = 0;
		Connection connection = getConnection();
		try {
			PreparedStatement st1 = connection.prepareStatement("select "+statement+" from Statistics where ID = '1'");
			ResultSet result = st1.executeQuery();
			if(result.next()){
				index = result.getInt(statement);
			}
			
			index += value;
			PreparedStatement st = connection.prepareStatement("update Statistics set "+statement+" = ? where ID='1'");
			st.setInt(1, index);
			st.execute();
		} catch (SQLException e) {
			System.out.println("increment error");
			e.printStackTrace();
		}
		return index;
	}
	public static boolean append(String tableName, String key, String value, String updatedColumn, String newValue){
		ResultSet result = select("select "+updatedColumn+" from "+tableName+" where "+key+"='"+value+"'");
		try {
			if(result.next()){
				String v = result.getString(updatedColumn);
				v += newValue;
				insert("update "+tableName+" set "+updatedColumn+"=? where "+key+"= "+value, v);
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean remove(String tableName, String key, String value, String updatedColumn, String newValue){
		ResultSet result = select("select "+updatedColumn+" from "+tableName+" where "+key+"='"+value+"'");
		try {
			if(result.next()){
				String v = result.getString(updatedColumn);
				v = v.replaceFirst(newValue, "");
				insert("update "+tableName+" set "+updatedColumn+"=? where "+key+"= "+value, v);
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}