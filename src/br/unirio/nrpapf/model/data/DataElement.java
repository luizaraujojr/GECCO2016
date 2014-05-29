package br.unirio.nrpapf.model.data;

import lombok.Getter;
import lombok.Setter;

/**
 * Class that represents a data element
 * 
 * @author Marcio Barros
 */
public class DataElement 
{
	/**
	 * DET name
	 */
	private @Getter String name;
	
	/**
	 * Indicates if the DET is a primary key
	 */
	private @Getter boolean primaryKey;
	
	/**
	 * Indicates whether the DET has semantic meaning
	 */
	private @Getter boolean hasSemanticMeaning;
	
	/**
	 * Name of the register element referenced by the data element
	 */
	private @Getter String referencedRegisterElement;
	
	/**
	 * Name of the data function containing the referenced RET
	 */
	private @Getter String referencedDataFunction;

	/**
	 * Description for the DET
	 */
	private @Getter String description;

	/**
	 * Indicates whether the DET is used
	 */
	private @Getter @Setter boolean used;
	
	/**
	 * Initializes the data element
	 */
	public DataElement(String name, boolean primaryKey, String referencedRegisterElement, String referencedDataFunction, String description, boolean hasSemanticMeaning)
	{
		this.name = name;
		this.primaryKey = primaryKey;
		this.referencedRegisterElement = referencedRegisterElement;
		this.referencedDataFunction = referencedDataFunction;
		this.description = description;
		this.hasSemanticMeaning = hasSemanticMeaning;
		this.used = false;
	}
}