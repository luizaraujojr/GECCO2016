package br.unirio.overwork.instance.report;

import lombok.Getter;

public class ReportDataFunction
{
	private @Getter String name;
	
	private @Getter int rets;
	
	private @Getter int dets;
	
	private @Getter int functionPoints;
	
	public ReportDataFunction(String name, int rets, int dets, int functionPoints)
	{
		this.name = name;
		this.rets = rets;
		this.dets = dets;
		this.functionPoints = functionPoints;
	}
}