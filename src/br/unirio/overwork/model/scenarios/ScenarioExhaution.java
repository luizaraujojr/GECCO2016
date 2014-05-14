package br.unirio.overwork.model.scenarios;

import br.unirio.overwork.model.base.Activity;
import br.unirio.overwork.model.base.Developer;
import br.unirio.simulation.Scenario;
import br.unirio.simulation.SimulationObject;
import br.unirio.simulation.Tables;

/**
 * Scenario that represents the exhaustion dynamics for software developers
 * 
 * @author Marcio Barros
 */
public class ScenarioExhaution extends Scenario<Activity>
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
	public void init(Activity activity)
	{
		double workHours = activity.getLocalState("dailyWorkHours", 8);
		activity.setLocalState("dailyWorkHoursCopy", workHours);
	}

	/**
	 * Executes the scenario before the simulation step
	 */
	@Override
	public void beforeStep(Activity activity)
	{
		Developer developer = activity.getDeveloper();
		boolean resting = activity.getGlobalState(developer.getName() + "_resting", false);
		double workHours = resting ? 8 : activity.getLocalState("dailyWorkHoursCopy", 8); 
		activity.setLocalState("dailyWorkHours", workHours);
	}
	
	/**
	 * Executes the scenario after the simulation step
	 */
	@Override
	public void afterStep(Activity activity)
	{
		if (!activity.hasConsumedEffort())
			return;
		
		Developer developer = activity.getDeveloper();
		double exhaustion = activity.getGlobalState(developer.getName() + "_exhaustion", 0);
		boolean resting = activity.getGlobalState(developer.getName() + "_resting", false);
		
		if (resting)
		{
			exhaustion -= MAX_EXHAUSTION / RESTING_PERIOD * SimulationObject.DT;
		}
		else
		{
			double workHours = activity.getLocalState("dailyWorkHours", 8);
			double workHourModifier = (workHours - 8) / (12 - 8);
			double exhaustionModifier = Tables.lookup(EXHAUSTION_FACTOR, workHourModifier, 0, 1);
			exhaustion += exhaustionModifier * SimulationObject.DT;
		}

		activity.setGlobalState(developer.getName() + "_exhaustion", exhaustion);

		if (!resting && exhaustion >= MAX_EXHAUSTION)
		{
			activity.setLocalState("dailyWorkHours", 8);
			activity.setGlobalState(developer.getName() + "_resting", true);
		}
		else if (resting && exhaustion < 0.001)
		{
			double dailyWorkHoursCopy = activity.getLocalState("dailyWorkHoursCopy", 8);
			activity.setLocalState("dailyWorkHours", dailyWorkHoursCopy);
			activity.setGlobalState(developer.getName() + "_resting", false);
		}
	}
}