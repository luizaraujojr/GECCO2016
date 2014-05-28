package br.unirio.optimization;

import jmetal.base.Algorithm;
import jmetal.base.Operator;
import jmetal.base.SolutionSet;
import jmetal.base.operator.crossover.SinglePointCrossover;
import jmetal.base.operator.crossover.UniformCrossover;
import jmetal.base.operator.mutation.BitFlipMutation;
import jmetal.base.operator.mutation.IntUniformMutation;
import jmetal.base.operator.selection.BinaryTournament;
import jmetal.metaheuristics.nsgaII.NSGAII;
import jmetal.metaheuristics.singleObjective.geneticAlgorithm.gGA;
import br.unirio.overwork.model.base.Project;

/**
 * Class that contains the genetic algorithm 
 * 
 * @author Luiz Araujo Jr
 */
public class HSGAIIMultiExperiment extends GenericExperiment{
	
	@Override
	public SolutionSet runCycle(Project instance, int instanceNumber, int cycles) throws Exception
	{
		ProjectProblem problem = new ProjectProblem(instance);
		int variableSize = instance.getActivitiesCount();

		int populationSize = 10;//3 * variableSize;
		int maxEvaluations = 200;// * populationSize;

//		Operator crossover = new SinglePointCrossover();
//		crossover.setParameter("probability", 0.8);
//
//		Operator mutation = new BitFlipMutation();
//		mutation.setParameter("probability", 0.02);
		
		
		Operator crossover = new UniformCrossover();
		crossover.setParameter("probability", 1.0);

		Operator mutation = new IntUniformMutation();
		mutation.setParameter("probability", 1.0 / problem.getNumberOfVariables());

		Operator selection = new BinaryTournament();
				
		Algorithm ga = new NSGAII(problem);		
		ga.setInputParameter("populationSize", populationSize);
		ga.setInputParameter("maxEvaluations", maxEvaluations);
		ga.addOperator("crossover", crossover);
		ga.addOperator("mutation", mutation);
		ga.addOperator("selection", selection);
		
		SolutionSet solutions = ga.execute();
		return solutions;
	}
}