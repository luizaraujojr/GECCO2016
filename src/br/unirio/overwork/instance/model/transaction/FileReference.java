package br.unirio.overwork.instance.model.transaction;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import br.unirio.overwork.instance.model.data.DataElement;
import br.unirio.overwork.instance.model.data.RegisterElement;

public class FileReference 
{
	private @Getter String name;
	
	private @Getter String ret;

	private @Getter String dataModelElement;
	
	private @Getter boolean useAllDataElements;
	
	private List<Field> fields;
	
	private @Getter @Setter RegisterElement referencedRegister;
	
	public FileReference(String name, String ret, String dataModelElement, boolean useAllDets) 
	{
		this.name = name;
		this.ret = ret;
		this.dataModelElement = dataModelElement;
		this.useAllDataElements = useAllDets;
		this.fields = new ArrayList<Field>();
	}

	public void addField(Field field) 
	{
		fields.add(field);
	}
	
	public Iterable<Field> getFields()
	{
		return fields;
	}

	public int countDataElements() 
	{
		int count = 0;
		
		if (useAllDataElements)
			for (DataElement det : referencedRegister.getDataElements())
				if (det.countsForTransaction())
					count++;
		
		for (Field field : fields)
			if (field.getDataElement().countsForTransaction())
				count++;

		return count;
	}

	public void markDataElements()
	{
		if (useAllDataElements)
			for (DataElement det : referencedRegister.getDataElements())
				det.setUsed(true);
		
		for (Field field : fields)
			field.getDataElement().setUsed(true);
	}
}