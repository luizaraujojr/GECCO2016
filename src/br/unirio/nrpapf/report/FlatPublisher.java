package br.unirio.nrpapf.report;


public class FlatPublisher
{
	public void run(Report report)
	{
		for (ReportDataFunction df : report.getDataFunctions())
			System.out.println("DF " + df.getName() + " " + df.getRets() + " " + df.getDets());
		
		System.out.println();

		for (ReportTransactionFunction tf : report.getTransactionFunctions())
			System.out.println("TF " + tf.getName() + " " + tf.getFtrs() + " " + tf.getDets());
	}
}