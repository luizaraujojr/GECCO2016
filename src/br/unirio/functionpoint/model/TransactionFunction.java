package br.unirio.functionpoint.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Class that represents a transaction function in a project
 * 
 * @author Marcio Barros
 */
public class TransactionFunction 
{
	/**
	 * Transaction name
	 */
	private @Getter String name;
	
	/**
	 * Transaction group, if available
	 */
	private @Getter String group;
	
	/**
	 * Transaction type
	 */
	private @Getter TransactionFunctionType type;
	
	/**
	 * Files referenced by the transaction
	 */
	private List<FileReference> fileReferences;
	
	/**
	 * Data elements handled by the transaction outside of data functions
	 */
	private @Getter int extraDataElements;

	/**
	 * Initialiazes a transaction function
	 */
	public TransactionFunction(String name, String group, TransactionFunctionType type, int extraDET)
	{
		this.name = name;
		this.group = group;
		this.type = type;
		this.fileReferences = new ArrayList<FileReference>();
		this.extraDataElements = extraDET;
	}
	
	/**
	 * Adds a file reference to the transaction
	 */
	public void addFileReference(FileReference ftr)
	{
		fileReferences.add(ftr);
	}
	
	/**
	 * Counts the number of file references in the transaction
	 */
	public int countFileReferences()
	{
		return fileReferences.size();
	}

	/**
	 * Returns a file reference, given its name
	 */
	public FileReference getFileReferenceByName(String name) 
	{
		for (FileReference ftr : fileReferences)
			if (ftr.getDataFunction().getName().compareToIgnoreCase(name) == 0)
				return ftr;
		
		return null;
	}

	/**
	 * Checks whether the transaction refers to a given data function
	 */
	public boolean referencesDataFunction(String ftrName) 
	{
		return getFileReferenceByName(ftrName) != null;
	}

	/**
	 * Returns a list of file references
	 */
	public Iterable<FileReference> getFileReferences() 
	{
		return fileReferences;
	}

	/**
	 * Creates a file reference, given a data function
	 */
	public FileReference createFileReference(DataFunction dataFunction) 
	{
		FileReference ftr = new FileReference(dataFunction);
		addFileReference(ftr);
		return ftr;
	}
	
	/**
	 * Counts the number of data elements in the transaction
	 */
	public int countDataElements()
	{
		int sum = extraDataElements;
		
		for (FileReference ftr : fileReferences)
			sum += ftr.countDataElements();

		return sum;
	}

	/**
	 * Checks whether the transaction refers to a given data element
	 */
	public boolean referencesDataElement(String ftrName, String detName) 
	{
		FileReference ftr = getFileReferenceByName(ftrName);
		
		if (ftr == null)
			return false;
		
		return ftr.referencesDataElement(detName);
	}

	/**
	 * Returns the complexity of the transaction function
	 */
	public Complexity getComplexity()
	{
		int ftr = countFileReferences();
		int det = countDataElements();
		
		if (type == TransactionFunctionType.EI)
		{
			if (ftr <= 1)
				return (det <= 15) ? Complexity.LOW : Complexity.AVERAGE;
			
			if (ftr == 2)
				return (det <= 4) ? Complexity.LOW : ((det <= 15) ? Complexity.AVERAGE : Complexity.HIGH);
			
			return (det <= 4) ? Complexity.AVERAGE : Complexity.HIGH;
		}
		else
		{
			if (ftr <= 1)
				return (det <= 19) ? Complexity.LOW : Complexity.AVERAGE;
			
			if (ftr <= 3)
				return (det <= 5) ? Complexity.LOW : ((det <= 19) ? Complexity.AVERAGE : Complexity.HIGH);
			
			return (det <= 5) ? Complexity.AVERAGE : Complexity.HIGH;
		}
	}

	/**
	 * Returns the number of function points for the transaction function
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