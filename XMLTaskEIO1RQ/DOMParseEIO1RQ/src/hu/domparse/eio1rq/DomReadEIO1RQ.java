package hu.domparse.eio1rq;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomReadEIO1RQ {
	public static void main(String args[]) {
		System.out.println("2a) Adatolvas�s");
		
		try {
			// File l�trehoz�za �s el�k�sz�t�s a feldolgoz�sra
			File inputFile = new File("XMLeio1rq.xml");			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(inputFile);
				
			// A gy�k�relem minden gyerekelem�nek elk�l�n�t�se
		    NodeList root = doc.getChildNodes();
		    // DOM fa leveleinek felt�rk�pez�se
		    ArrayList<String> content = getAllLeaves((Node)root);
		    
		    // Fa strukt�r�lt ki�rat�sa konzolra �s f�jlba
		    FileWriter writer = new FileWriter("XMLeio1rq_Strukturalt.txt");
		    for(int i = 0; i < content.size(); i++) {
		    	System.out.print(content.get(i));
		    	writer.write(content.get(i));
		    }
		    writer.close();
		    
		}catch(Exception e){
			e.printStackTrace();
		}
		 
	}
	
	// DOM fa leveleinek rekurz�v bej�r�sa
	public static ArrayList<String> getAllLeaves(Node node) {
		ArrayList<String> content = new ArrayList<String>();
		NodeList children = node.getChildNodes();
		if(children.getLength() == 0) {
			if(node.getNodeType() != Node.COMMENT_NODE) content.add(" "+node.getTextContent());			
		}
		else {
			// El�gaz�sok hozz�ad�se a megfelel� tartalom szepar�ci� �rdek�ben
			if(node.getNodeType() != Node.DOCUMENT_NODE) content.add(node.getNodeName());
			int len = children.getLength();
			// Gy�k�relemet lesz�m�tva a fa minden szintj�n elhagyjuk az utols� �res #text elemeti
			// Szebb indent�l�s �rdek�ben
			len -= (len > 1) ? 1 : 0; 
			for(int i = 0; i < len; i++) {
				content.addAll(getAllLeaves(children.item(i)));
			}
		}
		
		return content;
	}
}
