package domEIO1RQ1115;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class DOMQuery {
	public static void main(String args[]){
		
		try {
	         File inputFile = new File("orarendEIO1RQ.xml");
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         
	         System.out.print("Root element: ");
	         System.out.println(doc.getDocumentElement().getNodeName());
	         NodeList nList = doc.getElementsByTagName("ora");
	         System.out.println("----------------------------");
	         
	         List<String> classes = new ArrayList<String>();
	         
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element eElement = (Element) nNode;
	               if(!eElement.getAttribute("típus").equals("elõadás")) continue;
	               NodeList properties = eElement.getElementsByTagName("targy");
	               classes.add(properties.item(0).getTextContent());
	            }
	         }
	         
	         System.out.println("a)");
	         StringJoiner joiner = new StringJoiner(", ");
	         for(String lecture : classes){
	        	 joiner.add(lecture);
	         }
	         String joinedString = "Kurzusnév: [" + joiner.toString() + "]";
	         System.out.println(joinedString);
	         
	         System.out.println("b)");
	         {
	        	 Node instance = nList.item(0);
	        	 System.out.println(instance.getNodeName());
	        	 Element elem = (Element)instance;
	        	 NodeList properties = elem.getChildNodes();
	        	 for(int i = 0; i < properties.getLength(); i++){
	        		 System.out.print(properties.item(i).getNodeName() + ": " + properties.item(i).getTextContent());
	        	 }
	         }
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}
}
