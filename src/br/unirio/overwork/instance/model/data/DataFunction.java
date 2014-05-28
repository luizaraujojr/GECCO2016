package br.unirio.overwork.instance.model.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class DataFunction
{
	private @Getter String name;
	
	private @Getter DataFunctionType type;
	
	private @Getter @Setter int fp;
	
	private List<RegisterElement> registerElements;
	
	public DataFunction(String name, DataFunctionType type)
	{
		this.name = name;
		this.type = type;
		this.registerElements = new ArrayList<RegisterElement>();
		this.fp = 0;
	}

	public void addRegisterElement(RegisterElement ret) 
	{
		this.registerElements.add(ret);
	}
	
	public Iterable<RegisterElement> getRegisterElements()
	{
		return registerElements;
	}

	public RegisterElement getRegisterElementName(String name) 
	{
		for (RegisterElement ret : registerElements)
			if (ret.getName().compareToIgnoreCase(name) == 0)
				return ret;
		
		return null;
	}
}