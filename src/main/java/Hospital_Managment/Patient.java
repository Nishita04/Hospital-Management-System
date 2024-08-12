package Hospital_Managment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	private Connection con;
	private Scanner scanner;
	
	public Patient(Connection con,Scanner scanner) {
		this.con=con;
		this.scanner=scanner;
	}
	
	public void addPatient() {
		System.out.println("Enter patient Name: ");
		String name=scanner.next();
		System.out.println("Enter patient Age: ");
		int age=scanner.nextInt();
		System.out.println("Enter patient Gender: ");
		String gender=scanner.next();
		
		try {
			String query = "INSERT INTO PATIENTS(NAME,AGE,GENDER) VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, age);
			preparedStatement.setString(3, gender);
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows>0) {
				System.out.println("Patient added Successfully !!!");
			}
			else {
				System.out.println("failed to add patient!!! ");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();

		}
		
	}
	public void viewPatients() {
		String query="SELECT * FROM PATIENTS";
		try {
			PreparedStatement preparedStatement=con.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("patients: ");
			System.out.println("+------------+--------------------+---------+-----------+");
			System.out.println("| Patient_Id | Name               | Age     | Gender    |");
			System.out.println("+------------+--------------------+---------+-----------+");
			while(resultSet.next()) {
				int id = resultSet.getInt("ID");
				String name=resultSet.getString("NAME");
				int age = resultSet.getInt("AGE");
				String gender=resultSet.getString("GENDER");
				System.out.printf("| %-10s | %-18s | %-7s | %-9s |\n",id,name,age,gender);
				//System.out.println();
				System.out.println("+------------+--------------------+---------+-----------+");
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public boolean getPatientById(int id) {
		String query = "SELECT * FROM PATIENTS WHERE ID = ?";
		try {
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
		    if(resultSet.next()) {
		    	return true;
		    }
		    else {
		    	return false;
		    }
		}
		catch (SQLException e) {
   
			e.printStackTrace();
		}
		return false;
	}
}
