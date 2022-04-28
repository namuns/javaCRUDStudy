package com.mis.domain;

public class Criteria {
	
	private int page; //시작할 페이지

	private int perPageNum; //화면에 보여줄 게시글 수
	
	private int startPage; // MyBatis에서 사용할 시작 페이지
	
	//초기데이터를 위한 생성자
	
	public Criteria() {
		
		this.page = 1;
		
		this.perPageNum = 10;
		
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		
		//페이징에 대한 예외처리
		
		if(page <=0) {
			this.page = 1;
			return;
		}
		this.page = page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	
	// myBatis에서 사용할 method
	public int getPageEnd() {
		
		return this.startPage + 9;
	}
	
	public int getPageStart() {
		
	//페이징 시작 row 계산 1페이지면 1, 2페이지면 11, 3페이지면 21, 4페이지면 31
		
		this.startPage = (this.page * this.perPageNum) - 9;
		return this.startPage;
	}

	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + ", startPage=" + startPage + "]";
	}
	
	

}
