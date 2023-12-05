package hu.domparse.eio1rq;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomReadEIO1RQ {
	public static void main(String args[]) {
		System.out.println("2a) Adatolvasás");
		
		try {
			File inputFile = new File("XMLeio1rq.xml");			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(inputFile);
				
		    NodeList root = doc.getChildNodes();
		    ArrayList<String> content = getAllLeaves((Node)root);
		    
		    for(int i = 0; i < content.size(); i++) {
		    	System.out.print(content.get(i));
		    }
		    
		}catch(Exception e){
			e.printStackTrace();
		}
		 
	}
	
	public static ArrayList<String> getAllLeaves(Node node) {
		ArrayList<String> content = new ArrayList<String>();
		NodeList children = node.getChildNodes();
		if(children.getLength() == 0) {
			if(node.getNodeType() != Node.COMMENT_NODE) content.add(" "+node.getTextContent());			
		}
		else {
			if(node.getNodeType() != Node.DOCUMENT_NODE) content.add(node.getNodeName());
			int len = children.getLength();
			len -= (len > 1) ? 1 : 0;
			for(int i = 0; i < len; i++) {
				content.addAll(getAllLeaves(children.item(i)));
			}
		}
		
		return content;
	}
}
