package br.unirio.overwork.simulation;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Class that performs a continuous, object-oriented simulation
 * 
 * @author Marcio Barros
 */
public class Simulator
{
	/**
	 * Current simulation time
	 */
	private @Getter double currentSimulationTime;
	
	/**
	 * Objects subjected to simulation
	 */
	private List<SimulationObject> objects;
	
	/**
	 * Topological order of the objects subjected to simulation
	 */
	private List<SimulationObject> orderedObjects;
	
	/**
	 * Initializes the simulator
	 */
	public Simulator()
	{
		this.currentSimulationTime = 0;
		this.objects = new ArrayList<SimulationObject>();
		this.orderedObjects = null;
	}
	
	/**
	 * Adds an object to the simulation
	 */
	public void add(SimulationObject object)
	{
		this.objects.add(object);
	}
	
	/**
	 * Adds a set of objects to the simulation
	 */
	public void add(Iterable<? extends SimulationObject> objects)
	{
		for (SimulationObject o : objects)
			this.objects.add(o);
	}
	
	/**
	 * Initializes the simulation for all objects
	 */
	public void init() throws Exception
	{
		orderedObjects = new OrdenadorTopologicoSimulacao().sort(objects);
		
		for (SimulationObject object : orderedObjects)
			object.init();

		currentSimulationTime = 0;
	}

	/**
	 * Performs a single simulation step for all objects
	 */
	private void performSingleStep()
	{
		currentSimulationTime += SimulationObject.DT;
		
		for (SimulationObject object : orderedObjects)
		{
			object.setCurrentSimulationTime(currentSimulationTime);
			object.step();
			
			if (object.isLiveObject())
				performLifeCycleStep(object);
		}
	}

	/**
	 * Perform a single step in the life-cycle of a live object
	 */
	private void performLifeCycleStep(SimulationObject object)
	{
		if (!dependenciesConcluded(object))
			return;
		
		if (!object.isStarted())
		{
			object.beforeStart();
			object.start();
		}
		
		if (object.isStarted() && !object.isFinished())
			if (!object.liveStep())
			{
				object.beforeFinish();
				object.finish();
			}
	}

	/**
	 * Checks whether all live objects preceding an object are finished
	 */
	private boolean dependenciesConcluded(SimulationObject object)
	{
		List<SimulationObject> dependees = object.getDependencies();
		
		if (dependees == null)
			return true;
		
		for (SimulationObject dependee : dependees)
			if (dependee.isLiveObject() && !dependee.isFinished())
				return false;

		return true;
	}

	/**
	 * Runs a given number of simulation steps
	 */
	public void run(int steps)
	{
		for (int i = 0; i < steps; i++)
			performSingleStep();
	}
	
	/**
	 * Runs a single simulation step
	 */
	public void run()
	{
		run(1);
	}
}

/**
 * Topological sorter of simulation objects
 * 
 * @author Marcio Barros
 */
class OrdenadorTopologicoSimulacao extends TopologicalSort<SimulationObject>
{
	@Override
	protected List<SimulationObject> getDependencies(SimulationObject item)
	{
		return item.getDependencies();
	}
}