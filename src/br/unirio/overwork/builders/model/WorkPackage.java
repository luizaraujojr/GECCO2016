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
	 * Number of function points
	 */
	private @Getter int functionPoints;
	
	/**
	 * List of dependencies to other work packages
	 */
	private List<WorkPackage> dependencies;

	/**
	 * List of components within the work package
	 */
	private List<String> components;
	
	/**
	 * Initializes the work package
	 */
	public WorkPackage(String name)
	{
		this.name = name;
		this.functionPoints = 0;
		this.dependencies = new ArrayList<WorkPackage>();
		this.components = new ArrayList<String>();
	}

	/**
	 * Adds a dependency to a work package
	 */
	public void addDependency(WorkPackage wp)
	{
		dependencies.add(wp);
	}

	/**
	 * Adds a component to the work package
	 */
	public void addComponent(String name, int fp)
	{
		this.components.add(name);
		this.functionPoints += fp;
	}
	
	/**
	 * Returns all components from the work package
	 */
	public Iterable<String> getComponents()
	{
		return components;
	}
	
	/**
	 * Returns all dependencies from the work package
	 */
	public Iterable<WorkPackage> getDependencies()
	{
		return dependencies;
	}
}