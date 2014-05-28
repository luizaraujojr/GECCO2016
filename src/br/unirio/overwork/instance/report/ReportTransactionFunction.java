package br.unirio.overwork.instance.report;

import lombok.Getter;

public class ReportTransactionFunction
{
	private @Getter String name;
	
	private @Getter int ftrs;
	
	private @Getter int dets;
	
	private @Getter int functionPoints;
	
	public ReportTransactionFunction(String name, int ftrs, int dets, int functionPoints)
	{
		this.name = name;
		this.ftrs = ftrs;
		this.dets = dets;
		this.functionPoints = functionPoints;
	}
}