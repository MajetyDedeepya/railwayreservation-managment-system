package com.railway;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Reservation
 */
@WebServlet("/Reservation")
public class Reservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	String booked;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Reservation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con;
		try {
			int no=Integer.parseInt(request.getParameter("TrainNumber"));
			String passengerName=request.getParameter("passengerName");
			int age=Integer.parseInt(request.getParameter("age"));
		    String phoneNumber=request.getParameter("phoneNumber");	
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/train","dedeepya","6281869953");
			PreparedStatement pstmt=con.prepareStatement("select * from train_details where fromStation=? and toStation=?");
			HttpSession session=request.getSession();
			
			pstmt.setString(1, (String) session.getAttribute("sourceStation"));
			pstmt.setString(2, (String) session.getAttribute("destinationStation"));
			ResultSet rs=pstmt.executeQuery();
			//PreparedStatement pstmt1=con.prepareStatement("create table passenger_details(passenger_name varchar(255),age int,phoneNumber varchar(255),TrainNumber_booked int,booking_status varchar(255))");
			//pstmt1.execute();
			PrintWriter out=response.getWriter();
			
			while(rs.next()) {
				if(rs.getInt("TrainNumber")==no) {
					if(rs.getInt("TotalConfirmedSeats")<63 ) {
						int confirmedSeats=rs.getInt("TotalConfirmedSeats");
						confirmedSeats+=1;
						PreparedStatement pstmt2=con.prepareStatement("update train_details set TotalConfirmedSeats=? where TrainNumber=?");
						pstmt2.setInt(1,confirmedSeats);
						pstmt2.setInt(2,no);
						pstmt2.execute();
						booked="Confirmed";
						out.println("Reservation Sucessfull");
						
					}
					else if(rs.getInt("TotalRacSeats")<18) {
						int racSeats=rs.getInt("TotalRacSeats");
						racSeats+=1;
						PreparedStatement pstmt3=con.prepareStatement("update train_details set TotalRacSeats=? where TrainNumber=?");
						pstmt3.setInt(1,racSeats);
						pstmt3.setInt(2,no);
						pstmt3.execute();
						booked="RacWaitingList";
						out.println("Reservation Sucessful");
						
						
						
					}
					else {
						booked="NotConfirmed";
						out.println("No tickets Available");
					}
					
					
				}
				
			}
			PreparedStatement pstmt4=con.prepareStatement("insert into passenger_details values(?,?,?,?,?)");
			pstmt4.setString(1, passengerName);
			pstmt4.setInt(2, age);
			pstmt4.setString(3,phoneNumber);
			pstmt4.setInt(4,no);
			pstmt4.setString(5,booked);
			pstmt4.execute();
			
			
			
			
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
