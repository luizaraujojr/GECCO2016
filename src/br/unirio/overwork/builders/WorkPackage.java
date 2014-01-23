package br.unirio.overwork.builders;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Class that represents a work package within a project
 * 
 * @author Marcio Barros
 */
public class WorkPackage
{
	/**
	 * Work package's name
	 */
	private @Getter String name;
	
	/**
	 * Data functions comprising the work package
	 */
	private List<DataFunction> dataFunctions;

	/**
	 * Transactional functions comprising the work package
	 */
	private List<TransactionalFunction> transactionalFunctions;

	/**
	 * Initializes the work package
	 */
	public WorkPackage(String name)
	{
		this.name = name;
		this.dataFunctions = new ArrayList<DataFunction>();
		this.transactionalFunctions = new ArrayList<TransactionalFunction>();
	}

	/**
	 * Adds a data function to the work package
	 */
	public WorkPackage addDataFunction(String name, int functionPoints)
	{
		this.dataFunctions.add(new DataFunction(name, functionPoints));
		return this;
	}
	
	/**
	 * Adds a transactional function to the work package
	 */
	public WorkPackage addTransactionalFunction(String name, int functionPoints)
	{
		this.transactionalFunctions.add(new TransactionalFunction(name, functionPoints));
		return this;
	}
	
	/**
	 * Calculates the number of function points in the work package
	 */
	public int calculateFunctionPoints()
	{
		int soma = 0;
		
		for (DataFunction fd : dataFunctions)
			soma += fd.getFunctionPoints();
		
		for (TransactionalFunction ft : transactionalFunctions)
			soma += ft.getFunctionPoints();

		return soma;
	}
}

/**
 * Class that represents a data function within a work package
 * 
 * @author Marcio Barros
 */
class DataFunction
{
	private @Getter String name;
	private @Getter int functionPoints;
	
	public DataFunction(String name, int functionPoints)
	{
		this.name = name;
		this.functionPoints = functionPoints;
	}
}

/**
 * Class that represents a transactional function within a work package
 * 
 * @author Marcio Barros
 */
class TransactionalFunction
{
	private @Getter String name;
	private @Getter int functionPoints;
	
	public TransactionalFunction(String name, int functionPoints)
	{
		this.name = name;
		this.functionPoints = functionPoints;
	}
}