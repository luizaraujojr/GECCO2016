package br.unirio.overwork.model.base;

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
	private double errorCorrectionEffort;
	
	/**
	 * Initializes a testing activity
	 */
	public AtivityTesting(Project project, String nome, double errorCorrectionEffort)
	{
		super(project, nome);
		this.errorCorrectionEffort = errorCorrectionEffort;
	}

	/**
	 * Returns the effort required to complete the activity
	 */
	@Override
	public double getRemainingWork()
	{
		return getErrors() * errorCorrectionEffort;
	}

	/**
	 * Consumes an amount of effort in the activity
	 */
	@Override
	protected void consumeEffort(double effort)
	{
		setErrors(getErrors() - effort / errorCorrectionEffort);	
	}
}