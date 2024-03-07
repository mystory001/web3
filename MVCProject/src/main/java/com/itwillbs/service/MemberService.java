package com.itwillbs.service;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.itwill.dao.MemberDAO;
import com.itwillbs.domain.MemberDTO;

public class MemberService {

	MemberDAO memberDAO = null; 
			
	// 리턴할형없음  insertMember(HttpServletRequest request) 메서드 정의
	public void insertMember(HttpServletRequest request) {
		System.out.println("MemberService insertMember()");
		// insertPro.jsp 내용 넣기
		try {
			request.setCharacterEncoding("utf-8");
			// request 에서 id,pass,name 태그(파라미터)값 가져와서 => 변수에 저장
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");

			// 가입날짜 => 시스템의 날짜 설정
			Timestamp date = new Timestamp(System.currentTimeMillis());
			
			// 패키지 com.itwillbs.domain 파일이름 MemberDTO
			// 자바파일 MemberDTO 객체생성 => 기억장소 할당
			MemberDTO memberDTO = new MemberDTO();
			// 멤버변수에 request에 가져온값을 set 메서드 호출해서 저장
			memberDTO.setId(id);
			memberDTO.setPw(pw);
			memberDTO.setName(name);
			memberDTO.setDate(date);
			
			// 패키지 com.itwillbs.dao 파일이름 MemberDAO
			// 자바파일 MemberDAO 객체생성 => 기억장소 할당
			memberDAO = new MemberDAO();
			// insertMember() 메서드 호출
			memberDAO.insertMember(memberDTO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//insertMember()
	
	//boolean 형리턴 userCheck(request)메서드호출
	public boolean userCheck(HttpServletRequest request) {
		System.out.println("MemberService userCheck()");
		boolean result = false;
		try {
			//request 에서 id,pass 태그(파라미터)값 가져와서 => 변수에 저장
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			//자바파일 MemberDAO 객체생성 => 기억장소 할당
			memberDAO = new MemberDAO();
			//result = MemberDAO의 기억장소주소.userCheck(id,pass) 메서드 호출
			result = memberDAO.userCheck(id, pw);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}//userCheck()
	
	// => 리턴할형 MemberDTO memberDTO = getMember(id) 메서드 호출
	public MemberDTO getMember(String id) {
		System.out.println("MemberService getMember()");
		MemberDTO memberDTO = null;
		try {
			//자바파일 MemberDAO 객체생성 => 기억장소 할당
			memberDAO = new MemberDAO();
			// MemberDTO memberDTO = MemberDAO의 기억장소주소.getMember(id) 메서드 호출
			memberDTO = memberDAO.getMember(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberDTO;
	}//getMember()
	
	// => 리턴할형없음 updateMember(request) 메서드 호출
	public void updateMember(HttpServletRequest request) {
		System.out.println("MemberService updateMember()");
		try {
			// request 에서 id,pass,name 태그(파라미터)값 가져와서 => 변수에 저장
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			// 자바파일 MemberDTO 객체생성 => 기억장소 할당
			MemberDTO memberDTO = new MemberDTO();
			// 멤버변수에 request에 가져온값을 set 메서드 호출해서 저장
			memberDTO.setId(id);
			memberDTO.setPw(pw);
			memberDTO.setName(name);
			// 자바파일 MemberDAO 객체생성 => 기억장소 할당
			memberDAO = new MemberDAO();
			// updateMember(memberDTO) 메서드 호출
			memberDAO.updateMember(memberDTO);			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//updateMember()
	
	// => 리턴할형없음 deleteMember(request) 메서드 호출
	public void deleteMember(HttpServletRequest request) {
		System.out.println("MemberService deleteMember()");
		try {
			// request 에서 id 태그(파라미터)값 가져와서 => 변수에 저장
			String id = request.getParameter("id");
			// 자바파일 MemberDAO 객체생성 => 기억장소 할당
			memberDAO = new MemberDAO();
			// deleteMember(id) 메서드 호출
			memberDAO.deleteMember(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//deleteMember()
	
	 // ArrayList<MemberDTO> 리턴할형 memberList = getMemberList() 메서드 호출
	public ArrayList<MemberDTO> getMemberList() {
		ArrayList<MemberDTO> memberList = null;
		try {
			// MemberDAO 객체생성
			memberDAO = new MemberDAO();
			//  memberList = getMemberList() 메서드 호출
			memberList = memberDAO.getMemberList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberList;
	}//getMemberList()
	
}//MemberService 클래스

