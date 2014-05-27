package br.unirio.overwork.optimizer;

import jmetal.util.PseudoRandom;
import br.unirio.overwork.model.base.Developer;
import br.unirio.overwork.model.base.Project;
import br.unirio.overwork.model.scenarios.ScenarioOverworking;
import br.unirio.simulation.SimulationEngine;

public class Solution 
{
	private Project project;
	private int[] allocation;
	
	public Solution(Project project)
	{
		this.project = project;
		this.allocation = new int[project.countActivities()];
		randomize();
	}
	
	public void randomize()
	{
		for (int i = 0; i < allocation.length; i++)
			allocation[i] = PseudoRandom.randInt(0, 9);
	}
	
	public EvaluationResults evaluate() throws Exception
	{
		SimulationEngine simulator = new SimulationEngine();

		for (Developer developer : project.getDevelopers())
			simulator.addResource(developer.getEffort());
		
		simulator.addSimulationObjects(project.getActivities());

		for (int i = 0; i < allocation.length; i++)
		{
			ScenarioOverworking scenarioOverworking = new ScenarioOverworking(8.0 + allocation[i] * 0.5);
			scenarioOverworking.connect(project.getActivity(i));
		}

		simulator.init();
		
		while (!project.isConcluded())
			simulator.run();

		return new EvaluationResults(project.getMakespan(), project.getCost(), project.getOverworking());
	}
}