package br.unirio.simulation;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Class that performs a continuous, object-oriented simulation
 * 
 * @author Marcio Barros
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class SimulationEngine
{
	/**
	 * Current simulation time
	 */
	private @Getter double currentSimulationTime;
	
	/**
	 * Current simulation blackboard
	 */
	private StateBoard blackboard;
	
	/**
	 * Resources consumed by the simulation
	 */
	private List<Resource> resources;
	
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
	public SimulationEngine()
	{
		this.currentSimulationTime = 0;
		this.blackboard = new StateBoard();
		this.objects = new ArrayList<SimulationObject>();
		this.resources = new ArrayList<Resource>();
		this.orderedObjects = null;
	}
	
	/**
	 * Adds an object to the simulation
	 */
	public void addSimulationObject(SimulationObject object)
	{
		this.objects.add(object);
	}
	
	/**
	 * Adds a set of objects to the simulation
	 */
	public void addSimulationObjects(Iterable<? extends SimulationObject> objects)
	{
		for (SimulationObject o : objects)
			this.objects.add(o);
	}

	/**
	 * Adds a resource to the simulation
	 */
	public void addResource(Resource resource) 
	{
		this.resources.add(resource);
	}
	
	/**
	 * Initializes the simulation for all objects
	 */
	public void init() throws Exception
	{
		orderedObjects = new OrdenadorTopologicoSimulacao().sort(objects);
		currentSimulationTime = 0;
		blackboard.clear();

		for (SimulationObject object : orderedObjects)
		{
			object.prepare(currentSimulationTime, blackboard);
			object.init();
			
			for (Scenario scenario : object.getScenarios())
				scenario.init(object);
		}
	}

	/**
	 * Performs a single simulation step for all objects
	 */
	private void performStep()
	{
		for (Resource resource : resources)
			resource.reset();
		
		for (SimulationObject object : orderedObjects)
		{
			object.prepare(currentSimulationTime, blackboard);
			
			if (dependenciesConcluded(object))
			{
				if (!object.isStarted())
					performStepStart(object);
				
				if (!object.isFinished())
				{
					boolean hasFinished = performStepCore(object);

					if (hasFinished)
						performStepFinish(object);
				}
			}
		}

		currentSimulationTime += SimulationObject.DT;
	}

	/**
	 * Performs the start of a step
	 */
	private void performStepStart(SimulationObject object)
	{
		object.beforeStart();

		for (Scenario scenario : object.getScenarios())
			scenario.beforeStart(object);

		object.start();

		for (Scenario scenario : object.getScenarios())
			scenario.afterStart(object);
	}

	/**
	 * Performs the core part of a step
	 */
	private boolean performStepCore(SimulationObject object)
	{
		object.beforeStep();
		
		for (Scenario scenario : object.getScenarios())
			scenario.beforeStep(object);
		
		boolean hasFinished = !object.step();

		for (Scenario scenario : object.getScenarios())
			scenario.afterStep(object);
		
		object.afterStep();
		return hasFinished;
	}

	/**
	 * Performs the end of a step
	 */
	private void performStepFinish(SimulationObject object)
	{
		for (Scenario scenario : object.getScenarios())
			scenario.beforeFinish(object);

		object.finish();

		for (Scenario scenario : object.getScenarios())
			scenario.afterFinish(object);

		object.afterFinish();
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
			performStep();
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