package br.unirio.overwork.model.scenarios;

import br.unirio.overwork.model.ActivityDevelopment;
import br.unirio.overwork.simulation.Scenario;

/**
 * Scenario that represents the error regeneration dynamics for activities
 * 
 * @author Marcio Barros
 */
public class ScenarioErrorRegeneration extends Scenario<ActivityDevelopment>
{
//	PROC InheritedDensity InheritedErrors / FunctionPoints;
//	PROC RegenErrors (AnalysisTask + ArchitecTask + DesignTask + CodingTask) * InheritedErrors * 0.24 * RegenFactor;
//	PROC RegenFactor Max (1, LOOKUP (ActiveErrosDens, InheritedDensity , 0, 10));
//	TABLE ActiveErrosDens 1, 1.1, 1.2, 1.325, 1.45, 1.6, 2.0, 2.5, 3.25, 4.35, 6.0;
//	AFFECT RTErrors RTErrors + RegenErrors / DT;

	/**
	 * Table that determines ...
	 */
//	private static double[] ACTIVE_ERRORS_DENSITY_2 = {1.00, 1.10, 1.20, 1.325, 1.45, 1.60, 2.00, 2.50, 3.25, 4.35, 6.00};
	
	/**
	 * Initializes the scenario
	 */
	public ScenarioErrorRegeneration()
	{
	}
	
	/**
	 * Prepares the scenario for execution
	 */
	@Override
	public void init(ActivityDevelopment activity)
	{
		// pega o acumulado de erros ativos das atividades precedentes
	}

	/**
	 * Executes the scenario before the simulation step
	 */
	@Override
	public void beforeStep(ActivityDevelopment activity)
	{
		// anota o número de erros atual da atividade em um estado
	}

	/**
	 * Executes the scenario after the simulation step
	 */
	@Override
	public void afterStep(ActivityDevelopment activity)
	{
		// E0 <- pega o número de erros antes do passo na atividade, que está guardado em um estado

		// E1 <- pega o número de erros atual da atividade em um estado
		
		// D <- calcula a diferença no número de erros (E1 - E0)
		
		// 
	}
}