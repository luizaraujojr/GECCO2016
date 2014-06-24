package br.unirio.optimization.experiment;

import jmetal.base.Algorithm;
import jmetal.base.Operator;
import jmetal.base.operator.mutation.IntUniformMutation;
import jmetal.base.operator.selection.BinaryTournament;
import jmetal.metaheuristics.nsgaII.NSGAII;
import unirio.experiments.multiobjective.controller.Experiment;
import br.unirio.optimization.ProjectProblem;
import br.unirio.overwork.model.base.Project;

public class NSGAIIExperiment extends Experiment<Project>
{
	private int maxEvaluations;
	
	public NSGAIIExperiment(int maxEvaluations) 
	{
		this.maxEvaluations = maxEvaluations;
	}

	@Override
	protected Algorithm createAlgorithm(Project instance) throws Exception
	{
		ProjectProblem problem = new ProjectProblem(instance);

		Operator crossover = new OvertimeCrossover();
		crossover.setParameter("probability", 0.5);

		Operator mutation = new IntUniformMutation();
		mutation.setParameter("probability", 0.3);
		//mutation.setParameter("probability", 1.0 / problem.getNumberOfVariables());

		Operator selection = new BinaryTournament();

		int population = instance.getActivitiesCount();
		
		NSGAII algorithm = new NSGAII(problem);
		algorithm.setInputParameter("populationSize", population);
		algorithm.setInputParameter("maxEvaluations", maxEvaluations);
		
		algorithm.addOperator("crossover", crossover);
		algorithm.addOperator("mutation", mutation);
		algorithm.addOperator("selection", selection);
		return algorithm;
	}
}