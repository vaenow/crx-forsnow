package com.snowdream.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Demo extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("method: doget");

		// String url = "WEB-INF/jsp/demo.jsp";
		// String url = "html/direct.html";
		// req.getRequestDispatcher(url).forward(req, resp);

		StringBuilder sb = new StringBuilder();
		sb.append("{\"success\":true,\"result\":[{\"name\":\"name1\",\"cnt\":\"testContent\",\"date\":\"1mins\"}]}");
		
		resp.getWriter().write(sb.toString());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("method: dopost");
		doGet(req, resp);
	}

}
