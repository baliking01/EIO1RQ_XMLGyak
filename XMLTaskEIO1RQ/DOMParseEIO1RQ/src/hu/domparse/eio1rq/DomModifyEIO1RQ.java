package hu.domparse.eio1rq;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DomModifyEIO1RQ {
	public static void main(String args[]) {
		try {
			File inputFile = new File("XMLeio1rq.xml");
			
			// Document létrehozása
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder domBuilder = dbFactory.newDocumentBuilder();
			Document doc = domBuilder.parse(inputFile);
			
			// Kiválasztott elemek módosítása
			modify(doc);
			
			// Új dokumentum mentése
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc); 
			StreamResult result = new StreamResult(new File("XMLeio1rq_Modified.xml"));
			transformer.transform(source, result);
	        
			// Konzolra kiíratás
			StreamResult resultConsole = new StreamResult(System.out);
			transformer.transform(source, resultConsole);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void modify(Document doc) {
		// 4. Rendelés nevének módosítása
		NodeList orders = doc.getElementsByTagName("rendelés");
		Element order = (Element) orders.item(3);
		order.getElementsByTagName("termék_név").item(0).setTextContent("Csirkemellfilé");
		
		// 2. Megrendelõ emai címének módosítása
		NodeList clients = doc.getElementsByTagName("megrendelõ");
		Element client = (Element) clients.item(1);
		client.getElementsByTagName("elérhetõség").item(0).setTextContent("rothmayerbt@gmail.comm");
		
		// 4. Dolgozó nevének megváltoztatása
		NodeList workers = doc.getElementsByTagName("dolgozó");
		Element worker = (Element) workers.item(4);
		worker.getElementsByTagName("név").item(0).setTextContent("Cziglédi Bálint");
		
		// 1. Részleg vezetõjének és a kinevezés dátumának módosítása
		NodeList leaders = doc.getElementsByTagName("vezeti");
		Element leader = (Element) leaders.item(0);
		leader.setAttribute("DOLGOZÓ_FK", "5");
		leader.getElementsByTagName("mikortól").item(0).setTextContent(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
		// Legrégebbi rendelés dátumának módosítáas
		NodeList orderList = doc.getElementsByTagName("rendel");
		Element orderElement = (Element) orderList.item(4);
		orderElement.getElementsByTagName("rendelés_dátuma").item(0).setTextContent(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
	}
}
