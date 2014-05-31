package br.unirio.overwork.builders.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Class that represents a software system
 * 
 * @author Marcio Barros
 */
public class SoftwareSystem
{
	/**
	 * System's name
	 */
	private @Getter String name;
	
	/**
	 * List of work packages within the system
	 */
	private List<WorkPackage> workPackages;

	/**
	 * Initializes the system
	 */
	public SoftwareSystem(String name)
	{
		this.name = name;
		this.workPackages = new ArrayList<WorkPackage>();
	}
	
	/**
	 * Counts the number of work packages in the system
	 */
	public int countWorkPackages()
	{
		return workPackages.size();
	}
	
	/**
	 * Returns a work package, given its index
	 */
	public WorkPackage getWorkPackageIndex(int index)
	{
		return workPackages.get(index);
	}
	
	/**
	 * Returns the work packages
	 */
	public List<WorkPackage> getWorkPackages()
	{
		return workPackages;
	}
	
	/**
	 * Returns a work package, given its name
	 */
	public WorkPackage getWorkPackageName(String name)
	{
		for (WorkPackage wp : workPackages)
			if (wp.getName().compareToIgnoreCase(name) == 0)
				return wp;
		
		return null;
	}
	
	/**
	 * Returns the work packages comprising the system
	 */
	public Iterable<WorkPackage> getWorkPackages()
	{
		return workPackages;
	}

	/**
	 * Adds a work package to the system
	 */
	public WorkPackage addWorkPackage(String name)
	{
		WorkPackage wp = new WorkPackage(name);
		this.workPackages.add(wp);
		return wp;
	}
	
	
	/** verificar com o márcio
	 * Adds a work package to the system
	 */
	public void addWorkPackage(WorkPackage wp)
	{
		this.workPackages.add(wp);
	}
	
	
	/**
	 * Calculates the number of function points in the system
	 */
	public int calculateFunctionPoints()
	{
		int soma = 0;
		
		for (WorkPackage wp : workPackages)
			soma += wp.calculateFunctionPoints();
		
		return soma;
	}
}