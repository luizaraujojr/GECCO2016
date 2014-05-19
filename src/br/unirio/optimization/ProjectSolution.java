package br.unirio.optimization;

import java.util.Random;

import jmetal.base.Solution;
import lombok.Getter;
import lombok.Setter;
import br.unirio.overwork.model.base.Developer;
import br.unirio.overwork.model.base.Project;
import br.unirio.overwork.model.base.Activity;
import br.unirio.overwork.model.scenarios.ScenarioOverworking;
import br.unirio.simulation.SimulationEngine;

/**
 * Class that represents a solution for Project's activities overwork optimization problem
 */
public class ProjectSolution extends Solution
{
	private Project project;
	private ProjectProblem projectProblem;
	private int limits;
	
	/**
	 * array of the quantity of time allocated with the range from 8 to 12;
	 */
	private int[] activitiesOverworkAllocation;
	
	private @Getter @Setter double makespan;
	private @Getter @Setter double cost;
	private @Getter @Setter double overwork;
	private @Getter @Setter int location;
	private @Getter @Setter String heuristic;
	private @Getter @Setter long executionTime;
	private @Getter @Setter int cycle;
	private @Getter @Setter int evaluations;
		
	/**
	 * Creates a new solution
	 */
	public ProjectSolution(Project project, int evaluations)
	{
		this.project = project;
		//this.limits = limits;
		this.activitiesOverworkAllocation = new int[project.countActivities()];
		this.evaluations = evaluations;
		
	}
	
	/**
	 * Creates a new solution
	 */
	public ProjectSolution(ProjectProblem projectProblem, String heuristic)//, int limits)
	{
		this.projectProblem = projectProblem;
		this.heuristic = heuristic;
		this.project = projectProblem.getProject();
		//this.limits = limits;
		this.activitiesOverworkAllocation = new int[projectProblem.getProject().countActivities()];
		this.evaluations = evaluations;
	}
	
	/**
	 * Creates a random, potentially invalid solution
	 */
	public void randomizeSolution()
	{
		Random r = new Random();
		
		for (int i = 0; i < activitiesOverworkAllocation.length; i++)
			activitiesOverworkAllocation[i] = (r.nextInt((9 - 1) + 1) + 1);// * 0.5 + 7.5;
	}
	
	public void calculate()
	{
		this.cost = project.getCost();
		this.overwork = project.getOverworking();
		this.makespan = project.getMakespan();
	}	


	
	public void publishResult()
	{
		System.out.print(project.getName() + "\t" + this.heuristic + "\t" + "cycle#" + this.cycle + "\t" + this.executionTime + "\t" + projectProblem.getProject().getCost() + "\t" + projectProblem.getProject().getMakespan() + "\t" + projectProblem.getProject().getOverworking()+ "\t [");
		for (int i = 1; i < this.activitiesOverworkAllocation.length; i++)
			System.out.print(" " + this.activitiesOverworkAllocation[i]);
		
		System.out.println("]");
	}
	
	/**
	 * Creates a random, though valid, solution
	 */
//	public void randomizeValid()
//	{
//		Random r = new Random();
//		int ProjectSize = Project.size();
//		
//		int[] ActivityIndex = new int[ProjectSize];
//		
//		for (int i = 0; i < ProjectSize; i++)
//		{
//			ActivityIndex[i] = i;
//			Activitys[i] = false;
//		}
//		
//		int totalExecutionTime = 0;
//		
//		for (int i = 0; i < ProjectSize; i++)
//		{
//			int sample = r.nextInt(ProjectSize - i);
//			int index = ActivityIndex[sample];
//			
//			Activity Activity = Project.get(index);
//			totalExecutionTime += Activity.getDuration();
//			
//			if (totalExecutionTime > limits)
//				return;
//			
//			Activitys[index] = true;
//			
//			for (int j = sample; j < ProjectSize-i-1; j++)
//				ActivityIndex[j] = ActivityIndex[j+1];
//		}
//	}
	
	/**
	 * Sets a test case to a specific state
	 */
	public void setActivitiesOverworkAllocation(int index, int value)
	{
		activitiesOverworkAllocation[index] = value;
	}
	
	/**
	 * Returns the stateof a given test case
	 */
	public int getActivityOverworkAllocation(int index)
	{
		return activitiesOverworkAllocation[index];
	}
	
	public int [] getActivitiesOverworkAllocation()
	{
		return activitiesOverworkAllocation;
	}
	
	public void setActivitiesOverworkAllocations(int[] activitiesOverworkAllocation_ )
	{
		this.activitiesOverworkAllocation = activitiesOverworkAllocation_;
	}
	
	/**
	 * Calculates the execution time of the solution
	 */
//	public int getMakespan()
//	{
//		int duration = 0;
//		
//		for (int i = 0; i < Activitys.length; i++)
//			if (Activitys[i])
//				duration += Project.get(i).getDuration();
//		
//		return duration;
//	}

	/**
	 * Calculates the coverage of the solution
	 */
//	public int getCoverage()
//	{
//		int duration = 0;
//		
//		for (int i = 0; i < Activitys.length; i++)
//			if (Activitys[i])
//				duration += Project.get(i).getTotalCoverage();
//		
//		return duration;
//	}
	
	/**
	 * Calculates the fitness of the solution
	 */
	
	public double getFitness()
	{
//		if (getExecutionTime() > evaluations)
//			return 0;
		
		//return getMakespan() + getCost() + getOverwork();
		return getMakespan();// + getCost() + getOverwork();
	}
	
		
//			overtime = "";
//			
//			Solution solution = new Solution (project);
//			solution.randomize();
//					
//			
//			NumberFormat nf2 = new DecimalFormat("0.00");
//			
//			
//			System.out.println(nf2.format(project.getMakespan()) + "\t" + nf2.format(project.getCost()) + "\t" + nf2.format(project.getOverworking()) + "\t" + overtime);

	
	
	
	
	
//	}
	
	/**
	 * Creates a clone of the current solution
	 */
	public ProjectSolution clone()
	{
		ProjectSolution projectSolution = new ProjectSolution(this.projectProblem, this.heuristic);
	
		projectSolution.setMakespan(this.makespan);
		projectSolution.setCost(this.cost);
		projectSolution.setOverwork(this.overwork);
		projectSolution.setLocation(this.location);
		projectSolution.setHeuristic(this.heuristic);
		projectSolution.setExecutionTime(this.executionTime);
		projectSolution.setCycle(this.cycle);
		projectSolution.setEvaluations(this.evaluations);
		projectSolution.setActivitiesOverworkAllocations(this.activitiesOverworkAllocation); 

		return projectSolution;
	}
}