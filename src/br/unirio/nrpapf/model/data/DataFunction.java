package br.unirio.nrpapf.model.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Class that represents a data function for function point analysis
 * 
 * @author Marcio Barros
 */
public class DataFunction
{
	/**
	 * Data function name
	 */
	private @Getter String name;
	
	/**
	 * Data function type (EIF or ILF)
	 */
	private @Getter DataFunctionType type;
	
	/**
	 * Set of RET within the data function
	 */
	private List<RegisterElement> registerElements;
	
	/**
	 * Initializes the data function
	 */
	public DataFunction(String name, DataFunctionType type)
	{
		this.name = name;
		this.type = type;
		this.registerElements = new ArrayList<RegisterElement>();
	}

	/**
	 * Adds a RET to the data function
	 */
	public void addRegisterElement(RegisterElement ret) 
	{
		this.registerElements.add(ret);
	}
	
	/**
	 * Gets all RETs from the data function
	 */
	public Iterable<RegisterElement> getRegisterElements()
	{
		return registerElements;
	}

	/**
	 * Gets a RET given its name
	 */
	public RegisterElement getRegisterElementName(String name) 
	{
		for (RegisterElement ret : registerElements)
			if (ret.getName().compareToIgnoreCase(name) == 0)
				return ret;
		
		return null;
	}
}