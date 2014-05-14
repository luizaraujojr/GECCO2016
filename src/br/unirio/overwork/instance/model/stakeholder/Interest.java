package br.unirio.overwork.instance.model.stakeholder;

import lombok.Getter;
import br.unirio.overwork.instance.model.transaction.Transaction;

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