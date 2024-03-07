package com.itwillbs.service;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.itwill.dao.BoardDAO;
import com.itwill.dao.MemberDAO;
import com.itwillbs.domain.BoardDTO;
import com.itwillbs.domain.MemberDTO;
import com.itwillbs.domain.PageDTO;

public class BoardService {
	
	BoardDAO boardDAO = null;
	
	// 리턴할형없음 insertBoard(request) 메서드 호출
	public void insertBoard(HttpServletRequest request) {
		System.out.println("BoardService insertBoard()");
		try {
			// request 한글설정
			request.setCharacterEncoding("utf-8");
			// request에서 파라미터(태그) name, subject, content값을 가져와서 변수에 저장
			String name = request.getParameter("name");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			
			boardDAO = new BoardDAO();
			
			// max(num) + 1
			int num = boardDAO.getMaxNum() + 1;
			// 조회수 초기값 0 설정
			int readcount = 0;
			// date 현시스템 날짜 설정
			Timestamp date = new Timestamp(System.currentTimeMillis());
			
			// BoardDTO 객체생성(기억장소 할당)
			BoardDTO boardDTO = new BoardDTO();
			// set메서드 호출 (멤버변수의 기억장소에 파라미터(태그값을 저장)
			boardDTO.setNum(num);
			boardDTO.setName(name);
			boardDTO.setSubject(subject);
			boardDTO.setContent(content);
			boardDTO.setReadcount(readcount);
			boardDTO.setDate(date);
			
			// insertBoard(BoardDTO 주소) 메서드 호출
			boardDAO.insertBoard(boardDTO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//insertBoard()

	// ArrayList<BoardDTO> boardList = getBoardList() 메서드 호출
//	public ArrayList<BoardDTO> getBoardList() {
//		
//	}
	public ArrayList<BoardDTO> getBoardList(PageDTO pageDTO) {
		System.out.println("BoardService getBoardList()");
		ArrayList<BoardDTO> boardList = null;
		try {
			int pageSize = pageDTO.getPageSize();
			int currentPage = pageDTO.getCurrentPage();
			
			
			//시작하는 행 번호 구하기
			//currentPage pageSize로 startRow가 나오게
			//    1			10    => 0*10+1 => 0+1  => 1
			//    2         10    => 1*10+1 => 10+1 => 11
			// 	  3 		10    => 2*10+1 => 20+1 => 21
			int startRow = (currentPage-1)* pageSize + 1;
			
			//끝나는 행 번호 구하기(오라클에서 필요함)
			//startRow pageSize로 endRow가 나오게
			//    1       10   =>  1+10-1 => 10
			//   11       10   => 11+10-1 => 20
			//   21       10   => 21+10-1 => 30
			int endRow = startRow -1 + pageSize;
			//pageDTO 주소에 저장
			pageDTO.setStartRow(startRow);
			pageDTO.setEndRow(endRow);
			
			// BoardDAO 객체생성
			boardDAO = new BoardDAO();
			// boardList = getBoardList() 메서드 호출
//			boardList = boardDAO.getBoardList();
			boardList = boardDAO.getBoardList(pageDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardList;
	}//getBoardList()
	
	public void updateReadcount(int num) {
		System.out.println("BoardService updateReadcount()");
		try {
			// BoardDAO 객체생성
			boardDAO = new BoardDAO();
			// updateReadcount(num) 메서드 호출
			boardDAO.updateReadcount(num);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//updateReadcount()

	public BoardDTO getBoard(int num) {
		System.out.println("BoardService getBoard()");
		BoardDTO boardDTO = null;
		try {
			// BoardDAO 객체생성
			boardDAO = new BoardDAO();
			// boardDTO = getBoard(num) 메서드 호출
			boardDTO = boardDAO.getBoard(num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardDTO;
	}//getBoard()

	public void updateBoard(HttpServletRequest request) {
		System.out.println("BoardService updateBoard()");
		try {
			request.setCharacterEncoding("utf-8");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			String name = request.getParameter("name");
			int num = Integer.parseInt(request.getParameter("num"));
			BoardDTO boardDTO = new BoardDTO();
			boardDTO.setSubject(subject);
			boardDTO.setContent(content);
			boardDTO.setNum(num);
			boardDTO.setName(name);
			
			boardDAO = new BoardDAO();
			boardDAO.updateBoard(boardDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteBoard(int num) {
		System.out.println("BoardService deleteBoard()");
		try {
			boardDAO = new BoardDAO();
			boardDAO.deleteBoard(num);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public int getBoardCount() {
		System.out.println("BoardService getBoardCount()");
		int count = 0;
		try {
			//BoardDAO 객체 생성
			boardDAO = new BoardDAO();
			//count = getBoardCount()메소드 호출 count(*)
			count = boardDAO.getBoardCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	
	
	
	
	
	
}//BoardService클래스
