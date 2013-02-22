package server.widgets.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.widgets.bean.*;

/** 
 *  This is the class to access database of widgets.
 *  The operations contain create a widget, 
 *  retrieve the full information of a widget in database, 
 *  list all widgets in database,
 *  update a widget's information.
 *
 * @author Steven Zhang
 * @version 0.1 December 28, 2012.
 */
public class WidgetDao {
	
	/*
	 * This is the method to add a new widget into database.
	 * 
	 * @param creator_name	the name of user creates the widget.
	 * @param widget_name	the name of the widget.
	 * 
	 */
	public WidgetBean Create(WidgetBean widget) throws ClassNotFoundException, SQLException {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		WidgetBean item = null;
		
		try{
			
			conn = Connector.getConnction();
			String sql ="SELECT * FROM create_widget(?,?,?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, widget.getCreator_name());
			pstmt.setString(2, widget.getWidget_name());
			pstmt.setString(3, widget.getService_name());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				item = new WidgetBean();
				item.setId(rs.getInt("wid"));
				item.setCreator_name(rs.getString("username"));
				item.setWidget_name(rs.getString("widget_name"));
				item.setService_name(rs.getString("service_name"));
			}
			
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			if (conn != null && pstmt != null) {
				conn.close();
				pstmt.close();
			}
		}
		
		return item;
	}
	
	/*
	 * This is the method to retrieve full info of a widget.
	 * 
	 * @param creator_name	the name of user creates the widget.
	 * @param widget_name	the name of the widget.
	 * @return				the java bean of the widget.
	 */
	public WidgetBean Find(String creator_name, String widget_name) throws ClassNotFoundException, SQLException{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		WidgetBean item = null;
		
		try{
			
			conn = Connector.getConnction();
			String sql ="Select * from find_widget(?,?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, creator_name);
			pstmt.setString(2, widget_name);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				item = new WidgetBean();
				item.setId(rs.getInt("wid"));
				item.setCreator_name(rs.getString("username"));
				item.setWidget_name(rs.getString("widget_name"));
				item.setService_name(rs.getString("service_name"));
			}else{
				System.out.println(creator_name);
				System.out.println(widget_name);
				System.out.println("widget not found");
			}
			
		}catch(SQLException e){
			throw e;
		}finally{
			if (conn != null && pstmt != null) {
				conn.close();
				pstmt.close();
			}
		}
		
		return item;
	}
	
	/*
	 * This is the method to list all widgets.
	 * 
	 * @return				a list of java beans.
	 */
	public List<WidgetBean> List(String service) throws ClassNotFoundException, SQLException{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<WidgetBean> items = new ArrayList<WidgetBean>();
		
		try{
			
			conn = Connector.getConnction();
			String sql ="Select * from list_widgets(?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, service);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				WidgetBean item = new WidgetBean();
				item.setId(rs.getInt("wid"));
				item.setCreator_name(rs.getString("creator"));
				item.setWidget_name(rs.getString("widget_name"));
				items.add(item);
			}
			
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}finally{
			if (conn != null && pstmt != null) {
				conn.close();
				pstmt.close();
			}
		}
		
		return items;	
	}
	
	/*
	 * This is the method to update widget's info.
	 * 
	 * @param creator_name	the name of user creates the widget.
	 * @param widget_name	the name of the widget.
	 * @return				
	 */
	public String Update(WidgetBean widget) throws ClassNotFoundException, SQLException{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			
			conn = Connector.getConnction();
			String sql ="select * from update_widget(?,?,?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, widget.getCreator_name());
			pstmt.setString(2, widget.getWidget_name());
			pstmt.setString(3, widget.getService_name());
			pstmt.execute();
			
		}catch(SQLException e){
			conn.rollback();
			throw e;
		}finally{
			if (conn != null && pstmt != null) {
				conn.close();
				pstmt.close();
			}
		}
		
		return null;
	}
}
