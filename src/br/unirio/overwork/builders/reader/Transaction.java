package br.unirio.overwork.builders.reader;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Class that represents a transaction function 
 * 
 * @author Marcio Barros
 */
class Transaction
{
	/**
	 * Transaction name
	 */
	private @Getter String name;

	/**
	 * Number of function points
	 */
	private @Getter int functionPoints;
	
	/**
	 * List of file references
	 */
	private List<String> fileReferences;
	
	/**
	 * List of dependent transactions
	 */
	private List<String> transactionDependencies;

	/**
	 * Initializes a transaction
	 */
	public Transaction(String name, int functionPoints)
	{
		this.name = name;
		this.functionPoints = functionPoints;
		this.fileReferences = new ArrayList<String>();
		this.transactionDependencies = new ArrayList<String>();
	}
	
	/**
	 * Adds a file reference to a transaction
	 */
	public void addFileReference(String name)
	{
		this.fileReferences.add(name);
	}
	
	/**
	 * Returns all files referenced by a transaction
	 */
	public Iterable<String> getFileReferences()
	{
		return fileReferences;
	}
	
	/**
	 * Adds a transaction upon which the current transaction depends
	 */
	public void addTransacionDependency(String name)
	{
		this.transactionDependencies.add(name);
	}
	
	/**
	 * Returns all transactions upon which the transaction depends
	 */
	public Iterable<String> getTransactionDependencies()
	{
		return transactionDependencies;
	}
}