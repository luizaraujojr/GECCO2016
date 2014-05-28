package br.unirio.overwork.instance.reader;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import br.unirio.overwork.instance.model.FunctionPointSystem;
import br.unirio.overwork.instance.model.data.DataElement;
import br.unirio.overwork.instance.model.data.DataFunction;
import br.unirio.overwork.instance.model.data.DataFunctionType;
import br.unirio.overwork.instance.model.data.RegisterElement;
import br.unirio.overwork.instance.model.transaction.Dependency;
import br.unirio.overwork.instance.model.transaction.Field;
import br.unirio.overwork.instance.model.transaction.FileReference;
import br.unirio.overwork.instance.model.transaction.Transaction;
import br.unirio.overwork.instance.model.transaction.TransactionType;

/**
 * Class that reads instances in the detailed function point format
 * 
 * @author Marcio
 */
public class InstanceReader 
{
	/**
	 * Loads a detailed function point file to memory
	 */
	public FunctionPointSystem run(String filename) throws Exception 
	{
		File fXmlFile = new File(filename);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();

		Element root = doc.getDocumentElement();
		Element nameElement = (Element) root.getElementsByTagName("name").item(0);
		FunctionPointSystem fps = new FunctionPointSystem(nameElement.getTextContent());
		
		NodeList dataModelListNode = root.getElementsByTagName("data-model");
		
		for (int i = 0; i < dataModelListNode.getLength(); i++) 
		{
			Element dataModelNode = (Element) dataModelListNode.item(i);
			loadDataModel(fps, dataModelNode);
		}
		
		NodeList transactionsListNode = root.getElementsByTagName("transaction-model");
		
		for (int i = 0; i < transactionsListNode.getLength(); i++) 
		{
			Element transactionModelNode = (Element) transactionsListNode.item(i);
			loadTransactionModel(fps, transactionModelNode);
		}
		
		for (Transaction transaction : fps.getTransactions())
			completeReferences(transaction, fps);
		
		return fps;
	}

	/**
	 * Loads the data model comprising a detailed function point file
	 */
	private void loadDataModel(FunctionPointSystem fps, Element root) 
	{
		NodeList internalLogicFileListNode = root.getElementsByTagName("ilf");
		
		for (int i = 0; i < internalLogicFileListNode.getLength(); i++) 
		{
			Element ilfNode = (Element) internalLogicFileListNode.item(i);
			String name = ilfNode.getAttribute("name");
			DataFunction ilf = new DataFunction(name, DataFunctionType.ILF);
			fps.addDataFunction(ilf);
			loadDataModelElement(ilf, ilfNode);
		}

		NodeList externalInterfaceFileListNode = root.getElementsByTagName("eif");
		
		for (int i = 0; i < externalInterfaceFileListNode.getLength(); i++) 
		{
			Element eifNode = (Element) externalInterfaceFileListNode.item(i);
			String name = eifNode.getAttribute("name");
			DataFunction eif = new DataFunction(name, DataFunctionType.EIF);
			fps.addDataFunction(eif);
			loadDataModelElement(eif, eifNode);
		}
	}

	/**
	 * Loads a data model element from a detailed function point file
	 */
	private void loadDataModelElement(DataFunction element, Element root) 
	{
		NodeList retListNode = root.getElementsByTagName("ret");
		
		for (int i = 0; i < retListNode.getLength(); i++) 
		{
			Element retNode = (Element) retListNode.item(i);
			String name = retNode.getAttribute("name");
			RegisterElement ret = new RegisterElement(name);
			element.addRegisterElement(ret);
			loadRegisterElement(ret, retNode);
		}
	}

	/**
	 * Loads a register element from a detailed function point file
	 */
	private void loadRegisterElement(RegisterElement ret, Element root) 
	{
		NodeList detListNode = root.getElementsByTagName("det");
		
		for (int i = 0; i < detListNode.getLength(); i++) 
		{
			Element detNode = (Element) detListNode.item(i);
			String name = getAttribute(detNode, "name");
			boolean primaryKey = getBooleanAttribute(detNode, "primaryKey");
			String ref = getAttribute(detNode, "ref");
			String dataModelElement = getAttribute(detNode, "dataModelElement");
			String description = getAttribute(detNode, "description");
			boolean hasSemanticMeaning = getBooleanAttribute(detNode, "hasSemanticMeaning");
			
			DataElement det = new DataElement(name, primaryKey, ref, dataModelElement, description, hasSemanticMeaning);
			ret.addDataElements(det);
		}
	}

