package br.unirio.overwork.builders.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Class that represents a work package within a project
 * 
 * @author Marcio Barros
 */
public class WorkPackage
{
	/**
	 * Work package's name
	 */
	private @Getter String name;
	
	/**
	 * List of requirements within the work package
	 */
	private List<Requirement> requirements;

	/**
	 * Initializes the work package
	 */
	public WorkPackage(String name)
	{
		this.name = name;
		this.requirements = new ArrayList<Requirement>();
	}
	
	/**
	 * Counts the number of requirements in the work package
	 */
	public int countRequirements()
	{
		return requirements.size();
	}
	
	/**
	 * Returns a requirement, given its index
	 */
	public Requirement getRequirementIndex(int index)
	{
		return requirements.get(index);
	}
	
	/**
	 * Returns a requirement, given its name
	 */
	public Requirement getRequirementName(String name)
	{
		for (Requirement r : requirements)
			if (r.getName().compareToIgnoreCase(name) == 0)
				return r;
		
		return null;
	}

	/**
	 * Adds a requirement to the work package
	 */
	public Requirement addRequirement(String name, int functionPoints)
	{
		Requirement requirement = new Requirement(name, functionPoints);
		this.requirements.add(requirement);
		return requirement;
	}
	
	/**
	 * Calculates the number of function points in the work package
	 */
	public int calculateFunctionPoints()
	{
		int soma = 0;
		
		for (Requirement r : requirements)
			soma += r.getFunctionPoints();
		
		return soma;
	}
}