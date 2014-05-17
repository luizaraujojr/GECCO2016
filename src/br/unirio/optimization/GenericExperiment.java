package br.unirio.optimization;

import jmetal.base.Solution;
import unirio.experiments.monoobjective.execution.MonoExperiment;
import br.unirio.overwork.model.base.Project;


public abstract class GenericExperiment  extends MonoExperiment<Project>{

	protected ProjectProblem problem;

	protected double[] getSolutionData(Solution solution)
	{
		double[] data = new double[1];
		data[0] = solution.getObjective(0);
//		data[1] = solution.getObjective(1);
//		data[2] = solution.getObjective(2);
		return data;
	}
	
	protected ProjectProblem createProblem(Project instance) throws ClassNotFoundException
	{
		problem = new ProjectProblem(instance);
		return problem;
	}
}