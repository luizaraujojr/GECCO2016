package br.unirio.nrpapf.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import br.unirio.nrpapf.model.transaction.Transaction;

public class FunctionPointSet 
{
	private @Getter @Setter Set<Transaction> transactions;
	
	public FunctionPointSet() 
	{
		this.transactions = new HashSet<Transaction>();
	}
}