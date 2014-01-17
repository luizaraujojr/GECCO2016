package br.unirio.functionpoint.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Class that represents a data function for function point counting
 * 
 * @author Marcio Barros
 */
public class DataFunction 
{
	private @Getter String name;
	private @Getter DataFunctionType type;
	private List<RecordElement> rets;

	/**
	 * Initializes the data function
	 */
	public DataFunction(String name, DataFunctionType type)
	{
		this.name = name;
		this.type = type;
		this.rets = new ArrayList<RecordElement>();
	}
	
	/**
	 * Adds a record element to the data function
	 */
	public void addRecordElement(RecordElement ret)
	{
		rets.add(ret);
	}
	
	/**
	 * Counts the number of record elements in the data function
	 */
	public int countRecordElements()
	{
		return rets.size();
	}
	
	/**
	 * Returns the record elements registered in the data function
	 * @return
	 */
	public Iterable<RecordElement> getRecordElements()
	{
		return rets;
	}

	/**
	 * Returns the number of data elements in the data function
	 */
	public int countDataElements()
	{
		int sum = 0;
		
		for (RecordElement ret : rets)
			sum += ret.countVisibleDataElements();

		return sum;
	}

	/**
	 * Counts the number of data elements with a given name
	 */
	public int countDataElementByName(String name) 
	{
		int count = 0;
		
		for (RecordElement ret : rets)
			count += ret.countDataElementByName(name);
		
		return count;
	}

	/**
	 * Returns the index of a data element within the data function
	 */
	public int getDataElementIndex(String det)
	{
		int count = 0;
		
		for (RecordElement ret : rets)
		{
			int index = ret.getDataElementIndex(det);
			
			if (index != -1)
				return count + index;
			
			count += ret.countVisibleDataElements() + ret.countInvisibleDataElements();
		}
		
		return -1;
	}

	/**
	 * Returns the obfuscated name for a record element
	 */
	public String codify(RecordElement ret) 
	{
		return new DecimalFormat("0000").format(rets.indexOf(ret));
	}
	
	/**
	 * Returns the complexity of the data function
	 */
	public Complexity getComplexity()
	{
		int ret = countRecordElements();
		int det = countDataElements();
		
		if (ret == 1)
			return (det <= 50) ? Complexity.LOW : Complexity.AVERAGE;
		
		if (ret <= 5)
			return (det <= 19) ? Complexity.LOW : ((det <= 50) ? Complexity.AVERAGE : Complexity.HIGH);
		
		return (det <= 19) ? Complexity.AVERAGE : Complexity.HIGH;
	}

	/**
	 * Returns the number of function points associated to the data function
	 */
	public int getFunctionPoints()
	{
		Complexity c = getComplexity();
		
		if (c == Complexity.LOW)
			return type.getLowComplexityPoints();
		
		if (c == Complexity.AVERAGE)
			return type.getAverageComplexityPoints();
		
		return type.getHighComplexityPoints();
	}
}