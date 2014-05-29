package br.unirio.nrpapf.model.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Class that represents a RET within a data function
 * 
 * @author Marcio Barros
 */
public class RegisterElement 
{
	/**
	 * Register's name
	 */
	private @Getter String name;
	
	/**
	 * Register's parent data function
	 */
	private @Getter DataFunction dataFunction;
	
	/**
	 * The list of the register's elements
	 */
	private List<DataElement> dataElements;
	
	/**
	 * Initializes a register element
	 */
	public RegisterElement(String name, DataFunction dataFunction)
	{
		this.name = name;
		this.dataFunction = dataFunction;
		this.dataElements = new ArrayList<DataElement>();
	}

	/**
	 * Adds a data element to the register
	 */
	public void addDataElements(DataElement det) 
	{
		this.dataElements.add(det);
	}

	/**
	 * Returns a data element given its name
	 */
	public DataElement getDataElementName(String name) 
	{
		for (DataElement det : dataElements)
			if (det.getName().compareToIgnoreCase(name) == 0)
				return det;
		
		return null;
	}

	/**
	 * Returns the list of data elements
	 */
	public Iterable<DataElement> getDataElements()
	{
		return dataElements;
	}
}