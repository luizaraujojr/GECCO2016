package br.unirio.overwork.instance.model.transaction;

import lombok.Getter;
import lombok.Setter;
import br.unirio.overwork.instance.model.data.DataElement;

public class Field 
{
	private @Getter String name;

	private @Getter @Setter DataElement dataElement;
	
	public Field(String name) 
	{
		this.name = name;
	}
}