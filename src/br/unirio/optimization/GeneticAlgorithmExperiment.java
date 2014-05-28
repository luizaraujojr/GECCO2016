package br.unirio.optimization;

import jmetal.base.Algorithm;
import jmetal.base.Operator;
import jmetal.base.Solution;
import jmetal.base.SolutionSet;
import jmetal.base.operator.crossover.SinglePointCrossover;
import jmetal.base.operator.mutation.BitFlipMutation;
import jmetal.base.operator.selection.BinaryTournament;
import jmetal.metaheuristics.singleObjective.geneticAlgorithm.gGA;
import br.unirio.overwork.model.base.Project;

/**
 * Class that contains the genetic algorithm 
 * 
 * @author Luiz Araujo Jr
 */
public class GeneticAlgorithmExperiment extends GenericExperiment{
	
	@Override
	protected Solution runCycle(Project instance, int instanceNumber) throws Exception
	{
		ProjectProblem problem = createProblem(instance);
		int variableSize = instance.countActivities();

		int populationSize = 3 * variableSize;
		int maxEvaluations = 100 ;//* populationSize;

		Operator crossover = new SinglePointCrossover();
		crossover.setParameter("probability", 0.8);

		Operator mutation = new BitFlipMutation();
		mutation.setParameter("probability", 0.02);

		Operator selection = new BinaryTournament();
				
		Algorithm ga = new gGA(problem);		
		ga.setInputParameter("populationSize", populationSize);
		ga.setInputParameter("maxEvaluations", maxEvaluations);
		ga.addOperator("crossover", crossover);
		ga.addOperator("mutation", mutation);
		ga.addOperator("selection", selection);
		
		SolutionSet solutions = ga.execute();
		return solutions.get(0);
	}
}