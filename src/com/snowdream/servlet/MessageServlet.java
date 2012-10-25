package com.snowdream.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snowdream.bean.Discuss;
import com.snowdream.bean.User;
import com.snowdream.jdbc.JDBCConnection;

public class MessageServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		execute(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		execute(req, resp);
	}

	void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		JDBCConnection jdbc = new JDBCConnection();

		// user_name
		String user_name = proFilter(request.getParameter("user_name"));
		// message content
		String cont = proFilter(request.getParameter("msg_cnt"));

		if (user_name != null && cont != null && cont.trim().length() >= 1) {
			String ip = request.getRemoteAddr();
			// System.out.println("------count--jdbc--" + count);
			if (!jdbc.isUserExsit(user_name)) {
				User user = new User();
				user.setCreateIp(ip);
				user.setName(user_name);
				// add User entity
				jdbc.addUser(user);
			}

			Discuss di = new Discuss();
			User user = new User();
			user.setName(user_name);
			di.setFromUser(user);
			di.setIp(ip);
			di.setContent(cont);
			jdbc.addDiscuss(di);

		}

		List<Discuss> discuss = jdbc.getDiscuss();
		StringBuilder sb = new StringBuilder("{\"success\":true,\"result\":[");
		for (int i = 0; i < discuss.size(); i++) {
			Discuss dis = discuss.get(i);
			// "{\"success\":true,\"result\":[{\"name\":\"name1\",\"cnt\":\"testContent\",\"date\":\"1mins\"}]}"
			sb.append("{\"name\":\"" + dis.getFromUser().getName());
			sb.append("\",\"cnt\":\"" + dis.getContent());
			sb.append("\",\"date\":\"" + checkTime(dis.getDttm()));
			sb.append("\"},");
		}
		if (discuss.size() != 0)
			sb.deleteCharAt(sb.length() - 1);
		sb.append("]}");
		response.getWriter().write(sb.toString());
	}

	private String checkTime(Date oldDate) {
		long od = oldDate.getTime();
		long nd = new Date().getTime();
		long result = (nd - od) / 1000;
		if (result / 60 == 0) {
			// System.out.println("约" + result + "��ǰ�ύ");
			return (result == 0 ? 1 : result) + " secs";
		} else if (result / 60 / 60 == 0) {
			// System.out.println("��" + result / 60 + "����ǰ�ύ");
			return result / 60 + " mins";
		} else if (result / 60 / 60 / 24 == 0) {
			// System.out.println("��" + result / 60 / 60 + "Сʱǰ�ύ");
			return result / 60 / 60 + " hour";
		} else {
			// System.out.println("��" + result / 60 / 60 / 24 + "��ǰ�ύ");
			return result / 60 / 60 / 24 + " days";
		}
	}

	/**
	 * 字符过滤
	 *  
	 * @param str
	 * @return
	 */
	private String proFilter(String str) {
		if (str == null)
			return str;
		try {
			// 编码问题
			str = new String(str.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	private String keyName(String name) {
		if (name.equals("admin")) {
			name = "<font color=#7B68EE>" + name + "</font>";
		}
		/*
		 * if (name.equals("����")) { name = "<font color=#BB68EE>" + name +
		 * "</font>"; }
		 */
		return name;
	}
}
