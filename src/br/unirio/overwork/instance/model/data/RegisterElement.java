package br.unirio.overwork.instance.model.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class RegisterElement 
{
	private @Getter String name;
	
	private List<DataElement> dataElements;
	
	public RegisterElement(String name)
	{
		this.name = name;
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

	public int countDataElements() 
	{
		int count = 0;
		
		for (DataElement det : dataElements)
			if (det.isHasSemanticMeaning() || !det.isPrimaryKey())
				count++;

		return count;
	}
}