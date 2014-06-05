package br.unirio.overwork.builders.reader;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Class that represents a transaction group
 * 
 * @author Marcio Barros
 */
class TransactionGroup
{
	/**
	 * Transaction group's name
	 */
	private @Getter String name;
	
	/**
	 * List of transactions within the group
	 */
	private List<Transaction> transactions;
	
	/**
	 * Initializes the transaction group
	 */
	public TransactionGroup(String name)
	{
		this.name = name;
		this.transactions = new ArrayList<Transaction>();
	}
	
	/**
	 * Counts the number of transactions in the group
	 */
	public int countTransactions()
	{
		return transactions.size();
	}
	
	/**
	 * Returns a transaction, given its index
	 */
	public Transaction getTransactionIndex(int index)
	{
		return transactions.get(index);
	}
	
	/**
	 * Returns a transaction, given its name
	 */
	public Transaction getTransactionName(String name)
	{
		for (Transaction transaction : transactions)
			if (transaction.getName().compareToIgnoreCase(name) == 0)
				return transaction;
		
		return null;
	}
	
	/**
	 * Returns all transactions
	 */
	public Iterable<Transaction> getTransactions()
	{
		return transactions;
	}
	
	/**
	 * Adds a transaction to the group
	 */
	public void addTransaction(Transaction transaction)
	{
		this.transactions.add(transaction);
	}
	
	/**
	 * Calculates the number of function points in the transaction group
	 */
	public int calculateFunctionPoints()
	{
		int soma = 0;
		
		for (Transaction transaction : transactions)
			soma += transaction.getFunctionPoints();
		
		return soma;
	}
	
	/**
	 * Checks whether the group contains a given transaction
	 */
	public boolean containsTransaction(String name)
	{
		for (Transaction transaction : transactions)
			if (transaction.getName().compareTo(name) == 0)
				return true;

		return false;
	}

	/**
	 * Checks whether the group refers to a data function 
	 */
	public boolean containsFileReference(String name)
	{
		for (Transaction transaction : transactions)
			for (String fileReference : transaction.getFileReferences())
				if (fileReference.compareTo(name) == 0)
					return true;

		return false;
	}
}