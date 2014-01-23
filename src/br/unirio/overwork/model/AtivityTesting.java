package br.unirio.overwork.model;

/**
 * Class that represents a testing activity
 * 
 * @author Marcio Barros
 */
public class AtivityTesting extends Activity
{
	/**
	 * Average number of days required to correct an error
	 */
	private static final double ERROR_CORRECTION_EFFORT = 0.13;
	
	/**
	 * Initializes a testing activity
	 */
	public AtivityTesting(String nome)
	{
		super(nome);
	}

	/**
	 * Returns the effort required to complete the activity
	 */
	@Override
	protected double getRemainingWork()
	{
		return errors * ERROR_CORRECTION_EFFORT;
	}

	/**
	 * Consumes an amount of effort in the activity
	 */
	@Override
	protected void consumeEffort(double effort)
	{
		errors -= effort / ERROR_CORRECTION_EFFORT;	
	}
}