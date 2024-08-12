package Hospital_Managment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class HospitaManagementSystem {
	//private static Connection con=null;
	private static final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String username = "NISHITA";
	private static final String password = "123456";
	
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		try {
		    Connection con=DriverManager.getConnection(url,username,password);
		    Patient patient =new Patient(con, sc);
		    Doctor doctor =new Doctor(con);
		    while(true) {
		    	System.out.println("--HOSPITAL MANAGEMENT SYSTEM-- ");
		    	System.out.println("1.Add Patients");
		    	System.out.println("2.View Patients");
		    	System.out.println("3.View Doctors");
		    	System.out.println("4.Book Appointment");
		    	System.out.println("5.Exit");
		    	System.out.println("Enter Your Choice: ");
		    	int choice = sc.nextInt();
		    	
		    	switch(choice) {
		    	case 1://Add patients
		    		patient.addPatient();
		    		System.out.println();
		    		break;
		    	case 2://view patients
		    		patient.viewPatients();
		    		System.out.println();
		    		break;
		    	case 3://view doctors
		    		doctor.viewDoctors();
		    		System.out.println();
		    		break;
		    	case 4://book appointment
		    		bookAppointment(patient, doctor, con, sc);
		    		System.out.println();
		    		break;
		    	case 5:
		    		System.out.println("THANK YOU! FOR USING THIS APPLICATION");
		    		return ;
		    	  default:
		    		System.out.println("Enter valid choice!!!");
		    		break;
		    	}
		    	
		    }
		}
		catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	
	public static void bookAppointment(Patient patient,Doctor doctor, Connection con,Scanner scanner) {
		System.out.println("Enter patient id: ");
		int patientId =scanner.nextInt();
		System.out.println("Enter doctor id: ");
		int doctorId =scanner.nextInt();
		System.out.print("Enter appointment date(YYYY-MM-DD): ");
		String appointmentDate=scanner.next();
		
		if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {
			if(checkDoctorAvailibility(doctorId,appointmentDate,con)) {
				String appointmentQuery ="INSERT INTO APPOINTMENTS(PATIENT_ID,DOCTOR_ID,APP_DATE)VALUES (?, ?, ?)";
				try {
					PreparedStatement preparedstatement=con.prepareStatement(appointmentQuery);
					preparedstatement.setInt(1,patientId);
					preparedstatement.setInt(2,doctorId);
					preparedstatement.setString(3,appointmentDate);
					int rowsAffected = preparedstatement.executeUpdate();
					if(rowsAffected>0) {
						System.out.println("Appointment Booked!!!");
					}
					else {
						System.out.println("Failed to Book Appointment!!!");
					}
				}catch (Exception e) {
 
					e.printStackTrace();
				}
			}
			else {
				System.out.println("Doctor not available on this date!!");
			}
		}
		else {
			System.out.println("Either doctor or patient doesn't exist!!!");
		}
		
	}
	public static boolean checkDoctorAvailibility(int doctorId,String appointmentDate,Connection con) {
		String query ="SELECT COUNT(*) FROM APPOINTMENTS WHERE DOCTOR_ID =? AND APP_DATE=? ";
		try {
		
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setString(2, appointmentDate);
			ResultSet resultSet =preparedStatement.executeQuery();
			if(resultSet.next()) {
				int count=resultSet.getInt(1);
				if(count==0) {
					return true;
				}
				else {
					return false;
				}
			}
		}catch (Exception e) {

		
		}
		return false;
	}
}
