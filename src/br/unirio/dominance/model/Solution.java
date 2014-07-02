package br.unirio.dominance.model;

import java.util.ArrayList;
import java.util.List;

import br.unirio.overwork.builders.model.WorkPackage;

/**
 * Class that represents a solution
 * 
 * @author Luiz Antonio
 */
public class Solution
{	
	/**
	 * List of objectives 
	 */
	private List<Objective> objectives;
	
	/**
	 * Adds a objective to the solution
	 */
	public void addObjective(Objective objective)
	{
		this.objectives.add(objective);
	}
	
	/**
	 * Returns the objectives
	 */
	public Iterable<Objective> getObjectives()
	{
		return this.objectives;
	}
	
	/**
	 * Counts the number of work packages in the system
	 */
	public int countObjectives()
	{
		return objectives.size();
	}
	
	/**
	 * Returns a work package, given its index
	 */
	public Objective getObjective(int index)
	{
		return objectives.get(index);
	}
	
	public Solution()
	{
		this.objectives = new ArrayList<Objective>();
	}	
}