package br.unirio.optimization.experiment;

import jmetal.base.Algorithm;
import jmetal.base.Operator;
import jmetal.base.operator.crossover.UniformCrossover;
import jmetal.base.operator.mutation.IntUniformMutation;
import jmetal.base.operator.selection.BinaryTournament;
import jmetal.metaheuristics.nsgaII.NSGAII;
import lombok.Setter;
import unirio.experiments.multiobjective.controller.Experiment;
import br.unirio.optimization.ProjectProblem;
import br.unirio.overwork.model.base.Project;

public class NSGAIIExperiment extends Experiment<Project>
{
	public @Setter int maxEvaluations;
	
	@Override
	protected Algorithm createAlgorithm(Project instance) throws Exception
	{
		ProjectProblem problem = new ProjectProblem(instance);
		problem.setMaxEvaluations(maxEvaluations);

		Operator crossover = new UniformCrossover();
		crossover.setParameter("probability", 1.0);

		Operator mutation = new IntUniformMutation();
		mutation.setParameter("probability", 1.0 / problem.getNumberOfVariables());

		Operator selection = new BinaryTournament();

		int population = instance.getActivitiesCount();
		//int evaluations = 200;// * instance.getActivitiesCount() * population;
		
		NSGAII algorithm = new NSGAII(problem);
		algorithm.setInputParameter("populationSize", population);
		algorithm.setInputParameter("maxEvaluations", problem.getMaxEvaluations());
		
		algorithm.addOperator("crossover", crossover);
		algorithm.addOperator("mutation", mutation);
		algorithm.addOperator("selection", selection);
		return algorithm;
	}
}