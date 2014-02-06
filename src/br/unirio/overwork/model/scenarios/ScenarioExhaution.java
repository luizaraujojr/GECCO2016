package br.unirio.overwork.model.scenarios;

import br.unirio.overwork.model.Activity;
import br.unirio.overwork.model.Developer;
import br.unirio.overwork.simulation.Scenario;
import br.unirio.overwork.simulation.SimulationObject;
import br.unirio.overwork.simulation.support.Tables;

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
		double dailyWorkHours = activity.getLocalState("dailyWorkHours", 0);
		activity.setLocalState("dailyWorkHoursCopy", dailyWorkHours);
	}

	@Override
	public void beforeStep(Activity activity)
	{
		activity.setLocalState("workToDo", activity.getRemainingWork());
		
		Developer developer = activity.getDeveloper();
		boolean resting = activity.getGlobalState(developer.getName() + "_resting", false);

		if (resting)
		{
			activity.setLocalState("dailyWorkHours", 8);
		}
		else
		{
			double dailyWorkHoursCopy = activity.getLocalState("dailyWorkHoursCopy", 0);
			activity.setLocalState("dailyWorkHours", dailyWorkHoursCopy);
		}
	}
	
	/**
	 * Runs a step of the scenario
	 */
	@Override
	public void afterStep(Activity activity)
	{
		double wasToDo = activity.getLocalState("workToDo", 0);
		double remainsToDo = activity.getRemainingWork();
		
		if (wasToDo == remainsToDo)
			return;
		
		Developer developer = activity.getDeveloper();
		double exhaustion = activity.getGlobalState(developer.getName() + "_exhaustion", 0.0);
		boolean resting = activity.getGlobalState(developer.getName() + "_resting", false);
		
		if (resting)
		{
			exhaustion -= MAX_EXHAUSTION / RESTING_PERIOD * SimulationObject.DT;
			activity.setGlobalState(developer.getName() + "_exhaustion", exhaustion);
		}
		else
		{
			double dailyWorkHours = activity.getLocalState("dailyWorkHours", 0.0);
			double workHourModifier = (dailyWorkHours - 8) / (12 - 8);
			double exhaustionModifier = Tables.lookup(EXHAUSTION_FACTOR, workHourModifier, 0, 1);
			exhaustion += exhaustionModifier * SimulationObject.DT;
			activity.setGlobalState(developer.getName() + "_exhaustion", exhaustion);
		}

		if (!resting && exhaustion >= MAX_EXHAUSTION)
		{
			activity.setLocalState("dailyWorkHours", 8.0);
			activity.setGlobalState(developer.getName() + "_resting", true);
		}
		else if (resting && exhaustion < 0.001)
		{
			double dailyWorkHoursCopy = activity.getLocalState("dailyWorkHoursCopy", 0.0);
			activity.setLocalState("dailyWorkHours", dailyWorkHoursCopy);
			activity.setGlobalState(developer.getName() + "_resting", false);
		}
	}
}