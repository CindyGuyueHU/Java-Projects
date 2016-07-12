import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * comp6442 Assignment 1 - Personal Organiser
 * Author: Guyue HU, u5608260
 * ANU
 * 
 */

public class Contact {
	
	
	/*
	 * This class manage the json file and make the data persist when the application is exited
	 * 
	 */
	
	JSONObject jsonContact = null;
	JSONArray contactArray = new JSONArray();
	ArrayList<String> nameList = new ArrayList<String>();
	
	static final String FILENAME = "Contact.json";
	
	public Contact() throws Exception {
		try{
			load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		updateNameList();
	}
	
    // Load json object
	int load() throws Exception {
		int i = 0;
		File f = new File(FILENAME);
		if (f.exists()) {
			BufferedReader bufferReader = new BufferedReader(new FileReader(f));
			StringBuffer stringBuffer = new StringBuffer();
			String line = null; 
			while ((line = bufferReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			String jsonFileContent = stringBuffer.toString();
			JSONObject jsonObj = new JSONObject(jsonFileContent);
			jsonContact = jsonObj;
			bufferReader.close();
			i = 0;
			return i;
		} else {
			jsonContact = new JSONObject();
			i = 1;
			return i;
		}
	}
	
	// Save current jsonContact into json file
	String save() throws Exception {
		Writer w = new FileWriter(FILENAME);
		jsonContact.write(w);
		w.close();
		return FILENAME;
	}
	
	// add a new contact into the json Object
	JSONObject add(String name, HashMap<String, String> person) throws Exception {
		jsonContact.put(name, person);
		updateNameList();
		save();
		return jsonContact.getJSONObject(name);
	}
	
	// delete a contact from the json Object
	String delete(String name) throws Exception{
		jsonContact.remove(name);
		updateNameList();
		save();
		return name;
	}
	
	// update the list of contact names
	int updateNameList() throws JSONException {
		int i = 0;
		Iterator it = jsonContact.keys();
		JSONObject value = new JSONObject();
		while (it.hasNext()) {
			String key = (String) it.next();
			value = jsonContact.getJSONObject(key);
			nameList.add(key);
			i++;
		}
		nameList.sort(null);
		return i;
	}
	
	// return the list of contact names
	ArrayList<String> getNameList() {
		return nameList;
	}
	
	// return the list of contact being searched
	ArrayList<String> getSearchNamelist(String searchString) {
		ArrayList<String> searchResult = new ArrayList<String>();
		for (int i = 0; i < nameList.size(); i++) {
			String s1 = nameList.get(i).toLowerCase(Locale.US);
			String s2 = searchString.toLowerCase(Locale.US);
			if (s1.indexOf(s2) != -1) {
				searchResult.add(nameList.get(i));
			}
		}
		return searchResult;
	}
	
	// return the whole json object
	JSONObject getJsonContact() {
		return jsonContact;
	}
	
}