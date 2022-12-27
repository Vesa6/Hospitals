package hospitalSystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/hospital.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insertPatient(String social_security_number, String name, String date_of_birth, int gender, String contact_no) {
    	
    	String sql = "INSERT INTO patients(ssn,name,date_of_birth,gender,contact_no) VALUES(?,?,?,?,?)";
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, social_security_number);
            pstmt.setString(2, name);
            pstmt.setString(3, date_of_birth);
            pstmt.setInt(4, gender);
            pstmt.setString(5, contact_no);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
  }
    
    /**
     * @param id
     * @param name
     * @param specialty
     */
    public void insertDoctor(int id, String name, String specialty) {
    String sql = "INSERT INTO doctors (id, name, specialty) VALUES (?,?,?)";
    
    try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, id);
        pstmt.setString(2, name);
        pstmt.setString(3, specialty);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    }
    
    public void insertVisit(int doctor_id, String patient_ssn, String visit_date, String visit_info ) {
    String sql = "INSERT INTO hospital_visits (doctor_id, patient_ssn, visit_date, visit_info) VALUES (?,?,?,?)";
    
    try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, doctor_id);
        pstmt.setString(2, patient_ssn);
        pstmt.setString(3, visit_date);
        pstmt.setString(4, visit_info);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    }
    
    public void selectVisits(String patient_ssn) {
    	String sql = "SELECT * FROM hospital_visits WHERE patient_ssn = ?";
    	
    	        try (Connection conn = this.connect();
    	             PreparedStatement stmt  = conn.prepareStatement(sql)) {
    	             stmt.setString(1, patient_ssn);
    	             ResultSet rs = stmt.executeQuery();
    	          
    	            
    	            // loop through the result set
    	            while (rs.next()) {
    	                System.out.println(rs.getInt("doctor_id") +  "\t" + 
    	                                   rs.getString("patient_ssn") + "\t" +
    	                                   rs.getString("visit_date") + "\t" +
    	                                   rs.getString("visit_info"));
    	            }
    	        } catch (SQLException e) {
    	            System.out.println(e.getMessage());
    	        }
    	    }
    	    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Main app = new Main();
        Scanner myScan = new Scanner(System.in);
        
        while (true) {
        
        System.out.println("\nWelcome to the **WORKING NAME** Abbot-healthcare **WORKING NAME** system. \n\n");
        System.out.println("Please choose an option.");
        System.out.println("1. select visits   2. insert visit   3. insert doctor   4. insert patient   5. exit");
        int choice = Integer.valueOf(myScan.nextLine());
        
        switch (choice) {
        	
        case 1: System.out.println("To view visits, please enter the social security number of the patient.");
        		String ssn = myScan.nextLine();
        		app.selectVisits(ssn);
        		break;
        		
        case 2: System.out.println("To insert a visit, please provide the following information: \n");
        		System.out.print("Doctor id: ");
        		int docID = Integer.valueOf(myScan.nextLine());
        		System.out.println("Patient id: ");
        		String patID = myScan.nextLine();
        		System.out.println("Visit date:");
        		String date = myScan.nextLine();
        		System.out.println("Any extra information about the visit (follow-up, etc.)");
        		String info = myScan.nextLine();
        		
        		app.insertVisit(docID, patID, date, info);
        		
        case 5: 
        	System.out.println("Bye!");
        	myScan.close();
        	System.exit(0);
        }
        }
        //app.selectVisits("112244-1234");
        /*app.insertVisit(123, "112244-1234", "01.01.2022", "Follow-up appointment");
        app.insertDoctor(123, "John Smith", "Surgery");
        app.insertPatient("112244-1234", "Erkki Esimerkki", "01.01.2011", 0, "1234567890");*/
    }

   
}