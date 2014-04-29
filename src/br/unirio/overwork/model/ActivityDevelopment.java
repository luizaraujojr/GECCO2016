package br.unirio.overwork.model;

import lombok.Getter;

/**
 * Class that represents a development activity
 * 
 * @author Marcio Barros
 */
public class ActivityDevelopment extends Activity
{
	/**
	 * Work required to perform the activity
	 */
	private @Getter double workRequired;

	/**
	 * Work performed as part of the activity so far
	 */
	private @Getter double workPerformed;
	
	/**
	 * Number of errors expected to be generated during the activity
	 */
	private double errorsExpected;

	/**
	 * Errors inherited from previous activities
	 */
	private double inheritedErrors;
	
	/**
	 * Error regeneration rate for the activity
	 */
	private double errorRegeneration;
	
	/**
	 * Initializes the development activity
	 */
	public ActivityDevelopment(Project project, String name, double workRequired, double errorsExpected, double errorRegeneration)
	{
		super(project, name);
		this.workRequired = workRequired;
		this.workPerformed = 0.0;
		this.errorsExpected = errorsExpected;
		this.errorRegeneration = errorRegeneration;
	}
	
	/**
	 * Initializes the simulation of the development activity
	 */
	@Override
	public void init()
	{
		this.workPerformed = 0.0;
	}

	/**
	 * Collects errors from former activities before when the activity's life-cycle starts
	 */
	@Override
	public void beforeStart()
	{
		super.beforeStart();
		this.inheritedErrors = getErrors();
	}

	/**
	 * Returns the effort required to complete the activity
	 */
	@Override
	public double getRemainingWork()
	{
		return workRequired - workPerformed;
	}

	/**
	 * Consumes an amount of effort in the activity
	 */
	@Override
	protected void consumeEffort(double effort)
	{
		workPerformed += effort;
		double ratio = effort / workRequired;
		double newErrors = (errorsExpected + errorRegeneration * inheritedErrors) * ratio * getErrorGenerationRate();
		setErrors(getErrors() + newErrors);	
	}
}