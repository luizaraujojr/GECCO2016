package br.unirio.support;

import java.util.ArrayList;
import java.util.List;

public abstract class OrdenadorTopologico<T>
{
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
				List<T> dependees = getDependencies(item);
				boolean dependeesResolved = true;
				
				for (int j = 0; dependeesResolved && j < dependees.size(); j++)
				{
					T dependee = dependees.get(j);					

					if (!target.contains(dependee))
						dependeesResolved = false;
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
	
	protected abstract List<T> getDependencies(T item);
}