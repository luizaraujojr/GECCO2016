package br.unirio.simulation;

import java.util.HashMap;

/**
 * Class that supports storing values that persist across consecutive simulation steps
 * 
 * @author Marcio Barros
 */
class StateBoard
{
	private HashMap<String, Double> states;

	/**
	 * Initializes the state board
	 */
	public StateBoard()
	{
		this.states = new HashMap<String, Double>();
	}
	
	/**
	 * Clears all states
	 */
	public void clear()
	{
		states.clear();
	}
	
	/**
	 * Reads a state as a double value
	 */
	public double getState(String name, double _default)
	{
		Double value = states.get(name);
		return (value != null) ? value : _default;
	}
	
	/**
	 * Reads a state as a boolean value
	 */
	public boolean getState(String name, boolean _default)
	{
		Double value = states.get(name);
		
		if (value != null)
			return value != 0.0;
		
		return _default;
	}

	/**
	 * Writes a state as a double value
	 */
	public void setState(String name, double value)
	{
		states.put(name, value);
	}

	/**
	 * Writes a state as a boolean value
	 */
	public void setState(String name, boolean value)
	{
		states.put(name, value ? 1.0 : 0.0);
	}
}