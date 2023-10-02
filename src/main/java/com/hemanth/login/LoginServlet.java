package com.hemanth.login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uname = request.getParameter("username");
		String pwd = request.getParameter("password");
		RequestDispatcher rd = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hemanth", "root", "Hemanth@8251");
			PreparedStatement pst = con.prepareStatement("select * from user_info where uname=? and password=?");
			pst.setString(1, uname);
			pst.setString(2, pwd);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				rd = request.getRequestDispatcher("index.jsp");
				request.setAttribute("name", uname);
			} else {
				rd = request.getRequestDispatcher("login.jsp");
				request.setAttribute("status", "failed");
			}
			rd.forward(request, response);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
