import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONWrite {
	public static void main(String[] args) {
		JSONArray lessons = new JSONArray();

		lessons.add(createLesson("Mesterséges intelligencia", "kedd", "10", "12", "XXXII. előadó", "Kunné Dr. Tamás Judit", "G3BIW"));
		lessons.add(createLesson("Adatkezelés XML-ben", "kedd", "12", "14", "XXXII. előadó", "Dr. Bednarik László", "G3BIW"));
		lessons.add(createLesson("Webes alkalmazások (Java)", "kedd", "14", "16", "online", "Selmeci Viktor", "G3BIW"));
		lessons.add(createLesson("Adatkezelés XML-ben", "szerda", "10", "12", "Inf. 101", "Dr. Bednarik László", "G3BIW"));
		lessons.add(createLesson("Mesterséges intelligencia", "csütörtök", "10", "12", "I. előadó", "Fazekas Levente", "G3BIW"));
		JSONObject root = new JSONObject();
		root.put("ora", lessons);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("orarend_EIO1RQ", root);
		
		fileWrite(jsonObject, "orarendEIO1RQ.json");
		consoleWrite(jsonObject);
	}
	
	private static void fileWrite(JSONObject jsonObject, String fileName) {
		try(FileWriter writer = new FileWriter(fileName)){
			writer.write(indentJson(jsonObject.toJSONString()));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void consoleWrite(JSONObject jsonObject) {
		System.out.println("A felépített JSON dokumentum tartalma:\n");
		JSONObject root = (JSONObject) jsonObject.get("orarend_EIO1RQ");
		JSONArray lessons = (JSONArray) root.get("ora");
		for(int i=0; i<lessons.size(); i++) {
			JSONObject lesson = (JSONObject) lessons.get(i);
			JSONObject time = (JSONObject) lesson.get("idopont");
			System.out.println("Tárgy: "+lesson.get("targy"));
			System.out.println("Időpont: "+time.get("nap")+" "+time.get("tol")+"-"+time.get("ig"));
			System.out.println("Helyszín: "+lesson.get("helyszin"));
			System.out.println("Oktató: "+lesson.get("oktato"));
			System.out.println("Szak: "+lesson.get("szak")+"\n");
		}
	}