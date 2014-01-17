package br.unirio.functionpoint.reader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.unirio.functionpoint.model.DataFunction;
import br.unirio.functionpoint.model.DataFunctionType;
import br.unirio.functionpoint.model.FileReference;
import br.unirio.functionpoint.model.Project;
import br.unirio.functionpoint.model.RecordElement;
import br.unirio.functionpoint.model.Stakeholder;
import br.unirio.functionpoint.model.TransactionFunction;
import br.unirio.functionpoint.model.TransactionFunctionType;

/**
 * Class that reads a project represented by function points
 * 
 * @author Marcio Barros
 */
public class ProjectReader 
{
	/**
	 * Reads a XML file containing a description for the project
	 */
	public Project execute(String fileName) throws Exception
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new File(fileName));
		doc.getDocumentElement().normalize();

		Project project = new Project();
		NodeList xProjects = doc.getElementsByTagName("project");
		
		if (xProjects.getLength() != 1)
			throw new Exception("Invalid project file format");
		
		Element xProject = (Element) xProjects.item(0);
		
		List<Element> xDataFunctions = getChildrenByTagName(xProject, "datafunction");

		for (Element xDataFunction : xDataFunctions) 
			project.addDataFunction(loadDataFunction(xDataFunction));

		List<Element> xTransactionFunctions = getChildrenByTagName(xProject, "transactionfunction");

		for (Element xTransactionFunction : xTransactionFunctions) 
			project.addTransactionFunction(loadTransactionFunction(xTransactionFunction, project));

		List<Element> xStakeholders = getChildrenByTagName(xProject, "stakeholder");

		for (Element xStakeholder : xStakeholders)
			project.addStakeholder(loadStakeholder(xStakeholder, project));
		
		return project;
	}

	/**
	 * Returns all children of a node with a given tag
	 */
	private List<Element> getChildrenByTagName(Element parent, String name)
	{
		List<Element> result = new ArrayList<Element>();
		
	    for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling())
	        if (child instanceof Element && name.equals(child.getNodeName())) 
	        	result.add((Element)child);

	    return result;
	}
	
	/**
	 * Loads a data function from its XML representation
	 */
	private DataFunction loadDataFunction(Element root) throws Exception
	{
		String name = root.getAttribute("name");
		String type = root.getAttribute("type");
		
		DataFunctionType dft = DataFunctionType.get(type);
		
		if (dft == null)
			throw new Exception("Undefined data function type " + type);
		
		DataFunction df = new DataFunction(name, dft);
		NodeList xRecords = root.getElementsByTagName("ret");

		for (int i = 0; i < xRecords.getLength(); i++) 
		{
			Element xRecord = (Element) xRecords.item(i);
			RecordElement ret = loadRecordElement(xRecord); 
			df.addRecordElement(ret);
		}
		
		return df;
	}

	/**
	 * Loads a record element from its XML representation
	 */
	private RecordElement loadRecordElement(Element root)
	{
		String name = root.getAttribute("name");
		RecordElement ret = new RecordElement(name);
		
		NodeList xDataElements = root.getElementsByTagName("det");

		for (int i = 0; i < xDataElements.getLength(); i++) 
		{
			Element xDataElement = (Element) xDataElements.item(i);
			String detName = xDataElement.getAttribute("name");
			
			if (xDataElement.hasAttribute("cont"))
				ret.addInvisibleDataElement(detName);
			else
				ret.addVisibleDET(detName);
		}
		
		return ret;
	}

	/**
	 * Loads a transaction function from its XML representation
	 */
	private TransactionFunction loadTransactionFunction(Element root, Project project) throws Exception
	{
		String name = root.getAttribute("name");
		String type = root.getAttribute("type");
		
		TransactionFunctionType tft = TransactionFunctionType.get(type);
		
		if (tft == null)
			throw new Exception("Undefined transaction function type " + name);
		
		int extraDET = root.hasAttribute("extraDET") ? Integer.parseInt(root.getAttribute("extraDET")) : 0;
		TransactionFunction tf = new TransactionFunction(name, "", tft, extraDET);
		
		NodeList xFileReferences = root.getElementsByTagName("ftr");

		for (int i = 0; i < xFileReferences.getLength(); i++) 
		{
			Element xFileReference = (Element) xFileReferences.item(i);
			FileReference ftr = loadFileReferenceElement(xFileReference, project);
			tf.addFileReference(ftr);
		}
		
		return tf;
	}

	/**
	 * Loads a file reference by a transaction from its XML representation
	 */
	private FileReference loadFileReferenceElement(Element root, Project project) throws Exception
	{
		String name = root.getAttribute("name");
		DataFunction df = project.getDataFunctionName(name);
		
		if (df == null)
			throw new Exception("The referenced file " + name + " must be a data function");
		
		FileReference ftr = new FileReference(df);

		NodeList xDataElements = root.getElementsByTagName("det");

		for (int i = 0; i < xDataElements.getLength(); i++) 
		{
			Element xDataElement = (Element) xDataElements.item(i);
			String detName = xDataElement.getAttribute("name");
			ftr.addDataElement(detName);
		}
		
		return ftr;
	}
	
	/**
	 * Loads a stakeholder from its XML reference
	 */
	private Stakeholder loadStakeholder(Element xStakeholder, Project project) throws Exception
	{
		String name = xStakeholder.getAttribute("name");
		int weight = Integer.parseInt(xStakeholder.getAttribute("weight"));
		Stakeholder stakeholder = new Stakeholder(name, weight);

		NodeList xTransactions = xStakeholder.getElementsByTagName("transaction");

		for (int i = 0; i < xTransactions.getLength(); i++) 
		{
			Element xTransaction = (Element) xTransactions.item(i);
			
			String tname = xTransaction.getAttribute("name");
			TransactionFunction tf = project.getTransaction(tname, "");

			if (tf == null)
				throw new Exception("The project does not have a transaction named " + tname + "");
			
			stakeholder.addTransaction(tf);
		}
		
		return stakeholder;
	}
}