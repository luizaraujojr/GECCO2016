package br.unirio.overwork.model.base;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Class that represents a project with its activities and developers
 * 
 * @author Marcio Barros
 */
public class Project
{
	/**
	 * name of the project 
	 */
	private @Getter @Setter String name;
	
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
	 * Returns the number of activities in the project
	 */
	public int getActivitiesCount()
	{
		return activities.size();
	}
	
	/**
	 * Returns an activity, given its index
	 */
	public Activity getActivity(int index)
	{
		return activities.get(index);
	}
	
	/**
	 * Returns an activity, given its name
	 */
	public Activity getActivity(String name) 
	{
		for (Activity a : activities)
			if (a.getName().compareToIgnoreCase(name) == 0)
				return a;
		
		return null;
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

	/**
	 * Selects a subset of activities by a prefix of its name
	 */
	public List<Activity> getActivitiesByPrefix(String prefix)
	{
		List<Activity> result = new ArrayList<Activity>();
		
		for (Activity activity : activities)
			if (activity.getName().startsWith(prefix))
				result.add(activity);
		
		return result;
	}

	/**
	 * Returns the makespan for the project after a simulation run
	 */
	public double getMakespan()
	{
		double makespan = 0.0;
		
		for (Activity activity : activities)
		{
			double finish = activity.getFinishingTime();

			if (finish > makespan)
				makespan = finish;
		}
		
		return makespan;
	}

	/**
	 * Returns the cost of the project after a simulation run
	 */
	public double getCost()
	{
		double cost = 0.0;
		
		for (Activity activity : activities)
			cost += activity.getCost();
		
		return cost;
	}

	/**
	 * Returns the number of overworking hours within the project after a simulation run
	 */
	public double getOverworking()
	{
		double overworking = 0.0;
		
		for (Activity activity : activities)
			overworking += activity.getOverworkHours();
		

		return overworking;
	}
}