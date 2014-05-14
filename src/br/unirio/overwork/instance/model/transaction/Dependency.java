package br.unirio.overwork.instance.model.transaction;

import lombok.Getter;
import lombok.Setter;

public class Dependency
{
	private @Getter String dependeeTransactionName;

	private @Getter boolean canBeWeak;
	
	private @Getter @Setter Transaction dependeeTransaction;
	
	public Dependency(String dependeeTransactionName, boolean canbeWeak) 
	{
		this.dependeeTransactionName = dependeeTransactionName;
		this.canBeWeak = canbeWeak;
	}
}