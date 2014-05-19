package br.unirio.optimization;

import java.util.Arrays;
import java.util.Random;

import jmetal.base.Problem;
import jmetal.base.Solution;
import jmetal.base.solutionType.IntSolutionType;
import jmetal.util.JMException;
import lombok.Getter;
import lombok.Setter;
import br.unirio.overwork.model.base.Developer;
import br.unirio.overwork.model.base.Project;
import br.unirio.overwork.model.scenarios.ScenarioOverworking;
import br.unirio.simulation.SimulationEngine;

public class ProjectProblem extends Problem {
	
	//private static final double MinimumValue = 0;
	
	//private Project project;
	private int evaluations;
	
	private @Getter @Setter Project project;
	private @Getter @Setter String instanceName;
	private @Getter @Setter int maxEvaluations;
	

	public ProjectProblem(Project project, int maxEvaluations) throws ClassNotFoundException
	
	{	
		this.project = project;
		this.evaluations = 0;
		this.maxEvaluations = maxEvaluations;

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
		solution.setObjective(0, project.getMakespan()/project.getOverworking());//calcOverWork(solution));
//		solution.setObjective(1, project.getMakespan());//calcOverWork(solution));
//		solution.setObjective(2, project.getCost());//calcOverWork(solution));
		solution.setLocation(evaluations);
		
		//System.out.println(solution);
		evaluations++;
	}
	
	public void evaluate(ProjectSolution solution) throws JMException
	{
		runSimulation(solution);
		solution.setCost(project.getCost());
		solution.setMakespan(project.getMakespan());
		solution.setOverwork(project.getOverworking());
		solution.setLocation(evaluations);
		
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
	
//	public void randomizeSolution()
//	{
//		Random r = new Random();
//		
//		  
//		for (int i = 0; i < project.countActivities(); i++)
//			project.
//			testCases[i] = r.nextDouble() >= 0.5;
//	}
	
	public int[] convertToArray(Solution solution) throws JMException
	{
		int[] activitiesOverworkAllocations_ = new int [this.project.countActivities()];

		for (int i = 0; i < this.project.countActivities(); i++)
			activitiesOverworkAllocations_[i] = (int) solution.getDecisionVariables()[i].getValue();
		
		return activitiesOverworkAllocations_;
	}
	
	public void runSimulation(Solution solution) throws JMException
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
		}
				
		try 
		{
			simulator.init();
		} 
		catch (Exception e) 
		{
			throw new JMException(e.getMessage());
		}
		
		while (!project.isConcluded())
			simulator.run();
	}
	
	public void runSimulation(ProjectSolution solution) throws JMException
	{
		SimulationEngine simulator = new SimulationEngine();

		for (Developer developer : project.getDevelopers())
			simulator.addResource(developer.getEffort());
		
		simulator.addSimulationObjects(project.getActivities());
		
		for (int i = 0; i < project.countActivities(); i++)
		{
			ScenarioOverworking scenarioOverworking = new ScenarioOverworking(solution.getActivityOverworkAllocation(i) * 0.5 + 7.5);
			scenarioOverworking.connect(project.getActivity(i));
		}
				
		try 
		{
			simulator.init();
		} 
		catch (Exception e) 
		{
			throw new JMException(e.getMessage());
		}
		
		while (!project.isConcluded())
			simulator.run();
	}
	
	public void publishTitle()
	{
		System.out.println("name" + "\t" + "heuristic" + "\t" + "cycle" + "\t" + "executionTime" + "\t" + "Cost" + "\t" + "Makespan" + "\t" + "Overworking"+ "\t Activities");
	}
}

