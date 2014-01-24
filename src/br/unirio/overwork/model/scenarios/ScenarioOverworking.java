package br.unirio.overwork.model.scenarios;

import br.unirio.overwork.model.Developer;
import br.unirio.overwork.simulation.Scenario;
import br.unirio.overwork.simulation.Tables;

/**
 * Scenario that depicts overworking
 * 
 * @author Marcio Barros
 */
public class ScenarioOverworking extends Scenario<Developer>
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
	public ScenarioOverworking(int dailyWorkHours)
	{
		this.dailyWorkHours = dailyWorkHours;
	}
	
	/**
	 * Prepares the scenario for execution
	 */
	@Override
	public void init(Developer developer)
	{
		developer.setScenarioVariable("dailyWorkHours", dailyWorkHours);
	}

	/**
	 * Runs a step of the scenario
	 */
	@Override
	public void step(Developer developer)
	{
		double workHours = developer.getScenarioVariable("dailyWorkHours", dailyWorkHours);
		double workHourModifier = 1 + (workHours - 8) / (12 - 8);
		double errorGenerationFactor = Tables.lookup(ERROR_GENERATION_FACTOR, workHourModifier - 1, 0, 1);
		developer.setProductivity(developer.getProductivity() * workHours / 8.0);
		developer.setErrorGenerationRate(developer.getErrorGenerationRate() * errorGenerationFactor);
	}
}