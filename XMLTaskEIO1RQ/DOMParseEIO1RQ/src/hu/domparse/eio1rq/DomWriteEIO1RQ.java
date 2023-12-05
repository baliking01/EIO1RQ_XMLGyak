package hu.domparse.eio1rq;


import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomWriteEIO1RQ {
	public static void main(String args[]) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
	        Document document = dbBuilder.newDocument();
	        
	        // Gy�k�relem
	        Element root = document.createElement("EIO1RQ_Logisztikai_C�g");
	        root.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema-instance");
	        root.setAttribute("xs:noNamespaceSchemaLocation", "XMLSchemaeio1rq.xsd");
	        document.appendChild(root);
	        
	        // Rendel�s
	        root.appendChild(document.createComment("Rendel�sek"));
	        createOrder(document, root, "1", "1", "�sv�nyv�z", "13", "true", "3519", "Miskolc", "Apat utca 12.");
	        createOrder(document, root, "2", "2", "Hamburger pog�csa", "20", "false", "6723", "Szeged", "Eperjesi sor 2.");
	        createOrder(document, root, "3", "3", "Sony WH-XB910N fejhallgat�", "8", "true", "9025", "Gy�r", "Akac utca 35.");
	        createOrder(document, root, "4", "4", "Sert�skaraj", "20", "true", "7634", "P�cs", "Lankas utca 22.");
	        createOrder(document, root, "5", "5", "Coca Cola Cherry 6x1.5L", "17", "false", "4028", "Debrecen", "Abonyi utca 72.");

	        // Rendel kapcsolat
	        root.appendChild(document.createComment("Rendel"));
	        createOrderConn(document, root, "1", "1", "1", "2023-01-17");
	        createOrderConn(document, root, "2", "2", "2", "2023-06-04");
	        createOrderConn(document, root, "3", "3", "3", "2023-08-23");
	        createOrderConn(document, root, "4", "4", "1", "2023-02-10");
	        createOrderConn(document, root, "5", "5", "3", "2022-11-03");
	        
	        
	        // Megrendel�
	        root.appendChild(document.createComment("Megrendel�"));
	        createClient(document, root, "1", "Feny� �s T�rsa Kft.", "+3612045966", "info@tlx.hu");
	        createClient(document, root, "2", "Rothmayer Bt.", "+3633413665", "rothmayer1@gmail.com");
	        createClient(document, root, "3", "Horv�th Uno Kft.", "+36705469391", "horvathuno@gmail.com");
	        
	        // Dolgozik kapcsolat
	        root.appendChild(document.createComment("Dolgozik"));
	        createWorking(document, root, "1", "1", "1", "Logisztikai menedzser");
	        createWorking(document, root, "2", "1", "2", "Logisztikai menedzser");
	        createWorking(document, root, "3", "2", "3", "Logisztikai menedzser");
	        createWorking(document, root, "4", "2", "4", "Logisztikai menedzser");
	        createWorking(document, root, "5", "3", "5", "Logisztikai menedzser");
	        createWorking(document, root, "6", "3", "6", "Logisztikai menedzser");

	        // Dolgoz�
	        root.appendChild(document.createComment("Dolgoz�"));
	        createWorker(document, root, "1", "Kov�cs B�la");
	        createWorker(document, root, "2", "Kiss J�nos");
	        createWorker(document, root, "3", "Eged Csan�d");
	        createWorker(document, root, "4", "Kun Tam�s");
	        createWorker(document, root, "5", "J�mbor Andrea");
	        createWorker(document, root, "6", "T�th S�ndor");
	        
	        
	        // Vezeti
	        root.appendChild(document.createComment("Vezeti"));
	        createLeading(document, root, "1", "1", "1", "2017-01-01");
	        createLeading(document, root, "2", "2", "3", "2019-04-15");
	        createLeading(document, root, "3", "3", "6", "2020-09-07");
	        
	        
	        // R�szleg
	        root.appendChild(document.createComment("R�szleg"));
	        createDivision(document, root, "1", "�lelmiszer");
	        createDivision(document, root, "2", "Ital");
	        createDivision(document, root, "3", "Informatika");

	        
	        // Documentum fel�p�t�se
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "2");
			
			// Kimeneti f�jl
			DOMSource source = new DOMSource(document);
			File outputFile = new File("XMLeio1rq1.xml");
			StreamResult file = new StreamResult(outputFile);
			transformer.transform(source, file);
			
			//Konzolra ki�r�s
			StreamResult console = new StreamResult(System.out);
			transformer.transform(source, console);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void createOrder(Document document, Element root, String id, String div_fk, String name,
			String amount, String delivered, String irsz, String city, String dest) {
		
		Element order = document.createElement("rendel�s");
		order.setAttribute("RENDEL�S_ID", id);
		order.setAttribute("R�SZLEG_FK", div_fk);
		
		
		Element _name = createElementValue(document, "n�v", name);
		Element _amount = createElementValue(document, "mennyis�g", amount);
		Element _delivered = createElementValue(document, "lesz�ll�tva", delivered);

		Element _destination = document.createElement("sz�ll�t�si_hely");
		Element _irsz = createElementValue(document, "ir�ny�t�sz�m", irsz);
		Element _city = createElementValue(document, "v�ros", city);
		Element _dest = createElementValue(document, "utca_h�zsz�m", dest);
		
		_destination.appendChild(_irsz);
		_destination.appendChild(_city);
		_destination.appendChild(_dest);
		
		order.appendChild(_name);
		order.appendChild(_amount);
		order.appendChild(_delivered);
		order.appendChild(_destination);
		
		root.appendChild(order);
	}
	
	public static void createOrderConn(Document document, Element root, String id, String order_fk, String client_fk,
			String date) {
		
		Element orderConn = document.createElement("rendel");
		orderConn.setAttribute("RENDEL_ID", id);
		orderConn.setAttribute("RENDEL�S_FK", order_fk);
		orderConn.setAttribute("MEGRENDEL�_FK", client_fk);
		
		Element _date = createElementValue(document, "rendel�s_d�tuma", date);
		orderConn.appendChild(_date);
		
		root.appendChild(orderConn);
	}
	
	public static void createClient(Document document, Element root, String id, String name,
			String phone, String email) {
		
		Element orderClient = document.createElement("megrendel�");
		orderClient.setAttribute("MEGRENDEL�_ID", id);
		
		Element _name = createElementValue(document, "c�gn�v", name);
		Element _contact = document.createElement("el�rhet�s�g"); 
		Element _phone= createElementValue(document, "tel_sz�m", phone);
		Element _email = createElementValue(document, "email", email);
		
		_contact.appendChild(_phone);
		_contact.appendChild(_email);
		
		orderClient.appendChild(_name);
		orderClient.appendChild(_contact);
		
		root.appendChild(orderClient);
	}
	
	public static void createWorking(Document document, Element root, String id,
			String div_fk, String worker_fk, String position) {
		
		Element working = document.createElement("dolgozik");
		working.setAttribute("DOLGOZIK_ID", id);
		working.setAttribute("R�SZLEG_FK", div_fk);
		working.setAttribute("DOLGOZ�_FK", worker_fk);
		
		Element _position = createElementValue(document, "beoszt�s", position);
		working.appendChild(_position);
		
		root.appendChild(working);
	}
	
	public static void createWorker(Document document, Element root, String id, String name) {
		Element worker = document.createElement("dolgoz�");
		worker.setAttribute("DOLGOZ�_ID", id);
		
		Element _name = createElementValue(document, "n�v", name);
		worker.appendChild(_name);
		
		root.appendChild(worker);
	}
	
	public static void createLeading(Document document, Element root, String id, String div_fk,
			String worker_fk, String date) {
		Element leading = document.createElement("vezeti");
		leading.setAttribute("VEZETI_ID", id);
		leading.setAttribute("R�SZLEG_FK", div_fk);
		leading.setAttribute("DOLGOZ�_FK", worker_fk);
		
		Element _date = createElementValue(document, "mikort�l", date);
		leading.appendChild(_date);
		
		root.appendChild(leading);
	}
	
	public static void createDivision(Document document, Element root, String id, String name) {
		Element division = document.createElement("r�szleg");
		division.setAttribute("R�SZLEG_ID", id);
		
		Element _name = createElementValue(document, "n�v", name);
		division.appendChild(_name);
		
		root.appendChild(division);
	}
	
	public static Element createElementValue(Document doc, String name, String value) {
		Element elem = doc.createElement(name);
		elem.appendChild(doc.createTextNode(value));
		return elem;
	}
}
