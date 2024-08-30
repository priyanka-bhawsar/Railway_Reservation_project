package oasis_tasks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Task1 {
 private static final int min = 1000;
 private static final int max = 9999;
 
 public static class user{
	 private String username;
	 private String password;
	 
	 Scanner sc = new Scanner(System.in);
	 
	 public user() {
		 
		 }

	public String getUsername() {
		System.out.println("Enter Username :: " + " ");
		username = sc.nextLine();
		return username;
	}

	
	public String getPassword() {
		System.out.println("Enter Password :: " + " ");
		password	= sc.nextLine();
		return password;
	}
 
	 }
 
 public static class PnrRecord{
	 private int pnrNumber;
	 private String  passengerName;
	 private String trainNumber ;
	 private String  classType;
	 private String  jouneryDate;
	 private String  from;
	 private String  to;
	 
	 
	 Scanner sc = new Scanner(System.in);


	public int getPnrNumber() {
		Random random  = new Random();
		pnrNumber = random.nextInt(max)+min;
		return pnrNumber;
	}


	public String getPassengerName() {
		System.out.println("Enter The  Passenger Name :: " );
		passengerName = sc.nextLine();
		return passengerName;
	}


	public String getTrainNumber() {
		System.out.println("Enter The Train Number :: " );
		trainNumber = sc.nextLine();
		return trainNumber;
	}


	public String getClassType() {
		System.out.println("Enter The Class Type :: " );
		classType = sc.nextLine();
		return classType;
	}


	public String getJouneryDate() {
		System.out.println("Enter The Junerney Date as 'YYYY-MM-DD' format  :: " );
	     sc.nextLine();
		return jouneryDate;
	}


	public String getFrom() {
		System.out.println("Enter The Starting Place :: " );
		from = sc.nextLine();
		return from;
	}


	public String getTo() {
		System.out.println("Enter The Destination  Place :: " );
		to = sc.nextLine();
		return to;
	}


	public Scanner getSc() {
		return sc;
	}
	 
	
 }
 
 
 public static void main(String args[]) {
	 Scanner sc = new Scanner(System.in);
	 user ul = new user();
	 
	 String username = ul.getUsername();
	 String password = ul.getPassword();
	 
	 String url = "jdbc:mysql://localhost:3306/tasks";
	 try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
	try(Connection con = DriverManager.getConnection(url,username,password)){
		System.out.println("User Connection Granted.\n");
		while(true) {
			String InsertQuery = "insert into railway_reservation_  values(?,?,?,?,?,?,?)";
			String DeleteQuery = "delete from railway_reservation_ where pnr_number = ? ";
			String showquery = "select * from railway_reservation_";
			
			System.out.println("Enter the choice : \n");
			System.out.println("1 Insert  Record. : \n  ");
			System.out.println("2 Delete Record. :  \n ");
			System.out.println("3 Show All Records. : \n  ");
			System.out.println("4 Exit. :  \n  ");
			 int choice = sc.nextInt();
			
			if(choice == 1) {
				PnrRecord p1 = new PnrRecord();
				 int pnr_number = p1.getPnrNumber();
				 String  passengerName = p1.getPassengerName();
				 String trainNumber = p1.getTrainNumber() ;
				 String  classType = p1.getClassType();
				 String  jouneryDate = p1.getJouneryDate();
				 String  from = p1.getFrom();
				 String  to = p1.getTo();
				 
				 try(PreparedStatement ps = con.prepareStatement(InsertQuery)){
					ps.setInt(1, pnr_number);
					ps.setString(2,passengerName);
					ps.setString(3,trainNumber);
					ps.setString(4,classType);
					ps.setString(5,jouneryDate);
					ps.setString(6,from);
					ps.setString(7,to);
					
					int row = ps.executeUpdate();
					if(row > 0) {
						System.out.println("Record added sucessfully.");
					}
					else {
						System.out.println("No record were added.");
					}
				 
			}
				 catch(SQLException se) {
					System.out.println("SQLException" + se.getMessage() );
					
					
				 }	
				 }
			//insert data end 
			
			//delete query start 
			else if(choice == 2 ) {
				System.out.println("enter the PNR number to delete thr record : ");
				int pnrNumber = sc.nextInt();
				try (PreparedStatement ps = con.prepareStatement(DeleteQuery)){
					ps.setInt(1, pnrNumber);
					int row = ps.executeUpdate();
					if(row > 0) {
						System.out.println("record deleted sucessfully : ");
						
						
					}
					else {
						System.out.println("no record where deleted : ");
					}
				}
				catch(SQLException se) {
					System.out.println("SQLException" + se.getMessage() );
					
					
				 }
				
			}
			//delete query end 
			
			//select start 
			else if(choice == 3){
				try(PreparedStatement ps = con.prepareStatement(showquery);
					ResultSet rs = ps.executeQuery()){
					System.out.println("\n All record printing. \n");
					while(rs.next()) {
						String pnrNumber = rs.getString("pnr_number" );
						String passengerName = rs.getString("passenge_name");
						String trainNumber = rs.getString("train_number");
						String classType = rs.getString("class_type");
						String jouneryDate = rs.getString("journey_date");
						String fromLocation = rs.getString("from_location");
						String toLocation = rs.getString("to_Location");
						
						System.out.println("PNR Number : "   + pnrNumber);
						System.out.println("Passenger Name : "   + passengerName);
						System.out.println("Train Number : "   + trainNumber);
						System.out.println("Class Type : "   + classType);
						System.out.println("Journey Type : "   + jouneryDate);
						System.out.println("From Location : "   + fromLocation);
						System.out.println("To Location : "   + toLocation);
						System.out.println( " -------------------------");
					}
				}
				catch(SQLException se) {
					System.out.println("SQLException" + se.getMessage() );
					
					
				 }
				
			}
			
			
			//select end 
			
			
			//exit start 
			else if(choice == 4) {
				System.out.println("Exiting the programme. \n");
				break;
			}
			else {
				System.out.println("Invalid Choice Entered");
			}
			//exit end 
			//.connection end	
		}
		//driver
	}

	catch (SQLException e) {
		System.out.println("SQLException" + e.getMessage() );
		e.printStackTrace();
	}
	} catch (ClassNotFoundException ce) {
		
		System.out.println("Error Loding jdbc Driver"+ ce.getMessage() );
		
	}
	 sc.close();
 }
}
