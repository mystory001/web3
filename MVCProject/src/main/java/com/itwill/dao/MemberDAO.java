package com.itwill.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.itwillbs.domain.MemberDTO;

public class MemberDAO {

	// 멤버변수 
	// 생성자 => 생략하면 자동으로 기본생성자 만듬
	// 멤버함수(메서드 method)
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	
	// 1~2 단계 디비연결하는 getConnection()메서드 정의
	public Connection getConnection() {
		Connection con = null;
		try {
//			// 1단계 : 설치한 JDBC 프로그램 중에 Driver.class 자바실행 파일 불러오기
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			// 2단계 : 불러온 파일을 이용해서 데이터베이스 연결 => 연결정보를 저장
//			String dbUrl = "jdbc:mysql://localhost:3306/jspdb?serverTimezone=Asia/Seoul";
//			String dbUser = "root";
//			String dbpw = "1234";
//			con = DriverManager.getConnection(dbUrl, dbUser, dbpw);
			
			// p443 커넥션 풀(CP : 서버단에서 연결정보를 저장)
			// 데이터베이스와 연결된 Connection 객체를 미리 생성하여 풀(Pool) 저장
			// => 필요할때 마다 풀에 접근해서 Connection객체를 사용하고 끝나면 반환
			// => 장점 : 속도 향상, 디비연결정보를 한번만 수정해서 사용
			// 톰캣에서 제공하는 DBCP(DataBase Connection Pool) 사용
			// => 1) META-INF 폴더 context.xml 파일 만들고 커넥션 풀 디비연결자원 정의
			// => 2) DAO에서 디비연결자원의 이름을 불러서 사용 
			
//			import javax.naming.Context;
//			import javax.naming.InitialContext;
			Context init = new InitialContext();
			//import javax.sql.DataSource;
			//                                   자원위치/자원이름
			DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/Mysql");
			con = ds.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}//getConnection()
	
	// 내장객체 기억장소 해제 메서드
	public void dbClose() {
		//마무리 => 사용한 내장객체 기억장소 삭제(해제)
		if(rs != null) {
			try {
				rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if(con != null) {
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}//dbClose
	
	
	// insertMember() 메서드 정의
	// insertMember(String id,String pw,String name,Timestamp date)
	public void insertMember(MemberDTO memberDTO) {
		System.out.println("MemberDAO insertMember()");
//		System.out.println("전달받은 바구니 memberDTO의 주소 : " + memberDTO);
//		System.out.println("전달받은 아이디 : " + memberDTO.getId());
//		System.out.println("전달받은 비밀번호 : " + memberDTO.getpw());
//		System.out.println("전달받은 이름 : " + memberDTO.getName());
//		System.out.println("전달받은 가입날짜 : " + memberDTO.getDate());
		
		try {
			// 1단계 : 설치한 JDBC 프로그램 중에 Driver.class 자바실행 파일 불러오기
			// 2단계 : 불러온 파일을 이용해서 데이터베이스 연결 => 연결정보를 저장
			con = getConnection();
			// 3단계 : 연결정보를 이용해서 sql구문을 만들고 실행할수 있는 객체생성
			String sql = "insert into members(id,pw,name,date) values(?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberDTO.getId());
			pstmt.setString(2, memberDTO.getPw());
			pstmt.setString(3, memberDTO.getName());
			pstmt.setTimestamp(4, memberDTO.getDate());

			// 4단계 : sql구문 실행 (insert,update,delete)
			pstmt.executeUpdate();	
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//마무리
			dbClose();
		}
		
	}//insertMember()
	
	// MemberDAO에 리턴할형 boolean userCheck(String id,String pw) 메서드 정의
	public boolean userCheck(String id,String pw) {
		System.out.println("MemberDAO userCheck()");
		// 아이디 비밀번호 일치하면 => true 리턴
		// 아이디 비밀번호 틀리면  => false 리턴
		boolean result = false;
		try {
			//예외(에러) 발생할 가능성이 높은 명령
			// 1단계 : 설치한 JDBC 프로그램 중에 Driver.class 자바실행 파일 불러오기
			// 2단계 : 불러온 파일을 이용해서 데이터베이스 연결 => 연결정보를 저장
			con = getConnection();
			// 3단계 : 연결정보를 이용해서 sql구문을 만들고 실행할수 있는 객체생성
//			        select * from 테이블이름 where 조건열=값 and 조건열=값;
			String sql="select * from members where id=? and pw=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			// 4단계 : sql구문 실행 => 실행 후 결과 저장(select)=> ResultSet 내장객체 저장
			rs = pstmt.executeQuery();
			// 5단계 : 결과를 이용해서 출력, 다른곳에 저장(select)
			// if else 커서를 다음행 이동 => 리턴값 데이터 있으면 true => "아이디 비밀번호 일치" 출력
//			                            데이터 없으면 false => "아이디 비밀번호 틀림" 출력
			if(rs.next()){
				System.out.println("아이디 비밀번호 일치");
				result = true;
			}else{
				System.out.println("아이디 비밀번호 틀림");
				result = false;
			}			
		} catch (Exception e) {
			// 에러가 발생하면 잡아서 처리하는 작업 (에러메시지 출력)
			e.printStackTrace();
		}finally {
			// 마무리
			dbClose();
		}
		return result;
	}//userCheck()
	
	// MemberDAO에 리턴할형 MemberDTO  getMember(String id) 메서드 정의 
	public MemberDTO getMember(String id) {
		System.out.println("MemberDAO getMember()");
		// id,pw,name,date를 담은 => MemberDTO 하나를 리턴
		// MemberDTO 변수 선언
		MemberDTO memberDTO = null;
		try {
			// 1단계 : 설치한 JDBC 프로그램 중에 Driver.class 자바실행 파일 불러오기
			// 2단계 : 불러온 파일을 이용해서 데이터베이스 연결 => 연결정보를 저장
			con = getConnection();
			// 3단계 : 연결정보를 이용해서 sql구문을 만들고 실행할수 있는 객체생성
//			        select * from members where id=?
			String sql="select * from members where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			// 4단계 : sql구문 실행 => 실행 후 결과 저장(select)=> ResultSet 내장객체 저장
			rs = pstmt.executeQuery();
			// 5단계 : 결과를 이용해서 출력, 다른곳에 저장(select)
//			       if 커서를 다음행 이동 => 리턴값 데이터 있으면 true 
//			                       => id열,pw열,name열,date열 접근해서 출력
			if(rs.next()) {
				System.out.println("아이디 있음");
				// MemberDTO memberDTO  객체생성(기억장소 할당)
				memberDTO = new MemberDTO();
				System.out.println("아이디 있음 MemberDTO 바구니 주소 : "+memberDTO);
				memberDTO.setId(rs.getString("id"));
				memberDTO.setPw(rs.getString("pw"));
				memberDTO.setName(rs.getString("name"));
				memberDTO.setDate(rs.getTimestamp("date"));
			}else {
				System.out.println("아이디 없음");
				// MemberDTO memberDTO = null 설정
				memberDTO = null;
				System.out.println("아이디 없음 MemberDTO 바구니 주소 : "+memberDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//마무리
			dbClose();
		}
		return memberDTO;
	}//getMember()
	
	// 리턴값없음 updateMember(MemberDTO memberDTO) 메서드 정의
	public void updateMember(MemberDTO memberDTO) {
		try {
			// 1단계 : 설치한 JDBC 프로그램 중에 Driver.class 자바실행 파일 불러오기
			// 2단계 : 불러온 파일을 이용해서 데이터베이스 연결 => 연결정보를 저장
			con = getConnection();
		//  => 3단계 연결정보를 이용해서 sql구문을 만들고 실행할수 있는 객체생성
		//  => update 테이블이름 set 수정할열이름=수정값 where 조건열이름=값;
			String sql = "update members set name=? where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberDTO.getName());
			pstmt.setString(2, memberDTO.getId());
		//  => 4단계 : sql구문 실행 (insert,update,delete)	
			pstmt.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//마무리
			dbClose();
		}
	}//updateMember()
	
	// 리턴값없음 deleteMember(String id) 메서드 정의
	public void deleteMember(String id) {
		try {
			// 1단계 : 설치한 JDBC 프로그램 중에 Driver.class 자바실행 파일 불러오기
			// 2단계 : 불러온 파일을 이용해서 데이터베이스 연결 => 연결정보를 저장
			con = getConnection();
		//  => 3단계 연결정보를 이용해서 sql구문을 만들고 실행할수 있는 객체생성
		//  => delete from 테이블이름 where 조건열이름=값;
			String sql = "delete from members where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
		//  => 4단계 : sql구문 실행 (insert,update,delete)
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//마무리
			dbClose();
		}
	}//deleteMember()
	
//  ArrayList<MemberDTO> 리턴할형  getMemberList() 메서드 정의
	public ArrayList<MemberDTO> getMemberList() {
		ArrayList<MemberDTO> memberList = new ArrayList<MemberDTO>();
		try {
			// 1단계 : 설치한 JDBC 프로그램 중에 Driver.class 자바실행 파일 불러오기
			// 2단계 : 불러온 파일을 이용해서 데이터베이스 연결 => 연결정보를 저장
			con = getConnection();
			// 3단계 : 연결정보를 이용해서 sql구문을 만들고 실행할수 있는 객체생성
			//	        select * from 테이블이름 
			String sql = "select * from members";
			pstmt = con.prepareStatement(sql);
			// 4단계 : sql구문 실행 => 실행 후 결과 저장(select)=> ResultSet 내장객체 저장
			rs = pstmt.executeQuery();
			//5단계 : 결과를 이용해서 출력, 다른곳에 저장(select) => while (다음행접근)=> 열접근
			while(rs.next()){
				// 한 사람의 정보를 저장할 바구니 MemberDTO 객체생성
				MemberDTO memberDTO = new MemberDTO();	
				System.out.println("한 사람의 정보를 저장할 바구니 MemberDTO 주소" + memberDTO);
				// MemberDTO 멤버변수에 열접근해서 가져온데이터 저장
				memberDTO.setId(rs.getString("id"));
				memberDTO.setPw(rs.getString("pw"));
				memberDTO.setName(rs.getString("name")); 
				memberDTO.setDate(rs.getTimestamp("date"));
				// 배열 한칸에 저장(0번째 부터 순서대로 값이 저장)
				memberList.add(memberDTO);
			}
			System.out.println("배열에 저장된 내용 : " + memberList);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//마무리
			dbClose();
		}
		return memberList;
	}//getMemberList()
	
}//class MemberDAO






