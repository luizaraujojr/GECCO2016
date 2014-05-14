package br.unirio.optimization;

import java.util.Random;

import jmetal.base.Solution;
import lombok.Getter;
import lombok.Setter;
import br.unirio.overwork.model.Project;
import br.unirio.overwork.model.Activity;

/**
 * Class that represents a solution for Project's activities overwork optimization problem
 */
public class ProjectSolution extends Solution
{
	private Project project;
	private int limits;
	
	/**
	 * array of the quantity of time allocated with the range from 8 to 12;
	 */
	private double[] activitiesOverworkAllocation;
	
	private @Getter @Setter double makespan;
	private @Getter @Setter double cost;
	private @Getter @Setter double overwork;
	
	
	/**
	 * Creates a new solution
	 */
	public ProjectSolution(Project project)//, int limits)
	{
		this.project = project;
		//this.limits = limits;
		this.activitiesOverworkAllocation = new double[Project.countActivities()];
	}
	
	/**
	 * Creates a random, potentially invalid solution
	 */
	public void randomize()
	{
		Random r = new Random();
		
		for (int i = 0; i < activitiesOverworkAllocation.length; i++)
			activitiesOverworkAllocation[i] = (r.nextInt((9 - 1) + 1) + 1) * 0.5 + 7.5;
	}
	
	public void calculate()
	{
		this.cost = project.getCost();
		this.overwork = project.getOverworking();
		this.makespan = project.getMakespan();
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
	public void setActivitiesOverworkAllocation(int index, double value)
	{
		activitiesOverworkAllocation[index] = value;
	}
	
	/**
	 * Returns the stateof a given test case
	 */
	public double getActivitiesOverworkAllocation(int index)
	{
		return activitiesOverworkAllocation[index];
	}
	
	public double [] getActivitiesOverworkAllocations()
	{
		return activitiesOverworkAllocation;
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
//	public int getFitness()
//	{
//		if (getExecutionTime() > limits)
//			return 0;
//		
//		return getCoverage();
//	}
	
	/**
	 * Creates a clone of the current solution
	 */
//	public Solution clone()
//	{
//		Solution solution_ = new Solution(Project, limits);
//		
//		for (int i = 0; i < Activitys.length; i++)
//			solution_.setActivity(i, Activitys[i]);
//
//		return solution_;
//	}
}