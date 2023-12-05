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
			
			// Document l�trehoz�sa
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder domBuilder = dbFactory.newDocumentBuilder();
			Document doc = domBuilder.parse(inputFile);
			
			// Kiv�lasztott elemek m�dos�t�sa
			modify(doc);
			
			// �j dokumentum ment�se
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc); 
			StreamResult result = new StreamResult(new File("XMLeio1rq_Modified.xml"));
			transformer.transform(source, result);
	        
			// Konzolra ki�rat�s
			StreamResult resultConsole = new StreamResult(System.out);
			transformer.transform(source, resultConsole);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void modify(Document doc) {
		// 4. Rendel�s nev�nek m�dos�t�sa
		NodeList orders = doc.getElementsByTagName("rendel�s");
		Element order = (Element) orders.item(3);
		order.getElementsByTagName("term�k_n�v").item(0).setTextContent("Csirkemellfil�");
		
		// 2. Megrendel� emai c�m�nek m�dos�t�sa
		NodeList clients = doc.getElementsByTagName("megrendel�");
		Element client = (Element) clients.item(1);
		client.getElementsByTagName("el�rhet�s�g").item(0).setTextContent("rothmayerbt@gmail.comm");
		
		// 4. Dolgoz� nev�nek megv�ltoztat�sa
		NodeList workers = doc.getElementsByTagName("dolgoz�");
		Element worker = (Element) workers.item(4);
		worker.getElementsByTagName("n�v").item(0).setTextContent("Czigl�di B�lint");
		
		// 1. R�szleg vezet�j�nek �s a kinevez�s d�tum�nak m�dos�t�sa
		NodeList leaders = doc.getElementsByTagName("vezeti");
		Element leader = (Element) leaders.item(0);
		leader.setAttribute("DOLGOZ�_FK", "5");
		leader.getElementsByTagName("mikort�l").item(0).setTextContent(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
		// Legr�gebbi rendel�s d�tum�nak m�dos�t�as
		NodeList orderList = doc.getElementsByTagName("rendel");
		Element orderElement = (Element) orderList.item(4);
		orderElement.getElementsByTagName("rendel�s_d�tuma").item(0).setTextContent(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
	}
}
