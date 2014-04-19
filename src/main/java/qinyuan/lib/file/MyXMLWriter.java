package qinyuan.lib.file;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MyXMLWriter {

	private File xmlFile;
	private Document doc;

	/**
	 * Constructor, accept a string as the name of XML file
	 * 
	 * @param fileName
	 * @throws CreateXmlWriterException
	 */
	public MyXMLWriter(String fileName) throws Exception {
		xmlFile = new File(fileName);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder = factory.newDocumentBuilder();
		doc = builder.parse(xmlFile);
	}

	/**
	 * method to modify value of certain element in XML file
	 * 
	 * @param ElementName
	 * @param ElementValue
	 * @throws XmlUpdateException
	 */
	public void modifyElement(String ElementName, String ElementValue)
			throws Exception {
		// modify the value of certain element
		NodeList nodelist = doc.getElementsByTagName(ElementName);
		Node node = nodelist.item(0);
		Node textNode = node.getFirstChild();
		if (textNode == null) {
			node.appendChild(doc.createTextNode(ElementValue));
		} else {
			node.getFirstChild().setNodeValue(ElementValue);
		}

		// write the change to XML file
		TransformerFactory tff = TransformerFactory.newInstance();
		Transformer tf = tff.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(xmlFile);
		tf.transform(source, result);
	}

	public static void main(String[] args) throws Exception {
		MyXMLWriter writer = new MyXMLWriter("f:/test.xml");
		writer.modifyElement("field", "home");
	}
}
