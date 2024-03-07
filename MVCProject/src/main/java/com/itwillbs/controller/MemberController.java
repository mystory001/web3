package com.itwillbs.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.domain.MemberDTO;
import com.itwillbs.service.MemberService;

public class MemberController extends HttpServlet{
	
	RequestDispatcher dispatcher = null;
	MemberService memberService = null;

	// MemberController 파일을 servlet파일로 지정
	// => HttpServlet 자바내장객체(처리담당자) 상속
	// => 웹애플리케이션서버(처리담당서버)가 자동으로 서블릿 메서드 호출
	// => service(), doGet(), doPost(),.. 자동으로 호출
	// => doGet(), doPost() 를 재정의(메서드 오버라이딩)해서 주소매핑 작업 처리
	
	// alt shift s => v
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MemberController doGet()");
		//doProcess() 메서드 호출
		doProcess(request, response);
	}//doGet()

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MemberController doPost()");
		//doProcess() 메서드 호출
		doProcess(request, response);
	}//doPost()
	
	//주소매핑 메서드 정의
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MemberController doProcess()");
		
		//가상주소 http://localhost:8080/MVCProject/insert.me 
		//가상주소 뽑아오기                        => /insert.me 
		String sPath = request.getServletPath(); //프로젝트의 경로값만 가지고 옴
		System.out.println("가상주소 뽑아오기 : " + sPath);
		
