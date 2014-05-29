package br.unirio.nrpapf.model.transaction;

public enum TransactionType 
{
	EO,
	EI,
	EQ;

	public static TransactionType get(String name) 
	{
		if (name.compareToIgnoreCase("EI") == 0)
			return EI;
		
		if (name.compareToIgnoreCase("EO") == 0)
			return EO;
		
		return EQ;
	}
}