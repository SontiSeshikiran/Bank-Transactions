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
@WebServlet("/Withdraw")
public class Withdraw extends HttpServlet
{
     @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	String mb=req.getParameter("mb");
    	String password=req.getParameter("password");
    	HttpSession session = req.getSession();
    	PrintWriter writer=resp.getWriter();
    	resp.setContentType("text/html");
    	
    	String url="jdbc:mysql://localhost:3306/teca40?user=root&password=12345"; //This is placeholder incomplete data
    	
    	String select="select * from bank where MobileNumber=? and Pin=?";
    	
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(select); //PrepareStatement is used in runtime envirolment.
			//set the data in placeholder
			ps.setString(1, mb);
			ps.setString(2, password);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				Random r=new Random(); //class r=new constructor();
				int otp=r.nextInt(10000); //return type =method
				if(otp<1000) {
					otp+=1000;
				}
				double damount=rs.getDouble("amount");
				session.setAttribute("otp", otp);
				session.setAttribute("damount", damount);
				//session.setAttribute("Amount", Amount);
				session.setAttribute("smb", mb);
				session.setAttribute("pin", password);
				session.setMaxInactiveInterval(15);
			 writer.println("<center><h1>"+otp+"</h1></center>");
			 RequestDispatcher rd=req.getRequestDispatcher("otp.html");
			 rd.include(req, resp);
			}else {
				RequestDispatcher rd=req.getRequestDispatcher("Withdraw.html");
				 rd.include(req, resp);
				 writer.println("<center><h1>Invalid Details</h1></center>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}