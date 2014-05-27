package br.unirio.overwork.optimizer;

import lombok.Getter;

public class EvaluationResults 
{
	private @Getter double makespan;
	
	private @Getter double cost;
	
	private @Getter double overworking;

	public EvaluationResults(double makespan, double cost, double overworking) 
	{
		this.makespan = makespan;
		this.cost = cost;
		this.overworking = overworking;
	}
}