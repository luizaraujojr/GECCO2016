package br.unirio.nrpapf.model.stakeholder;

import lombok.Getter;
import br.unirio.nrpapf.model.transaction.Transaction;

public class Interest
{
	private @Getter Stakeholder stakeholder;
	
	private @Getter Transaction transaction;
	
	private @Getter long interest;
	
	public Interest(Stakeholder stakeholder, Transaction transaction, long interest)
	{
		this.stakeholder = stakeholder;
		this.transaction = transaction;
		this.interest = interest;
	}
}