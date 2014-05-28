package br.unirio.overwork.instance.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import br.unirio.overwork.instance.model.data.DataFunction;
import br.unirio.overwork.instance.model.data.RegisterElement;
import br.unirio.overwork.instance.model.stakeholder.Interest;
import br.unirio.overwork.instance.model.stakeholder.Stakeholder;
import br.unirio.overwork.instance.model.transaction.Transaction;

public final class FunctionPointSystem 
{
	private @Getter @Setter String name;
	
	private List<DataFunction> dataFunctions;
	
	private List<Transaction> transactions;
	
	private List<Stakeholder> stakeholders;
	
	private List<Interest> interests;
	
	public FunctionPointSystem(String name)
	{
		this.name = name;
		this.dataFunctions = new ArrayList<DataFunction>();
		this.transactions = new ArrayList<Transaction>();
		this.stakeholders = new ArrayList<Stakeholder>();
		this.interests = new ArrayList<Interest>();
	}

	public void addDataFunction(DataFunction element) 
	{
		dataFunctions.add(element);
	}

	public DataFunction getDataFunctionName(String name) 
	{
		for (DataFunction data : dataFunctions)
			if (data.getName().compareToIgnoreCase(name) == 0)
				return data;
		
		return null;
	}
	
	public Iterable<DataFunction> getDataFunctions()
	{
		return dataFunctions;
	}

	public void addTransaction(Transaction transaction) 
	{
		transactions.add(transaction);
	}

	public Transaction getTransactionName(String name) 
	{
		for (Transaction transaction : transactions)
			if (transaction.getName().compareToIgnoreCase(name) == 0)
				return transaction;
		
		return null;
	}
	
	public Iterable<Transaction> getTransactions()
	{
		return transactions;
	}
	
	public Iterable<Stakeholder> getStakeholders()
	{
		return stakeholders;
	}
	
	public Iterable<Interest> getInterest()
	{
		return interests;
	}
	
	public List<Interest> getInterests(Transaction transaction) 
	{
		List<Interest> interests = new ArrayList<Interest>();
		
		for (Interest interest : interests)
			if (interest.getTransaction().getName().equals(transaction.getName()))
				interests.add(interest);

		return interests;
	}

	public RegisterElement getRegisterElementName(String dataFunctionName, String registerName)
	{
		if (dataFunctionName.length() == 0)
			dataFunctionName = registerName;
		
		DataFunction df = getDataFunctionName(dataFunctionName);
		
		if (df == null)
			return null;
		
		return df.getRegisterElementName(registerName);
	}
}