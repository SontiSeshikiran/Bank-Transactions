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
import javax.servlet.http.HttpSession;
@WebServlet("/amount1")
public class amount1 extends  HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tramount=req.getParameter("amount");
		double ramount=Double.parseDouble(tramount);
		HttpSession session = req.getSession();
		Double samount=(Double)session.getAttribute("samount");
		double rdamount = (double) session.getAttribute("ramount");
		Integer smb = (Integer) session.getAttribute("smb");
		Integer rmb = (Integer) session.getAttribute("rmb");
		PrintWriter writer = resp.getWriter();
		if(samount>= ramount)
		{
			double sub=samount-ramount;
			double add=rdamount+ramount;
			String url="jdbc:mysql://localhost:3306/teca40?user=root&passsword=12345";
			String supdate="update bank set Amount=? where MobileNumber=?";
			String rupdate="update bank set Amount=? where MobileNumber=?";
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement ps = connection.prepareStatement(supdate);
				ps.setDouble(1, sub);
				ps.setInt(2, smb);
				int num = ps.executeUpdate();
				if(num>0)
				{
					PreparedStatement ps1 = connection.prepareStatement(rupdate);
					ps1.setDouble(1,add);
					ps1.setInt(1, rmb);
					int rs1 = ps1.executeUpdate();
					if(rs1>0)
					{
						RequestDispatcher r=req.getRequestDispatcher("MainPage.html");
						r.include(req, resp);
						writer.println("<center><h1>Transaction succes ful...........</h1></center>");
					}
					else
					{
						RequestDispatcher r=req.getRequestDispatcher("Amountreciver.html");
						r.include(req, resp);
						writer.println("<center><h1>Insuficient Balance...........</h1></center>");
					}
					
					
				}
				else
				{
					RequestDispatcher r=req.getRequestDispatcher("MainPage.html");
					r.include(req, resp);
					writer.println("<center><h1>Enter Valid Information...........</h1></center>");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			RequestDispatcher r=req.getRequestDispatcher("MainPage.html");
			r.include(req, resp);
			writer.println("<center><h1>Enter Valid Amount...........</h1></center>");
		}
	}

}
