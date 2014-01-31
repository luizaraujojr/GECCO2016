package br.unirio.overwork.simulation;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import br.unirio.overwork.simulation.resource.IResource;
import br.unirio.overwork.simulation.support.TopologicalSort;

/**
 * Class that performs a continuous, object-oriented simulation
 * 
 * @author Marcio Barros
 */
@SuppressWarnings({"rawtypes", "unchecked"})
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
	 * Resources consumed by the simulation
	 */
	private List<IResource> resources;
	
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
		this.resources = new ArrayList<IResource>();
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
	 * Adds a resource to the simulation
	 */
	public void addResource(IResource resource) 
	{
		this.resources.add(resource);
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
		{
			object.init();
			
			for (Scenario scenario : object.getScenarios())
				scenario.init(object);
		}
		
		currentSimulationTime = 0;
	}

	/**
	 * Performs a single simulation step for all objects
	 */
	private void performSingleStep()
	{
		for (IResource resource : resources)
			resource.reset();
		
		for (SimulationObject object : orderedObjects)
		{
			object.setCurrentSimulationTime(currentSimulationTime);
			
			if (dependenciesConcluded(object))
			{
				if (!object.isStarted())
					object.start();
				
				if (object.isStarted() && !object.isFinished())
				{
					object.beforeStep();
					
					for (Scenario scenario : object.getScenarios())
						scenario.beforeStep(object);
					
					boolean hasFinished = !object.step();

					for (Scenario scenario : object.getScenarios())
						scenario.afterStep(object);
					
					if (hasFinished)
						object.finish();
					
					object.afterStep();
				}
			}
		}

		currentSimulationTime += SimulationObject.DT;
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
			if (!dependee.isFinished())
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