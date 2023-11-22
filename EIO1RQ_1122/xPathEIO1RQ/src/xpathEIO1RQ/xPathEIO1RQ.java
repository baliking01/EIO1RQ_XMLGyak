package xpathEIO1RQ;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class xPathEIO1RQ {

	public static void main(String args[])throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true); // never forget this!
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse("studentEIO1RQ.xml");
		
		XPathFactory xpathfactory = XPathFactory.newInstance();
	    XPath xpath = xpathfactory.newXPath();
	    
	    System.out.println("1) Válassza ki az összes student elemet, amely a class gyermekei!");
	    XPathExpression expr = xpath.compile("//class/student");
	    Object result = expr.evaluate(doc, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;
	    for (int i = 0; i < nodes.getLength(); i++) {
	    	System.out.print(doc.getElementsByTagName("keresztnev").item(0).getTextContent());
	    	System.out.print(doc.getElementsByTagName("vezeteknev").item(0).getTextContent());
	    	System.out.print(doc.getElementsByTagName("becenev").item(0).getTextContent());
	    	System.out.print(doc.getElementsByTagName("kor").item(0).getTextContent());
	    }
	    
	    System.out.println("2) Válassza ki azt a student elemet, amely rendelkezik 'id' attribútummal és értéke '02'");
	    expr = xpath.compile("//class/student[@id='02']");
	    result = expr.evaluate(doc, XPathConstants.NODESET);
	    nodes = (NodeList) result;
	    for (int i = 0; i < nodes.getLength(); i++) {
	    	NodeList children = nodes.item(i).getChildNodes();
	    	for(int j = 0; j < children.getLength(); j++){
	    		System.out.print(children.item(j).getTextContent());
	    	}
	    }
	}
}
