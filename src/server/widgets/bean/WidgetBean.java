package server.widgets.bean;

public class WidgetBean {
	
	private int id;
	private String creator_name;
	private String widget_name;
	
	private FileBean files;
	
	private String service_name;
	
	private ConfigurationBean configurations;

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
