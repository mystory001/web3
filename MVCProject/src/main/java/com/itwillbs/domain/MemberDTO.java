package com.itwillbs.domain;

import java.sql.Timestamp;

public class MemberDTO {
	// 데이터 은닉과 캡슐화
	//멤버변수 => 변수를 아무나 접근못하게 막아줌 => 데이터 은닉
	private String id;
	private String pw;
	private String name;
	private Timestamp date;
	
	// 데이터를 접근하기위한 setter, getter   public 메서드 정의
	// alt shift s => r
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
}
