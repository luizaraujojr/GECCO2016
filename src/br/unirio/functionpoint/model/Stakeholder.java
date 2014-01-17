package br.unirio.functionpoint.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Class that represents a stakeholder in a project
 * 
 * @author Marcio Barros
 */
public class Stakeholder 
{
	/**
	 * Stakeholder's name
	 */
	private @Getter String name;

	/**
	 * Stakeholder's importance to the project
	 */
	private @Getter int weight;

	/**
	 * Stakeholder's desired transactions
	 */
	private List<TransactionFunction> transactions;
	
	/**
	 * Initializes a stakeholder
	 */
	public Stakeholder(String name, int weight)
	{
		this.name = name;
		this.weight = weight;
		this.transactions = new ArrayList<TransactionFunction>();
	}
	
	/**
	 * Adds a transaction to the stakeholder's profile
	 */
	public void addTransaction(TransactionFunction tf)
	{
		this.transactions.add(tf);
	}

	/**
	 * Returns the transactions desired by the stakeholder
	 */
	public Iterable<TransactionFunction> getTransactions()
	{
		return this.transactions;
	}

	/**
	 * Checks whether the stakeholder desired a given transaction
	 */
	public boolean hasTransactionFunction(TransactionFunction tf) 
	{
		return this.transactions.contains(tf);
	}
}