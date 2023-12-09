package hu.domparse.eio1rq;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DomQueryEIO1RQ {
	public static void main(String args[]) {
		try {
			// XML fájl beolvasása és dokumentum objekt elõkészítése
			File inputFile = new File("XMLeio1rq.xml");
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(inputFile);
	        doc.getDocumentElement().normalize();
	        
	        // Gyökérelem azonosítása
	        System.out.print("Gyökérelem: ");
	        System.out.println(doc.getDocumentElement().getNodeName());
	        NodeList nodes = doc.getElementsByTagName("rendelés");
	        System.out.println("----------------------------");
	        
	        // A rendelések kilistázása
	        System.out.println("\nAz összes rendelés listája");
	        System.out.println("----------------------------");
	        for(int i = 0; i < nodes.getLength(); i++) {
	        	Node node = nodes.item(i);
	        	if (node.getNodeType() == Node.ELEMENT_NODE) {
	        		 Element eElement = (Element) node;
	        		 System.out.println(eElement.getElementsByTagName("termék_név").item(0).getTextContent());
	        	}
	        }
	        
	        // A cégnél található részlegek számossága
	        System.out.println("\nRészlegek száma");
	        System.out.println("----------------------------");
	        nodes = doc.getElementsByTagName("részleg");
	        System.out.println(nodes.getLength());
	        
	        
	        // Egyes részlegekre érkezett megrendelések száma
	        System.out.println("\nMelyik részlegre mennyi megrendelés érkezett");
	        System.out.println("----------------------------");
	        int[] rendelesek = new int[nodes.getLength()];
	        
	        nodes = doc.getElementsByTagName("rendelés");
	        for(int i = 0; i < nodes.getLength(); i++) {
	        	Node node = nodes.item(i);
	        	if (node.getNodeType() == Node.ELEMENT_NODE) {
	        		 Element eElement = (Element) node;
	        		 rendelesek[Integer.parseInt(eElement.getAttribute("RÉSZLEG_FK")) - 1]++;
	        	}
	        }
	        nodes = doc.getElementsByTagName("részleg");
	        for(int i = 0; i < nodes.getLength(); i++) {
	        	Node node = nodes.item(i);
	        	if (node.getNodeType() == Node.ELEMENT_NODE) {
	        		 Element eElement = (Element) node;
	        		 System.out.println(eElement.getElementsByTagName("név").item(0).getTextContent() + ": " + rendelesek[i]);
	        	}
	        }
	        
	        // Kilistázzuk a kizárólag 2. részlegen dolgozók neveit
	        System.out.println("\nA második részlegen dolgozók listája");
	        System.out.println("----------------------------");
	        nodes = doc.getElementsByTagName("dolgozik");
	        List<String> workersID = new ArrayList<String>();
	        for(int i = 0; i < nodes.getLength(); i++) {
	        	Node node = nodes.item(i);
	        	if (node.getNodeType() == Node.ELEMENT_NODE) {
	        		 Element eElement = (Element) node;
	        		 if(eElement.getAttribute("RÉSZLEG_FK").equals("2")) {
	        			 workersID.add(eElement.getAttribute("DOLGOZÓ_FK"));
	        		 }
	        	}
	        }
	        nodes = doc.getElementsByTagName("dolgozó");
	        for(int i = 0; i < nodes.getLength(); i++) {
	        	Node node = nodes.item(i);
	        	if (node.getNodeType() == Node.ELEMENT_NODE) {
	        		 Element eElement = (Element) node;
	        		 if(workersID.contains(eElement.getAttribute("DOLGOZÓ_ID"))) {
	        			 System.out.println(eElement.getElementsByTagName("név").item(0).getTextContent());
	        		 }
	        	}
	        }
	        
	        // Bizonyos részlegek vezetõi mikor szerezték meg a pozíciót
	        System.out.println("\nEgyes részlegeket mikortól vezeti a kijelölt személy");
	        System.out.println("----------------------------");
	        nodes = doc.getElementsByTagName("vezeti");
	        NodeList workers = doc.getElementsByTagName("dolgozó");
	        NodeList divs = doc.getElementsByTagName("részleg");
	        for(int i = 0; i < nodes.getLength(); i++) {
	        	Node node = nodes.item(i);
	        	if (node.getNodeType() == Node.ELEMENT_NODE) {
	        		 Element eElement = (Element) node;
	        		 eElement.getAttribute("RÉSZLEG_FK");
	        		 
	        		 for(int j = 0; j < divs.getLength(); j++) {
	        			 Node n1 = divs.item(j);
	        			 if (n1.getNodeType() == Node.ELEMENT_NODE) {
	     	        		 Element elem1 = (Element) n1;
	     	        		 if(elem1.getAttribute("RÉSZLEG_ID").equals(eElement.getAttribute("RÉSZLEG_FK"))) {
	     	        			 System.out.print(elem1.getElementsByTagName("név").item(0).getTextContent() + " : ");
	     	        		 }
	        			 }
	        		 }
	        		 
	        		 for(int j = 0; j < workers.getLength(); j++) {
	        			 Node n1 = workers.item(j);
	        			 if (n1.getNodeType() == Node.ELEMENT_NODE) {
	     	        		 Element elem1 = (Element) n1;
	     	        		 if(elem1.getAttribute("DOLGOZÓ_ID").equals(eElement.getAttribute("DOLGOZÓ_FK"))) {
	     	        			 System.out.println(elem1.getElementsByTagName("név").item(0).getTextContent());
	     	        		 }
	        			 }
	        		 }
	        		 
	        	}
	        }
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}