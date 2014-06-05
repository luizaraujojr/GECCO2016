package br.unirio.overwork.builders.reader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import br.unirio.overwork.builders.model.SoftwareSystem;
import br.unirio.overwork.builders.model.WorkPackage;
import br.unirio.simulation.TopologicalSort;

/**
 * Class that reads an instance from a XML file
 * 
 * @author Marcio Barros
 */
public class Reader 
{
	/**
	 * Loads a software system from a XML file
	 */
	public SoftwareSystem run(String filename) throws Exception 
	{
		Element root = loadXmlFile (filename);
		String name = loadSystemName(root);
		
		List<DataFunction> dataFunctions = new ArrayList<DataFunction>();
		loadDataFunctions(root, dataFunctions);
		
		List<TransactionGroup> transactionGroups = new ArrayList<TransactionGroup>();
		loadTransactionGroups(root, transactionGroups);
		
		checkDuplicateDataFunctions(dataFunctions);
		checkDuplicateTransactionGroups(transactionGroups);
		checkDuplicateTransactions(transactionGroups);
		checkFileReferences(transactionGroups, dataFunctions);
		checkTransactionDependencies(transactionGroups);
		
		TransactionGroupTopologicalSorter wpts = new TransactionGroupTopologicalSorter();
		transactionGroups = wpts.sort(transactionGroups);
		
		SoftwareSystem system = new SoftwareSystem(name);
		createWorkPackages(transactionGroups, system);
		createWorkPackageDependencies(transactionGroups, system);		
		createDataFunctions(dataFunctions, transactionGroups, system);
		return system;
	}
	
	/**
	 * Loads the contents of a XML file to memory
	 */
	private Element loadXmlFile (String filename) throws Exception
	{
		File fXmlFile = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		return doc.getDocumentElement();
	}

	/**
	 * Load the name of the system
	 */
	private String loadSystemName(Element root)
	{
		NodeList xNames = root.getElementsByTagName("name");
		
		if (xNames.getLength() > 0) 
		{
			Element xName = (Element) xNames.item(0);
			return xName.getTextContent();
		}
		
		return "";
	}
	
	/**
	 * Load the data functions
	 */
	private void loadDataFunctions(Element root, List<DataFunction> dataFunctions)
	{
		NodeList xDataFunctions = root.getElementsByTagName("data");
		
		for (int i = 0; i < xDataFunctions.getLength(); i++) 
		{
			Element xDataFunction = (Element) xDataFunctions.item(i);
			String name = getAttribute(xDataFunction, "name");
			int fp = getIntAttribute(xDataFunction, "fp");
			dataFunctions.add(new DataFunction(name, fp));
		}		
	}
	
	/**
	 * Load the transaction groups
	 */
	private void loadTransactionGroups(Element root, List<TransactionGroup> transactionGroups)
	{
		NodeList xTransactionGroups = root.getElementsByTagName("group");
		
		for (int i = 0; i < xTransactionGroups.getLength(); i++) 
		{
			Element xTransactionGroup = (Element) xTransactionGroups.item(i);
			String name = getAttribute(xTransactionGroup, "name");

			TransactionGroup group = new TransactionGroup(name);
			loadTransactions(xTransactionGroup, group);
			transactionGroups.add(group);
		}		
	}
	
	/**
	 * Load the transactions comprising a transaction group
	 */
	private void loadTransactions(Element root, TransactionGroup transactionGroup)
	{
		NodeList xTransactions = root.getElementsByTagName("transaction");
		
		for (int i = 0; i < xTransactions.getLength(); i++) 
		{
			Element xTransaction = (Element) xTransactions.item(i);
			String name = getAttribute(xTransaction, "name");
			int fp = getIntAttribute(xTransaction, "fp");

			Transaction transaction = new Transaction(name, fp);
			loadFileReferences(xTransaction, transaction);
			loadTransactionDependencies(xTransaction, transaction);
			transactionGroup.addTransaction(transaction);
		}		
	}
	
