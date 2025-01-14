package Hospital_Managment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {

	private Connection con;
	
	
	public Doctor(Connection con) {
		this.con=con;
		
	}

	public void viewDoctors() {
		String query="SELECT * FROM DOCTORS";
		try {
			PreparedStatement preparedStatement=con.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("Doctors: ");
			System.out.println("+------------+--------------------+-------------------+");
			System.out.println("| Doctor_Id  | Name               | Specialization    |");
			System.out.println("+------------+--------------------+-------------------+");
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name=resultSet.getString("name");
				String specialization=resultSet.getString("specialization");
				System.out.printf("| %-10s | %-18s | %-17s |\n",id,name,specialization);
				System.out.println("+------------+--------------------+-------------------+");				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public boolean getDoctorById(int id) {
		String query = "SELECT * FROM DOCTORS WHERE ID = ?";
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
