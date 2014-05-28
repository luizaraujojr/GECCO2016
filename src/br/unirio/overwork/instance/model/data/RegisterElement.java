package br.unirio.overwork.instance.model.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class RegisterElement 
{
	private @Getter String name;
	
	private @Getter DataFunction dataFunction;
	
	private List<DataElement> dataElements;
	
	public RegisterElement(String name, DataFunction dataFunction)
	{
		this.name = name;
		this.dataFunction = dataFunction;
		this.dataElements = new ArrayList<DataElement>();
	}

	public void addDataElements(DataElement det) 
	{
		this.dataElements.add(det);
	}

	public DataElement getDataElementName(String name) 
	{
		for (DataElement det : dataElements)
			if (det.getName().compareToIgnoreCase(name) == 0)
				return det;
		
		return null;
	}

	public Iterable<DataElement> getDataElements()
	{
		return dataElements;
	}
}