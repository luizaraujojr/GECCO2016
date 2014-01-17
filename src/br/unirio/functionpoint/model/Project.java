package br.unirio.functionpoint.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a software project described in terms of function points
 * 
 * @author Marcio Barros
 */
public class Project 
{
	private List<DataFunction> dataFunctions;
	private List<TransactionFunction> transactionFunctions;
	private List<Stakeholder> stakeholders;

	/**
	 * Initializes the project
	 */
	public Project()
	{
		this.dataFunctions = new ArrayList<DataFunction>();
		this.transactionFunctions = new ArrayList<TransactionFunction>();
		this.stakeholders = new ArrayList<Stakeholder>();
	}

	/**
	 * Adds a data function to the project
	 */
	public void addDataFunction(DataFunction df) 
	{
		dataFunctions.add(df);
	}

	/**
	 * Counts the number of data functions
	 */
	public int countDataFunctions() 
	{
		return dataFunctions.size();
	}

	/**
	 * Counts the number of data functions of a given type
	 */
	public int countDataFunctions(DataFunctionType type) 
	{
		int count = 0;
		
		for (DataFunction df : dataFunctions)
			if (df.getType() == type)
				count++;
		
		return count;
	}

	/**
	 * Counts the number of data functions of a given type and complexity
	 */
	public int countDataFunctions(DataFunctionType type, Complexity c) 
	{
		int count = 0;
		
		for (DataFunction df : dataFunctions)
			if (df.getType() == type && df.getComplexity() == c)
				count++;
		
		return count;
	}

	/**
	 * Returns a data function, given its name
	 */
	public DataFunction getDataFunctionName(String name) 
	{
		for (DataFunction df : dataFunctions)
			if (df.getName().compareToIgnoreCase(name) == 0)
				return df;
		
		return null;
	}
	
	/**
	 * Returns the list of data function
	 */
	public Iterable<DataFunction> getDataFunctions()
	{
		return dataFunctions;
	}

	/**
	 * Adds a transaction function to the project
	 */
	public void addTransactionFunction(TransactionFunction tf) 
	{
		transactionFunctions.add(tf);
	}

	/**
	 * Counts the number of transactions of a given type
	 */
	public int countTransactions(TransactionFunctionType tft)
	{
		int count = 0;
		
		for (TransactionFunction tf : transactionFunctions)
			if (tf.getType() == tft)
				count++;
		
		return count;
	}

	/**
	 * Counts the number of transactions of a given type and complexity
	 */
	public int countTransactions(TransactionFunctionType tft, Complexity c)
	{
		int count = 0;
		
		for (TransactionFunction tf : transactionFunctions)
			if (tf.getType() == tft && tf.getComplexity() == c)
				count++;
		
		return count;
	}

	/**
	 * Retuns a transaction, given its name and group
	 */
	public TransactionFunction getTransaction(String name, String group) 
	{
		for (TransactionFunction tf : transactionFunctions)
			if (tf.getGroup().compareToIgnoreCase(group) == 0 && tf.getName().compareToIgnoreCase(name) == 0)
				return tf;

		return null;
	}
	
	/**
	 * Returns the list of transaction functions
	 */
	public Iterable<TransactionFunction> getTransactionFunctions()
	{
		return transactionFunctions;
	}

	/**
	 * Checks whether the project has a given transaction group
	 */
	public boolean hasTransactionGroup(String group) 
	{
		for (TransactionFunction tf : transactionFunctions)
			if (tf.getGroup().compareToIgnoreCase(group) == 0)
				return true;

		return false;
	}

	/**
	 * Adds a stakeholder to the project
	 */
	public void addStakeholder(Stakeholder stakeholder)
	{
		stakeholders.add(stakeholder);
	}
	
	/**
	 * Returns all project stakeholders
	 */
	public Iterable<Stakeholder> getStakeholders()
	{
		return this.stakeholders;
	}

	/**
	 * Generates the obfuscated name for a data function
	 */
	public String codify(DataFunction df) 
	{
		return new DecimalFormat("0000").format(dataFunctions.indexOf(df));
	}

	/**
	 * Generates the obfuscated name for a transaction function
	 */
	public String codify(TransactionFunction tf) 
	{
		return new DecimalFormat("0000").format(transactionFunctions.indexOf(tf));
	}

	/**
	 * Generates the obfuscated name for a stakeholder
	 */
	public String codify(Stakeholder sh) 
	{
		return new DecimalFormat("0000").format(stakeholders.indexOf(sh));
	}

	/**
	 * Returns the number of function points in the project
	 */
	public int getFunctionPoints()
	{
		int count = 0;
		
		for (DataFunction df : dataFunctions)
			count += df.getFunctionPoints();
		
		for (TransactionFunction tf : transactionFunctions)
			count += tf.getFunctionPoints();
		
		return count;
	}
}