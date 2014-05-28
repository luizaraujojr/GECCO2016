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
	
	public boolean countsForTransaction(DataFunction dataFunction)
	{
		if (!used)
			return false;
		
		if (!(hasSemanticMeaning || !primaryKey))
			return false;
		
		if (referencedRegister.length() == 0)
			return true;

		RegisterElement register = dataFunction.getRegisterElementName(referencedRegister);
		
		if (dataModelElement.length() == 0 && register == null)
			return true;

		if (dataModelElement.compareToIgnoreCase(dataFunction.getName()) == 0 && register == null)
			return true;
		
		return false;
//		return !primaryKey;
	}
	
	public boolean countsForDataFunction(DataFunction dataFunction)
	{
		if (!used)
			return false;
		
		if (!(hasSemanticMeaning || !primaryKey))
			return false;
		
		if (referencedRegister.length() == 0)
			return true;

		RegisterElement register = dataFunction.getRegisterElementName(referencedRegister);
		
		if (dataModelElement.length() == 0 && register == null)
			return true;

		if (dataModelElement.compareToIgnoreCase(dataFunction.getName()) == 0 && register == null)
			return true;
		
		return false;
	}
}