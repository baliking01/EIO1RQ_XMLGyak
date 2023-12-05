package hu.domparse.eio1rq;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomRead2_EIO1RQ {
	public static void main(String args[]) {
		System.out.println("2d) Adatírás");
		
		try {
			File inputFile = new File("XMLeio1rq.xml");			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(inputFile);
				
		    NodeList root = doc.getChildNodes();
		    ArrayList<String> content = getAllLeaves((Node)root);
		    content.add("");
		    
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
			String c = node.getTextContent();
			if(node.getNodeType() == Node.COMMENT_NODE) c = "<!--" + c + "-->";
			content.add(c);
		}
		else {
			String c = node.getNodeName();
			if(node.getNodeType() != Node.DOCUMENT_NODE) content.add("<" + c);
			if(node.getAttributes() != null) {
				for(int i = 0; i < node.getAttributes().getLength(); i++) {
					content.add(" "+node.getAttributes().item(i).getNodeName() + 
							"=" + '"'+node.getAttributes().item(i).getNodeValue()+'"');
				}
			}
			if(node.getNodeType() != Node.DOCUMENT_NODE) content.add(">");
			for(int i = 0; i < children.getLength(); i++) {
				content.addAll(getAllLeaves(children.item(i)));
			}
			if(node.getNodeType() != Node.DOCUMENT_NODE) content.add("</" + c + ">");
		}
		
		return content;
	}
}
