package scandoc.Fichier;

import java.io.File;
import java.io.IOException;
import java.util.TimeZone;
import hirondelle.date4j.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;


public class XConfigFile2 {

	private String folderPath;
	private Boolean isFolderPathConfigured;
	
	private String backupPath;
	private Boolean isBackupPathConfigured;
	
	private String backupName;
	private Boolean isBackupNameConfigured;
	
	private DateTime lastBackupDate;
	private Boolean isLastBackupDateConfigured;
	
	private Boolean fileExist;
	private Boolean configured;
	
	public XConfigFile2()throws IOException {
		folderPath = "";
		backupPath = "";
		lastBackupDate = null;
		
		
		if(ExistXConfigFile()) {
			this.folderPath = this.ReadXConfigFolderPath();
			this.backupPath = this.ReadXConfigBackupPath();
			this.lastBackupDate = this.ReadXConfigLastBackupDate();
			
			if(isFolderPathConfigured && isBackupPathConfigured && isLastBackupDateConfigured) {
				this.configured = true;
			}else {
				this.configured = false;
			}
		}else {
			File f = new File("config.xml");
			f.createNewFile();
			this.configured = false;
			this.isLastBackupDateConfigured = false;
			this.isBackupPathConfigured = false;
			this.isFolderPathConfigured = false;
			this.setBackupPath("null");
			this.setFolderPath("null");
			this.setIsLastBackupDateConfigured(null);
		}
	}
	
