package br.unirio.overwork.simulation;

import java.util.HashMap;

import br.unirio.overwork.simulation.state.IState;
import br.unirio.overwork.simulation.state.State;

/**
 * Object that participates in the simulation and maintains states
 * 
 * @author Marcio
 */
public class StatefulObject 
{
	/**
	 * States maintained by the object
	 */
	private HashMap<String, IState> states;

	/**
	 * Initializes the object
	 */
	public StatefulObject()
	{
		this.states = new HashMap<String, IState>();
	}
	
	/**
	 * Returns the value of a state
	 */
	public double getState(String name, double _default)
	{
		if (states.containsKey(name))
			return states.get(name).get();
		
		states.put(name, new State(name, _default));
		return _default;
	}
	
	/**
	 * Sets the value of a state
	 */
	public void setState(String name, double value)
	{
		states.put(name, new State(name, value));
	}
}