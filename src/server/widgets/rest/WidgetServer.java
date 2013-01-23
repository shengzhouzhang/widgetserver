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

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

import server.widgets.bean.*;
import server.widgets.dao.*;
import unsw.manager.code.interfaces.*;
import unsw.manager.code.manager.*;

@Path("/widgets")
public class WidgetServer {

	/*
	 * Create a widget
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response Create(
			JSONObject widget) {
		
		System.out.println("Creating");
		Response response = null;
		WidgetDao dao = null;
		GitAPI api = null;

		try {
			
			String email = "";
			
			Gson gson = new Gson();
			
			WidgetBean widgetBean = new WidgetBean();
			
			widgetBean.setCreatorName(gson.fromJson(widget.getString("creator_name"), String.class));
			widgetBean.setWidgetName(gson.fromJson(widget.getString("widget_name"), String.class));
//			widgetBean.setFiles(gson.fromJson(widget.getString("files"), FileBean.class));
			widgetBean.setDependencies(gson.fromJson(widget.getString("dependencies"), Dependencies.class));
			
			String folder = widgetBean.getCreatorName() + "/" + widgetBean.getWidgetName();
			
			dao = new WidgetDao();
			
			dao.Create(widgetBean);
			
			api = new JGitAgency();
			
			api.init_repository(folder);
			
			System.out.println(widget.getString("files"));
			
			api.commit(widgetBean.getCreatorName(), email, widget.getString("files"), "Initial Commit");
			
			api.close_repository();
			
			response = Response.ok("Created").build();
		
		} catch (Exception e) {
			e.printStackTrace();
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return response;
	}
	
	/*
	 * Obtain a list of widget's name
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response List() {
		
		System.out.println("Listing");
		Response response = null;
		WidgetDao dao = null;
		List<WidgetBean> widgetList = null;
		
		try {
			
			dao = new WidgetDao();
			
			widgetList = dao.List();
			JSONArray ja = new JSONArray();
			
			for (int i = 0; i < widgetList.size(); i++) {
				JSONObject jo = new JSONObject();
				jo.put("id", widgetList.get(i).getId());
				jo.put("creator_name", widgetList.get(i).getCreatorName());
				jo.put("widget_name",  widgetList.get(i).getWidgetName());
				ja.put(jo);
			}
			response = Response.ok(ja.toString()).build();
		
		} catch (Exception e) {
			e.printStackTrace();
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return response;
	}
	
	/*
	 * Obtain all detail of a widget
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
			
			System.out.println(json_file);
			
			api.close_repository();
			
			Gson gson = new Gson();
			FileBean files = gson.fromJson(json_file, FileBean.class);
			
			widget.setFiles(files);
			
			System.out.println(gson.toJson(widget));
			
			response = Response.ok(gson.toJson(widget)).build();
		
		} catch (Exception e) {
			e.printStackTrace();
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return response;
	}
	
	
	/*
	 * Obtain a file of the widget
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
				file = files.getTemplates().get(0);
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
	
	/*
	 * Obtain a file of the widget
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
//		GitAPI api = null;
		
		try {
			
//			api = new JGitAgency();
			
			String path = creator_name + "/" + widget_name;
			
//			api.open_repository(folder);
//			String json_file = api.readLatest();
//			api.close_repository();
//			
//			Gson gson = new Gson();
//			FileBean files = gson.fromJson(json_file, FileBean.class);

			
			StringBuffer stream = new StringBuffer();
			
			stream.append("<!DOCTYPE html>\n");
			stream.append("<html>\n");
			stream.append("<head>\n");
			stream.append("<title>Preview</title>\n");
			//append css
			stream.append(add_link_resource("http://twitter.github.com/bootstrap/assets/css/bootstrap.css"));
			stream.append(add_link_resource("http://twitter.github.com/bootstrap/assets/css/bootstrap-responsive.css"));
			stream.append(add_link_resource("http://localhost:8080/widgetserver/widgets/" + path + "/style.css"));
			stream.append("</head>\n");
			stream.append("<body>\n");
			stream.append("<div id=\"example-widget-container\"></div>\n");
			
			//append js
			stream.append(add_script_resource("http://localhost:8080/widget-backbone-boilerplate/widgetapp/resources/jquery/jquery-1.8.3.js"));
			stream.append(add_script_resource("http://localhost:8080/widget-backbone-boilerplate/widgetapp/resources/underscore/underscore.js"));
			stream.append(add_script_resource("http://localhost:8080/widget-backbone-boilerplate/widgetapp/resources/backbone/backbone.js"));
			stream.append(add_script_resource("http://localhost:8080/widget-backbone-boilerplate/widgetapp/resources/application.js"));
			stream.append(add_script_resource("http://localhost:8080/widgetserver/widgets/" + path + "/widget.js"));
			stream.append(add_script_resource("http://localhost:8080/widgetserver/widgets/" + path + "/run.js"));
			
			stream.append("</body>\n");
			stream.append("</html>\n");
//			if(file_name.equals("js")){
//				file = files.getJavascript();
//				response = Response.ok(file, "application/x-javascript").build();
//			}else if(file_name.equals("templates")){
//				file = gson.toJson(files.getTemplates());
//				response = Response.ok(file, MediaType.APPLICATION_JSON).build();
//			}else if(file_name.equals("css")){
//				file = files.getCss();
//				response = Response.ok(file, "text/css").build();
//			}else if(file_name.equals("container")){
//				file = files.getContainer();
//				response = Response.ok(file, "application/x-javascript").build();
//			}else{
//				throw new Exception("NOT FOUND: " + file_name);
//			}
			
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
	
	/*
	 * Update the detail of a widget
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
			
			widgetBean.setCreatorName(gson.fromJson(widget.getString("creator_name"), String.class));
			widgetBean.setWidgetName(gson.fromJson(widget.getString("widget_name"), String.class));
			widgetBean.setDependencies(gson.fromJson(widget.getString("dependencies"), Dependencies.class));
			
			dao.Update(widgetBean);
			
			String folder = widgetBean.getCreatorName() + "/" + widgetBean.getWidgetName();
			
			api.open_repository(folder);
			api.commit(creator_name, "", widget.getString("files"), "Update Commit");
			api.close_repository();
			
			response = Response.ok("Updated").build();
		
		} catch (Exception e) {
			e.printStackTrace();
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return response;
	}
}
