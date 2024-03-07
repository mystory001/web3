package com.itwillbs.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwill.dao.BoardDAO;
import com.itwillbs.domain.BoardDTO;
import com.itwillbs.domain.PageDTO;
import com.itwillbs.service.BoardService;

public class BoardController extends HttpServlet{
	
	RequestDispatcher dispatcher = null;
	BoardService boardService = null;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BoardController doGet()");
		doProcess(request, response);
	}//doGet()

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BoardController doPost()");
		doProcess(request, response);
	}//doPost()
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BoardController doProcess()");
		
		
		//가상주소 http://localhost:8080/MVCProject/write.bo 
		//가상주소 뽑아오기                        => /write.bo 
		String sPath = request.getServletPath();
		System.out.println("가상주소 뽑아오기 : " + sPath);
		
		//주소매핑 : 가상주소 비교 => 실제페이지 연결
		if(sPath.equals("/write.bo")) {
			System.out.println("가상주소 비교  /write.bo 일치");
			
			// 1) 실제페이지 board/write.jsp 주소변경없이 이동(특이한 이동방식)
			// 액션태그 <jsp:forward > 같은 이동방식
			// 2) 이동할때 request, response 값을 들고 이동
			dispatcher =request.getRequestDispatcher("board/write.jsp");
			dispatcher.forward(request, response);
		}//
		
		if(sPath.equals("/writePro.bo")) {
			System.out.println("가상주소 비교  /writePro.bo 일치");
			// BoardService 객체생성
			boardService = new BoardService();
			// 리턴할형없음 insertBoard(request) 메서드 호출
			boardService.insertBoard(request);
			// list.bo 글목록 주소변경하면서 이동(웹이동, 하이퍼링크)
			response.sendRedirect("list.bo");
		}//
		
		if(sPath.equals("/list.bo")) {
			System.out.println("가상주소 비교  /list.bo 일치");
			//http://localhost:8080/MVCProject/list.bo
			//모든 게시판 1페이지
			//http://localhost:8080/MVCProject/list.bo?pageNum=2
			//한 페이지에 보여줄 글 개수 설정 10
			int pageSize = 10;
			//현 페이지 번호 가져오기
			String pageNum = request.getParameter("pageNum");
			//현 페이지 번호가 없으면(null이면) "1"페이지로 설정
			if(pageNum==null) {
				pageNum = "1";
			}
			//페이지 번호를 정수형으로 변경해서 계산
			int currentPage = Integer.parseInt(pageNum);
			
			//PageDTO 객체 생성
			//pageSize, pageNum, currentPage 저장
			PageDTO pageDTO = new PageDTO();
			pageDTO.setPageSize(pageSize);
			pageDTO.setPageNum(pageNum);
			pageDTO.setCurrentPage(currentPage);
			
			
			// BoardService 객체생성
			boardService = new BoardService();
        // ArrayList<BoardDTO> boardList = getBoardList() 메서드 호출
//			ArrayList<BoardDTO> boardList = boardService.getBoardList();
			
			ArrayList<BoardDTO> boardList = boardService.getBoardList(pageDTO);
			
			//페이징 작업
			//전체 글 개수 구하기
			//int count = getBoardCount() 메소드 호출
			int count = boardService.getBoardCount();
			
			//한 화면에 보여줄 페이지 개수 설정
			int pageBlock = 10;
			//1~10, 11~20, 21~30, ....
			//시작하는 페이지 번호 구하기
			//currentPage pageBlock => startPage(정수형 나누기)
			//   1~10(0~9) 	     10     => (0 ~ 9)/10*10+1 => 0*10 + 1 =>  0+1 => 1
			//  11~20(10~19)     10     => (10~19)/10*10+1 => 1*10 + 1 => 10+1 => 2
			//  21~30(20~29)     10     => (20~29)/10*10+1 => 2*10 + 1 => 20+1 => 3
			//int startPage = 계산식;
			int startPage = (currentPage-1)/pageBlock*pageBlock+1;
			
			//끝나는 페이지 번호 구하기
			//startPage pageBlock => endPage
			//   1			10    =>   10
			//  11			10    =>   20
			//  21			10    =>   30
			//int endPage = 계산식;
			int endPage = (startPage-1)+pageBlock;
			
			//전체 페이지 개수
			//ex) 전체 글 개수가 19개, 한 화면에 보여줄 글 개수 10
			//→ 12/10 = 1 → 1 + 나머지 있음(19 % 10 = 9) → 나머지가 있을 경우 1페이지 추가
			//ex) 전체 글의 개수 10개
			//10%10 = 1 + 나머지 없음(10%10 = 0) → 나머지가 없는 경우 0 페이지 추가
			// => 글 개수/ 한 화면에 보여줄 글의 개수 + (조건(나머지가 없으면)? 0 : 1)
			int pageCount = count/pageSize + ((count%pageSize==0)? 0:1);
			
			
			//  endPage > 전체 페이지 개수
			if(endPage > pageCount) {
				endPage = pageCount;
			}
			
			
			//pageDTO에 값을 저장
			pageDTO.setCount(count);
			pageDTO.setPageBlock(pageBlock);
			pageDTO.setStartPage(startPage);
			pageDTO.setEndPage(endPage);
			pageDTO.setPageCount(pageCount);
			
			//request에 "pageDTO" 저장
			request.setAttribute("pageDTO", pageDTO);
			
			// request 에 "boardList", boardList 담아서
			request.setAttribute("boardList", boardList);
			
			// board/list.jsp 주소변경없이 이동
			dispatcher =request.getRequestDispatcher("board/list.jsp");
			dispatcher.forward(request, response);
		}//
		
		if(sPath.equals("/content.bo")) {
			System.out.println("가상주소 비교  /content.bo 일치");
			//content.bo?num=1
			// request에서 "num" 가져오기
			int num = Integer.parseInt(request.getParameter("num"));
			// BoardService 객체생성
			boardService = new BoardService();
			// 조회수 1증가
			// 리턴할형없음 updateReadcount(num)메서드 호출
			boardService.updateReadcount(num);
			// num에 대한 글 가져오기
			// BoardDTO 리턴할형 getBoard(num) 메서드 호출
			BoardDTO boardDTO = boardService.getBoard(num);
			// request에 "boardDTO", boardDTO 값 가져오기
			request.setAttribute("boardDTO", boardDTO);
			// board/content.jsp 주소변경없이 이동 
			dispatcher =request.getRequestDispatcher("board/content.jsp");
			dispatcher.forward(request, response);
		}//
		
		if(sPath.equals("/update.bo")) {
			System.out.println("가상주소 비교  /update.bo 일치");
			//update.bo?num=
			//request에서 "num" 가져오기
			int num = Integer.parseInt(request.getParameter("num"));
			//BoardService 객체생성
			boardService = new BoardService();
			//num에 대한 글 가져오기
			//BoardDTO getBoard(num) 메소드 호출
			BoardDTO boardDTO = boardService.getBoard(num);
			
			//request에 "boardDTO", boardDTO 값 가져오기
			request.setAttribute("boardDTO", boardDTO);
			//board/update.jsp 주소변경없이 이동
			dispatcher =request.getRequestDispatcher("board/update.jsp");
			dispatcher.forward(request, response);
		}
		
		if(sPath.equals("/updatePro.bo")) {
			request.setCharacterEncoding("utf-8");
			System.out.println("가상주소 비교  /updatePro.bo 일치");
			// BoardService 객체생성
			boardService = new BoardService();
			// 리턴할형없음 updateBoard(request) 메서드 호출
			boardService.updateBoard(request);
			// list.bo 글목록 주소변경하면서 이동(웹이동, 하이퍼링크)
			response.sendRedirect("list.bo");
		}//
		
		if(sPath.equals("/delete.bo")) {
			request.setCharacterEncoding("utf-8");
			System.out.println("가상주소 비교 /delete.bo 일치");
			//delete.bo?num=
			//request에서 "num"가져오기
			int num = Integer.parseInt(request.getParameter("num"));
			//BoardService 객체 생성
			boardService = new BoardService();
			boardService.deleteBoard(num);
			//list.bo 글목록 주소 변경하면서 이동
			response.sendRedirect("list.bo");
		}
		

		
	}//doProcess()
	
	

}//BoardController 클래스
