package org.king.entity;



public class PageInfo {
	
	private int currentPage;
	private int pageSize;
	private int totalRows;
	private int totalPages;
	
	
	//-----------------------------------------
	
	
	public PageInfo(){
		
	}
	
	public PageInfo(int currentPage,int pageSize,int totalRows,int totalPages){
		
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalRows = totalRows;
		this.totalPages = totalPages;
	}
	
	//-----------------------------------------
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	
	//-----------------------------------------
	
	

}