	/**
	 * Load the files referenced by a transaction
	 */
	private void loadFileReferences(Element root, Transaction transaction)
	{
		NodeList xFileReferences = root.getElementsByTagName("ftr");
		
		for (int i = 0; i < xFileReferences.getLength(); i++) 
		{
			Element xFileReference = (Element) xFileReferences.item(i);
			String name = getAttribute(xFileReference, "name");
			transaction.addFileReference(name);
		}
	}
	
	/**
	 * Load the transaction upon which a transaction depends
	 */
	private void loadTransactionDependencies(Element root, Transaction transaction)
	{
		NodeList xDependencies = root.getElementsByTagName("depends");
		
		for (int i = 0; i < xDependencies.getLength(); i++) 
		{
			Element xDependency = (Element) xDependencies.item(i);
			String name = getAttribute(xDependency, "name");
			transaction.addTransacionDependency(name);
		}
	}
	
	/**
	 * Check if there exist two or more data functions with the same name
	 */
	private void checkDuplicateDataFunctions(List<DataFunction> dataFunctions) throws Exception
	{
		for (DataFunction dataFunction1 : dataFunctions)
			for (DataFunction dataFunction2 : dataFunctions)
				if (dataFunction1 != dataFunction2 && dataFunction1.getName().compareTo(dataFunction2.getName()) == 0)
					throw new Exception("There are at least two data functions named '" + dataFunction1.getName() + "'");
	}
	
	/**
	 * Check if there exist two or more transaction groups with the same name
	 */
	private void checkDuplicateTransactionGroups(List<TransactionGroup> transactionGroups) throws Exception
	{
		for (TransactionGroup wp1 : transactionGroups)
			for (TransactionGroup wp2 : transactionGroups)
				if (wp1 != wp2 && wp1.getName().compareTo(wp2.getName()) == 0)
					throw new Exception("There are at least two transaction groups named '" + wp1.getName() + "'");
	}
	
	/**
	 * Check if there exist two or more transactions with the same name
	 */
	private void checkDuplicateTransactions(List<TransactionGroup> transactionGroups) throws Exception
	{
		for (TransactionGroup group1 : transactionGroups)
		{
			for (Transaction transaction1 : group1.getTransactions())
			{
				for (TransactionGroup group2 : transactionGroups)
				{
					for (Transaction transaction2 : group2.getTransactions())
					{
						if (transaction1 != transaction2 && transaction1.getName().compareTo(transaction2.getName()) == 0)
							throw new Exception("There are at least two transactions named '" + transaction1.getName() + "'");
					}
				}
			}
		}
	}

	/**
	 * Check if all file references are associated to valid data functions
	 */
	private void checkFileReferences(List<TransactionGroup> transactionGroups, List<DataFunction> dataFunctions) throws Exception
	{
		for (TransactionGroup wp : transactionGroups)
		{
			for (Transaction requirement : wp.getTransactions())
			{
				for (String fileReference : requirement.getFileReferences())
					if (getDataFunctionName(dataFunctions, fileReference) == null)
						throw new Exception("There is no data function named '" + fileReference + "' as referenced by transaction '" + requirement.getName() +"'");
			}
		}
	}
	
	/**
	 * Returns a data function, given its name
	 */
	private DataFunction getDataFunctionName(List<DataFunction> dataFunctions, String name)
	{
		for (DataFunction dataFunction : dataFunctions)
			if (dataFunction.getName().compareTo(name) == 0)
				return dataFunction;
		
		return null;
	}

	/**
	 * Check if all transaction references are associated to valid transactions
	 */
	private void checkTransactionDependencies(List<TransactionGroup> transactionGroups) throws Exception
	{
		for (TransactionGroup group : transactionGroups)
		{
			for (Transaction transaction : group.getTransactions())
			{
				for (String transactionDependency : transaction.getTransactionDependencies())
				{
					if (getTransactionName(transactionGroups, transactionDependency) == null)
						throw new Exception("There is no transaction name '" + transactionDependency + "' as referenced by transaction '" + transaction.getName() +"'");
				}
			}
		}
	}
	
	/**
	 * Returns a transaction, given its name
	 */
	private Transaction getTransactionName(List<TransactionGroup> transactionGroups, String name)
	{
		for (TransactionGroup group : transactionGroups)
			for (Transaction transaction : group.getTransactions())
				if (transaction.getName().compareTo(name) == 0)
					return transaction;
		
		return null;
	}

