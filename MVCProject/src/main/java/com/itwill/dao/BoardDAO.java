package com.itwill.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.itwillbs.domain.BoardDTO;
import com.itwillbs.domain.PageDTO;

public class BoardDAO {
	
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
//			String dbPass = "1234";
//			con = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			
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
	
	
	// 리턴값없음 insertBoard(BoardDTO 변수)메서드 정의()
	public void insertBoard(BoardDTO boardDTO) {
		try {
			//1,2 단계 디비연결 메서드 호출
			con = getConnection();
			
			// 3단계 : 연결정보를 이용해서 sql구문을 만들고 실행할수 있는 객체생성
			String sql = "insert into board(num,name,subject,content,readcount,date) values(?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, boardDTO.getNum());
			pstmt.setString(2, boardDTO.getName());
			pstmt.setString(3, boardDTO.getSubject());
			pstmt.setString(4, boardDTO.getContent());
			pstmt.setInt(5, boardDTO.getReadcount());
			pstmt.setTimestamp(6, boardDTO.getDate());
			
			// 4단계 : sql구문 실행 (insert,update,delete)
			pstmt.executeUpdate();	
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//마무리 => 사용한 내장객체 기억장소 삭제(해제)
			dbClose();			
		}
	}//insertBoard()
	
	// int 리턴할형  getMaxNum()  메서드 정의 
	public int getMaxNum() {
		int num = 0;
		try {
			//1,2 단계 디비연결 메서드 호출
			con = getConnection();
			// 3단계 : 연결정보를 이용해서 sql구문을 만들고 실행할수 있는 객체생성
			//        select max(num) from board
			String sql = "select max(num) from board";
			pstmt = con.prepareStatement(sql);
			// 4단계 : sql구문 실행 => 실행 후 결과 저장(select)=> ResultSet 내장객체 저장
			rs = pstmt.executeQuery();
			// 5단계 : 결과를 이용해서 출력, 다른곳에 저장(select)
			// if 커서를 다음행 이동 => 리턴값 데이터 있으면 true 
			//                 => num 변수에 "max(num)" 열데이터 저장
			if(rs.next()) {
				num = rs.getInt("max(num)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//마무리
			dbClose();
		}
		return num;
	}//getMaxNum()
	
	// ArrayList<BoardDTO>  리턴할형  getBoardList() 메서드 정의
//	public ArrayList<BoardDTO> getBoardList() {
//		System.out.println("BoardDAO getBoardList()");
//		ArrayList<BoardDTO> boardList = new ArrayList<BoardDTO>();
//		try {
//			//1,2 단계 디비연결 메서드 호출
//			con = getConnection();
//			// 3단계 : 연결정보를 이용해서 sql구문을 만들고 실행할수 있는 객체생성
//			//        select * from board order by num desc
//			String sql = "select * from board order by num desc";
//			pstmt = con.prepareStatement(sql);
//			// 4단계 : sql구문 실행 => 실행 후 결과 저장(select)=> ResultSet 내장객체 저장
//			rs = pstmt.executeQuery();
//			// 5단계 : 결과를 이용해서 출력, 다른곳에 저장(select)
//			// while 커서를 다음행 이동 => 리턴값 데이터 있으면 true 
//			//                    => BoardDTO 객체생성=>set(디비열 데이터)
//			//             => boardList 배열 한칸에 BoardDTO의 주소 저장
//			while(rs.next()) {
//				BoardDTO boardDTO = new BoardDTO();
//				boardDTO.setNum(rs.getInt("num"));
//				boardDTO.setName(rs.getString("name"));
//				boardDTO.setSubject(rs.getString("subject"));
//				boardDTO.setContent(rs.getString("content"));
//				boardDTO.setReadcount(rs.getInt("readcount"));
//				boardDTO.setDate(rs.getTimestamp("date"));
////				=> boardList 배열 한칸에 BoardDTO의 주소 저장
//				boardList.add(boardDTO);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			//마무리
//			dbClose();
//		}
//		return boardList;
//	}//getBoardList()
	
	public ArrayList<BoardDTO> getBoardList(PageDTO pageDTO) {
		System.out.println("BoardDAO getBoardList()");
		ArrayList<BoardDTO> boardList = new ArrayList<BoardDTO>();
		try {
			//1,2 단계 디비연결 메서드 호출
			con = getConnection();
			// 3단계 : 연결정보를 이용해서 sql구문을 만들고 실행할수 있는 객체생성
			//        select * from board order by num desc limit 시작행 -1, 글 개수
			String sql = "select * from board order by num desc limit ?, ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pageDTO.getStartRow()-1); //시작행 - 1
			pstmt.setInt(2, pageDTO.getPageSize());//글 개수
			// 4단계 : sql구문 실행 => 실행 후 결과 저장(select)=> ResultSet 내장객체 저장
			rs = pstmt.executeQuery();
			// 5단계 : 결과를 이용해서 출력, 다른곳에 저장(select)
			// while 커서를 다음행 이동 => 리턴값 데이터 있으면 true 
			//                    => BoardDTO 객체생성=>set(디비열 데이터)
			//             => boardList 배열 한칸에 BoardDTO의 주소 저장
			while(rs.next()) {
				BoardDTO boardDTO = new BoardDTO();
				boardDTO.setNum(rs.getInt("num"));
				boardDTO.setName(rs.getString("name"));
				boardDTO.setSubject(rs.getString("subject"));
				boardDTO.setContent(rs.getString("content"));
				boardDTO.setReadcount(rs.getInt("readcount"));
				boardDTO.setDate(rs.getTimestamp("date"));
//				=> boardList 배열 한칸에 BoardDTO의 주소 저장
				boardList.add(boardDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//마무리
			dbClose();
		}
		return boardList;
	}//getBoardList()
	
	
	
	// BoardDTO 리턴할형 getBoard(int num) 메서드 정의
	public BoardDTO getBoard(int num) {
		System.out.println("BoardDAO getBoard()");
		BoardDTO boardDTO = null;
		try {
			//1,2 단계 디비연결 메서드 호출
			con = getConnection();
			// 3단계 : 연결정보를 이용해서 sql구문을 만들고 실행할수 있는 객체생성
			//        select * from board where num = ?
			String sql = "select * from board where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			// 4단계 : sql구문 실행 => 실행 후 결과 저장(select)=> ResultSet 내장객체 저장
			rs = pstmt.executeQuery();
			// 5단계 : 결과를 이용해서 출력, 다른곳에 저장(select)
			// if 커서를 다음행 이동 => 리턴값 데이터 있으면 true 
			//                    => BoardDTO 객체생성=>set(디비열 데이터)
			if(rs.next()) {
				boardDTO = new BoardDTO();
				boardDTO.setNum(rs.getInt("num"));
				boardDTO.setName(rs.getString("name"));
				boardDTO.setSubject(rs.getString("subject"));
				boardDTO.setContent(rs.getString("content"));
				boardDTO.setReadcount(rs.getInt("readcount"));
				boardDTO.setDate(rs.getTimestamp("date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//마무리
			dbClose();
		}
		return boardDTO;
	}//getBoard()
	
	// 리턴할형없음 updateReadcount(int num)메서드 정의
	public void updateReadcount(int num) {
		System.out.println("BoardDAO updateReadcount()");
		try {
			//1,2 디비연결
			con = getConnection();
			//3 sql update
			String sql = "update board set readcount = readcount + 1 where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			//4 실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//마무리
			dbClose();
		}
	}//updateReadcount()
	
	// 리턴할형없음 updateBoard(BoardDTO 변수) 메서드 정의
	public void updateBoard(BoardDTO boardDTO) {
		System.out.println("BoardDAO updateBoard()");
		try {
			
			//1,2 디비연결
			con = getConnection();
			//3 sql update
			String sql = "update board set subject = ?, content = ? where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, boardDTO.getSubject());
			pstmt.setString(2, boardDTO.getContent());
			pstmt.setInt(3, boardDTO.getNum());
			//4 실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//마무리
			dbClose();
		}
	}//updateBoard()
	
	// 리턴할형없음 deleteBoard(int num) 메서드 정의
	public void deleteBoard(int num) {
		System.out.println("BoardDAO deleteBoard()");
		try {
			//1,2 디비연결
			con = getConnection();
			//3 sql delete
			String sql = "delete from board where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			//4 실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//마무리
			dbClose();
		}
	}//deleteBoard()

	public int getBoardCount() {
		System.out.println("BoardDAO getBoardCount()");
		int count = 0;
		try {
			con = getConnection();
			String sql = "select count(*) from board ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count=rs.getInt("count(*)");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		
		return count;
	}
	
	
	
	
	
	
	
	
	
	
}//BoardDAO 클래스
