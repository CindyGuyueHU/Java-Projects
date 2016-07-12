import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;


/*
 * comp6442 Assignment 1 - Personal Organiser
 * Author: Guyue HU, u5608260
 * ANU
 * 
 */

public class ContactTest {
	@Test
	//test two branches which the .json file already exists, or create new .json file.
	public void testload() throws Exception {
		Contact contact = new Contact();
		// If the .jason file exists, should pass this case
		assertEquals(contact.load(), 0, 0.1);
		// If the .jason file doesn't exist
		//assertEquals(contact.load(), 1, 0.1);
	}
	
	//test if the json object is saved in the right .json file
	public void testsave() throws Exception {
		Contact contact = new Contact();
		assertEquals(contact.save(),"Contact.json");
	}
	
	//test if a single person is added into the .json file correctly
	public void testadd() throws Exception {
		Contact contact = new Contact();
		
		JSONObject testP = new JSONObject();
		HashMap<String,String> t = new HashMap<String, String>();
		t.put("PhoneNumber", "test0000");
		t.put("EMailAddress", "testEmail");
		t.put("HomeAddress", "testAddress");
		t.put("DateOfBirth", "testDOB");
		t.put("PhotoPath", "testPath");
		
		testP.put("PhoneNumber", "test0000");
		testP.put("EMailAddress", "testEmail");
		testP.put("HomeAddress", "testAddress");
		testP.put("DateOfBirth", "testDOB");
		testP.put("PhotoPath", "testPath");
		
		assertEquals(contact.add("testname",t), testP);
	}
	
	//test if a record can be deleted by its key(name)
	public void testdelete() throws Exception {
		Contact contact = new Contact();
		assertEquals(contact.delete("testname"), "testname");
	}
	
	
	//test if the right number of recorded can be decoded from the json 
	public void testupdateNameList() throws Exception {
		Contact contact = new Contact();
		int totalNumberofRecords = 100;
		assertEquals(contact.updateNameList(), totalNumberofRecords, 0.1);
	}
	
}