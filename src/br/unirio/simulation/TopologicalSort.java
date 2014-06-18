package br.unirio.simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that returns a topological partial order of a set of dependent objects
 * 
 * @author Marcio Barros
 */
public abstract class TopologicalSort<T>
{
	/**
	 * Performs the topological sort
	 */
	public List<T> sort(List<T> items) throws Exception
	{
		List<T> source = new ArrayList<T>();
		
		for (T item : items)
			source.add(item);
		
		List<T> target = new ArrayList<T>();

		while (source.size() > 0)
		{
			boolean changed = false;
			
			for (int i = source.size()-1; i >= 0; i--)
			{
				T item = source.get(i);
				List<T> dependees = getDependencies(items, item);
				boolean dependeesResolved = true;
				
				if (dependees != null)
				{
					for (int j = 0; dependeesResolved && j < dependees.size(); j++)
					{
						T dependee = dependees.get(j);					
	
						if (!target.contains(dependee))
							dependeesResolved = false;
					}
				}
				
				if (dependeesResolved)
				{
					source.remove(i);
					target.add(item);
					changed = true;
				}
			}
			
			if (!changed)
				throw new Exception("At least one circular dependency was detected on the list.");
		}		
		
		return target;
	}
	
	/**
	 * Abstract method to return the dependencies of a given object
	 */
	protected abstract List<T> getDependencies(List<T> items, T item);
}