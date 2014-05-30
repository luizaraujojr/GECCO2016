package br.unirio.nrpapf.report;

import java.util.ArrayList;
import java.util.List;

import br.unirio.nrpapf.model.FunctionPointSystem;
import br.unirio.nrpapf.model.transaction.Dependency;
import br.unirio.nrpapf.model.transaction.FileReference;
import br.unirio.nrpapf.model.transaction.Transaction;

/**
 * Class that publishes a report in the format of a overworking optimizer's instance
 * 
 * @author Marcio Barros
 */
public class OverworkPublisher
{
	/**
	 * Publishes an instance in the format required by the overworking optimizer
	 */
	public void run(FunctionPointSystem fps, Report report) throws Exception
	{
		System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		System.out.println("<system>");
		System.out.println("\t<name>" + fps.getName() + "</name>");

		for (ReportDataFunction df : report.getDataFunctions())
			System.out.println("\t<data name=\"" + df.getName() + "\" fp=\"" + df.getFunctionPoints() + "\"/>");
		
		for (ReportTransactionFunction tf : report.getTransactionFunctions())
		{
			Transaction transaction = fps.getTransactionName(tf.getName());
			
			if (transaction == null)
				throw new Exception("A transacao '" + tf.getName() + "' nao foi encontrada.");
			
			System.out.println("\t<transaction name=\"" + tf.getName() + "\" fp=\"" + tf.getFunctionPoints() + "\">");
			publishFileReferences(transaction);
			publishTransactionDependencies(transaction);
			System.out.println("\t</transaction>");			
		}
		
		System.out.println("</system>");
	}

	/**
	 * Publishes all data function dependencies for a given transaction
	 */
	private void publishFileReferences(Transaction transaction)
	{
		List<String> dataFunctionReferences = new ArrayList<String>();
		
		for (FileReference fileReference : transaction.getFileReferences())
		{
			String dataFunctionName = fileReference.getReferencedDataFunction();
			
			if (dataFunctionName.length() == 0)
				dataFunctionName = fileReference.getName();
			
			if (!contains(dataFunctionReferences, dataFunctionName))
			{	
				System.out.println("\t\t<ftr name=\"" + dataFunctionName + "\"/>");
				dataFunctionReferences.add(dataFunctionName);
			}
		}
	}

	/**
	 * Publishes all transaction function dependencies for a given transaction
	 */
	private void publishTransactionDependencies(Transaction transaction)
	{
		for (Dependency dependency : transaction.getDependencies())
			System.out.println("\t\t<depends name=\"" + dependency.getDependeeTransactionName() + "\"/>");
	}

	/**
	 * Checks whether a data function name is contained in a list
	 */
	private boolean contains(List<String> list, String item)
	{
		for (String s : list)
			if (s.compareToIgnoreCase(item) == 0)
				return true;
		
		return false;
	}
}