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
 *  The operations contain:
 *  create a widget, 
 *  find a widget by name, 
 *  list widgets in a service,
 *  update a widget.
 *
 * @author Steven Zhang
 * @version 1.0 February 24, 2013.
 */
public class WidgetDao {
	
	/**
	 * This is the method to add a new widget into database.
	 * 
	 * @param widget an instance of WidgetBean
	 * 
	 * @return an instance of WidgetBean with id
	 */
	public WidgetBean Create(WidgetBean widget) throws ClassNotFoundException, SQLException {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			conn = Connector.getConnction();
			String sql ="SELECT * FROM create_widget(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			pstmt = conn.prepareStatement(sql);
			
			// widget basic information
			pstmt.setString(1, widget.getCreator_name());
			pstmt.setString(2, widget.getWidget_name());
			pstmt.setString(3, widget.getService_name());
			
			
			ConfigurationBean config = widget.getConfiguration();
			// configuration information
			pstmt.setString(4, config.getHtml_link());
			pstmt.setString(5, config.getJavascript_link());
			pstmt.setString(6, config.getCss_link());
			pstmt.setString(7, config.getContainer_link());
			pstmt.setBoolean(8, config.getLoad_from_html_link());
			pstmt.setBoolean(9, config.getLoad_from_javascript_link());
			pstmt.setBoolean(10, config.getLoad_from_css_link());
			pstmt.setBoolean(11, config.getLoad_from_container_link());
			pstmt.setBoolean(12, config.getSave_to_github());
			pstmt.setBoolean(13, config.getLoad_from_github());
			pstmt.setString(14, config.getOwner_name());
			pstmt.setString(15, config.getRepo_name());
			pstmt.setString(16, config.getHtml_path());
			pstmt.setString(17, config.getJavascript_path());
			pstmt.setString(18, config.getCss_path());
			pstmt.setString(19, config.getContainer_path());
			pstmt.setString(20, config.getGithub_account());
			pstmt.setString(21, config.getGithub_password());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				widget.setId(rs.getInt("wid"));
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
		
		return widget;
	}
	
	/**
	 * This is the method to retrieve full info of a widget.
	 * 
	 * @param creator_name	the name of user creates the widget.
	 * @param widget_name	the name of the widget.
	 * @return				an instance of WidgetBean.
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
				
				ConfigurationBean config = new ConfigurationBean();
				
				config.setHtml_link(rs.getString("html_link"));
				config.setJavascript_link(rs.getString("javascript_link"));
				config.setCss_link(rs.getString("css_link"));
				config.setContainer_link(rs.getString("container_link"));
				
				config.setLoad_from_html_link(rs.getBoolean("load_from_html_link"));
				config.setLoad_from_javascript_link(rs.getBoolean("load_from_javascript_link"));
				config.setLoad_from_css_link(rs.getBoolean("load_from_css_link"));
				config.setLoad_from_container_link(rs.getBoolean("load_from_container_link"));
				
				config.setSave_to_github(rs.getBoolean("save_to_github"));
				config.setLoad_from_github(rs.getBoolean("load_from_github"));
				config.setOwner_name(rs.getString("owner_name"));
				config.setRepo_name(rs.getString("repo_name"));
				config.setHtml_path(rs.getString("html_path"));
				config.setJavascript_path(rs.getString("javascript_path"));
				config.setCss_path(rs.getString("css_path"));
				config.setContainer_path(rs.getString("container_path"));
				config.setGithub_account(rs.getString("github_account"));
				config.setGithub_password(rs.getString("github_password"));
				
				item.setConfiguration(config);
				
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
	
	/**
	 * This is the method to list widgets of a service.
	 * 
	 * @param service the name of service, e.g. eBay, Facebook
	 * @return	a list of WidgetBeans.
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
	
	/**
	 * This is the method to update widget.
	 * 
	 * @param widget an instance of WidgetBean.
	 * 				
	 */
	public void Update(WidgetBean widget) throws ClassNotFoundException, SQLException{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			
			conn = Connector.getConnction();
			String sql ="select * from update_widget(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			pstmt = conn.prepareStatement(sql);
			
			// widget basic information
			pstmt.setString(1, widget.getCreator_name());
			pstmt.setString(2, widget.getWidget_name());
			pstmt.setString(3, widget.getService_name());
			
			ConfigurationBean config = widget.getConfiguration();
			// configuration information
			pstmt.setString(4, config.getHtml_link());
			pstmt.setString(5, config.getJavascript_link());
			pstmt.setString(6, config.getCss_link());
			pstmt.setString(7, config.getContainer_link());
			pstmt.setBoolean(8, config.getLoad_from_html_link());
			pstmt.setBoolean(9, config.getLoad_from_javascript_link());
			pstmt.setBoolean(10, config.getLoad_from_css_link());
			pstmt.setBoolean(11, config.getLoad_from_container_link());
			pstmt.setBoolean(12, config.getSave_to_github());
			pstmt.setBoolean(13, config.getLoad_from_github());
			pstmt.setString(14, config.getOwner_name());
			pstmt.setString(15, config.getRepo_name());
			pstmt.setString(16, config.getHtml_path());
			pstmt.setString(17, config.getJavascript_path());
			pstmt.setString(18, config.getCss_path());
			pstmt.setString(19, config.getContainer_path());
			pstmt.setString(20, config.getGithub_account());
			pstmt.setString(21, config.getGithub_password());
			pstmt.execute();
			
		}catch(SQLException e){
			throw e;
		}finally{
			if (conn != null && pstmt != null) {
				conn.close();
				pstmt.close();
			}
		}
		
		return;
	}
}