//		String sPath = request.getServletPath(); // 프로젝트의 경로값만 가져옴(/insert.me)
//		String rUrl = request.getRequestURL(); //전체 경로를 가져옴(http://localhost:8080/MVCProject/insert.me)
//		String rUri = request.getRequestURI(); //프로젝트 경로부터 파일까지의 경로값을 얻어옴(/MVCProject/insert.me)
//		String cPath = request.getContextPath();//프로젝트의 경로값만 가져옴(/MVCProject)
		
		
		//주소매핑 : 가상주소 비교 => 실제페이지 연결
		if(sPath.equals("/insert.me")) {
			System.out.println("가상주소 비교  /insert.me 일치");
			//실제페이지 member/insert.jsp 웹방식 주소변경되면서 이동(하이퍼링크)
//			response.sendRedirect("member/insert.jsp");
			
			// 1) 실제페이지 member/insert.jsp 주소변경없이 이동(특이한 이동방식)
			// 액션태그 <jsp:forward > 같은 이동방식
			// 2) 이동할때 request, response 값을 들고 이동
			dispatcher = request.getRequestDispatcher("member/insert.jsp"); //인자값으로 이동할 페이지의 경로를 지정
			dispatcher.forward(request, response); //페이지를 이동시키는 메소드. 이동하면서 현재페이지에서 사용하던 HttpServletRequest와 HttpServletResponse 객체를 인자값으로 전달. 해당 객체
		}//
		
		if(sPath.equals("/insertPro.me")) {
			System.out.println("가상주소 비교  /insertPro.me 일치");
			//패키지 com.itwillbs.service 자바파일 MemberService
// 리턴할형없음  insertMember(HttpServletRequest request) 메서드 정의
			
			//MemberService 객체생성
			memberService = new MemberService();
			//insertMember(request) 메서드호출
			memberService.insertMember(request);
			
			// 로그인페이지로 이동
			// 가상주소 login.me 주소 변경되면서 이동(하이퍼링크)
			response.sendRedirect("login.me");
		}//
		
		// /login.me 주소가 일치하면 member/login.jsp 주소변경없이 이동
		if(sPath.equals("/login.me")) {
			System.out.println("가상주소 비교  /login.me 일치");
			
//			response.sendRedirect("member/login.jsp");
			
			dispatcher = request.getRequestDispatcher("member/login.jsp");
			dispatcher.forward(request, response);
			
		}//
		
		// /loginPro.me 주소가 일치하면 
		// MemberService객체생성 boolean 형리턴 userCheck(request)메서드호출
		if(sPath.equals("/loginPro.me")) {
			System.out.println("가상주소 비교  /loginPro.me 일치");
			memberService = new MemberService();
			boolean result = memberService.userCheck(request);
			String id = request.getParameter("id");
			if(result == true) {
				//아이디 비밀번호 일치 => 로그인 표시(세션값 "id",id)=> main.me이동
				HttpSession session = request.getSession();
				session.setAttribute("id", id);
//				response.sendRedirect("member/main.jsp");
				response.sendRedirect("main.me");
			}else {
				//아이디 비밀번호 틀림 => login.me 이동
				response.sendRedirect("login.me");
			}
		}//
		
		// /main.me 주소가 일치하면 member/main.jsp 주소변경없이 이동
		if(sPath.equals("/main.me")) {
			System.out.println("가상주소 비교  /main.me 일치");
//			response.sendRedirect("member/main.jsp");
			
			dispatcher = request.getRequestDispatcher("member/main.jsp");
			dispatcher.forward(request, response);
		}//
		
		// /logout.me 주소가 일치하면 => 세션초기화 => main.me 이동
		if(sPath.equals("/logout.me")) {
			System.out.println("가상주소 비교  /logout.me 일치");
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("main.me");
		}//
		
		// /info.me 주소가 일치하면 
		// => 세션값(로그인 표시값) 가져오기 => String id 변수에 저장
		// => MemberService객체생성
		// => 리턴할형 MemberDTO memberDTO = getMember(id) 메서드 호출
		// => request에 "memberDTO",memberDTO  담아서 이동 
		// => member/info.jsp 주소변경없이 이동
		if(sPath.equals("/info.me")) {
			System.out.println("가상주소 비교  /info.me 일치");
			// => 세션값(로그인 표시값) 가져오기 => String id 변수에 저장
			HttpSession session = request.getSession();
			String id = (String)session.getAttribute("id");
			// => MemberService객체생성
			memberService = new MemberService();
			// => 리턴할형 MemberDTO memberDTO = getMember(id) 메서드 호출
			MemberDTO memberDTO = memberService.getMember(id);
			// => request에 "memberDTO",memberDTO  담아서 이동
			request.setAttribute("memberDTO", memberDTO);
			// => member/info.jsp 주소변경없이 이동
			dispatcher = request.getRequestDispatcher("member/info.jsp");
			dispatcher.forward(request, response);
		}//
		
		if(sPath.equals("/update.me")) {
			System.out.println("가상주소 비교  /update.me 일치");
			// => 세션값(로그인 표시값) 가져오기 => String id 변수에 저장
			HttpSession session = request.getSession();
			String id = (String)session.getAttribute("id");
			// => MemberService객체생성
			memberService = new MemberService();
			// => 리턴할형 MemberDTO memberDTO = getMember(id) 메서드 호출
			MemberDTO memberDTO = memberService.getMember(id);
			// => request에 "memberDTO",memberDTO  담아서 이동
			request.setAttribute("memberDTO", memberDTO);
			// => member/update.jsp 주소변경없이 이동
			dispatcher = request.getRequestDispatcher("member/update.jsp");
			dispatcher.forward(request, response);
		}//
		
		if(sPath.equals("/updatePro.me")) {
			System.out.println("가상주소 비교  /updatePro.me 일치");
			// request 한글처리 
			request.setCharacterEncoding("utf-8");
			// => MemberService객체생성
			memberService = new MemberService();
			// boolean result = userCheck(request)메서드 호출
			boolean result = memberService.userCheck(request);
			
			if(result == true) {
				//아이디 비밀번호 일치 => 
				// => 리턴할형없음 updateMember(request) 메서드 호출
				memberService.updateMember(request);
				// => main.me이동
				response.sendRedirect("main.me");
			}else {
				//아이디 비밀번호 틀림 => update.me 이동
				response.sendRedirect("update.me");
			}
			
		}//
		
		if(sPath.equals("/delete.me")) {
			System.out.println("가상주소 비교  /delete.me 일치");
			// => member/delete.jsp 주소변경없이 이동
			dispatcher = request.getRequestDispatcher("member/delete.jsp");
			dispatcher.forward(request, response);
		}//
		
		if(sPath.equals("/deletePro.me")) {
			System.out.println("가상주소 비교  /deletePro.me 일치");
			// request 한글처리
			request.setCharacterEncoding("utf-8");
			// => MemberService객체생성
			memberService = new MemberService();
			// boolean result = userCheck(request)메서드 호출
			boolean result = memberService.userCheck(request);
			
			if(result == true) {
				//아이디 비밀번호 일치 => 
				// => 리턴할형없음 deleteMember(request) 메서드 호출
				memberService.deleteMember(request);
				// => 세션초기화(로그아웃)
				HttpSession session = request.getSession();
				session.invalidate();
				// => main.me이동
				response.sendRedirect("main.me");
			}else {
				//아이디 비밀번호 틀림 => delete.me 이동
				response.sendRedirect("delete.me");
			}
			
		}//
		
		if(sPath.equals("/list.me")) {
			System.out.println("가상주소 비교  /list.me 일치");
			// => MemberService객체생성
			memberService = new MemberService();
 // ArrayList<MemberDTO> 리턴할형 memberList = getMemberList() 메서드 호출
			ArrayList<MemberDTO> memberList = memberService.getMemberList();
			// request  "memberList",memberList 값을 저장
			request.setAttribute("memberList", memberList);
			// 주소변경없이 member/list.jsp 이동
			dispatcher = request.getRequestDispatcher("member/list.jsp");
			dispatcher.forward(request, response);
		}//
		
		
		if(sPath.equals("/content.bo")) {
			System.out.println("가상주소 비교  /content.bo 일치");
		}
		
		
		
		
		
		
		
		
		
		
		
		
	}//doProcess()
	
	
}//MemberController클래스
