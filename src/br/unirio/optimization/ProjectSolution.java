package br.unirio.optimization;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

import jmetal.base.Solution;
import lombok.Getter;
import lombok.Setter;
import br.unirio.overwork.model.base.Project;

/**
 * Class that represents a solution for Project's activities overwork optimization problem
 * 
 * @author Luiz Araujo Jr
 */
public class ProjectSolution extends Solution
{
	@SuppressWarnings("unused")
	private Project project;
	private ProjectProblem projectProblem;
	
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
	 * Creates a new solution for the jmetal framework
	 */
	public ProjectSolution(Project project, int evaluations)
	{
		this.project = project;
		this.activitiesOverworkAllocation = new int[project.countActivities()];
		this.evaluations = evaluations;	
	}
	
	/**
	 * Creates a new solution
	 */
	public ProjectSolution(ProjectProblem projectProblem, String heuristic)
	{
		this.projectProblem = projectProblem;
		this.heuristic = heuristic;
		this.project = projectProblem.getProject();
		this.activitiesOverworkAllocation = new int[projectProblem.getProject().countActivities()];
	}
	
	/**
	 * Creates a random solution
	 */
	public void randomizeSolution()
	{
		Random r = new Random();
		
		for (int i = 0; i < activitiesOverworkAllocation.length; i++)
			activitiesOverworkAllocation[i] = (r.nextInt((9 - 1) + 1) + 1);// * 0.5 + 7.5;
	}
	
	/**
	 * Prints out the result of the experiment´s cycle
	 */
	public void publishResult()
	{
		NumberFormat nf2 = new DecimalFormat("0.0000");
		
		System.out.print("Cycle #" + this.cycle + "; " + this.executionTime + "; " + nf2.format(projectProblem.getProject().getOverworking()) + "; " + nf2.format(projectProblem.getProject().getMakespan()) + "; " + nf2.format(projectProblem.getProject().getCost())+ ";  "+ this.getLocation() + "; [");
		for (int i = 1; i < this.activitiesOverworkAllocation.length; i++)
			System.out.print(" " + this.activitiesOverworkAllocation[i]);
		
		System.out.println("]");
		
//		System.out.print(project.getName() + "\t" + this.heuristic + "\t" + "cycle#" + this.cycle + "\t" + this.executionTime + "\t" + nf2.format(projectProblem.getProject().getCost()) + "\t" + nf2.format(projectProblem.getProject().getMakespan()) + "\t" + nf2.format(projectProblem.getProject().getOverworking())+ "\t [");
//		for (int i = 1; i < this.activitiesOverworkAllocation.length; i++)
//			System.out.print(" " + this.activitiesOverworkAllocation[i]);
//		
//		System.out.println("]");
	}
	
	/**
	 * Sets an allocation of overwork to a specific activity
	 */
	public void setActivitiesOverworkAllocation(int index, int value)
	{
		activitiesOverworkAllocation[index] = value;
	}
	
	/**
	 * Returns the value of the associated overwork for a specific activity
	 */
	public int getActivityOverworkAllocation(int index)
	{
		return activitiesOverworkAllocation[index];
	}
	
	/**
	 * Returns all the values of the associated overwork
	 */
	public int [] getActivitiesOverworkAllocation()
	{
		return activitiesOverworkAllocation;
	}
	
	/**
	 * Sets an overwork quantity for an activity
	 */	
	public void setActivitiesOverworkAllocations(int[] activitiesOverworkAllocation )
	{
		this.activitiesOverworkAllocation = activitiesOverworkAllocation;
	}
	
	/**
	 * Calculates the fitness of the solution
	 */
	public double getFitness()
	{
		return getMakespan();
	}
	
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