package com.railway;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class Cancellation
 */
@WebServlet("/Cancellation")
public class Cancellation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cancellation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/train","dedeepya","6281869953");
			int TrainNumber=Integer.parseInt(request.getParameter("TrainNumber"));
			String PassengerName=request.getParameter("PassengerName");
			PreparedStatement ps=con.prepareStatement("select * from passenger_details where passenger_name=? and TrainNumber_booked=?");
			ps.setString(1,PassengerName);
			ps.setInt(2, TrainNumber);
			ResultSet rs1=ps.executeQuery();
			rs1.next();
			PreparedStatement ps1=con.prepareStatement("select * from train_details where TrainNumber=?");
			ps1.setInt(1, TrainNumber);
			ResultSet rs2=ps1.executeQuery();
			rs2.next();
			PreparedStatement ps2=con.prepareStatement("select count(*)as total from passenger_details where TrainNumber_booked=? and booking_status='RacWaitingList'");
			ps2.setInt(1, TrainNumber);
			ResultSet rs3=ps2.executeQuery();
			rs3.next();
			PrintWriter out=response.getWriter();
			
			String  booking_status;
			if(rs1.getString("booking_status").equalsIgnoreCase("confirmed")) {
				if(rs3.getInt("total")>0) {
					int rac_tickets=rs2.getInt("TotalRacSeats");
					rac_tickets=rac_tickets-1;
					PreparedStatement update_passengerDetails=con.prepareStatement("update passenger_details set booking_status='confirmed' where TrainNumber_booked=? and booking_status='RacWaitingList'limit 1");
					update_passengerDetails.setInt(1,TrainNumber);
					
					update_passengerDetails.execute();
					PreparedStatement update_racSeats=con.prepareStatement("update train_details set TotalRacSeats=? where TrainNumber=?");
					update_racSeats.setInt(1, rac_tickets);
					update_racSeats.setInt(2,TrainNumber);
					update_racSeats.execute();
				}
				else {
				int confirmed_tickets=rs2.getInt("TotalConfirmedSeats");
				
				System.out.println(confirmed_tickets);
				confirmed_tickets=confirmed_tickets-1;
				
				PreparedStatement update_confirmedSeats=con.prepareStatement("update train_details set TotalConfirmedSeats=? where TrainNumber=?");
				update_confirmedSeats.setInt(1, confirmed_tickets);
				update_confirmedSeats.setInt(2,TrainNumber);
				update_confirmedSeats.execute();
				}
				
				PreparedStatement delete_passengerDetails=con.prepareStatement("update passenger_details set booking_status='Cancelled' where passenger_name=? and TrainNumber_booked=? ");
				delete_passengerDetails.setString(1,PassengerName);
				delete_passengerDetails.setInt(2, TrainNumber);
				delete_passengerDetails.execute();
				
				out.println("Cancellation is successful");
				
				
				
				
			}
			else if(rs1.getString("booking_status").equalsIgnoreCase("RacWaitingList")) {
				int rac_tickets=rs2.getInt("TotalRacSeats");
				rac_tickets=rac_tickets-1;
				PreparedStatement update_trainDetails=con.prepareStatement("update train_details set TotalRacSeats=? where TrainNumber=?");
				update_trainDetails.setInt(1, rac_tickets);
				update_trainDetails.setInt(2,TrainNumber);
				update_trainDetails.execute();
				PreparedStatement delete_passengerDetails=con.prepareStatement("update passenger_details set booking_status='Cancelled' where passenger_name=? and TrainNumber_booked=?");
				delete_passengerDetails.setInt(2, TrainNumber);
				delete_passengerDetails.setString(1,PassengerName);
				delete_passengerDetails.execute();
				out.println("Cancellation is successful");
				
			}
			else
				out.println("Cancellation is not successful");
				
			
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
