package server.widgets.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

import server.widgets.bean.*;
import server.widgets.dao.*;
import unsw.manager.code.interfaces.*;
import unsw.manager.code.manager.*;

/** 
 *  This is the class for rest service.
 *
 * @author Steven Zhang
 * @version 1.0 February 24, 2013.
 */
@Path("/widgets")
public class WidgetServer {

	/**
	 * Create a widget
	 * <p>
	 * /widgets/	POST
	 * 
	 * @param widget a JSON string of widget
	 * @return a JSON string of widget with id in database.
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response Create(
			JSONObject widget) {
		
		System.out.println("Creating");
		Response response = null;
		WidgetDao dao = null;
		GitAPI api = null;
		WidgetBean send = null;

		try {
			
			String email = "";
			
			WidgetBean widgetBean = new WidgetBean();
			
			Gson gson = new Gson();
			
			widgetBean.setCreator_name(widget.getString("creator_name"));
			widgetBean.setWidget_name(widget.getString("widget_name"));
			widgetBean.setService_name(widget.getString("service_name"));
			
			ConfigurationBean config = gson.fromJson(widget.getString("configurations"), ConfigurationBean.class);
			
			widgetBean.setConfiguration(config);			
			
			String folder = widgetBean.getCreator_name() + "/" + widgetBean.getWidget_name();
			
			dao = new WidgetDao();
			
			send = dao.Create(widgetBean);
			
			api = new JGitAgency();
			
			api.init_repository(folder);
			
			
			api.commit(widgetBean.getCreator_name(), email, widget.getString("files"), "Initial Commit");
			
			api.close_repository();
			
			System.out.println(gson.toJson(send));
			
			response = Response.ok(gson.toJson(send)).build();
		
		} catch (Exception e) {
			e.printStackTrace();
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return response;
	}
	
	/**
	 * List widgets in a service
	 * <p>
	 * /widgets/:serviceName/	GET
	 * 
	 * @param service_name a string of service name
	 * @return the JSON list of widgets in the service
	 */
	@GET
	@Path("{service_name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response List(
			@PathParam("service_name") String service_name
			) {
		
		System.out.println("Listing");

		Response response = null;
		WidgetDao dao = null;
		List<WidgetBean> widgetList = null;
		
		try {
			
			dao = new WidgetDao();
			
			widgetList = dao.List(service_name);
			
			Gson gson = new Gson();
			
			String json = gson.toJson(widgetList);  
			
			response = Response.ok(json).build();
		
		} catch (Exception e) {
			e.printStackTrace();
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return response;
	}
	
	/**
	 * Find a widget by names
	 * <p>
	 * /widgets/:creatorName/:widgetName	GET
	 * 
	 * @param creator_name the string of creator name
	 * @param widget_name the string of widget name
	 * 
	 * @return the JSON string of the widget
	 */
	@GET
	@Path("{creator_name}/{widget_name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response Find(
			@PathParam("creator_name") String creator_name, 
			@PathParam("widget_name") String widget_name) {
		
		System.out.println("Finding");
		
		Response response = null;
		WidgetDao dao = null;
		WidgetBean widget = null;
		GitAPI api = null;
		
		try {
			
			dao = new WidgetDao();
			api = new JGitAgency();
			
			widget = dao.Find(creator_name, widget_name);
			
			String folder = creator_name + "/" + widget_name;
			
			api.open_repository(folder);
			String json_file = api.readLatest();
			
			api.close_repository();
			
			Gson gson = new Gson();
			FileBean files = gson.fromJson(json_file, FileBean.class);
			
			widget.setFiles(files);
			
			response = Response.ok(gson.toJson(widget)).build();
		
		} catch (Exception e) {
			e.printStackTrace();
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return response;
	}
	

	/**
	 * Obtain a file of widget
	 * <p>
	 * /widgets/:creatorName/:widgetName/:fileName	GET
	 * 
	 * File Name Attribute: widget.js, template.html, style.css, and container.js
	 * 
	 * @param creator_name the string of creator name
	 * @param widget_name the string of widget name
	 * @param file_name the string of file name
	 * 
	 * @return the string of the file content
	 * 
	 */
	@GET
	@Path("{creator_name}/{widget_name}/{file_name}")
	public Response Retrieve(
			@PathParam("creator_name") String creator_name, 
			@PathParam("widget_name") String widget_name,
			@PathParam("file_name") String file_name) {
		
		System.out.println("Retrieving");
		
		Response response = null;
		GitAPI api = null;
		
		try {
			
			api = new JGitAgency();
			
			String folder = creator_name + "/" + widget_name;
			
			api.open_repository(folder);
			String json_file = api.readLatest();
			api.close_repository();
			
			Gson gson = new Gson();
			FileBean files = gson.fromJson(json_file, FileBean.class);
			
			String file = null;
			
			if(file_name.equals("widget.js")){
				file = files.getJavascript();
				response = Response.ok(file, "application/x-javascript").build();
			}else if(file_name.equals("template.html")){
				file = files.getHtml();
				response = Response.ok(file, MediaType.TEXT_HTML).build();
			}else if(file_name.equals("style.css")){
				file = files.getCss();
				response = Response.ok(file, "text/css").build();
			}else if(file_name.equals("run.js")){
				file = files.getContainer();
				response = Response.ok(file, "application/x-javascript").build();
			}else{
				throw new Exception("NOT FOUND: " + file_name);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return response;
	}
	
	/**
	 * Obtain a HTML code of Preview
	 * <p>
	 * /widgets/:creatorName/:widgetName/preview	GET
	 * 
	 * @param creator_name the string of creator name
	 * @param widget_name the string of widget name
	 * 
	 * @return HTML code of preview
	 * 
	 */
	@GET
	@Path("{creator_name}/{widget_name}/preview")
	@Produces(MediaType.TEXT_HTML)
	public Response Preview(
			@PathParam("creator_name") String creator_name, 
			@PathParam("widget_name") String widget_name,
			@PathParam("file_name") String file_name) {
		
		System.out.println("preview");
		
		Response response = null;
		
		try {
			
			String path = creator_name + "/" + widget_name;

			
			StringBuffer stream = new StringBuffer();
			
			stream.append("<!DOCTYPE html>\n");
			stream.append("<html>\n");
			stream.append("<head>\n");
			stream.append("<title>Preview</title>\n");
			//append css
			stream.append(add_link_resource("http://localhost:8080/widgeteditor/resources/bootstrap/css/bootstrap.min.css"));
			stream.append(add_link_resource("http://localhost:8080/widgeteditor/resources/bootstrap/css/bootstrap-responsive.min.css"));
			stream.append(add_script_resource("http://localhost:8080/widgeteditor/resources/jquery.js"));
			stream.append(add_script_resource("http://localhost:8080/widgeteditor/resources/underscore-min.js"));
			stream.append(add_script_resource("http://localhost:8080/widgeteditor/resources/backbone-min.js"));
			stream.append(add_script_resource("http://localhost:8080/widgeteditor/resources/application.js"));
			stream.append(add_script_resource("http://localhost:8080/widgetserver/widgets/" + path + "/widget.js"));
			stream.append(add_script_resource("http://localhost:8080/widgetserver/widgets/" + path + "/run.js"));
			stream.append("</head>\n");
			stream.append("<body>\n");
			stream.append("<div id=\"example-widget-container\"></div>\n");
			stream.append("<script>\n");
			stream.append("</script>\n");
			//append js
			
			stream.append("</body>\n");
			stream.append("</html>\n");
			
			response = Response.ok(stream.toString()).build();
		
		} catch (Exception e) {
			e.printStackTrace();
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return response;
	}
	
	
	private String add_link_resource(String src){
		
		StringBuffer stream = new StringBuffer();
		
		stream.append("<link href=\"");
		stream.append(src);
		stream.append("\" rel=\"stylesheet\">\n");
		
		return stream.toString();
	}
	
	private String add_script_resource(String src){
		
		StringBuffer stream = new StringBuffer();
		
		stream.append("<script src=\"");
		stream.append(src);
		stream.append("\"></script>\n");
		
		return stream.toString();
	}
	
	/**
	 * Update a widget
	 * <p>
	 * /widgets/:creatorName/:widgetName	PUT
	 * 
	 * @param creator_name the string of creator name
	 * @param widget_name the string of widget name
	 * 
	 * @return a JSON string of the widget
	 * 
	 */
	@PUT
	@Path("{creator_name}/{widget_name}")
	public Response Update(
			@PathParam("creator_name") String creator_name, 
			@PathParam("widget_name") String widget_name, 
			JSONObject widget) {
		
		System.out.println("Updating");
		
		Response response = null;
		WidgetDao dao = null;
		GitAPI api = null;
		
		try {
			
			dao = new WidgetDao();
			api = new JGitAgency();
			Gson gson = new Gson();
			
			WidgetBean widgetBean = new WidgetBean();
			
			widgetBean.setCreator_name(widget.getString("creator_name"));
			widgetBean.setWidget_name(widget.getString("widget_name"));
			widgetBean.setService_name(widget.getString("service_name"));
			
			ConfigurationBean config = gson.fromJson(widget.getString("configurations"), ConfigurationBean.class);
			widgetBean.setConfiguration(config);
			
			dao.Update(widgetBean);
			
			String folder = widgetBean.getCreator_name() + "/" + widgetBean.getWidget_name();
			
			api.open_repository(folder);

			api.commit(creator_name, "", widget.getString("files"), "Update Commit");
			api.close_repository();
			
			response = Response.ok(widget.toString()).build();
		
		} catch (Exception e) {
			e.printStackTrace();
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return response;
	}
}
