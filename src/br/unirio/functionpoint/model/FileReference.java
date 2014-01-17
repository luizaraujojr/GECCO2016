package br.unirio.functionpoint.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Class that represents a file reference in a transaction function
 * 
 * @author Marcio Barros
 */
public class FileReference 
{
	/**
	 * Data function referenced by the transaction
	 */
	private @Getter DataFunction dataFunction; 
	
	/**
	 * Data elements within the data function referenced by the transaction
	 */
	private List<String> dataElements;

	/**
	 * Initializes a file reference
	 */
	public FileReference(DataFunction dataFunction)
	{
		this.dataFunction = dataFunction;
		this.dataElements = new ArrayList<String>();
	}
	
	/**
	 * Adds a data element in the reference
	 */
	public void addDataElement(String name)
	{
		dataElements.add(name);
	}
	
	/**
	 * Counts the number of data elements
	 */
	public int countDataElements()
	{
		return dataElements.size();
	}
	
	/**
	 * Returns the list of data elements
	 */
	public Iterable<String> getDataElements()
	{
		return dataElements;
	}

	/**
	 * Checks whether the file reference contains a certain data element
	 */
	public boolean referencesDataElement(String name) 
	{
		for (String s : dataElements)
			if (s.compareToIgnoreCase(name) == 0)
				return true;
		
		return false;
	}
}