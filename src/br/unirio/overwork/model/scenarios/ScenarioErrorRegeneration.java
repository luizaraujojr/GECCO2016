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
	// em relação ao início e término da atividade ou do projeto
//	private static double[] ACTIVE_ERRORS_DENSITY = {0.85, 0.50, 0.20, 0.075, 0.0, 0.0};

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
//		developer.setScenarioVariable("tempDWH", developer.getScenarioVariable("dailyWorkHours", 8));
//		developer.setScenarioVariable("exhaustion", 0);
//		developer.setScenarioVariable("resting", 0);
	}

	/**
	 * Runs a step of the scenario
	 */
//	@Override
//	public void beforeStep(ActivityDevelopment activity)
//	{
//		PROC InheritedDensity InheritedErrors / FunctionPoints;
//		PROC RegenErrors ExpectedErrors * InheritedErrors * RegenFactor;
//		PROC RegenFactor Max (1, LOOKUP (ActiveErrosDens, InheritedDensity , 0, 10));
//		TABLE ActiveErrosDens 1, 1.1, 1.2, 1.325, 1.45, 1.6, 2.0, 2.5, 3.25, 4.35, 6.0;
//		AFFECT RTErrors RTErrors + RegenErrors / DT;
//	}
}