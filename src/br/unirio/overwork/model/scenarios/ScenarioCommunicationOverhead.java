package br.unirio.overwork.model.scenarios;

import br.unirio.overwork.model.base.Activity;
import br.unirio.overwork.model.base.Developer;
import br.unirio.simulation.Scenario;
import br.unirio.simulation.Tables;

/**
 * Scenario that represents the communication overhead for project due the number of developers
 * 
 * @author Luiz Antonio
 */
public class ScenarioCommunicationOverhead  extends Scenario<Activity>
{
	/**
	 * Table depicting the decrease over productivity due communication overhead
	 */
	private static double[] COMMUNICATION_OVERHEAD_FACTOR = {0, 0.0,15, 0.06, 0.135, 0.24, 0.375, 0.54};
	
	/**
	 * Initializes the scenario
	 */
	public ScenarioCommunicationOverhead()
	{
	}
	
	/**
	 * Prepares the scenario for execution
	 */
	@Override
	public void beforeStep(Activity activity)
	{
		int count = countDevelopers(activity);
		double overhead = Tables.lookup(COMMUNICATION_OVERHEAD_FACTOR, count, 0, 30);
		activity.setProductivity(activity.getProductivity() * (1 - overhead));
	}

	/**
	 * Counts the number of developers engaged in the project
	 */
	@SuppressWarnings("unused")
	private int countDevelopers(Activity activity)
	{
		int count = 0;
		
		for (Developer developer : activity.getProject().getDevelopers())
			count++;
		
		return count;
	}
}