	/**
	 * Returns a list of dependencies for a transaction group
	 */
	private List<TransactionGroup> getDependencies(List<TransactionGroup> transactionGroups, TransactionGroup transactionGroup)
	{
		List<TransactionGroup> dependencies = new ArrayList<TransactionGroup>();
		
		for (Transaction transaction : transactionGroup.getTransactions())
			for (String transactionName : transaction.getTransactionDependencies())
			{
				TransactionGroup wp = getTransactionGroupForTransaction(transactionGroups, transactionName);
				
				if (wp != transactionGroup && !dependencies.contains(wp))
					dependencies.add(wp);
			}
		
		return dependencies;
	}	

	/**
	 * Creates the dependencies for a set of work packages
	 */
	private void createWorkPackageDependencies(List<TransactionGroup> transactionGroups, SoftwareSystem system) throws Exception
	{
		for (TransactionGroup group : transactionGroups)
		{
			List<TransactionGroup> dependencies = getDependencies(transactionGroups, group);

			WorkPackage wp = system.getWorkPackageName(group.getName());
			
			if (wp == null)
				throw new Exception("The work package '" + group.getName() + "' was not found");
			
			for (TransactionGroup dependency : dependencies)
			{
				WorkPackage dependent = system.getWorkPackageName(dependency.getName());
				
				if (dependent == null)
					throw new Exception("The work package '" + dependency.getName() + "' was not found");

				if (dependent != wp)
					wp.addDependency(dependent);
			}
		}
	}

	/**
	 * Returns the transaction group containing a transaction
	 */
	private TransactionGroup getTransactionGroupForTransaction(List<TransactionGroup> transactionGroups, String name)
	{
		for (TransactionGroup group : transactionGroups)
			if (group.containsTransaction(name))
				return group;
		
		return null;
	}

	/**
	 * Creates the work packages to represent transaction groups
	 */
	private void createWorkPackages(List<TransactionGroup> transactionGroups, SoftwareSystem system)
	{
		for (TransactionGroup group : transactionGroups)
		{
			WorkPackage wp = new WorkPackage(group.getName());
			
			for (Transaction transaction : group.getTransactions())
				wp.addComponent("TF " + transaction.getName(), transaction.getFunctionPoints());

			system.addWorkPackage(wp);
		}
	}

	/**
	 * Creates the components to represent data functions
	 */
	private void createDataFunctions(List<DataFunction> dataFunctions, List<TransactionGroup> transactionGroups, SoftwareSystem system) throws Exception
	{
		for (DataFunction dataFunction : dataFunctions)
		{
			boolean found = false;
			
			for (int i = 0; i < transactionGroups.size() && !found; i++)
			{
				TransactionGroup group = transactionGroups.get(i);
				
				if (group.containsFileReference(dataFunction.getName()))
				{
					WorkPackage wp = system.getWorkPackageName(group.getName());
					
					if (wp != null)
					{
						wp.addComponent("DF " + dataFunction.getName(), dataFunction.getFunctionPoints());
						found = true;
					}
				}
			}
			
			if (!found)
				throw new Exception("The data function named '" + dataFunction.getName() + "' is not referenced by transactions");
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

class TransactionGroupTopologicalSorter extends TopologicalSort<TransactionGroup>
{
	private TransactionGroup getTransactionGroupForTransaction(List<TransactionGroup> items, String name)
	{
		for (TransactionGroup wp : items)
			if (wp.containsTransaction(name))
				return wp;
		
		return null;
	}
	
	@Override
	protected List<TransactionGroup> getDependencies(List<TransactionGroup> items, TransactionGroup item)
	{
		List<TransactionGroup> dependencies = new ArrayList<TransactionGroup>();
		
		for (Transaction requirement : item.getTransactions())
			for (String transactionName : requirement.getTransactionDependencies())
			{
				TransactionGroup wp = getTransactionGroupForTransaction(items, transactionName);
				
				if (wp != item && !dependencies.contains(wp))
					dependencies.add(wp);
			}
		
		return dependencies;
	}	
}