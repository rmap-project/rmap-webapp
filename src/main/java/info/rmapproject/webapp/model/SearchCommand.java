package info.rmapproject.webapp.model;


public class SearchCommand {
	
	//@NotEmpty(message="A URI must be provided")
	private String search;
	
	public String getSearch() {
		return search;
	}
	
	public void setSearch(String search)	{
		this.search = search;
	}
	
}
