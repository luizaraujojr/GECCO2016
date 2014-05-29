package br.unirio.nrpapf.model.transaction;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import br.unirio.nrpapf.model.data.RegisterElement;

/**
 * Class represents a transaction function
 * 
 * @author Marcio Barros
 */
public class Transaction 
{
	private @Getter String name;
	
	private @Getter boolean countErrorMessages;
	
	private @Getter TransactionType type;
	
	private @Getter int extraDataElements;
	
	private List<FileReference> fileReferences;
	
	private List<Dependency> dependencies;

	/**
	 * Initializes the transaction
	 */
	public Transaction(String name, boolean errorMessages, TransactionType type, int extraDET) 
	{
		this.name = name;
		this.countErrorMessages = errorMessages;
		this.type = type;
		this.extraDataElements = extraDET;
		this.dependencies = new ArrayList<Dependency>();
		this.fileReferences = new ArrayList<FileReference>();
	}

	/**
	 * Adds a file reference for the transaction
	 */
	public void addFileReference(FileReference ftr) 
	{
		fileReferences.add(ftr);
	}
	
	/**
	 * Returns the file references for the transaction
	 */
	public Iterable<FileReference> getFileReferences()
	{
		return fileReferences;
	}

	/**
	 * Adds a dependency to another transaction
	 */
	public void addDependency(Dependency dependency) 
	{
		dependencies.add(dependency);
	}
	
	/**
	 * Returns the dependencies upon other transactions
	 */
	public Iterable<Dependency> getDependencies()
	{
		return dependencies;
	}

	/**
	 * Determines if the transaction refers to a given RET
	 */
	public boolean containsFileReference(RegisterElement register)
	{
		for (FileReference fileReference : fileReferences)
			if (fileReference.getReferencedRegister().getDataFunction() == register.getDataFunction())
				return true;
		
		return false;
	}
}