package server.widgets.bean;

/** 
 *  This is the class to store widget information.
 *
 * @author Steven Zhang
 * @version 1.0 February 24, 2013.
 */
public class WidgetBean {
	
	private int id;								// id of widget
	private String creator_name;				// name of widget's creator
	private String widget_name;					// name of widget
	
	private FileBean files;						// HTML, JavaScript, CSS, and Container files
	
	private String service_name;				// the name of service. e.g. eBay
	
	private ConfigurationBean configurations;	// configurations of widget

	public WidgetBean(){
	}

	public WidgetBean(String name, FileBean files, String creator){
		this.widget_name = name;
		this.files = files;
		this.creator_name = creator;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreator_name() {
		return creator_name;
	}

	public void setCreator_name(String creator_name) {
		this.creator_name = creator_name;
	}

	public String getWidget_name() {
		return widget_name;
	}

	public void setWidget_name(String widget_name) {
		this.widget_name = widget_name;
	}

	public FileBean getFiles() {
		return files;
	}

	public void setFiles(FileBean files) {
		this.files = files;
	}

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public ConfigurationBean getConfiguration() {
		return configurations;
	}

	public void setConfiguration(ConfigurationBean configuration) {
		this.configurations = configuration;
	}

}