	//
	// Getters & Setters
	//

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}
	
	public Boolean getIsFolderPathConfigured() {
		return isFolderPathConfigured;
	}

	public void setIsFolderPathConfigured(Boolean folderPathConfigured) {
		this.isFolderPathConfigured = folderPathConfigured;
	}

	public String getBackupPath() {
		return backupPath;
	}

	public void setBackupPath(String backupPath) {
		this.backupPath = backupPath;
	}
	
	public Boolean getIsBackupPathConfigured() {
		return isBackupPathConfigured;
	}

	public void setIsBackupPathConfigured(Boolean backupPathIsConfigured) {
		this.isBackupPathConfigured = backupPathIsConfigured;
	}

	public String getBackupName() {
		return backupName;
	}

	public void setBackupName(String backupName) {
		this.backupName = backupName;
	}

	public Boolean getIsBackupNameConfigured() {
		return isBackupNameConfigured;
	}

	public void setIsBackupNameConfigured(Boolean isBacckupNameConfigured) {
		this.isBackupNameConfigured = isBacckupNameConfigured;
	}

	public DateTime getLastBackupDate() {
		return lastBackupDate;
	}

	public void setLastBackupDate(DateTime lastBackupDate) {
		this.lastBackupDate = lastBackupDate;
	}

	public Boolean getIsLastBackupDateConfigured() {
		return isLastBackupDateConfigured;
	}

	public void setIsLastBackupDateConfigured(Boolean isLastBackupDateConfigured) {
		this.isLastBackupDateConfigured = isLastBackupDateConfigured;
	}

	public Boolean getFileExist() {
		return fileExist;
	}

	public void setFileExist(Boolean fileExist) {
		this.fileExist = fileExist;
	}

	public Boolean getConfigured() {
		return configured;
	}

	public void setConfigured(Boolean configured) {
		this.configured = configured;
	}

	public void WriteXConfigBackupName(String backupName) {
		try {
			setBackupPath(backupPath);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("config");
			doc.appendChild(rootElement);
			Element dataFolderElement = doc.createElement("datafolderpath");
			dataFolderElement.appendChild(doc.createTextNode(ReadXConfigFolderPath()));
			rootElement.appendChild(dataFolderElement);
			Element backupNameElement = doc.createElement("backupname");
			backupNameElement.appendChild(doc.createTextNode(backupName));
			rootElement.appendChild(backupNameElement);
			Element backupElement = doc.createElement("backuppath");
			backupElement.appendChild(doc.createTextNode(ReadXConfigBackupPath()));
			rootElement.appendChild(backupElement);
			Element lastBackupDateElement = doc.createElement("lastbackup");
			lastBackupDateElement.appendChild(doc.createTextNode(ReadXConfigLastBackupDate().toString()));
			rootElement.appendChild(lastBackupDateElement);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("config.xml"));
			transformer.transform(source, result);
			setIsBackupNameConfigured(true);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void WriteXConfigBackupPath(String backupPath) {
		try {
			setBackupPath(backupPath);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("config");
			doc.appendChild(rootElement);
			Element dataFolderElement = doc.createElement("datafolderpath");
			dataFolderElement.appendChild(doc.createTextNode(ReadXConfigFolderPath()));
			rootElement.appendChild(dataFolderElement);
			Element backupNameElement = doc.createElement("backupname");
			backupNameElement.appendChild(doc.createTextNode(ReadXConfigBackupName()));
			rootElement.appendChild(backupNameElement);
			Element backupElement = doc.createElement("backuppath");
			backupElement.appendChild(doc.createTextNode(backupPath));
			rootElement.appendChild(backupElement);
			Element lastBackupDateElement = doc.createElement("lastbackup");
			lastBackupDateElement.appendChild(doc.createTextNode(ReadXConfigLastBackupDate().toString()));
			rootElement.appendChild(lastBackupDateElement);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("config.xml"));
			transformer.transform(source, result);
			setIsBackupPathConfigured(true);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void WriteXConfigLastBackupDate(DateTime lastBackupDate) {
		try {
			setBackupPath(backupPath);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("config");
			doc.appendChild(rootElement);
			Element dataFolderElement = doc.createElement("datafolderpath");
			dataFolderElement.appendChild(doc.createTextNode(ReadXConfigFolderPath()));
			rootElement.appendChild(dataFolderElement);
			Element backupNameElement = doc.createElement("backupname");
			backupNameElement.appendChild(doc.createTextNode(ReadXConfigBackupName()));
			rootElement.appendChild(backupNameElement);
			Element backupElement = doc.createElement("backuppath");
			backupElement.appendChild(doc.createTextNode(backupPath));
			rootElement.appendChild(backupElement);
			Element lastBackupDateElement = doc.createElement("lastbackup");
			lastBackupDateElement.appendChild(doc.createTextNode(lastBackupDate.toString()));
			rootElement.appendChild(lastBackupDateElement);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("config.xml"));
			transformer.transform(source, result);
			setIsLastBackupDateConfigured(true);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void WriteXConfigFolderPath(String folderPath){
		try {
			setFolderPath(folderPath);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("config");
			doc.appendChild(rootElement);
			Element dataFolderElement = doc.createElement("datafolderpath");
			dataFolderElement.appendChild(doc.createTextNode(folderPath));
			rootElement.appendChild(dataFolderElement);			
			Element backupNameElement = doc.createElement("backupname");
			backupNameElement.appendChild(doc.createTextNode(ReadXConfigBackupName()));
			rootElement.appendChild(backupNameElement);
			Element backupElement = doc.createElement("backuppath");
			backupElement.appendChild(doc.createTextNode(ReadXConfigBackupPath()));
			rootElement.appendChild(backupElement);
			Element lastBackupDateElement = doc.createElement("lastbackup");
			lastBackupDateElement.appendChild(doc.createTextNode(ReadXConfigLastBackupDate().toString()));
			rootElement.appendChild(lastBackupDateElement);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("config.xml"));
			transformer.transform(source, result);
			setIsFolderPathConfigured(true);
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Read Folder Path
	public String ReadXConfigFolderPath() {
		if(ExistXConfigFile()) {
			try {
				File fXmlFile = new File("config.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbFactory.newDocumentBuilder();
				Document dom = db.parse(fXmlFile);
				Element doc =dom.getDocumentElement();
				folderPath=getTextValue(folderPath,doc, "datafolderpath");
				if(folderPath == "") {
					folderPath = "null";
					setIsFolderPathConfigured(false);
				}else {
					setIsFolderPathConfigured(true);
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}else {
			setFolderPath("null");
			setIsFolderPathConfigured(false);
		}
		return getFolderPath();
	}
	
	// Read Backup Path
	public String ReadXConfigBackupName() {
		if(ExistXConfigFile()) {
			try {
				File fXmlFile = new File("config.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbFactory.newDocumentBuilder();
				Document dom = db.parse(fXmlFile);
				Element doc =dom.getDocumentElement();
				backupName=getTextValue(backupName,doc, "backuppath");
				if(backupName == "") {
					backupName = "null";
					setIsBackupNameConfigured(false);
				}else {
					setIsBackupNameConfigured(true);
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}else {
			setBackupName("null");
			setIsBackupNameConfigured(false);
		}
		return backupName;
	}
	
	// Read Backup Path
	public String ReadXConfigBackupPath() {
		if(ExistXConfigFile()) {
			try {
				File fXmlFile = new File("config.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbFactory.newDocumentBuilder();
				Document dom = db.parse(fXmlFile);
				Element doc =dom.getDocumentElement();
				backupPath=getTextValue(backupPath,doc, "backuppath");
				if(backupPath == "") {
					backupPath = "null";
					setIsBackupPathConfigured(false);
				}else {
					setIsBackupPathConfigured(true);
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}else {
			setBackupPath("null");
			setIsBackupPathConfigured(false);
		}
		return backupPath;
	}
	
	// Read Last Backup Date
	public DateTime ReadXConfigLastBackupDate() {
		if(ExistXConfigFile()) {
			try {
				String lastBackupString = "";
				File fXmlFile = new File("config.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbFactory.newDocumentBuilder();
				Document dom = db.parse(fXmlFile);
				Element doc =dom.getDocumentElement();
				lastBackupString=getTextValue(lastBackupString,doc, "lastbackup");
				if(lastBackupString != "") {
					setLastBackupDate(new DateTime(lastBackupString));
					setIsLastBackupDateConfigured(true);
				}else {
					setLastBackupDate(DateTime.today(TimeZone.getDefault()));
					setIsLastBackupDateConfigured(false);
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}else {
			setLastBackupDate(DateTime.today(TimeZone.getDefault()));
			setIsLastBackupDateConfigured(false);
		}
		return lastBackupDate;
	}
	
	public Boolean ExistXConfigFile() {
		Boolean exist;
		File f = new File("config.xml");
		exist = f.exists();
		this.setFileExist(exist);
		return exist;
	}

	private String getTextValue(String def, Element doc, String tag) {
	    String value = def;
	    NodeList nl;
	    nl = doc.getElementsByTagName(tag);
	    if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
	        value = nl.item(0).getFirstChild().getNodeValue();
	    }
	    return value;
	}
	
	private void p(String s) {
		System.out.println(s);
	}
	
}
