package br.unirio.optimization;

import br.unirio.overwork.model.base.Project;
import jmetal.base.Problem;
import jmetal.base.Solution;
import unirio.experiments.monoobjective.execution.MonoExperiment;


public abstract class GenericExperiment  extends MonoExperiment<Project>{

	protected ProjectProblem problem;

	protected double[] getSolutionData(Solution solution)
	{
		double[] data = new double[3];
		data[0] = solution.getObjective(0);
//		data[1] = problem.calcCoverage(solution);
//		data[2] = problem.calcExecutionTime(solution);
		return data;
	}
	
	protected ProjectProblem createProblem(Project instance) throws ClassNotFoundException
	{
		problem = new ProjectProblem(instance);
		return problem;
	}
}