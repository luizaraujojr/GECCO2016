package br.unirio.overwork.model.scenarios;

import br.unirio.overwork.model.Activity;
import br.unirio.overwork.simulation.Scenario;
import br.unirio.overwork.simulation.SimulationObject;
import br.unirio.overwork.simulation.Tables;

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
		double dailyWorkHours = activity.getScenarioVariable("dailyWorkHours", 0);
		activity.setScenarioVariable("dailyWorkHoursCopy", dailyWorkHours);

		activity.getDeveloper().setScenarioVariable("exhaustion", 0);
		activity.getDeveloper().setScenarioVariable("resting", 0);
		activity.setScenarioVariable("workToDo", 0);
	}

	public void beforeLiveStep(Activity activity)
	{
		activity.setScenarioVariable("workToDo", activity.getRemainingWork());
	}
	
	/**
	 * Runs a step of the scenario
	 */
	@Override
	public void afterLiveStep(Activity activity)
	{
		double wasToDo = activity.getScenarioVariable("workToDo", 0);
		double remainsToDo = activity.getRemainingWork();
		
		if (wasToDo == remainsToDo)
			return;
		
		double exhaustion = activity.getDeveloper().getScenarioVariable("exhaustion", 0);
		boolean resting = activity.getDeveloper().getScenarioVariable("resting", 0) > 0;
		
		if (resting)
		{
			exhaustion -= MAX_EXHAUSTION / RESTING_PERIOD * SimulationObject.DT;
			activity.getDeveloper().setScenarioVariable("exhaustion", exhaustion);
		}
		else
		{
			double dailyWorkHours = activity.getScenarioVariable("dailyWorkHours", 0);
			double workHourModifier = (dailyWorkHours - 8) / (12 - 8);
			double exhaustionModifier = Tables.lookup(EXHAUSTION_FACTOR, workHourModifier, 0, 1);
			exhaustion += exhaustionModifier * SimulationObject.DT;
			activity.getDeveloper().setScenarioVariable("exhaustion", exhaustion);
		}
		
		if (!resting && exhaustion >= MAX_EXHAUSTION)
		{
			activity.setScenarioVariable("dailyWorkHours", 8);
			activity.getDeveloper().setScenarioVariable("resting", 1);
		}
		else if (resting && exhaustion < 0.001)
		{
			double dailyWorkHoursCopy = activity.getScenarioVariable("dailyWorkHoursCopy", 0);
			activity.setScenarioVariable("dailyWorkHours", dailyWorkHoursCopy);
			activity.getDeveloper().setScenarioVariable("resting", 0);
		}
	}
}