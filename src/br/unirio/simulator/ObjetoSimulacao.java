package br.unirio.simulator;

import java.lang.reflect.Field;
import java.util.List;

public abstract class ObjetoSimulacao
{
	protected static final double DT = 0.1;
	
	public abstract void init();
	
	public abstract List<ObjetoSimulacao> getDependencies();
	
	public abstract void step();
	
	public abstract void update();
	
	protected double min(double...args)
	{
		if (args.length == 0)
			return 0.0;
		
		double minimo = args[0];
		
		for (int i = 1; i < args.length; i++)
			if (args[i] < minimo)
				minimo = args[i];
		
		return minimo;
	}
	
	protected double max(double...args)
	{
		if (args.length == 0)
			return 0.0;
		
		double maximo = args[0];
		
		for (int i = 1; i < args.length; i++)
			if (args[i] < maximo)
				maximo = args[i];
		
		return maximo;
	}
	
	protected double or(double...args)
	{
		if (args.length == 0)
			return 0.0;
		
		for (int i = 0; i < args.length; i++)
			if (args[i] > 0.001)
				return 1.0;
		
		return 0.0;
	}
	
	protected double and(double...args)
	{
		if (args.length == 0)
			return 0.0;
		
		for (int i = 0; i < args.length; i++)
			if (args[i] < 0.001)
				return 0.0;
		
		return 1.0;
	}
	
	protected double not(double arg)
	{
		return (arg < 0.001) ? 1.0 : 0.0;
	}
	
	protected double equal(double opEsquerda, double opDireita)
	{
		return (opEsquerda == opDireita) ? 1.0 : 0.0;
	}
	
	protected double diff(double opEsquerda, double opDireita)
	{
		return (opEsquerda != opDireita) ? 1.0 : 0.0;
	}
	
	protected double less(double opEsquerda, double opDireita)
	{
		return (opEsquerda < opDireita) ? 1.0 : 0.0;
	}
	
	protected double lessEqual(double opEsquerda, double opDireita)
	{
		return (opEsquerda <= opDireita) ? 1.0 : 0.0;
	}
	
	protected double greater(double opEsquerda, double opDireita)
	{
		return (opEsquerda > opDireita) ? 1.0 : 0.0;
	}
	
	protected double greaterEqual(double opEsquerda, double opDireita)
	{
		return (opEsquerda >= opDireita) ? 1.0 : 0.0;
	}
	
	protected double groupmin(List<? extends ObjetoSimulacao> references, String id)
	{
		if (references.size() == 0)
			return 0.0;
		
		double minimo = getPropertyValue(references.get(0), id);
		
		for (int i = 1; i < references.size(); i++)
		{
			double value = getPropertyValue(references.get(i), id);

			if (value < minimo)
				minimo = value;
		}
		
		return minimo;
	}
	
	protected double groupmax(List<? extends ObjetoSimulacao> references, String id)
	{
		if (references.size() == 0)
			return 0.0;
		
		double maximo = getPropertyValue(references.get(0), id);
		
		for (int i = 1; i < references.size(); i++)
		{
			double value = getPropertyValue(references.get(i), id);

			if (value > maximo)
				maximo = value;
		}
		
		return maximo;
	}
	
	protected double groupsum(List<? extends ObjetoSimulacao> references, String id)
	{
		if (references.size() == 0)
			return 0.0;
		
		double soma = 0.0;
		
		for (int i = 0; i < references.size(); i++)
			soma += getPropertyValue(references.get(i), id);
		
		return soma;
	}

	private double getPropertyValue(ObjetoSimulacao objeto, String id)
	{
		try
		{
			Field t = objeto.getClass().getDeclaredField(id);
			return t.getDouble(objeto);
		} 
		catch (Exception e)
		{
			System.out.println("Erro ao acessar a propriedade " + id + " da classe " + objeto.getClass().getName());
			return 0.0;
		}
	}
}