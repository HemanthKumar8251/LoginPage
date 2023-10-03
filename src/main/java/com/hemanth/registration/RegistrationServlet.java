package com.hemanth.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("name");
		String email = request.getParameter("email");
		String pwd = request.getParameter("pass");
		String mobile = request.getParameter("contact");
		RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hemanth","root","Hemanth@8251");
			PreparedStatement pst = con.prepareStatement("insert into user_info values(?,?,?,?)");
			pst.setString(1, uname);
			pst.setString(2, email);
			pst.setString(3, pwd);
			pst.setString(4, mobile);
			int row = pst.executeUpdate();
			if(row>0) {
				request.setAttribute("status", "success");
			}
			else {
				request.setAttribute("status","failed");
			}
			rd.forward(request,response);
		}
		catch(SQLIntegrityConstraintViolationException e) {
			request.setAttribute("status", "usernametaken");
			rd.forward(request, response);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}
