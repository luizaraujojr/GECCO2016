package br.unirio.overwork.instance.calculator;

import java.util.ArrayList;
import java.util.List;

import br.unirio.overwork.instance.model.FunctionPointSystem;
import br.unirio.overwork.instance.model.data.DataElement;
import br.unirio.overwork.instance.model.data.DataFunction;
import br.unirio.overwork.instance.model.data.DataFunctionType;
import br.unirio.overwork.instance.model.data.RegisterElement;
import br.unirio.overwork.instance.model.transaction.FileReference;
import br.unirio.overwork.instance.model.transaction.Transaction;
import br.unirio.overwork.instance.model.transaction.TransactionType;

/**
 * Function-point calculator based on a detailed function point model
 */
public class FunctionPointCalculator 
{
	/**
	 * Calculates the number of function points given to set of transactions and data functions
	 */
	public int calculate(FunctionPointSystem fps)
	{
		int total = 0;
		
		for (Transaction transaction : fps.getTransactions())
			for (FileReference ftr : transaction.getFileReferences())
				ftr.markDataElements();

		for(DataFunction dataFunction : fps.getDataFunctions()) 
			total += calculateDataFunctionValue(dataFunction);
		
		System.out.println();

		for (Transaction transaction : fps.getTransactions())
			total += calculateTransactionValue(fps, transaction);

		return total;
	}
	
	/**
	 * Calculates the number of function points given to a data function
	 */
	public int calculateDataFunctionValue(DataFunction dataFunction) 
	{
		int dets = 0;
		int rets = 0;
		
		for (RegisterElement ret : dataFunction.getRegisterElements())
		{
			rets++;

			for (DataElement det : ret.getDataElements())
				if (det.countsForDataFunction(dataFunction))
					dets++;
		}

		if (dets > 0 && rets > 0)
		{
			System.out.println("DF " + dataFunction.getName() + " " + rets + " " + dets);
			Complexity complexity = calculateDataFunctionComplexity(rets, dets);
			return calculateFunctionPointsDataFunction(complexity, dataFunction.getType());
		}
		
		return 0;
	}

	/**
	 * Calculates the number of function points given to a transaction
	 */
	public int calculateTransactionValue(FunctionPointSystem fps, Transaction transaction) 
	{
		List<String> dataFunctions = new ArrayList<String>();
		int fields = transaction.getExtraDataElements();
		
		if (transaction.isCountErrorMessages())
			fields++;
		
		int ftrs = 0;
		
		for (FileReference ftr : transaction.getFileReferences())
		{
			String dataFunctionName = ftr.getDataModelElement();
			
			if (dataFunctionName.length() == 0)
				dataFunctionName = ftr.getReferencedRegister().getDataFunction().getName();
			
			if (!dataFunctions.contains(dataFunctionName))
			{
				dataFunctions.add(dataFunctionName);
				ftrs++;
			}
			
			DataFunction dataFunction = fps.getDataFunctionName(dataFunctionName);
			
			if (dataFunction == null)
				System.out.println("A função de dados '" + dataFunctionName + "' não foi encontrada.");
			
			if (dataFunction != null)
				fields += ftr.countDataElements(dataFunction);
		}

		System.out.println("TF " + transaction.getName() + " " + ftrs + " " + fields);
		Complexity complexity = calculateTransactionComplexity(ftrs, fields, transaction.getType());
		return calculateFunctionPointsTransaction(complexity, transaction.getType());
	}
	
	/**
	 * Calculates the complexity of a data function
	 */
	private Complexity calculateDataFunctionComplexity(int ret, int det) 
	{
		if ((ret>=6 && det>=20) || (ret<=5 && ret>=2 && det>=51))
			return Complexity.HIGH;

		if (ret>=6 || (ret<=5 && ret>=2 && det>=20 && det<=50) || (ret==1 && det>=51)) 
			return Complexity.MEDIUM;

		return Complexity.LOW;
	}
	
	/**
	 * Calculates the number of function points to be assigned to a data function
	 */
	private int calculateFunctionPointsDataFunction(Complexity complexity, DataFunctionType type)
	{
		if ((complexity==Complexity.LOW && type.equals(DataFunctionType.ILF)) || (complexity==Complexity.MEDIUM && type.equals(DataFunctionType.EIF)))
			return 7;

		if ((complexity==Complexity.MEDIUM && type.equals(DataFunctionType.ILF)) || (complexity==Complexity.HIGH && type.equals(DataFunctionType.EIF)))
			return 10;

		if (complexity==Complexity.LOW && type.equals(DataFunctionType.EIF))
			return 5;
		
		if (complexity==Complexity.HIGH && type.equals(DataFunctionType.ILF))
			return 15;
		
		return 0;
	}
	
	/**
	 * Calculates the complexity of a transaction
	 */
	private Complexity calculateTransactionComplexity(int ftrs, int dets, TransactionType type) 
	{
		if ((((ftrs >= 3 && dets >= 5) || (ftrs==2 && dets>=16)) && type.equals(TransactionType.EI)) || (((ftrs>=4 && dets>=6) || (ftrs>=2 && ftrs<=3 && dets>=20)) && (type.equals(TransactionType.EO)||type.equals(TransactionType.EQ))))
			return Complexity.HIGH;

		if ((((ftrs >= 4 && dets >= 6) || (ftrs>=2 && ftrs<=3 && dets>=20)) && (type.equals(TransactionType.EO) || type.equals(TransactionType.EQ))))
			return Complexity.HIGH;

		if ((((ftrs >= 3 && dets <= 4) || (ftrs==2 && dets>=5 && dets<=15) || (ftrs<=1 && dets>=16)) && type.equals(TransactionType.EI)))
			return Complexity.MEDIUM;
				
		if ((((ftrs >= 4 && dets <= 5) || (ftrs>=2 && ftrs<=3 && dets>=6 && dets<=19) || (ftrs<=1 && dets>=20)) && (type.equals(TransactionType.EO) || type.equals(TransactionType.EQ))))
			return Complexity.MEDIUM;

		return Complexity.LOW;
	}
	
	/**
	 * Calculates the number of function points to be assigned to a transaction
	 */
	private int calculateFunctionPointsTransaction(Complexity complexity, TransactionType type)
	{
		if ((complexity==Complexity.LOW && type.equals(TransactionType.EO)) || (complexity==Complexity.MEDIUM && (type.equals(TransactionType.EI) || type.equals(TransactionType.EQ))))
			return 4;
		
		if ((complexity==Complexity.MEDIUM && type.equals(TransactionType.EO)))
			return 5;
		
		if (complexity==Complexity.HIGH && type.equals(TransactionType.EO))
			return 7;
		
		if (complexity==Complexity.LOW && (type.equals(TransactionType.EI) || type.equals(TransactionType.EQ)))
			return 3;
		
		if (complexity==Complexity.HIGH && (type.equals(TransactionType.EI) || type.equals(TransactionType.EQ)))
			return 6;
		
		return 0;
	}
}