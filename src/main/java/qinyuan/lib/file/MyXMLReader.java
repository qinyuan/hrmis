package qinyuan.lib.file;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MyXMLReader {

	private Document doc;

	/**
	 * Constructor, accept a string as XML file name
	 * 
	 * @param fileName
	 * @throws CreateXmlReaderException
	 */
	public MyXMLReader(String fileName) throws Exception {
		this(new File(fileName));
	}

	public MyXMLReader(File xmlFile) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		doc = builder.parse(xmlFile);
	}

	/**
	 * accept a string as the tag name, then return its value
	 * 
	 * @param tagName
	 * @return
	 */
	public String getByTagName(String tagName) {
		NodeList nodelist = doc.getElementsByTagName(tagName);
		Node node = nodelist.item(0);
		try {
			return node.getFirstChild().getNodeValue();
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		MyXMLReader reader = new MyXMLReader("e:/test.xml");
		System.out.println(reader.getByTagName("name"));
	}
}
