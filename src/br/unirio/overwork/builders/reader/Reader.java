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
		Element root = loadXmlFile (filename);
		
		//extracting from the transaction list and including in the requirement list
		ArrayList<Requirement> tfRequirementList = new ArrayList<Requirement>();
		NodeList transactionNodeList = root.getElementsByTagName("transaction");
		extractRequirements (transactionNodeList, tfRequirementList);
		
		//looping in the data function list to include in the requirement list
		ArrayList<Requirement> dfRequirementList = new ArrayList<Requirement>();
		NodeList dataNodeList = root.getElementsByTagName("data");
		extractRequirements (dataNodeList, dfRequirementList);
		
		//looping in the transaction list to include the dependency over a transaction function 
		for (int i = 0; i < transactionNodeList.getLength(); i++) 
		{
			Element transactionNode = (Element) transactionNodeList.item(i);
						
			extractDependencies(transactionNode, "depends", tfRequirementList,tfRequirementList);
			
			extractDependencies(transactionNode, "ftr", tfRequirementList, dfRequirementList);
		}
		return createSoftwareSystem(root, dfRequirementList, tfRequirementList);
	}
	
	private Element loadXmlFile (String filename) throws Exception
	{
		File fXmlFile = new File(filename);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();

		return doc.getDocumentElement();
	}
	
	private void extractRequirements(NodeList nodeList, ArrayList<Requirement> reqs)
	{		
		for (int i = 0; i < nodeList.getLength(); i++) 
		{
			Element node = (Element) nodeList.item(i);
						
			String name = getAttribute(node, "name");
			int fp = getIntAttribute(node, "fp");
			
			Requirement req = new Requirement(name,fp); 
			reqs.add(req);
		}		
	}
	
	private void extractDependencies(Element transactionNode, String dependencyTagName, ArrayList<Requirement> parentReqs, ArrayList<Requirement> childReqs)
	{		
		Requirement parentR = getRequirementByName(parentReqs, getAttribute(transactionNode, "name"));
		
		NodeList dependencyListNode = transactionNode.getElementsByTagName(dependencyTagName);
		
		for (int j = 0; j < dependencyListNode.getLength(); j++)
		{
			Element dependstransactionNode = (Element) dependencyListNode.item(j);
			String ftrname = getAttribute(dependstransactionNode, "name");
			
			Requirement childR = getRequirementByName(childReqs, ftrname);
			
			parentR.addDependence(childR);				
		}
	}
	
	private SoftwareSystem createSoftwareSystem(Element root, ArrayList<Requirement> dfRequirementList, ArrayList<Requirement> tfRequirementList)
	{
		Element nameElement = (Element) root.getElementsByTagName("name").item(0);
		
		SoftwareSystem ss = new SoftwareSystem(nameElement.getTextContent());
		
		for(Requirement r1 : dfRequirementList)
		{
			WorkPackage wp = new WorkPackage(r1.getName());
			wp.addRequirement(r1.getName(), r1.getFunctionPoints());
			
			for (Requirement r2 : getRequirementsByDataFunctionDependence(tfRequirementList, r1.getName()))
			{
				wp.addRequirement(r2);		
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
