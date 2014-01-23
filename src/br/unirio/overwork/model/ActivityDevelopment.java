package br.unirio.overwork.model;

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
	private double workRequired;

	/**
	 * Work performed as part of the activity so far
	 */
	private double workPerformed;
	
	/**
	 * Number of errors expected to be generated during the activity
	 */
	private double errorsExpected;
	
	/**
	 * Initializes the development activity
	 */
	public ActivityDevelopment(String name, double workRequired, double errorsExpected)
	{
		super(name);
		this.workRequired = workRequired;
		this.errorsExpected = errorsExpected;
	}
	
	/**
	 * Initializes the simulation of the development activity
	 */
	@Override
	public void init()
	{
		super.init();
		this.workPerformed = 0.0;
	}

	/**
	 * Returns the effort required to complete the activity
	 */
	@Override
	protected double getRemainingWork()
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
		errors += (effort / workRequired) * errorsExpected * getDeveloper().getErrorGenerationRate();	
	}
}