package server.widgets.bean;

public class WidgetBean {
	
	private int id;
	private String creator_name;
	private String widget_name;
	
	private FileBean files;
	
	private Dependencies dependencies;
	
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

	public String getCreatorName() {
		return creator_name;
	}

	public void setCreatorName(String creator_name) {
		this.creator_name = creator_name;
	}

	public String getWidgetName() {
		return widget_name;
	}

	public void setWidgetName(String widget_name) {
		this.widget_name = widget_name;
	}

	public FileBean getFiles() {
		return files;
	}

	public void setFiles(FileBean files) {
		this.files = files;
	}

	public Dependencies getDependencies() {
		return dependencies;
	}

	public void setDependencies(Dependencies dependencies) {
		this.dependencies = dependencies;
	}
	
	

}
