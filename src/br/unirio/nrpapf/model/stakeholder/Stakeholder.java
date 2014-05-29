package br.unirio.nrpapf.model.stakeholder;

import lombok.Getter;
import lombok.Setter;

public class Stakeholder
{
	private @Getter @Setter String name;
	
	private @Getter @Setter int weight;
	
	public Stakeholder(String name, int weight)
	{
		this.name = name;
		this.weight = weight;
	}
}