package br.unirio.overwork.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import br.unirio.overwork.simulation.SimulationObject;

/**
 * Class that represents an activity
 * 
 * @author Marcio Barros
 */
public abstract class Activity extends SimulationObject
{
	/**
	 * List of activities that precede the current activity
	 */
	private List<Activity> precedences;
	
	/**
	 * Develop assigned to the activity
	 */
	private @Getter Developer developer;
	
	/**
	 * Indicates whether the activity has started
	 */
	private @Getter boolean started;
	
	/**
	 * Records the time when the activity was finished
	 */
	private @Getter double conclusionTime;
	
	/**
	 * Number of erros remaining when the activity is finished
	 */
	protected @Getter double errors;

	/**
	 * Initializes an activity
	 */
	public Activity(String name)
	{
		super(name);
		this.precedences = new ArrayList<Activity>();
	}

	/**
	 * Sets the developer working in the activity
	 */
	public Activity setDeveloper(Developer developer)
	{
		this.developer = developer;
		return this;
	}

	/**
	 * Adds a precedent activity to the current one
	 */
	public Activity addPrecedent(Activity activity)
	{
		this.precedences.add(activity);
		return this;
	}

	/**
	 * Returns the precedent activities of the current one
	 */
	public Iterable<Activity> getPrecedences()
	{
		return precedences;
	}
	
	/**
	 * Checks whether the activity has finished
	 */
	public boolean isConcluded()
	{
		return started && (getRemainingWork() < 0.001);
	}
	
	/**
	 * Checks whether the precendent activities were concluded
	 */
	private boolean precedentConcluded()
	{
		for (Activity activity : precedences)
			if (!activity.isConcluded())
				return false;
		
		return true;
	}
	
	/**
	 * Counts the number of errors inherited from the precedent activities
	 */
	private double countPrecedentErrors()
	{
		double sum = 0.0;
		
		for (Activity activity : getPrecedences())
			sum += activity.getErrors();
		
		return sum;
	}

	/**
	 * Returns the list of dependencies for simulation
	 */
	@Override
	public List<SimulationObject> getDependencies()
	{
		List<SimulationObject> result = new ArrayList<SimulationObject>();
		result.addAll(precedences);
		result.add(developer);
		return result;
	}

	/**
	 * Prepares the activities' simulation
	 */
	@Override
	public void init()
	{
		this.started = false;
		this.conclusionTime = 0.0;
		this.errors = 0.0;
	}
	
	/**
	 * Simulation step for the activity
	 */
	@Override
	public void step()
	{
		if (!started)
		{
			boolean precConcluded = precedentConcluded();

			if (precConcluded)
			{
				errors += countPrecedentErrors();
				started = true;
			}
		}

		double remainingWork = getRemainingWork();

		if (started && remainingWork >= 0.001)
		{
			double effortAvailable = developer.getEffortAvailable();
			double effortUsed = Math.min(effortAvailable, remainingWork);
			developer.setEffortAvailable(effortAvailable - effortUsed);
			consumeEffort(effortUsed);
			
			if (getRemainingWork() < 0.001)
				conclusionTime = getCurrentSimulationTime();
		}
	}
	
	/**
	 * Returns the amount of work to be performed
	 */
	protected abstract double getRemainingWork();
	
	/**
	 * Consumes an amount of effort
	 */
	protected abstract void consumeEffort(double effort);

	/**
	 * Presents the activity as a string
	 */
	@Override
	public String toString()
	{
		return getName() + "\t" + conclusionTime + "\t" + errors;
	}
}