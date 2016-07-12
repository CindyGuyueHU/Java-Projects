import java.io.FileWriter;
import java.io.Writer;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

public class PersonalOrganiserTest {

	/**
	 * This class automatically create test cases
	 */
	
	static JSONObject jsonContact = new JSONObject();

	static int TestNumber = 100;
	
	static String FILENAME = "TestContact.json";
	
	static String cap = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static String sml = "abcdefghijklmnopqrstuvwxyz";
	static String num = "0123456789";
	static String space = " ";
	static String[] mail = {"@163.com", "@gmail.com", "@hotmail.com"};
	
	
	public static String RandomString(int length, String str, int slenth) {  
	    Random random = new Random();  
	    StringBuffer buf = new StringBuffer();  
	    for (int i = 0; i < length; i++) {  
	        int num = random.nextInt(slenth);  
	        buf.append(str.charAt(num));  
	    }  
	    return buf.toString();  
	}  
	

	public static void main(String[] args) throws Exception {
		
		for (int i = 0; i < TestNumber; i++) {
			
			
			int r = (int) (1 + Math.random()*10);
			String fname = RandomString(1, cap, 26) + RandomString(r, sml, 26);
			//System.out.println(fname);
			
			r = (int) (1 + Math.random()*5);
			String lname = RandomString(1, cap, 26) + RandomString(r, sml, 26);
			
			String name = lname + ", "+ fname;
			
			r = (int) (1 + Math.random()*8);
			String pnumber = "04" + RandomString(r, num, 10);
			
			r = (int) (Math.random() * 2);
			int r2 = (int) (Math.random()* 15);
			String email = RandomString(r2, sml, 26) + mail[r];
			
			
			String dob = RandomString(2, num, 10) + "-" + RandomString(2, num, 10) + "-" + RandomString(2, num, 10);
			
			
			r = (int) (1+ Math.random()*3);
			r2 = (int) (1 + Math.random()*10);
			String address = RandomString(r, num, 10) + " " + RandomString(r2, sml, 26);
			
			JSONObject c = new JSONObject();
			c.put("PhoneNumber", pnumber);
			c.put("EMailAddress", email);
			c.put("HomeAddress", address);
			c.put("DateOfBirth", dob);
			c.put("PhotoPath", "Image/Default/defaultPhoto.jpg");
			
			jsonContact.put(name, c);
			save();
			
		}
		
	}
	

	
	public static void save() throws Exception {
		Writer w = new FileWriter(FILENAME);
		jsonContact.write(w);
		w.close();
	}
	
}