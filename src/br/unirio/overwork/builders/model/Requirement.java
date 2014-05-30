package br.unirio.overwork.builders.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Class that represents a requirement in a work package 
 * 
 * @author Marcio Barros
 */
public class Requirement
{
	private @Getter String name;
	
	private @Getter int functionPoints;
	
	private List<Requirement> dependences;

	public Requirement(String name, int functionPoints)
	{
		this.name = name;
		this.functionPoints = functionPoints;
		this.dependences = new ArrayList<Requirement>();
	}
	
	public void addDependence(Requirement r)
	{
		this.dependences.add(r);
	}
	
	public Iterable<Requirement> getDependencies()
	{
		return dependences;
	}
}