package br.unirio.dominance.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Class that represents a objective
 * 
 * @author Luiz Antonio
 */
public class Objective
{
	/**
	 * Objective's name
	 */
	private @Setter @Getter String name;
	
	/**
	 * Objective's value
	 */
	private @Setter @Getter double value;
	
	/**
	 * List of objectives 
	 */
	private List<Objective> bigger;
	
	/**
	 * Adds a objective to the solution
	 */
	public void addBigger(Objective ob)
	{
		this.bigger.add(ob);
	}
	
	/**
	 * Returns the objectives
	 */
	public Iterable<Objective> getBigger()
	{
		return this.bigger;
	}
	
	/**
	 * Counts the number of work packages in the system
	 */
	public int countBigger()
	{
		return this.bigger.size();
	}
	
	/**
	 * Counts the number of work packages in the system
	 */
	public int countSmaller()
	{
		return this.smaller.size();
	}
	
	/**
	 * List of objectives 
	 */
	private List<Objective> smaller;
	
	/**
	 * Adds a objective to the solution
	 */
	public void addSmaller(Objective objective)
	{
		this.smaller.add(objective);
	}
	
	/**
	 * Returns the objectives
	 */
	public Iterable<Objective> getSmaller()
	{
		return this.bigger;
	}
	
	
	public Objective(String name, double value)
	{
		super();
		this.name = name;
		this.value = value;
		this.smaller = new ArrayList<Objective>();
		this.bigger = new ArrayList<Objective>();
		
	}
}