package br.unirio.optimization;

import jmetal.base.Solution;
import unirio.experiments.monoobjective.execution.MonoExperiment;
import unirio.experiments.monoobjective.execution.MonoExperimentListener;
import br.unirio.overwork.model.base.Project;

/**
 * Class that contains an abstration of the generic experiment 
 * 
 * @author Luiz Araujo Jr
 */
public abstract class GenericExperiment  extends MonoExperiment<Project>{

	protected ProjectProblem problem;

	protected double[] getSolutionData(Solution solution)
	{
		double[] data = new double[3];
		data[0] = solution.getObjective(0);
		data[1] = solution.getObjective(1);
		data[2] = solution.getObjective(2);
		return data;
	}
	
	protected ProjectProblem createProblem(Project instance) throws ClassNotFoundException
	{
		problem = new ProjectProblem(instance);
		return problem;
	}
}