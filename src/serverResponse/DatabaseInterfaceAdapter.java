package serverResponse;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javafx.util.Pair;

import java.util.*;

public class DatabaseInterfaceAdapter {
	private static final String username = "leezhiran";
	private static final String password = "3721qwer";
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/serverdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";   
	/*
	 * inner method initializes statement
	 * */
	private static Statement init_statement() {
		Connection connection;
		Statement statement;
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, username, password);
			statement = connection.createStatement();	
			return statement;		
		}catch(ClassNotFoundException cnfe1) {
			cnfe1.printStackTrace();
		}catch(SQLException se1) {
			se1.printStackTrace();
		}
		return null;
	}
	
	private static PreparedStatement init_prepare_statement(String sql_statement) {
		Connection connection;
		PreparedStatement statement;
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, username, password);
			statement = connection.prepareStatement(sql_statement);	
			return statement;		
		}catch(ClassNotFoundException cnfe1) {
			cnfe1.printStackTrace();
		}catch(SQLException se1) {
			se1.printStackTrace();
		}
		return null;
	}
	public static boolean isResultSetEmpty(ResultSet rs) {
		try {
			boolean result;
			if(rs.next()){
				result=true;
				rs.previous();
				return result;
			}else {
				return false;
			}
		}catch(SQLException se1) {
			se1.printStackTrace();
		}
		return true;
	}
	/*
	 * 
	 * @param items(select String1 form String2)
	 * @param tableName which table
	 * */
	public static ResultSet doQuerySelectWhere(Map<String,String> items,String tableName) {
		Statement statement=init_statement();
		ResultSet rs=null;
		boolean first=true;
		String sql_statement;
		sql_statement="select * from "+tableName;
		for(String para1:items.keySet()) {
			if(first==true) {
				sql_statement+=" where "+para1+" = \""+items.get(para1)+"\"";
				first=false;
			}else {
				sql_statement+=" or "+para1+" = \""+items.get(para1)+"\"";
			}
		}
		sql_statement+=";";
		System.out.println(sql_statement);
		try{
			rs=statement.executeQuery(sql_statement);
		}catch(SQLException sqle1) {
			sqle1.printStackTrace(System.out);
		}
		return rs;
	}
	/*
	 * 
	 * @param items(select String1 form String2)
	 * @param tableName which table
	 * */
	public static int doInsert(Map<String,String> items,String tableName) {
		Statement statement=init_statement();
		int rs=0;
		boolean first=true;
		String sql_statement;
		sql_statement="insert into "+tableName+" (";
		for(String para1:items.keySet()) {
			if(first==true) {
				sql_statement+=para1+" ";
				first=false;
			}else {
				sql_statement+=" , "+para1+" ";
			}
			
		}
		sql_statement+=") values (";
		first=true;
		for(String para2:items.values()) {
			if(first==true) {
				sql_statement+="\""+para2+"\" ";
				first=false;
			}else {
				sql_statement+=" , \""+para2+"\" ";
			}
			
		}
		sql_statement+=");";
		System.out.println(sql_statement);
		try{
			rs=statement.executeUpdate(sql_statement);
		}catch(SQLException sqle1) {
			sqle1.printStackTrace(System.out);
		}
		return rs;
	}
	public static int insertBlob(byte[] file,Pair<String,String> item,String tableName) throws FileNotFoundException{
		
		String sql_statement="update user SET user_avatar=? where user_name=?";
		PreparedStatement statement=init_prepare_statement(sql_statement);
		InputStream is= new ByteArrayInputStream(file);
		try {
			statement.setBlob(1,is);
			statement.setString(2,item.getValue());
			statement.executeUpdate();
			statement.close();
		}catch(SQLException se1) {
			se1.printStackTrace();
		}
		return 1;
	}
	public static int insertBlob(InputStream is,Pair<String,String> item,String tableName) throws FileNotFoundException{
		
		String sql_statement="update user SET user_avatar=? where user_name=?";
		PreparedStatement statement=init_prepare_statement(sql_statement);
		try {
			statement.setBlob(1,is);
			statement.setString(2,item.getValue());
			statement.executeUpdate();
			statement.close();
		}catch(SQLException se1) {
			se1.printStackTrace();
		}
		return 1;
	}
	public static byte[] readBlobFromResultSet(ResultSet rs,String blobColumnName,int maxSize) {
		byte[] ret=null;
		try {
			rs.next();
			Blob cache=rs.getBlob(blobColumnName);
			long length=cache.length();
			ret=new byte[(int) length];
			InputStream is=cache.getBinaryStream();
			is.read(ret);
		is.close();
		}catch(SQLException se1) {
			se1.printStackTrace();
		}catch(IOException ie1) {
			ie1.printStackTrace();
		}
		return ret;
	}
	public static byte[] decodeBase64(String input) {
		byte[] ret=null;
		ret =Base64.getDecoder().decode(input.toString().replace(" ", "+"));
		return ret;
	}
	public static String encodeBase64(byte[] input) {
		String ret=Base64.getEncoder().encodeToString(input);
		return ret;
	}
}