	/**
	 * Loads a transaction model from a detailed function point file
	 */
	private void loadTransactionModel(FunctionPointSystem fps, Element root) 
	{
		NodeList transactionListNode = root.getElementsByTagName("transaction");
		
		for (int i = 0; i < transactionListNode.getLength(); i++) 
		{
			Element transactionNode = (Element) transactionListNode.item(i);
			String name = getAttribute(transactionNode, "name");
			int extraDET = getIntAttribute(transactionNode, "extraDET");
			boolean errorMessages = getBooleanAttribute(transactionNode, "errorMsg");
			TransactionType type = TransactionType.get(getAttribute(transactionNode, "type"));
			
			Transaction transaction = new Transaction(name, errorMessages, type, extraDET);
			fps.addTransaction(transaction);
			loadTransaction(transaction, transactionNode);
		}
	}

	/**
	 * Loads a transaction from a detailed function point file
	 */
	private void loadTransaction(Transaction transaction, Element root) 
	{
		NodeList ftrListNode = root.getElementsByTagName("ftr-list");
		
		for (int i = 0; i < ftrListNode.getLength(); i++) 
		{
			Element ftrNode = (Element) ftrListNode.item(i);
			loadFileReferencedListElement(transaction, ftrNode);
		}

		NodeList dependencyListNode = root.getElementsByTagName("dependencies");
		
		for (int i = 0; i < dependencyListNode.getLength(); i++) 
		{
			Element dependencyNode = (Element) dependencyListNode.item(i);
			loadDependencyListElement(transaction, dependencyNode);
		}
	}

	/**
	 * Loads a transaction's file references from a detailed function point file
	 */
	private void loadFileReferencedListElement(Transaction transaction, Element root) 
	{
		NodeList ftrListNode = root.getElementsByTagName("ftr");
		
		for (int i = 0; i < ftrListNode.getLength(); i++) 
		{
			Element ftrNode = (Element) ftrListNode.item(i);
			
			String name = getAttribute(ftrNode, "name");
			String ret = getAttribute(ftrNode, "ret");
			String dataModelElement = getAttribute(ftrNode, "dataModelElement");
			boolean useAllDets = getBooleanAttribute(ftrNode, "useAllDets");

			FileReference ftr = new FileReference(name, ret, dataModelElement, useAllDets);
			loadFields(ftr, ftrNode); 
			transaction.addFileReference(ftr);
		}
	}

	/**
	 * Loads a list of fields referenced by a transaction from a detailed function point file
	 */
	private void loadFields(FileReference ftr, Element root) 
	{
		NodeList fieldListNode = root.getElementsByTagName("det");
		
		for (int i = 0; i < fieldListNode.getLength(); i++) 
		{
			Element fieldNode = (Element) fieldListNode.item(i);
			String name = getAttribute(fieldNode, "name");
			
			Field field = new Field(name);
			ftr.addField(field);
		}
	}

	/**
	 * Loads a list of transaction dependencies from a detailed function point file
	 */
	private void loadDependencyListElement(Transaction transaction, Element root) 
	{
		NodeList dependencyListNode = root.getElementsByTagName("dependency");
		
		for (int i = 0; i < dependencyListNode.getLength(); i++) 
		{
			Element dependencyNode = (Element) dependencyListNode.item(i);
			String ref = getAttribute(dependencyNode, "ref");
			boolean canbeWeak = getBooleanAttribute(dependencyNode, "canBeWeak");

			Dependency dependency = new Dependency(ref, canbeWeak);
			transaction.addDependency(dependency);
		}
	}

	/**
	 * Complete the references of the model
	 */
	private void completeReferences(Transaction transaction, FunctionPointSystem fps) throws Exception 
	{
		for (Dependency dependency : transaction.getDependencies())
		{
			String name = dependency.getDependeeTransactionName();
			Transaction dependee = fps.getTransactionName(name);
			
			if (dependee == null)
				throw new Exception("Transaction '" + name + "' not identified in dependency.");
			
			dependency.setDependeeTransaction(dependee);
		}
		
		for (FileReference ftr : transaction.getFileReferences())
		{
			if (ftr.getRet().length() > 0)
			{
				DataFunction data = fps.getDataFunctionName(ftr.getDataModelElement()); 
				RegisterElement ret = null;
				
				if (data != null)
					ret = data.getRegisterElementName(ftr.getRet());
				else
					ret = fps.getRegisterElementName(ftr.getRet());
				
				if (ret == null)

					throw new Exception("RET '" + ftr.getRet() + "' not identified in FTR.");
				
				ftr.setReferencedRegister(ret);
				
				for (Field field : ftr.getFields())
				{
					DataElement det = ret.getDataElementName(field.getName());
					
					if (det == null)
						throw new Exception("DET '" + field.getName() + "' not identified in FTR.");
					
					field.setDataElement(det);
				}
			}
		}
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
	private boolean getBooleanAttribute(Element element, String name) 
	{
		String value = element.getAttribute(name);
		
		if (value == null)
			return false;
		
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
}