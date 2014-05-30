package br.unirio.nrpapf.model.transaction;

import lombok.Getter;
import lombok.Setter;
import br.unirio.nrpapf.model.data.DataElement;

/**
 * Class that represents a field within a transaction
 * 
 * @author Marcio Barros
 */
public class Field 
{
	private @Getter String name;

	private @Getter @Setter DataElement dataElement;
	
	public Field(String name) 
	{
		this.name = name;
	}
}