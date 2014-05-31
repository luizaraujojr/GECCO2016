package br.unirio.instance;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import br.unirio.overwork.builders.model.Requirement;
import br.unirio.overwork.builders.model.SoftwareSystem;
import br.unirio.overwork.builders.model.WorkPackage;

public class Reader {
	
	public SoftwareSystem run(String filename) throws Exception 
	{
		File fXmlFile = new File(filename);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();

		Element root = doc.getDocumentElement();
		Element nameElement = (Element) root.getElementsByTagName("name").item(0);
		
		SoftwareSystem ss = new SoftwareSystem(nameElement.getTextContent());
		
		ArrayList<WorkPackage> workPackageList = new ArrayList<WorkPackage>();
		
		ArrayList<Requirement> tfRequirementList = new ArrayList<Requirement>();
		ArrayList<Requirement> dfRequirementList = new ArrayList<Requirement>();
		
		NodeList transactionNodeList = root.getElementsByTagName("transaction");
		
		//loop pelas transações
		for (int i = 0; i < transactionNodeList.getLength(); i++) 
		{
			Element transactionNode = (Element) transactionNodeList.item(i);
			NodeList childtransactionNodeList = transactionNodeList.item(i).getChildNodes();
						
			String name = getAttribute(transactionNode, "name");
			int fp = getIntAttribute(transactionNode, "fp");
			
			Requirement req = new Requirement(name,fp); 
			tfRequirementList.add(req);
		}
		
		NodeList dataNodeList = root.getElementsByTagName("data");
		
		//loop pelas data functions
		for (int i = 0; i < dataNodeList.getLength(); i++) 
		{
			Element dataNode = (Element) dataNodeList.item(i);
			NodeList childdataNodeList = dataNodeList.item(i).getChildNodes();
						
			String name = getAttribute(dataNode, "name");
			int fp = getIntAttribute(dataNode, "fp");
			
			Requirement req = new Requirement(name,fp); 
			dfRequirementList.add(req);
		}

		//loop pelas transações
		for (int i = 0; i < transactionNodeList.getLength(); i++) 
		{
			Element transactionNode = (Element) transactionNodeList.item(i);
			NodeList childtransactionNodeList = transactionNodeList.item(i).getChildNodes();
						
			String name = getAttribute(transactionNode, "name");
			
			//loop pelas dependências com outras transações
			NodeList dependsListNode = transactionNode.getElementsByTagName("depends");
			
			Requirement parentR = getRequirementByName(tfRequirementList, name);
						
			for (int j = 0; j < dependsListNode.getLength(); j++)
			{
				Element dependstransactionNode = (Element) dependsListNode.item(j);
				String ftrname = getAttribute(dependstransactionNode, "name");
				
				Requirement childR = getRequirementByName(tfRequirementList, ftrname);
				
				parentR.addDependence(childR);				
			}
		}
		
		//loop pelas data functions
		for (int i = 0; i < dataNodeList.getLength(); i++) 
		{
			Element dataNode = (Element) dataNodeList.item(i);
			NodeList childdataNodeList = dataNodeList.item(i).getChildNodes();
						
			String name = getAttribute(dataNode, "name");
			int fp = getIntAttribute(dataNode, "fp");
			
			Requirement req = new Requirement(name,fp); 
			dfRequirementList.add(req);
		}
		
		for(Requirement r1 : dfRequirementList)
		{
			WorkPackage wp = new WorkPackage(r1.getName());
			wp.addRequirement(r1.getName(), r1.getFunctionPoints());
			
			for (Requirement r2 : getRequirementsByDataFunctionDependence(tfRequirementList, r1.getName()))
			{
				wp.addRequirement(r2.getName(), r2.getFunctionPoints());
				
			}
			ss.addWorkPackage(wp);
		}		
		return ss;
	}

	/**
	 * Loads an optional string attribute from a XML element
	 */
	private String getAttribute(Element element, String name)
	{
		String value = element.getAttribute(name);
		return (value != null) ? value : "";
	}
	
	/**
	 * Loads an optional boolean attribute from a XML element
	 */
	private boolean getBooleanAttribute(Element element, String name, boolean defValue) 
	{
		String value = element.getAttribute(name);
		
		if (value == null || value.length() == 0)
			return defValue;
		
		return value.compareToIgnoreCase("true") == 0;
	}
	
	/**
	 * Loads an optional integer attribute from a XML element
	 */
	private int getIntAttribute(Element element, String name) 
	{
		String value = element.getAttribute(name);
		
		if (value == null)
			return 0;
		
		if (value.length() == 0)
			return 0;
		
		return Integer.parseInt(value);
	}
	
	private Requirement getRequirementByName(ArrayList<Requirement> requirementList, String name)
	{
		for (Requirement r : requirementList)
		{
			if (r.getName().endsWith(name))
			{
				return r;
			}
			
		}
	return null;
	}
	

	private ArrayList<Requirement> getRequirementsByDataFunctionDependence(ArrayList<Requirement> requirementList, String name)
	{
		ArrayList<Requirement> rDependence = new ArrayList<Requirement>();
		for (Requirement r1 : requirementList)
		{
			for (Requirement r2 : r1.getDependencies())
			if (r2.getName().endsWith(name))
			{
				rDependence.add(r1);
				break;
			}
			
		}
	return rDependence;
	}

	
}
