package br.unirio.overwork.instance.model.data;

import lombok.Getter;
import lombok.Setter;

public class DataElement 
{
	private @Getter String name;
	
	private @Getter boolean primaryKey;
	
	private @Getter String dataModelElement;

	private @Getter String referencedRegister;
	
	private @Getter String description;
	
	private @Getter boolean hasSemanticMeaning;
	
	private @Getter @Setter boolean used;
	
	public DataElement(String name, boolean primaryKey, String referencedRegister, String dataModelElement, String description, boolean hasSemanticMeaning)
	{
		this.name = name;
		this.primaryKey = primaryKey;
		this.referencedRegister = referencedRegister;
		this.dataModelElement = dataModelElement;
		this.description = description;
		this.hasSemanticMeaning = hasSemanticMeaning;
		this.used = false;
	}
}