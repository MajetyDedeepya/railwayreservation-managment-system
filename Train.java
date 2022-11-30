 package com.railway;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Train
 */
@WebServlet("/Train")
public class Train extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Train() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/train","dedeepya","6281869953");
			//PreparedStatement pstmt= con.prepareStatement("create table train_details ( TrainNumber int ,TrainName varchar(255),TotalConfirmedSeats int,TotalRacSeats int, FromStation varchar(255),ToStation varchar(255))");
			//pstmt.execute();
			//Statement stmt=con.createStatement();
			//String s1="insert into train_details values(12235,'Rajadhani Express',0,0,'Dibrugarh','Newdelhi'),(12077,'JanSatabdi',0,0,'Mgr Chennai Central','vijayawada'),(12615,'Grand Trunk',0,0,'Mgr Chennai Central','Chirala'),(12712,'Pinakini Express',0,0,'Mgr Chennai Central','Chirala'),(12603,'Hyderabad Express',0,0,'Mgr Chennai Central','Hyderabad'),(12295,'Sanghamitra Express',0,0,'Bengaluru','vijayawada')";
			//stmt.execute(s1);
			
			String sourceStation=request.getParameter("sourceStation");
			String destinationStation=request.getParameter("destinationStation");
			String choice=request.getParameter("choice");
			HttpSession session=request.getSession();
			session.setAttribute("sourceStation", sourceStation);
			session.setAttribute("destinationStation", destinationStation);
			if(choice.equalsIgnoreCase("reservation")) {
			response.sendRedirect("Reservation.html");
			}
			else
				
				response.sendRedirect("Cancellation.html");
				
			
			
			
			
			
		} catch (SQLException e) {
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
