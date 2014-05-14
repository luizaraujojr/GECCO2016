package br.unirio.overwork.instance.model.transaction;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class Transaction 
{
	private @Getter String name;
	
	private @Getter boolean countErrorMessages;
	
	private @Getter TransactionType type;
	
	private @Getter int extraDataElements;
	
	private List<FileReference> fileReferences;
	
	private List<Dependency> dependencies;

	public Transaction(String name, boolean errorMessages, TransactionType type, int extraDET) 
	{
		this.name = name;
		this.countErrorMessages = errorMessages;
		this.type = type;
		this.extraDataElements = extraDET;
		this.dependencies = new ArrayList<Dependency>();
		this.fileReferences = new ArrayList<FileReference>();
	}

	public void addFileReference(FileReference ftr) 
	{
		fileReferences.add(ftr);
	}
	
	public Iterable<FileReference> getFileReferences()
	{
		return fileReferences;
	}

	public void addDependency(Dependency dependency) 
	{
		dependencies.add(dependency);
	}
	
	public Iterable<Dependency> getDependencies()
	{
		return dependencies;
	}
}