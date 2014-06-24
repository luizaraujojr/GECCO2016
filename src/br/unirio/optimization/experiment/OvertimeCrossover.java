package br.unirio.optimization.experiment;

import jmetal.base.Solution;
import jmetal.base.operator.crossover.Crossover;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;

public class OvertimeCrossover extends Crossover
{
	public Object execute(Object object) throws JMException
	{
		Solution[] parents = (Solution[]) object;
		Solution parent1 = parents[0];
		Solution parent2 = parents[1];

		if (parents.length < 2)
		{
			Configuration.logger_.severe("OvertimeCrossover.execute: operator needs two parents");
			throw new JMException("Exception in OvertimeCrossover.execute()");
		}

		Double probability = (Double) getParameter("probability");
		
		if (probability == null)
		{
			Configuration.logger_.severe("OvertimeCrossover.execute: operator needs probability parameter");
			throw new JMException("Exception in OvertimeCrossover.execute()");
		}
			
		Solution[] offSpring = new Solution[2];
		offSpring[0] = new Solution(parent1);
		offSpring[1] = new Solution(parent2);

		if (PseudoRandom.randDouble() < probability)
		{
			double p = PseudoRandom.randDouble();
			int crossoverPoint = PseudoRandom.randInt(0, parent1.numberOfVariables() - 1);
	
			for (int i = crossoverPoint; i < parent1.numberOfVariables(); i++)
			{
				int parentValue1 = (int) parent1.getDecisionVariables()[i].getValue();
				int parentValue2 = (int) parent2.getDecisionVariables()[i].getValue();
	
				int offspringValue1 = (p >= 0.5) ? Math.max(parentValue1, parentValue2) : Math.min(parentValue1, parentValue2);
				int offspringValue2 = (parentValue1 + parentValue2) / 2;
				
				offSpring[0].getDecisionVariables()[i].setValue(offspringValue1);
				offSpring[1].getDecisionVariables()[i].setValue(offspringValue2);
			}
		}

		for (int i = 0; i < offSpring.length; i++)
		{
			offSpring[i].setCrowdingDistance(0.0);
			offSpring[i].setRank(0);
		}
		
		return offSpring;
	}
}