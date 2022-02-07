	package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");

		// 방명록 리스트 + 방명록 추가
		if ("add".equals(action)) {
			//파라미터 3개를 꺼내와요
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");

			//dao를 메모리에 올려요..
			GuestbookDao dao = new GuestbookDao();
			GuestbookVo vo = new GuestbookVo(name, password, content);
			
			
			dao.insert(vo);
			System.out.println(vo.toString());

			// 리다이렉트
			WebUtil.redirect(request, response, "/guestbook2/gbc");

			//방명록 삭제폼
		} else if ("deleteform".equals(action)) {

			// 포워드
			WebUtil.forward(request, response, "/WEB-INF/deleteForm.jsp");

			//방명록 삭제
		} else if ("delete".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("pass");

			GuestbookVo vo = new GuestbookVo();
			vo.setNo(no);
			vo.setPassword(password);

			GuestbookDao dao = new GuestbookDao();
			dao.delete(vo);

			// 리다이렉트
			WebUtil.redirect(request, response, "/guestbook2/gbc");

		} else {// 리스트 를 기본값으로
			GuestbookDao dao = new GuestbookDao();
			List<GuestbookVo> gList = dao.getList();

			request.setAttribute("guestList", gList);

			// 포워드
			WebUtil.forward(request, response, "/WEB-INF/addList.jsp");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}