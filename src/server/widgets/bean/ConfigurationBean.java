package server.widgets.bean;

public class ConfigurationBean {

	private String html_link;
	private String javascript_link;
	private String css_link;
	private String container_link;
	
	private Boolean load_from_html_link;
	private Boolean load_from_javascript_link;
	private Boolean load_from_css_link;
	private Boolean load_from_container_link;
	
	// github configuration
	private Boolean save_to_github;
	private Boolean load_from_github;
	private String owner_name;
	private String repo_name;
	private String html_path;
	private String javascript_path;
	private String css_path;
	private String container_path;
	private String github_account;
	private String github_password;
	public String getHtml_link() {
		return html_link;
	}
	public void setHtml_link(String html_link) {
		this.html_link = html_link;
	}
	public String getJavascript_link() {
		return javascript_link;
	}
	public void setJavascript_link(String javascript_link) {
		this.javascript_link = javascript_link;
	}
	public String getCss_link() {
		return css_link;
	}
	public void setCss_link(String css_link) {
		this.css_link = css_link;
	}
	public String getContainer_link() {
		return container_link;
	}
	public void setContainer_link(String container_link) {
		this.container_link = container_link;
	}
	public Boolean getLoad_from_html_link() {
		return load_from_html_link;
	}
	public void setLoad_from_html_link(Boolean load_from_html_link) {
		this.load_from_html_link = load_from_html_link;
	}
	public Boolean getLoad_from_javascript_link() {
		return load_from_javascript_link;
	}
	public void setLoad_from_javascript_link(Boolean load_from_javascript_link) {
		this.load_from_javascript_link = load_from_javascript_link;
	}
	public Boolean getLoad_from_css_link() {
		return load_from_css_link;
	}
	public void setLoad_from_css_link(Boolean load_from_css_link) {
		this.load_from_css_link = load_from_css_link;
	}
	public Boolean getLoad_from_container_link() {
		return load_from_container_link;
	}
	public void setLoad_from_container_link(Boolean load_from_container_link) {
		this.load_from_container_link = load_from_container_link;
	}
	public Boolean getSave_to_github() {
		return save_to_github;
	}
	public void setSave_to_github(Boolean save_to_github) {
		this.save_to_github = save_to_github;
	}
	public Boolean getLoad_from_github() {
		return load_from_github;
	}
	public void setLoad_from_github(Boolean load_from_github) {
		this.load_from_github = load_from_github;
	}
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	public String getRepo_name() {
		return repo_name;
	}
	public void setRepo_name(String repo_name) {
		this.repo_name = repo_name;
	}
	public String getHtml_path() {
		return html_path;
	}
	public void setHtml_path(String html_path) {
		this.html_path = html_path;
	}
	public String getJavascript_path() {
		return javascript_path;
	}
	public void setJavascript_path(String javascript_path) {
		this.javascript_path = javascript_path;
	}
	public String getCss_path() {
		return css_path;
	}
	public void setCss_path(String css_path) {
		this.css_path = css_path;
	}
	public String getContainer_path() {
		return container_path;
	}
	public void setContainer_path(String container_path) {
		this.container_path = container_path;
	}
	public String getGithub_account() {
		return github_account;
	}
	public void setGithub_account(String github_account) {
		this.github_account = github_account;
	}
	public String getGithub_password() {
		return github_password;
	}
	public void setGithub_password(String github_password) {
		this.github_password = github_password;
	}
	
	
}
