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
import java.util.StringJoiner;

public class DomQueryEIO1RQ {
	public static void main(String args[]) {
		try {
			File inputFile = new File("XMLeio1rq.xml");
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(inputFile);
	        doc.getDocumentElement().normalize();
	        
	        System.out.print("2b) Gy�k�relem: ");
	        System.out.println(doc.getDocumentElement().getNodeName());
	        NodeList nodes = doc.getElementsByTagName("rendel�s");
	        System.out.println("----------------------------");
	        
	        
	        System.out.println("\nAz �sszes rendel�s list�ja");
	        System.out.println("----------------------------");
	        for(int i = 0; i < nodes.getLength(); i++) {
	        	Node node = nodes.item(i);
	        	if (node.getNodeType() == Node.ELEMENT_NODE) {
	        		 Element eElement = (Element) node;
	        		 System.out.println(eElement.getElementsByTagName("term�k_n�v").item(0).getTextContent());
	        	}
	        }
	        
	        System.out.println("\nR�szlegek sz�ma");
	        System.out.println("----------------------------");
	        nodes = doc.getElementsByTagName("r�szleg");
	        System.out.println(nodes.getLength());
	        
	        
	        
	        System.out.println("\nMelyik r�szlegre mennyi megrendel�s �rkezett");
	        System.out.println("----------------------------");
	        int[] rendelesek = new int[nodes.getLength()];
	        
	        nodes = doc.getElementsByTagName("rendel�s");
	        for(int i = 0; i < nodes.getLength(); i++) {
	        	Node node = nodes.item(i);
	        	if (node.getNodeType() == Node.ELEMENT_NODE) {
	        		 Element eElement = (Element) node;
	        		 rendelesek[Integer.parseInt(eElement.getAttribute("R�SZLEG_FK")) - 1]++;
	        	}
	        }
	        nodes = doc.getElementsByTagName("r�szleg");
	        for(int i = 0; i < nodes.getLength(); i++) {
	        	Node node = nodes.item(i);
	        	if (node.getNodeType() == Node.ELEMENT_NODE) {
	        		 Element eElement = (Element) node;
	        		 System.out.println(eElement.getElementsByTagName("n�v").item(0).getTextContent() + ": " + rendelesek[i]);
	        	}
	        }
	        
	        
	        System.out.println("\nAz m�sodik r�szlegen dolgoz�k list�ja");
	        System.out.println("----------------------------");
	        nodes = doc.getElementsByTagName("dolgozik");
	        List<String> workersID = new ArrayList<String>();
	        for(int i = 0; i < nodes.getLength(); i++) {
	        	Node node = nodes.item(i);
	        	if (node.getNodeType() == Node.ELEMENT_NODE) {
	        		 Element eElement = (Element) node;
	        		 if(eElement.getAttribute("R�SZLEG_FK").equals("2")) {
	        			 workersID.add(eElement.getAttribute("DOLGOZ�_FK"));
	        		 }
	        	}
	        }
	        nodes = doc.getElementsByTagName("dolgoz�");
	        for(int i = 0; i < nodes.getLength(); i++) {
	        	Node node = nodes.item(i);
	        	if (node.getNodeType() == Node.ELEMENT_NODE) {
	        		 Element eElement = (Element) node;
	        		 if(workersID.contains(eElement.getAttribute("DOLGOZ�_ID"))) {
	        			 System.out.println(eElement.getElementsByTagName("n�v").item(0).getTextContent());
	        		 }
	        	}
	        }
	        
	        
	        System.out.println("\nEgyes r�szlegeket mikort�l vezeti a kijel�lt szem�ly");
	        System.out.println("----------------------------");
	        nodes = doc.getElementsByTagName("vezeti");
	        NodeList workers = doc.getElementsByTagName("dolgoz�");
	        NodeList divs = doc.getElementsByTagName("r�szleg");
	        for(int i = 0; i < nodes.getLength(); i++) {
	        	Node node = nodes.item(i);
	        	if (node.getNodeType() == Node.ELEMENT_NODE) {
	        		 Element eElement = (Element) node;
	        		 eElement.getAttribute("R�SZLEG_FK");
	        		 
	        		 for(int j = 0; j < divs.getLength(); j++) {
	        			 Node n1 = divs.item(j);
	        			 if (n1.getNodeType() == Node.ELEMENT_NODE) {
	     	        		 Element elem1 = (Element) n1;
	     	        		 if(elem1.getAttribute("R�SZLEG_ID").equals(eElement.getAttribute("R�SZLEG_FK"))) {
	     	        			 System.out.print(elem1.getElementsByTagName("n�v").item(0).getTextContent() + " : ");
	     	        		 }
	        			 }
	        		 }
	        		 
	        		 for(int j = 0; j < workers.getLength(); j++) {
	        			 Node n1 = workers.item(j);
	        			 if (n1.getNodeType() == Node.ELEMENT_NODE) {
	     	        		 Element elem1 = (Element) n1;
	     	        		 if(elem1.getAttribute("DOLGOZ�_ID").equals(eElement.getAttribute("DOLGOZ�_FK"))) {
	     	        			 System.out.println(elem1.getElementsByTagName("n�v").item(0).getTextContent());
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
