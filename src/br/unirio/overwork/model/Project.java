package br.unirio.overwork.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a project with its activities and developers
 * 
 * @author Marcio Barros
 */
public class Project
{
	/**
	 * List of project activities
	 */
	private List<Activity> activities;

	/**
	 * List of developers engaged in the activity
	 */
	private List<Developer> developers;

	/**
	 * Initializes the project
	 */
	public Project()
	{
		this.activities = new ArrayList<Activity>();
		this.developers = new ArrayList<Developer>();
	}
	
	/**
	 * Adds an activity to the project
	 */
	public void addActivity(Activity activity)
	{
		this.activities.add(activity);
	}
	
	/**
	 * Returns the list of activities from the project
	 */
	public Iterable<Activity> getActivities()
	{
		return this.activities;
	}
	
	/**
	 * Adds a developer to the project
	 */
	public void addDeveloper(Developer developer)
	{
		this.developers.add(developer);
	}
	
	/**
	 * Returns the list of developers
	 */
	public Iterable<Developer> getDevelopers()
	{
		return this.developers;
	}
	
	/**
	 * Checks whether the project is concluded
	 */
	public boolean isConcluded()
	{
		for (Activity a : activities)
			if (!a.isFinished())
				return false;
		
		return true;
	}
}