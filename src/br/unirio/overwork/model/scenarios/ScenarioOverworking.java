package br.unirio.overwork.model.scenarios;

import br.unirio.overwork.model.Activity;
import br.unirio.simulation.Scenario;
import br.unirio.simulation.Tables;

/**
 * Scenario that depicts overworking
 * 
 * @author Marcio Barros
 */
public class ScenarioOverworking extends Scenario<Activity>
{
	/**
	 * Table depicting the increase/decrease on error generation rates
	 */
	private static double[] ERROR_GENERATION_FACTOR = {0.90, 0.94, 1.00, 1.05, 1.14, 1.24, 1.36, 1.50};

	/**
	 * Number of hours worked per day
	 */
	private double dailyWorkHours;
	
	/**
	 * Initializes the scenario
	 */
	public ScenarioOverworking(double dailyWorkHours)
	{
		this.dailyWorkHours = dailyWorkHours;
	}
	
	/**
	 * Prepares the scenario for execution
	 */
	@Override
	public void init(Activity atividade)
	{
		atividade.setLocalState("dailyWorkHours", dailyWorkHours);
	}

	/**
	 * Runs the scenario before a simulation step
	 */
	@Override
	public void beforeStep(Activity activity)
	{
		double workHours = activity.getLocalState("dailyWorkHours", dailyWorkHours);
		double workHourModifier = (workHours - 8) / (12 - 8);
		double errorGenerationFactor = Tables.lookup(ERROR_GENERATION_FACTOR, workHourModifier, 0, 1);
		
		activity.setEffortMultiplier(activity.getEffortMultiplier() * workHours / 8.0);
		activity.setErrorGenerationRate(activity.getErrorGenerationRate() * errorGenerationFactor);
	}
}