import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;


public class ReutersImportUtil {
	
	private final static String TRI_KEY = "3crX73rRtMVLkFPUuSD9wFKyy5oIJhnV";
	private final static int MAX_NUM_OFFSET = 50;
	private final static int CALL_DELAY_SEC = 1;
	
	private static boolean testingEnv= false;	
	private static String srchString = "STAND";
	private static String fileName = "C:\\Users\\sunil\\Downloads\\PermIDExtract.csv";

	public static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }

	public static JSONObject HttpReutersRequest(String srchString){
	//final String TRI_KEY = "3crX73rRtMVLkFPUuSD9wFKyy5oIJhnV";
		
		JSONObject jsonObject = null;
		
		try{
		   
			String postURI = "https://api.thomsonreuters.com/permid/search?";
			String paramValue = "access-token=" + TRI_KEY +"&q="+ srchString + "&entityType=organization&start=1&num="+ MAX_NUM_OFFSET;		
			
			HttpResponse<String> response = Unirest.get(postURI+paramValue)
					  .header("User-Agent", "PostmanRuntime/7.18.0")
					  .header("Accept", "*/*")
					  .header("Cache-Control", "no-cache")
					  .header("Postman-Token", "a2f3beaf-bb24-4f83-9019-f927248c0056,e719f45d-9764-4208-b7d9-3c796a76da86")
					  .header("Host", "api.thomsonreuters.com")
					  .header("Accept-Encoding", "gzip, deflate")
					  .header("Connection", "keep-alive")
					  .header("cache-control", "no-cache")
					  .asString();
		
				//System.out.println(response.getBody());
				jsonObject = new JSONObject(response.getBody());

		    }
		catch(Exception e){
			System.out.print(e.toString());
			System.out.print("Caught an exception here....");
			jsonObject = null;
		}
		return jsonObject;
	}
	
	public static List<String> extractPermID(JSONObject jsonObject) {
		List<String> PermList = new ArrayList<String>();
		try{
			JSONArray entityList = jsonObject.getJSONObject("result").getJSONObject("organizations").getJSONArray("entities");
			
//			jsonObject.length()
			for (int i = 0, size = entityList.length(); i < size; i++) {
		    		      PermList.add(entityList.getJSONObject(i).get("@id").toString());
		    }
		}
		catch(Exception e){
			System.out.print(e.toString());
		}
		
		return PermList;
	}
	
	public static boolean isJSONValid(String testJson) {
	    try {
	        new JSONObject(testJson);
	    } catch (JSONException ex) {
	        // edited, to include @Arthur's comment
	        // e.g. in case JSONArray is valid as well...
	        try {
	            new JSONArray(testJson);
	        } catch (JSONException ex1) {
	            return false;
	        }
	    }
	    return true;
	}

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		if(args.length != 3) {
			System.out.print("Usage is permExtractUtil [Testing?(Yes/No)] [Search String] [FileName]");
			System.exit(0);
		}

		if(args[0].toString().compareToIgnoreCase("Yes")!=0 && args[0].toString().compareToIgnoreCase("No")!=0  ) {
			System.out.print("Usage is permExtractUtil [Testing?(Yes/No)] [Search String] [FileName]");
			System.exit(0);
		}
		
		if(args[1].toString().compareToIgnoreCase("Yes")==0 || testingEnv) {
			testingEnv = true;
		}


		System.out.println("args passed");
		System.out.println("===========");
		System.out.println("Testing:"+ args[0].toString());
		System.out.println("Search String:"+ args[1].toString());
		System.out.println("Filename:"+ args[2].toString());
		

		int sizeExtraction=5;
		srchString = args[1].toString();
		fileName = args[2].toString()+"."+srchString;
		List<String> permList = new ArrayList<String>();
		List<JSONObject> jsonPermData = new ArrayList<JSONObject>();
		JSONObject jsonObject = HttpReutersRequest(srchString);
		permList = extractPermID(jsonObject);
		
		if (!testingEnv) {
			sizeExtraction=permList.size();
			System.out.println("\n===Download Count: "+sizeExtraction+"===");
		}

		
		//System.out.println(""+permList.toString());
		//System.out.print(permList.get(0));
		
		PrintWriter permWriter = null;
		
		//Empty the file if it already exists
		try {
		    permWriter = new PrintWriter(new FileWriter(fileName));
		} catch (FileNotFoundException e) {
		    System.out.println("Error init the given filename");
			e.printStackTrace();
		}
		finally {
			permWriter.close();
		}
		
		// Write the header record
		try {
		    permWriter = new PrintWriter(new FileWriter(fileName,true));
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		      sb.append("legalEntityIdentifier,");
		      sb.append("legalEntityName,");
		      sb.append("isIncorporatedIn,");
		      sb.append("isDomiciledIn,");
		      sb.append("ShortName,");
		      sb.append("PermID");
		      sb.append('\n');
		permWriter.write(sb.toString());
		
			try{
			
		    //https://permid.org/1-4295861160?format=json-ld&?access-token=3crX73rRtMVLkFPUuSD9wFKyy5oIJhnV&
			//permList.size()
			JSONObject jsonCurrentObj = null;
			String responseBody = null;
			
			for(int i=0; i<sizeExtraction; i++) {
					jsonCurrentObj = null;
					String postURI = permList.get(i);
					String NextpostURI="";
					if((i+1)<permList.size()) {
							 NextpostURI = permList.get(i+1);
							}
					String paramValue = "?format=json-ld&?access-token=" + TRI_KEY;		
					//System.out.print(postURI+paramValue);
					
					HttpResponse<String> response = Unirest.get(postURI+paramValue)
							  .header("User-Agent", "PostmanRuntime/7.18.0")
							  .header("Accept", "*/*")
							  .header("Cache-Control", "no-cache")
							  .header("Postman-Token", "780f4a3a-7c5e-4694-8e11-6d19e307bece,3b5ba3c7-2f6a-4501-b0af-7fba7adfbbab")
							  .header("Host", "permid.org")
							  .header("Accept-Encoding", "gzip, deflate")
							  .header("Cookie", "TR-TMS-MDaaS-SESSIONID=AW34bE9LnZsKqer7SJrU^66aa47b4")
							  .header("Connection", "keep-alive")
							  .header("cache-control", "no-cache")
							  .asString();
						
						responseBody = response.getBody();
						
						if (response.getBody().isEmpty()) {
								System.out.println("skipped");
								continue;
							}
						
						if (!isJSONValid(responseBody)) {
							System.err.println("\nError: Response received-"+responseBody);
							throw new RuntimeException();
						}
						else {
							jsonCurrentObj =new JSONObject(responseBody);
						}
						
						jsonPermData.add(jsonCurrentObj);
						//System.out.println("Current: "+postURI+paramValue+":"+i);
						//System.out.println("Next: "+NextpostURI+paramValue+":"+i);
						
						//legalEntityIdentifier   : "tr-org:hasLEI" 
						//legalEntityName    : "vcard:organization-name"
						//ApplicationID    
						//ApplicationStatus    
						//"isIncorporatedIn"  : "isIncorporatedIn" 
						//"isDomiciledIn"    : "isDomiciledIn"
						//"SICCode"    :  
						//"InternalID"   
						//"ShortName"  : "vcard:organization-name"   
						//"PermID"	: "tr-common:hasPermId"
						//Build the row string
						 
						StringBuilder sbr = new StringBuilder();
						
						
						if(jsonCurrentObj.has("tr-org:hasLEI")) {
							sbr.append(jsonCurrentObj.get("tr-org:hasLEI"));
						}
						
						sbr.append(",");
						if(jsonCurrentObj.has("vcard:organization-name")) {
							sbr.append(jsonCurrentObj.get("vcard:organization-name"));
						}
						
						sbr.append(",");
						if(jsonCurrentObj.has("isIncorporatedIn")) {
							sbr.append(jsonCurrentObj.get("isIncorporatedIn"));
						}
						
						sbr.append(",");
						if(jsonCurrentObj.has("isDomiciledIn")) {
							sbr.append(jsonCurrentObj.get("isDomiciledIn"));
						}
						sbr.append(",");
						
						if(jsonCurrentObj.has("vcard:organization-name")) {
							sbr.append(jsonCurrentObj.get("vcard:organization-name"));
						}
						sbr.append(",");
						if(jsonCurrentObj.has("tr-common:hasPermId")) {
							sbr.append(jsonCurrentObj.get("tr-common:hasPermId"));
							//System.out.println(jsonCurrentObj.has("tr-common:hasPermId"));
						}
						
						sbr.append('\n');
						//System.out.println(response.getBody());
						permWriter.write(sbr.toString());
						System.out.println("Row " + (i+1) + " done. ");
						TimeUnit.SECONDS.sleep(CALL_DELAY_SEC);
				    }
				 }
				catch(Exception e){
					System.out.print(e.toString());
					
				}
				finally {
					permWriter.close();
				}
 	}

}
