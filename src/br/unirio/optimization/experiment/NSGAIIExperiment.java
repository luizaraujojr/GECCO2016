package br.unirio.optimization.experiment;

import java.io.Writer;
import java.text.DecimalFormat;
import java.util.List;

import br.unirio.optimization.ProjectProblem;
import br.unirio.overwork.model.base.Project;
import jmetal.base.Algorithm;
import jmetal.base.Operator;
import jmetal.base.Solution;
import jmetal.base.operator.crossover.UniformCrossover;
import jmetal.base.operator.mutation.IntUniformMutation;
import jmetal.base.operator.selection.BinaryTournament;
import jmetal.metaheuristics.nsgaII.NSGAII;
import unirio.experiments.multiobjective.controller.Experiment;

public class NSGAIIExperiment extends Experiment<Project>
{
	@Override
	protected Algorithm createAlgorithm(Project instance) throws Exception
	{
		ProjectProblem problem = new ProjectProblem(instance);

		Operator crossover = new UniformCrossover();
		crossover.setParameter("probability", 1.0);

		Operator mutation = new IntUniformMutation();
		mutation.setParameter("probability", 1.0 / problem.getNumberOfVariables());

		Operator selection = new BinaryTournament();

		int population = 10;// * instance.getActivitiesCount();
		//int evaluations = 200;// * instance.getActivitiesCount() * population;
		
		NSGAII algorithm = new NSGAII(problem);
		algorithm.setInputParameter("populationSize", population);
		algorithm.setInputParameter("maxEvaluations", problem.getMaxEvaluations());
		
		algorithm.addOperator("crossover", crossover);
		algorithm.addOperator("mutation", mutation);
		algorithm.addOperator("selection", selection);
		return algorithm;
	}
	
	public void printParetoFrontier(Writer bw, Project instance, List<Solution> uniques) throws Exception
	{
		DecimalFormat dc = new DecimalFormat("0.####");
	
		for (int j = 0; j < uniques.size(); j++)
		{
			Solution solution = uniques.get(j);
			print(bw, dc.format (solution.getObjective(0)));
			
			for (int k = 1; k < 3; k++)
				print(bw, "; " + dc.format(solution.getObjective(k)));
					
			print(bw, "; " + dc.format(solution.getLocation()));
			println(bw, "");
		}
	}
}