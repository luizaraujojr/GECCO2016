package br.unirio.nrpapf.model.transaction;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import br.unirio.nrpapf.model.data.DataElement;
import br.unirio.nrpapf.model.data.RegisterElement;

/**
 * Class that represents a data function reference from a transaction
 * 
 * @author Marcio Barros
 */
public class FileReference 
{
	private @Getter String name;
	
	private @Getter String referencedRegisterElement;

	private @Getter String referencedDataFunction;
	
	private @Getter boolean useAllDataElements;
	
	private List<Field> fields;
	
	private @Getter @Setter RegisterElement referencedRegister;
	
	/**
	 * Initializes a file reference from a transaction
	 */
	public FileReference(String name, String referencedRegisterElement, String referencedDataFunction, boolean useAllDets) 
	{
		this.name = name;
		this.referencedRegisterElement = referencedRegisterElement;
		this.referencedDataFunction = referencedDataFunction;
		this.useAllDataElements = useAllDets;
		this.fields = new ArrayList<Field>();
	}

	/**
	 * Adds a field in a file reference
	 */
	public void addField(Field field) 
	{
		fields.add(field);
	}
	
	/**
	 * Returns all fields in a file reference
	 */
	public Iterable<Field> getFields()
	{
		return fields;
	}
	
	/**
	 * Mark all data elements as used
	 */
	public void markDataElements()
	{
		if (useAllDataElements)
			for (DataElement det : referencedRegister.getDataElements())
				det.setUsed(true);
		
		for (Field field : fields)
			field.getDataElement().setUsed(true);
	}
}