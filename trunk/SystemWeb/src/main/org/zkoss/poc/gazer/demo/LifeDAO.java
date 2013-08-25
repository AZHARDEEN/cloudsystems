package org.zkoss.poc.gazer.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class LifeDAO {
	private String url = "jdbc:hsqldb:file:c:/hsqldb/life";

	private String user = "sa";

	private String pwd = "";
	
	public LifeDAO() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public List readAll(){
		Statement stmt = null;
		Connection conn = null;
		List lifeStatsList = new ArrayList();
		try {
			// get connection
			conn = DriverManager.getConnection(url, user, pwd);
			
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from life");
//			ResultSet rs = stmt.executeQuery("select * from life order by country");
			
			
			// fetch all events from database
			Life lifeStats;
			while (rs.next()) {
				lifeStats = new Life();
				lifeStats.setCountry(rs.getString("country"));
				lifeStats.setFemaleLife(rs.getInt(2));			
				lifeStats.setMaleLife(rs.getInt(3));
				lifeStatsList.add(lifeStats);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return lifeStatsList;
	}
	
}
