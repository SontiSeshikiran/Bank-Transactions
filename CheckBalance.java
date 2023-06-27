package com.jsp.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/CheckBalance")
public class CheckBalance extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mb = req.getParameter("mb");
		String pin = req.getParameter("pin");
		String url="jdbc:mysql://localhost:3306/teca40?user=root&password=12345";
		String select="select * from bank where MobileNumber=? and Pin=?";
		PrintWriter writer = resp.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1, mb);
			ps.setString(2, pin);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				double Amount = rs.getDouble(3);
				
				RequestDispatcher r=req.getRequestDispatcher("MainPage.html");
				r.include(req, resp);
				writer.println("<center><h1>Your Account balance is :</h1></center>"+Amount);
			}
			else
			{
				writer.println("Enter valid Mobile Number");
				RequestDispatcher r=req.getRequestDispatcher("CheckBalance.html");
				r.include(req, resp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
