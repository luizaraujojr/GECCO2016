package br.unirio.optimization;

import java.util.Arrays;

import jmetal.base.Problem;
import jmetal.base.Solution;
import jmetal.base.solutionType.IntSolutionType;
import jmetal.util.JMException;
import br.unirio.overwork.model.base.Developer;
import br.unirio.overwork.model.base.Project;
import br.unirio.overwork.model.scenarios.ScenarioOverworking;
import br.unirio.simulation.SimulationEngine;

public class ProjectProblem extends Problem {
	
	//private static final double MinimumValue = 0;
	
	private Project project;
	private int evaluations;

	public ProjectProblem(Project project) throws ClassNotFoundException
	
	{	
		this.project = project;
		this.evaluations = 0;

		numberOfObjectives_ = 3;
		numberOfVariables_ = project.countActivities();
		solutionType_ = new IntSolutionType(this);
		length_ = new int[numberOfVariables_];
		length_[0] =  1;
		
		double[] lLimit= new double [numberOfVariables_];
		Arrays.fill(lLimit, 1.0);
		double[] uLimit= new double [numberOfVariables_];
		Arrays.fill(uLimit, 9.0);
		
		lowerLimit_ = lLimit;
		upperLimit_ = uLimit;
	}
	
	@Override
	public void evaluate(Solution solution) throws JMException
	{
		runSimulation(solution);
		solution.setObjective(0, project.getOverworking());//calcOverWork(solution));
		solution.setObjective(1, project.getMakespan());//calcOverWork(solution));
		solution.setObjective(2, project.getCost());//calcOverWork(solution));
		solution.setLocation(evaluations);
		
		//System.out.println(solution);
		evaluations++;
	}

//	public double calcOverWork(Solution solution)
//	{
//		int[] solution_ = convertToArray(solution);
//		ProjectSolution ps = new ProjectSolution(this.project);
//		ps.calculate();
//		ps.setActivitiesOverworkAllocations(solution_);
//		
//		return ps.getOverwork();
//	}
	
	public int[] convertToArray(Solution solution)
	{
		int[] activitiesOverworkAllocations_ = new int [this.project.countActivities()];
		for (int i = 0; i < this.project.countActivities(); i++)
			try {
				activitiesOverworkAllocations_[i] = (int) solution.getDecisionVariables()[i].getValue();
			} catch (JMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return activitiesOverworkAllocations_;
	}
	
	public void runSimulation(Solution solution)
	{
		SimulationEngine simulator = new SimulationEngine();

		for (Developer developer : project.getDevelopers())
			simulator.addResource(developer.getEffort());
		
		simulator.addSimulationObjects(project.getActivities());
		
		int [] activitiesOverworkAllocation = convertToArray(solution);
		
		for (int i = 0; i < project.countActivities(); i++)
		{
			ScenarioOverworking scenarioOverworking = new ScenarioOverworking(activitiesOverworkAllocation[i] * 0.5 + 7.5);
			scenarioOverworking.connect(project.getActivity(i));
			
			//overtime = overtime + solution.getActivitiesOverworkAllocation(i)  + "\t";
		}
				
//		ScenarioExhaution scenarioExhaustion = new ScenarioExhaution();
//		scenarioExhaustion.connect(project.getActivities());

		try {
			simulator.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (!project.isConcluded())
			simulator.run();
		
		//return project.getOverworking();
	}

}
