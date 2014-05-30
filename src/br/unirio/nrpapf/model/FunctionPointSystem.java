package br.unirio.nrpapf.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import br.unirio.nrpapf.model.data.DataFunction;
import br.unirio.nrpapf.model.stakeholder.Interest;
import br.unirio.nrpapf.model.stakeholder.Stakeholder;
import br.unirio.nrpapf.model.transaction.Transaction;

/**
 * Class that represents a system organized in function points
 * 
 * @author Marcio Barros
 */
public final class FunctionPointSystem 
{
	private @Getter String name;
	
	private List<DataFunction> dataFunctions;
	
	private List<Transaction> transactions;
	
	private List<Stakeholder> stakeholders;
	
	private List<Interest> interests;
	
	/**
	 * Initializes the system
	 */
	public FunctionPointSystem(String name)
	{
		this.name = name;
		this.dataFunctions = new ArrayList<DataFunction>();
		this.transactions = new ArrayList<Transaction>();
		this.stakeholders = new ArrayList<Stakeholder>();
		this.interests = new ArrayList<Interest>();
	}

	/**
	 * Adds a data function to the system
	 */
	public void addDataFunction(DataFunction element) 
	{
		dataFunctions.add(element);
	}

	/**
	 * Returns a data function, given its name
	 */
	public DataFunction getDataFunctionName(String name) 
	{
		for (DataFunction data : dataFunctions)
			if (data.getName().compareToIgnoreCase(name) == 0)
				return data;
		
		return null;
	}
	
	/**
	 * Returns all data functions
	 */
	public Iterable<DataFunction> getDataFunctions()
	{
		return dataFunctions;
	}

	/**
	 * Adds a transaction function to the system
	 */
	public void addTransaction(Transaction transaction) 
	{
		transactions.add(transaction);
	}

	/**
	 * Returns a transaction function, given its name
	 */
	public Transaction getTransactionName(String name) 
	{
		for (Transaction transaction : transactions)
			if (transaction.getName().compareToIgnoreCase(name) == 0)
				return transaction;
		
		return null;
	}
	
	/**
	 * Returns all transaction functions
	 */
	public Iterable<Transaction> getTransactions()
	{
		return transactions;
	}
	
	/**
	 * Returns all stakeholders
	 */
	public Iterable<Stakeholder> getStakeholders()
	{
		return stakeholders;
	}
	
	/**
	 * Returns all stakeholder interests
	 */
	public Iterable<Interest> getInterest()
	{
		return interests;
	}
	
	/**
	 * Retuns all interests in for a given transaction
	 */
	public List<Interest> getInterests(Transaction transaction) 
	{
		List<Interest> interests = new ArrayList<Interest>();
		
		for (Interest interest : interests)
			if (interest.getTransaction().getName().equals(transaction.getName()))
				interests.add(interest);

		return interests;
	}
}