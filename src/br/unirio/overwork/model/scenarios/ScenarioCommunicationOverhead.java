package br.unirio.overwork.model.scenarios;

import java.util.ArrayList;

import br.unirio.overwork.model.Activity;
import br.unirio.overwork.model.Developer;
import br.unirio.overwork.simulation.Scenario;
import br.unirio.overwork.simulation.Tables;

public class ScenarioCommunicationOverhead  extends Scenario<Activity>{

	/**
	 * Table depicting the decrease over productivity due communication overhead
	 */
	private static double[] COMMUNICATION_OVERHEAD_FACTOR = {0, 0.015, 0.06, 0.135, 0.24, 0.375, 0.54};
	
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
	public void init(Activity activity)
	{
		ArrayList<Developer> countDeveloper = (ArrayList<Developer>) activity.getProject().getDevelopers();
		int countDeveloperModifier = Math.round(countDeveloper.size()/5);
		double communicationOverheadFactor = COMMUNICATION_OVERHEAD_FACTOR[countDeveloperModifier];
				
		activity.setProductivity(activity.getProductivity() * (1-communicationOverheadFactor));
		activity.setCommunicationOverheadRate(communicationOverheadFactor); 
	
	}
}