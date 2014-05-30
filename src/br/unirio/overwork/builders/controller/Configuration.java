package br.unirio.overwork.builders.controller;

import lombok.Getter;

/**
 * 
 * 
 * REFERÊNCIAS:
 * 
 * Jones, C., 2007, Estimating Software Costs: Bringing Realism to Estimating
 * McGraw-Hill Osborne Media; 2nd edition, ISBN: 0071483004
 * 
 * @author Marcio Barros
 */
public enum Configuration
{
	FP10(27.8, 5.0, 5.0, 50.0, 27.0),
	FP100(27.8, 5.0, 6.0, 40.0, 30.0),
	FP1000(27.8, 7.0, 10.0, 30.0, 30.0),
	FP10K(27.8, 8.0, 12.0, 20.0, 33.0),
	FP100K(27.8, 9.0, 13.0, 15.0, 34.0);
	
	/**
	 * Average number of function points produced by a developer in a month
	 */
	private @Getter double averageProductivity;
		
	/**
	 * Average percentile of project effort dedicated to requirements (Jones, 2007, pg 97)
	 */
	private @Getter double requirementsEffort;
	
	/**
	 * Average percentile of project effort dedicated to design (Jones, 2007, pg 97)
	 */
	private @Getter double designEffort;
	
	/**
	 * Average percentile of project effort dedicated to coding (Jones, 2007, pg 97)
	 */
	private @Getter double codingEffort;
	
	/**
	 * Average percentile of project effort dedicated to testing (Jones, 2007, pg 97)
	 */
	private @Getter double testingEffort;
	
	private Configuration(double avgProductivity, double requirementsEffort, double designEffort, double codingEffort, double testingEffort)
	{
		this.averageProductivity = avgProductivity;

		double totalEffort = requirementsEffort + designEffort + codingEffort + testingEffort;
		this.requirementsEffort = requirementsEffort / totalEffort;
		this.designEffort = designEffort / totalEffort;
		this.codingEffort = codingEffort / totalEffort;
		this.testingEffort = testingEffort / totalEffort;
	}
	
	public static Configuration getConfigurationForFunctionPoints(double fp)
	{
		if (fp < 100)
			return FP10;
		
		if (fp < 1000)
			return FP100;
		
		if (fp < 10000)
			return FP1000;
		
		if (fp < 100000)
			return FP10K;
		
		return FP100K;
	}
}