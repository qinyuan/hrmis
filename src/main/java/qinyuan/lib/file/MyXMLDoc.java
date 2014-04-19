package qinyuan.lib.file;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class MyXMLDoc {

	private Document doc;
	private File xmlFile;

	public MyXMLDoc(String fileName) throws Exception {
		xmlFile = new File(fileName);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		doc = builder.parse(fileName);
	}

	/**
	 * return the XML Document object
	 * 
	 * @return
	 */
	public Document getDocument() {
		return doc;
	}

	public NodeList getElementsByTagName(String tagName) {
		return doc.getElementsByTagName(tagName);
	}

	public void updateFile() throws Exception {
		TransformerFactory tff = TransformerFactory.newInstance();
		Transformer tf = tff.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(xmlFile);
		tf.transform(source, result);
	}
}
