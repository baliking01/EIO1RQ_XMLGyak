package eio1rq;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;




public class JSONRead {
	public static void main(String[] args) {
		try(FileReader reader = new FileReader("orarendEIO1RQ.json")){



			//Parse
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(reader);
			
			//Root, majd óra lista lekérése
			JSONObject root = (JSONObject) jsonObject.get("orarendEIO1RQ");
			JSONArray lessons = (JSONArray) root.get("ora");
			
			//Óra adatok kiírása
			System.out.println("Orarend\n");
			for(int i=0; i<lessons.size(); i++) {
				JSONObject lesson = (JSONObject) lessons.get(i);
				JSONObject time = (JSONObject) lesson.get("idopont");
				System.out.println("Targy: "+lesson.get("targy"));
				System.out.println("Idopont: "+time.get("nap")+" "+time.get("tol")+"-"+time.get("ig"));
				System.out.println("Helyszin: "+lesson.get("helyszin"));
				System.out.println("Oktato: "+lesson.get("oktato"));
				System.out.println("Szak: "+lesson.get("szak")+"\n");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}