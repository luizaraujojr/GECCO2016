package br.unirio.overwork.instance.report;

import java.util.ArrayList;
import java.util.List;

public class Report
{
	private List<ReportDataFunction> dataFunctions;
	
	private List<ReportTransactionFunction> transactionFunctions;
	
	public Report()
	{
		this.dataFunctions = new ArrayList<ReportDataFunction>();
		this.transactionFunctions = new ArrayList<ReportTransactionFunction>();
	}
	
	public void addDataFunction(String name, int rets, int dets, int functionPoints)
	{
		dataFunctions.add(new ReportDataFunction(name, rets, dets, functionPoints));
	}
	
	public Iterable<ReportDataFunction> getDataFunctions()
	{
		return dataFunctions;
	}
	
	public void addTransactionFunction(String name, int ftrs, int dets, int functionPoints)
	{
		transactionFunctions.add(new ReportTransactionFunction(name, ftrs, dets, functionPoints));
	}
	
	public Iterable<ReportTransactionFunction> getTransactionFunctions()
	{
		return transactionFunctions;
	}
}