package br.unirio.overwork.model.scenarios;

import br.unirio.overwork.model.Activity;
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
		double dailyWorkHours = activity.getState("dailyWorkHours", 0);
		activity.setState("dailyWorkHoursCopy", dailyWorkHours);

		activity.getDeveloper().setState("exhaustion", 0);
		activity.getDeveloper().setState("resting", 0);
		activity.setState("workToDo", 0);
	}

	public void beforeStep(Activity activity)
	{
		activity.setState("workToDo", activity.getRemainingWork());
		
		boolean resting = activity.getDeveloper().getState("resting", 0) > 0;

		if (resting)
		{
			activity.setState("dailyWorkHours", 8);
		}
		else
		{
			double dailyWorkHoursCopy = activity.getState("dailyWorkHoursCopy", 0);
			activity.setState("dailyWorkHours", dailyWorkHoursCopy);
		}
	}
	
	/**
	 * Runs a step of the scenario
	 */
	@Override
	public void afterStep(Activity activity)
	{
		double wasToDo = activity.getState("workToDo", 0);
		double remainsToDo = activity.getRemainingWork();
		
		if (wasToDo == remainsToDo)
			return;
		
		double exhaustion = activity.getDeveloper().getState("exhaustion", 0);
		boolean resting = activity.getDeveloper().getState("resting", 0) > 0;
		
		if (resting)
		{
			exhaustion -= MAX_EXHAUSTION / RESTING_PERIOD * SimulationObject.DT;
			activity.getDeveloper().setState("exhaustion", exhaustion);
		}
		else
		{
			double dailyWorkHours = activity.getState("dailyWorkHours", 0);
			double workHourModifier = (dailyWorkHours - 8) / (12 - 8);
			double exhaustionModifier = Tables.lookup(EXHAUSTION_FACTOR, workHourModifier, 0, 1);
			exhaustion += exhaustionModifier * SimulationObject.DT;
			activity.getDeveloper().setState("exhaustion", exhaustion);
		}

		if (!resting && exhaustion >= MAX_EXHAUSTION)
		{
			activity.setState("dailyWorkHours", 8);
			activity.getDeveloper().setState("resting", 1);
		}
		else if (resting && exhaustion < 0.001)
		{
			double dailyWorkHoursCopy = activity.getState("dailyWorkHoursCopy", 0);
			activity.setState("dailyWorkHours", dailyWorkHoursCopy);
			activity.getDeveloper().setState("resting", 0);
		}
	}
}