package br.unirio.nrpapf.model.transaction;

import lombok.Getter;
import lombok.Setter;

/**
 * Class that represents a transaction dependency on a second transaction
 * 
 * @author Marcio Barros
 */
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