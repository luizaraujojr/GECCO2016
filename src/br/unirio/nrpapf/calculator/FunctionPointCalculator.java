package br.unirio.nrpapf.calculator;

import java.util.ArrayList;
import java.util.List;

import br.unirio.nrpapf.model.FunctionPointSystem;
import br.unirio.nrpapf.model.data.DataElement;
import br.unirio.nrpapf.model.data.DataFunction;
import br.unirio.nrpapf.model.data.DataFunctionType;
import br.unirio.nrpapf.model.data.RegisterElement;
import br.unirio.nrpapf.model.transaction.Field;
import br.unirio.nrpapf.model.transaction.FileReference;
import br.unirio.nrpapf.model.transaction.Transaction;
import br.unirio.nrpapf.model.transaction.TransactionType;
import br.unirio.nrpapf.report.Report;

/**
 * Function-point calculator based on a detailed function point model
 */
public class FunctionPointCalculator 
{
	/**
	 * Calculates the number of function points given to set of transactions and data functions
	 */
	public Report calculate(FunctionPointSystem fps)
	{
		Report report = new Report();
		
		for (Transaction transaction : fps.getTransactions())
			for (FileReference ftr : transaction.getFileReferences())
				ftr.markDataElements();

		for(DataFunction dataFunction : fps.getDataFunctions()) 
			calculateDataFunctionValue(fps, report, dataFunction);
		
		for (Transaction transaction : fps.getTransactions())
			calculateTransactionValue(fps, report, transaction);
		
		return report;
	}
	
	/**
	 * Calculates the number of function points given to a data function
	 */
	public void calculateDataFunctionValue(FunctionPointSystem fps, Report report, DataFunction dataFunction) 
	{
		int dets = 0;
		int rets = 0;
		
		for (RegisterElement ret : dataFunction.getRegisterElements())
		{
			rets++;

			for (DataElement det : ret.getDataElements())
				if (countsForDataFunction(fps, det, dataFunction))
					dets++;
		}

		if (dets > 0 && rets > 0)
		{
			Complexity complexity = calculateDataFunctionComplexity(rets, dets);
			int functionPoints = calculateFunctionPointsDataFunction(complexity, dataFunction.getType());
			report.addDataFunction(dataFunction.getName(), rets, dets, functionPoints);
		}
	}

	/**
	 * Calculates the number of function points given to a transaction
	 */
	public void calculateTransactionValue(FunctionPointSystem fps, Report report, Transaction transaction) 
	{
		List<String> dataFunctions = new ArrayList<String>();
		int fields = transaction.getExtraDataElements();
		
		if (transaction.isCountErrorMessages())
			fields++;
		
		int ftrs = 0;
		
		for (FileReference ftr : transaction.getFileReferences())
		{
			String dataFunctionName = ftr.getReferencedDataFunction();
			
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
				fields += countDataElements(fps, transaction, ftr, dataFunction);
		}

		Complexity complexity = calculateTransactionComplexity(ftrs, fields, transaction.getType());
		int functionPoints = calculateFunctionPointsTransaction(complexity, transaction.getType());
		report.addTransactionFunction(transaction.getName(), ftrs, fields, functionPoints);
	}

	/**
	 * Counts all data elements used by an FTR
	 */
	public int countDataElements(FunctionPointSystem fps, Transaction transaction, FileReference ftr, DataFunction dataFunction) 
	{
		int count = 0;
		
		if (ftr.isUseAllDataElements())
			for (DataElement det : ftr.getReferencedRegister().getDataElements())
				if (countsForTransaction(fps, transaction, det, dataFunction))
					count++;
		
		for (Field field : ftr.getFields())
			if (field.getDataElement().isUsed())
				count++;

		return count;
	}
	
	/**
	 * Determines whether a DET counts for a transaction's complexity
	 */
	public boolean countsForTransaction(FunctionPointSystem fps, Transaction transaction, DataElement det, DataFunction dataFunction)
	{
		if (!det.isUsed())
			return false;
		
		if (det.isHasSemanticMeaning())
			return true;
		
		if (det.isPrimaryKey())
			return false;
	
		if (det.getReferencedRegisterElement().length() == 0)
			return true;
		
		RegisterElement register = getRegisterElementName(fps, det.getReferencedDataFunction(), det.getReferencedRegisterElement());

		if (det.getReferencedDataFunction().compareToIgnoreCase(dataFunction.getName()) == 0)
			return false;
	
		if (transaction.containsFileReference(register))
			return false;

		return true;
	}

	/**
	 * Returns a given register element
	 */
	public RegisterElement getRegisterElementName(FunctionPointSystem fps, String dataFunctionName, String registerName)
	{
		if (dataFunctionName.length() == 0)
			dataFunctionName = registerName;
		
		DataFunction df = fps.getDataFunctionName(dataFunctionName);
		
		if (df == null)
			return null;
		
		return df.getRegisterElementName(registerName);
	}

	/**
	 * Determines whether a DET counts for a data function's complexity
	 */
	public boolean countsForDataFunction(FunctionPointSystem fps, DataElement det, DataFunction dataFunction)
	{
		if (!det.isUsed())
			return false;
		
		if (det.isHasSemanticMeaning())
			return true;
		
		if (det.isPrimaryKey())
			return false;
	
		if (det.getReferencedRegisterElement().length() == 0)
			return true;

		if (det.getReferencedDataFunction().compareToIgnoreCase(dataFunction.getName()) == 0 /*&& register != null*/)
			return false;
		
		return true;
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