package hu.domparse.eio1rq;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomReadEIO1RQ {
	public static void main(String args[]) {
		System.out.println("2a) Adatolvasás");
		
		try {
			File inputFile = new File("XMLeio1rq.xml");
			System.out.println(inputFile);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true); // never forget this!
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(inputFile);
			
			XPathFactory xpathfactory = XPathFactory.newInstance();
			XPath xpath = xpathfactory.newXPath();
			
			XPathExpression expr =  xpath.compile("*");
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
		    NodeList nodes = (NodeList) result;
		    
		    for(int i = 0; i < nodes.getLength(); i++) {
		    	for(int j = 0; j < nodes.item(i).getChildNodes().getLength(); j++) {
		    		if(nodes.item(i).getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE) {
		    		//System.out.print(children.item(i).getNodeType());
		    		System.out.println(nodes.item(i).getChildNodes().item(j).getTextContent());
		    		}
		    	}
		    	
		    }
		    
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
