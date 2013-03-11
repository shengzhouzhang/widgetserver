package server.widgets.bean;

/** 
 *  This is the class to store codes information.
 *
 * @author Steven Zhang
 * @version 1.0 February 24, 2013.
 */
public class FileBean {

	private String javascript;		// file of JavaScript
	private String html;			// file of HTML
	private String css;				// file of CSS
	private String container;		// file of Container
	
	public String getJavascript() {
		return javascript;
	}
	public void setJavascript(String javascript) {
		this.javascript = javascript;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getCss() {
		return css;
	}
	public void setCss(String css) {
		this.css = css;
	}
	public String getContainer() {
		return container;
	}
	public void setContainer(String container) {
		this.container = container;
	}
	
	
}
