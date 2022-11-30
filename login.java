package com.railway;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/train","dedeepya","6281869953");
		PreparedStatement pstmt=con.prepareStatement("select * from login_details where username=? and passwords=?");
		pstmt.setString(1, username);
		pstmt.setString(2, password);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()) {
			if(rs.getString("passwords").equals(password)) {
				
				response.sendRedirect("Train.html");
			}
			else
				response.sendRedirect("login.html");
		}
		
		PreparedStatement pstmt1=con.prepareStatement("insert into login_details values(?,?)");
		pstmt1.setString(1, username);
		pstmt1.setString(2, password);
		pstmt1.execute();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
