package br.unirio.optimization;

import java.util.Arrays;

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

/**
 * Class that contains the project problem
 * 
 * @author Luiz Araujo Jr
 */

public class ProjectProblem extends Problem {

	private int evaluations;
	
	private @Getter @Setter Project project;
	private @Getter @Setter String instanceName;
	private @Getter @Setter int maxEvaluations;
	
	/**
	 * Creates a new project problem for the jmetal framework
	 */
	public ProjectProblem(Project project, int maxEvaluations) throws ClassNotFoundException
	
	{	
		
		this.project = project;
		this.evaluations = 0;
		this.maxEvaluations = maxEvaluations;

		numberOfObjectives_ = 3;
		numberOfVariables_ = project.getActivitiesCount();
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

	/**
	 * Creates a new project problem
	 */
public ProjectProblem(Project project) throws ClassNotFoundException
	
	{	
		this.project = project;
		this.evaluations = 0;
		
		numberOfObjectives_ = 3;
		numberOfVariables_ = project.getActivitiesCount();
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

	/**
	 * Complete evaluate the allocation for jmetal
	 */
	@Override
	public void evaluate(Solution solution) throws JMException
	{
		runSimulation(solution);
		solution.setObjective(0, project.getOverworking());
		solution.setObjective(1, project.getMakespan());
		solution.setObjective(2, project.getCost());
		solution.setLocation(evaluations);
		
		evaluations++;
	}
	
	/**
	 * Complete evaluate the allocation
	 */
//	public void evaluate(Solution solution, int cycle, long initTime) throws JMException
//	{
//		runSimulation(solution);
//		solution.setObjective(0, project.getOverworking());
//		solution.setObjective(1, project.getMakespan());
//		solution.setObjective(2, project.getCost());
////		solution.setCost(project.getCost());
////		solution.setMakespan(project.getMakespan());
////		solution.setOverwork(project.getOverworking());
//		solution.setLocation(evaluations);
//		solution.setCycle(cycle);
//		solution.setExecutionTime(System.currentTimeMillis() - initTime);
//		
//		
//		evaluations++;
//	}
	
	/**
	 * Runs the simulations with jmetal
	 */
	public void runSimulation(Solution solution) throws JMException
	{
		SimulationEngine simulator = new SimulationEngine();

		for (Developer developer : project.getDevelopers())
			simulator.addResource(developer.getEffort());
		
		simulator.addSimulationObjects(project.getActivities());
				
		for (int i = 0; i < project.getActivitiesCount(); i++)
		{
			ScenarioOverworking scenarioOverworking = new ScenarioOverworking( solution.getDecisionVariables()[i].getValue() * 0.5 + 7.5);
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

	/**
	 * Runs the simulations
	 */
//	public void runSimulation(ProjectSolution solution) throws JMException
//	{
//		SimulationEngine simulator = new SimulationEngine();
//
//		for (Developer developer : project.getDevelopers())
//			simulator.addResource(developer.getEffort());
//		
//		simulator.addSimulationObjects(project.getActivities());
//		
//		for (int i = 0; i < project.getActivitiesCount(); i++)
//		{
//			ScenarioOverworking scenarioOverworking = new ScenarioOverworking(solution.getActivityOverworkAllocation(i) * 0.5 + 7.5);
//			scenarioOverworking.connect(project.getActivity(i));
//		}
//				
//		try 
//		{
//			simulator.init();
//		} 
//		catch (Exception e) 
//		{
//			throw new JMException(e.getMessage());
//		}
//		
//		while (!project.isConcluded())
//			simulator.run();
//	}
	
	/**
	 * Prints out the information for the title of the results
	 */
	public void publishTitle()
	{
		//System.out.println("name" + "\t" + "heuristic" + "\t" + "cycle" + "\t" + "executionTime" + "\t" + "Cost" + "\t" + "Makespan" + "\t" + "Overworking"+ "\t Activities");
		System.out.println("cycle" + "; " + "executionTime" + "; " + "Overworking" + "; " + "Makespan" + "; " + "Cost"+ "; " + "Location"+ "; Activities");
	}
}

