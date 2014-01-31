package br.unirio.overwork.simulation.state;

/**
 * Class that represents a resource used during a simulation
 * 
 * @author Marcio
 */
public class State implements IState 
{
	/**
	 * State's name
	 */
	private String name;
	
	/**
	 * State's value
	 */
	private double value;
	
	/**
	 * Initializes the state
	 */
	public State(String name, double value)
	{
		this.name = name;
		this.value = value;
	}
	
	/**
	 * Initializes the state
	 */
	public State(String name)
	{
		this(name, 0.0);
	}

	/**
	 * Gets the name of the state
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value for the state
	 */
	public void set(double value)
	{
		this.value = value;
	}

	/**
	 * Gets the value of the state
	 */
	public double get()
	{
		return value;
	}
}