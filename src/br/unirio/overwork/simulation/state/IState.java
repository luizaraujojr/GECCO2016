package br.unirio.overwork.simulation.state;

/**
 * Interface that represents a state maintained by a simulation object
 * 
 * @author Marcio
 */
public interface IState 
{
	/**
	 * Gets the name of the state
	 */
	public String getName();

	/**
	 * Sets the value for the state
	 */
	public void set(double value);

	/**
	 * Gets the value of the state
	 */
	public double get();
}