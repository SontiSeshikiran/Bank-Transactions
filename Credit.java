package com.jsp.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Credit")
public class Credit extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mb = req.getParameter("mb");
		String password = req.getParameter("password");
		HttpSession session = req.getSession();
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");

		String url = "jdbc:mysql://localhost:3306/teca40?user=root&password=12345"; // This is placeholder incomplete
																					// data

		String select = "select * from bank where MobileNumber=? and Pin=?";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select); // PrepareStatement is used in runtime
																		
			// set the data in placeholder
			ps.setString(1, mb);
			ps.setString(2, password);
			  ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				double damount = rs.getDouble(3);
				session.setAttribute("damount", damount);
				session.setAttribute("smb", mb);
				session.setAttribute("pin", password);
				Random r = new Random();
				int OTP = r.nextInt(10000);
				if (OTP < 1000) {
					OTP += 1000;
				}
				session.setAttribute("otp", OTP);
				writer.println("<center><h1>" + OTP + "</h1></center>");
				RequestDispatcher rd = req.getRequestDispatcher("OTPCredit.html");
				rd.include(req, resp);

			} else {
				RequestDispatcher rd = req.getRequestDispatcher("Credit.html");
				rd.include(req, resp);
				writer.println("<center><h1>Invalid Details</h1></center>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}