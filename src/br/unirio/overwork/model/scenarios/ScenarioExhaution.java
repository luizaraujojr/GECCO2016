package br.unirio.overwork.model.scenarios;

import br.unirio.overwork.model.Developer;
import br.unirio.overwork.simulation.Scenario;
import br.unirio.overwork.simulation.SimulationObject;
import br.unirio.overwork.simulation.Tables;

/**
 * Scenario that represents the exhaustion dynamics for software developers
 * 
 * @author Marcio Barros
 */
public class ScenarioExhaution extends Scenario<Developer>
{
	/**
	 * Table that determines exhaustion increase factors for different daily work hours
	 */
	private static double[] EXHAUSTION_FACTOR = {0.00, 0.00, 0.20, 0.30, 0.40, 0.50, 0.60, 0.70, 0.80, 0.90, 1.15, 1.30, 1.60, 1.90, 2.20, 2.50};

	/**
	 * Maximum cumulative exhaustion accepted by software developers
	 */
	private static double MAX_EXHAUSTION = 50.0;

	/**
	 * Period required to rest from maximum exhaustion
	 */
	private static double RESTING_PERIOD = 20.0;
	
	/**
	 * Initializes the scenario
	 */
	public ScenarioExhaution()
	{
	}
	
	/**
	 * Prepares the scenario for execution
	 */
	@Override
	public void init(Developer developer)
	{
		developer.setScenarioVariable("tempDWH", developer.getScenarioVariable("dailyWorkHours", 8));
		developer.setScenarioVariable("exhaustion", 0);
		developer.setScenarioVariable("resting", 0);
	}

	/**
	 * Runs a step of the scenario
	 */
	@Override
	public void step(Developer developer)
	{
		double exhaustion = developer.getScenarioVariable("exhaustion", 0);
		double dailyWorkHours = developer.getScenarioVariable("dailyWorkHours", 0);

		double workHourModifier = 1 + (dailyWorkHours - 8) / (12 - 8);
		double dedication = 0.6 + (workHourModifier - 1) * (1.2 - 0.6);
		double dedicationFactor = 1 - (1 - dedication) / 0.4;
		double exhaustionModifier = Tables.lookup(EXHAUSTION_FACTOR, dedicationFactor, 0, 0.15);
		
		boolean resting = developer.getScenarioVariable("resting", 0) > 0;
		
		if (resting)
			exhaustion -= MAX_EXHAUSTION / RESTING_PERIOD * SimulationObject.DT;
		else
			exhaustion += exhaustionModifier * SimulationObject.DT;
		
		developer.setScenarioVariable("exhaustion", exhaustion);
		
		if (!resting && exhaustion >= MAX_EXHAUSTION)
		{
			developer.setScenarioVariable("dailyWorkHours", 8);
			developer.setScenarioVariable("resting", 1);
		}
		
		if (resting && exhaustion < 0.001)
		{
			developer.setScenarioVariable("dailyWorkHours", developer.getScenarioVariable("tempDWH", 8));
			developer.setScenarioVariable("resting", 0);
		}
	}
}